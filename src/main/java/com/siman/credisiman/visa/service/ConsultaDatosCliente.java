package com.siman.credisiman.visa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.datoscliente.ConsultaDatosClienteResponse;
import com.siman.credisiman.visa.utils.ConnectionHandler;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaDatosCliente {
    //private static Logger log = LoggerFactory.getLogger(ConsultaDatosCliente.class);
    private static final String namespace = "http://siman.com/ConsultaDatosCliente";
    private static final String operationResponse = "ObtenerDatosClienteResponse";

    public static Connection conexion;
    public static PreparedStatement sentencia = null;
    public static ResultSet rs;

    public static XmlObject obtenerDatosCliente(String pais, String identificacion, String remoteJndiSunnel,
                                                String remoteJndiOrion, String siscardUrl, String siscardUser,
                                                String binCredisiman,
                                                String esquemaSunnel, String esquemaOrion, String esquemaEstcta) {
        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();

        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            //log.info("pais required");
            return message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(identificacion) || utils.validateNotEmpty(identificacion)) {
            //log.info("identificacion required");
            return message.genericMessage("ERROR", "400", "El campo identificación es obligatorio", namespace, operationResponse);
        }

        //validar longitudes
        if (!utils.validateLongitude(pais, 3)) {
            //log.info("pais, size overload");
            return message.genericMessage("ERROR", "400", "La longitud del campo pais debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(identificacion, 19)) {
            //log.info("identificacion, size overload");
            return message.genericMessage("ERROR", "400", "La longitud del campo identificacion debe ser menor o igual a 19", namespace, operationResponse);
        }

        ConsultaDatosClienteResponse response2;
        ConsultaDatosClienteResponse response3;
        try {
            //DATOS TARJETA PRIVADA
            response2 = tarjetaPrivada(identificacion, remoteJndiSunnel, pais, esquemaSunnel);
            //log.info(String.valueOf(response2));
            //DATOS TARJETA CREDISIMAN
            response3 = tarjetaCredisiman(pais, siscardUser, identificacion, siscardUrl);
            //log.info(String.valueOf(response3));

            // RESPUESTA ALL
            if (response2 != null && response3 != null) {
                //log.info("all");
                response3.setCorreoElectronico(response2.getCorreoElectronico());
                return estructura(response3);
            }

            // RESPUESTA PRIVADA
            if (response2 != null) {
                //log.info("privada");
                return estructura(response2);
            }

            // RESPUESTA VISA
            if (response3 != null) {
                //log.info("visa");
                return estructura(response3);
            }


            //log.info(message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse).toString());
            return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);

        } catch (SQLException e) {
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse);
        } catch (NullPointerException nul) {
            //log.info(message.genericMessage("ERROR", "400", "La consulta no devolvio resultados...", namespace, operationResponse).toString());
            return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados...", namespace, operationResponse);
        } catch (Exception ex) {
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }

    }


    //ESTRUCTURA DE SALIDA
    public static XmlObject estructura(ConsultaDatosClienteResponse response) {
        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);

        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");

        cursor.insertElementWithText(new QName(namespace, "primerNombre"), response.getNombre());
        cursor.insertElementWithText(new QName(namespace, "segundoNombre"), response.getSegundoNombre());
        cursor.insertElementWithText(new QName(namespace, "primerApellido"), response.getPrimerApellido());
        cursor.insertElementWithText(new QName(namespace, "segundoApellido"), response.getSegundoApellido());
        cursor.insertElementWithText(new QName(namespace, "apellidoCasada"), response.getApellidoCasada());
        cursor.insertElementWithText(new QName(namespace, "fechaNacimiento"), response.getNacimiento());
        cursor.insertElementWithText(new QName(namespace, "tipoIdentificacion"), response.getTipoIdentificacion());
        cursor.insertElementWithText(new QName(namespace, "identificacion"), response.getIdentificacion());
        cursor.insertElementWithText(new QName(namespace, "correo"), response.getCorreoElectronico());
        cursor.insertElementWithText(new QName(namespace, "celular"), response.getTelefonoCelular());
        cursor.insertElementWithText(new QName(namespace, "lugarTrabajo"), response.getNombrePatrono());
        cursor.insertElementWithText(new QName(namespace, "direccion"), response.getDireccion());
        cursor.insertElementWithText(new QName(namespace, "direccionPatrono"), response.getDireccionPatrono());
        cursor.insertElementWithText(new QName(namespace, "correoSiscard"), response.getCorreoSiscard());

        cursor.toParent();
        //log.info(result.toString());
        return result;
    }

    //OBTENER DATOS TARJETA CREDISIMAN
    public static ConsultaDatosClienteResponse tarjetaCredisiman(String pais, String siscardUser, String identificacion, String siscardUrl) throws Exception {

        ConsultaDatosClienteResponse response2;

        //crear identificaci?n con formato sin(-,_ y espacios en blanco )
        String identificacionFormater = identificacion.replace("-", "").replace("_", "").replace(" ", "");

        JSONObject jsonSend = new JSONObject(); //json a enviar
        jsonSend.put("country", pais)
                .put("processIdentifier", "ConsultaDatosEnte")
                .put("tipoMensaje", 4300)
                .put("identificacion", identificacionFormater)
                .put("usuarioSiscard", siscardUser);

        HttpResponse<String> jsonResponse //realizar petición demiante unirest
                = Unirest
                .post(siscardUrl.concat("/consultaDatosEnte"))
                .header("Content-Type", "application/json")
                .body(jsonSend.toString())
                .asString();

        //capturar respuesta
        JSONObject response = new JSONObject(jsonResponse.getBody().replaceAll("u200B", ""));
        //log.info(String.valueOf(response));
        if (response.getString("code").equals("OSB-380000")) {
            //log.info("error code");
            return null;
        } else {
            if (response.getString("status").equals("ERROR")) {
                //log.info("error status");
                return null;
            }
            response2 = new ObjectMapper()
                    .readValue(response.toString(), ConsultaDatosClienteResponse.class);
            response2.setCorreoElectronico(" ");
            response2.setCorreoSiscard(response.getString("correoElectronico"));
            return response2;
        }
    }

    //OBTENER DATOS TARJETA PRIVADA
    public static ConsultaDatosClienteResponse tarjetaPrivada(String identifier, String remoteJndiSunnel, String pais, String esquemaSunnel) throws Exception {

        String query = "SELECT " +
                "    c.customerid AS niu," +
                "    substr(pc.firstname, 1, instr(pc.firstname, ' ') - 1) as primerNombre, " +
                "    substr(pc.firstname, instr(pc.firstname,' ') + 1)    as segundoNombre, " +
                "    pc.lastname1 AS primerApellido, " +
                "    pc.lastname2 AS segundoApellido, " +
                "    pc.marriedname AS apellidoCasada, " +
                "    TO_CHAR(pc.birthdate,'YYYYMMDD') AS fechaNacimiento, " +
                "    c.identificationtypeid AS tipoIdentificacion, " +
                "    c.identificationnumber AS numeroIdentificacion, " +
                "    NVL((" +
                "            SELECT LOWER(ea.email) " +
                "            FROM " + esquemaSunnel + ".t_gemailaddress ea " +
                "            WHERE ea.customerid = c.customerid AND ea.addressid = (SELECT MAX(eat.addressid) FROM " + esquemaSunnel + ".t_gemailaddress eat WHERE eat.customerid = ea.customerid AND eat.enabledind = 'T') " +
                "        ), ' ') AS correo, " +
                "    NVL((" +
                "            SELECT p.phonenumber " +
                "            FROM " + esquemaSunnel + ".t_gphone p " +
                "            WHERE p.customerid =  c.customerid AND p.phoneid = (SELECT MAX(pt.phoneid) FROM " + esquemaSunnel + ".t_gphone pt WHERE pt.customerid = p.customerid AND pt.phonetype = 'MOBILE' AND pt.enabledind = 'T' AND pt.phonenumber <> '.') " +
                "        ), ' ') AS celular, " +
                "    ' ' AS lugarTrabajo, " +
                "    NVL((" +
                "            SELECT ba.address || NVL(', '|| c.name, ' ') || NVL(', '|| s.name, ' ') " +
                "            FROM " + esquemaSunnel + ".t_gbuildingaddress ba " +
                "                     INNER JOIN " + esquemaSunnel + ".t_gcity c ON c.cityid = ba.cityid AND c.countryid = ba.countryid AND c.stateid = ba.stateid " +
                "                     INNER JOIN " + esquemaSunnel + ".t_gstate s ON s.stateid = ba.stateid AND s.countryid = ba.countryid AND ba.addressid = (SELECT MAX (addressid) FROM T_Gbuildingaddress bat WHERE bat.addresstype = 'HOME' AND bat.customerid = ba.customerid) " +
                "            WHERE ba.customerid = c.customerid " +
                "        ), ' ') AS direccion, " +
                "    NVL((" +
                "            SELECT ba.address || NVL(', '|| c.name, ' ') || NVL(', '|| s.name, ' ') " +
                "            FROM " + esquemaSunnel + ".t_gbuildingaddress ba " +
                "                     INNER JOIN " + esquemaSunnel + ".t_gcity c ON c.cityid = ba.cityid AND c.countryid = ba.countryid AND c.stateid = ba.stateid " +
                "                     INNER JOIN " + esquemaSunnel + ".t_gstate s ON s.stateid = ba.stateid AND s.countryid = ba.countryid AND ba.addressid = (SELECT MAX (addressid) FROM T_Gbuildingaddress bat WHERE bat.addresstype = 'BUSINESS' AND bat.customerid = ba.customerid) " +
                "            WHERE ba.customerid = c.customerid " +
                "        ), ' ') AS direccionTrabajo " +
                "FROM " +
                "    " + esquemaSunnel + ".t_gcustomer c " +
                "        INNER JOIN " + esquemaSunnel + ".t_gpersoncustomer pc ON pc.customerid = c.customerid " +
                "WHERE c.identificationnumber IN(?,?) ";

        //instancia de conexion
        conexion = new ConnectionHandler().getConnection(remoteJndiSunnel);
        sentencia = conexion.prepareStatement(query);

        //crear identificaci?n con formato sin(-,_ y espacios en blanco )
        String identificacionFormater = identifier.replace("-", "").replace("_", "").replace(" ", "");
        sentencia.setString(1, identifier);
        sentencia.setString(2, identificacionFormater);
        rs = sentencia.executeQuery();
        ConsultaDatosClienteResponse response1 = new ConsultaDatosClienteResponse();

        if (rs.next()) {
            //Display values
            response1.setNombre(rs.getString("primerNombre"));
            response1.setSegundoNombre(rs.getString("segundoNombre"));
            response1.setPrimerApellido(rs.getString("primerApellido"));
            response1.setSegundoApellido(rs.getString("segundoApellido"));
            response1.setApellidoCasada(rs.getString("apellidoCasada"));
            response1.setNacimiento(rs.getString("fechaNacimiento"));
            response1.setTipoIdentificacion(rs.getString("tipoIdentificacion"));
            response1.setIdentificacion(rs.getString("numeroIdentificacion"));
            response1.setCorreoElectronico(rs.getString("correo"));
            response1.setTelefonoCelular(rs.getString("celular"));
            response1.setDireccion(rs.getString("direccion"));
            response1.setNombrePatrono(rs.getString("direccionTrabajo"));
            response1.setDireccionPatrono(rs.getString("lugarTrabajo"));
            response1.setCorreoSiscard(" ");
            conexion.close();
            return response1;
        }
        conexion.close();
        return null;
    }
}
