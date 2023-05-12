package com.siman.credisiman.visa.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.estadocuenta.ListadoEstadoCuenta;
import com.siman.credisiman.visa.utils.ConnectionHandler;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListadoEstadosCuentas {
    //private static Logger log = LoggerFactory.getLogger(ListadoEstadosCuentas.class);
    private static final String namespace = "http://siman.com/ListadoEstadosCuenta";
    private static final String operationResponse = "ObtenerListadoEstadosCuentaResponse";

    public static Connection conexion;
    public static PreparedStatement sentencia = null;
    public static ResultSet rs;

    public static XmlObject obtenerListadoEstadosCuenta(String pais, String numeroTarjeta, String remoteJndiSunnel,
                                                        String remoteJndiOrion, String siscardUrl, String siscardUser,
                                                        String binCredisiman, String tipoTarjeta,
                                                        String identificacion, String esquemaSunnel,
                                                        String esquemaOrion, String esquemaEstcta, String webService) {

        System.out.println("SERVICIO DE ESTADO DE CUENTA DPRO");

        System.out.println("metodo obtenerListadoEstadosCuenta");

        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();
        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            System.out.println("pais required");
            return message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(numeroTarjeta) || utils.validateNotEmpty(numeroTarjeta)) {
            System.out.println("numero tarjeta required");
            return message.genericMessage("ERROR", "400", "El campo número tarjeta es obligatorio", namespace, operationResponse);
        }
        /*if (utils.validateNotNull(identificacion) || utils.validateNotEmpty(identificacion)) {
            return message.genericMessage("ERROR", "400", "El campo identificación es obligatorio", namespace, operationResponse);
        }*/
        if (utils.validateNotNull(tipoTarjeta) || utils.validateNotEmpty(tipoTarjeta)) {
            System.out.println("tipo tarjeta required");
            return message.genericMessage("ERROR", "400", "El campo tipo tarjeta es obligatorio", namespace, operationResponse);
        }

        //validar longitudes
        if (!utils.validateLongitude(pais, 3)) {
            System.out.println("La longitud del campo pais debe ser menor o igual a 3");
            return message.genericMessage("ERROR", "400", "La longitud del campo pais debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(numeroTarjeta, 16)) {
            System.out.println("La longitud del campo número tarjeta debe ser menor o igual a 16");
            return message.genericMessage("ERROR", "400",
                    "La longitud del campo número tarjeta debe ser menor o igual a 16", namespace, operationResponse);
        }


        //OBTENER DATOS
        try {
            switch (tipoTarjeta) {
                case "P":
                    //datos tarjeta privada
                    //List<ListadoEstadoCuenta> response1 = obtenerDatosTarjetaPrivada(numeroTarjeta, remoteJndiOrion, identificacion, pais, esquemaEstcta);
                    // response1_size = response1.size();

                    List<ListadoEstadoCuenta> response3 = obtenerDatosTarjetaPrivadaWebService(numeroTarjeta, webService);

                    /*if (response1.size() > 0 && response3.size() > 0) {
                        response1.addAll(response3);
                        System.out.println("Privada y Dpro");
                        return estructura(response1, "PRIVADA");
                    }

                    if (response1.size() > 0) {
                        System.out.println("Privada");
                        return estructura(response1, "PRIVADA");
                    }*/

                    if (response3.size() > 0) {
                        System.out.println("Dpro");
                        return estructura(response3, "PRIVADA");
                    }

                    //log.info(message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse).toString());
                    return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
                case "V":
                    //datos tarjeta visa
                    List<ListadoEstadoCuenta> response2 = obtenerDatosTarjetaVisa(numeroTarjeta, remoteJndiOrion, identificacion, pais, esquemaOrion);

                    if (response2.size() > 0) {
                        return estructura(response2, "VISA");
                    } else {
                        //log.info(message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse).toString());
                        return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
                    }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema... - " + ex.getMessage(), namespace, operationResponse);
        }
        System.out.println("La consulta no devolvio resultados...");
        return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados...", namespace, operationResponse);
    }


    public static List<ListadoEstadoCuenta> obtenerDatosTarjetaPrivada(String numeroTarjeta, String remoteJndiOrion,
                                                                       String identificacion, String pais, String esquemaEstcta) throws SQLException {
        System.out.println("metodo obtenerDatosTarjetaPrivada");
        String tarjeta = null;
        if (numeroTarjeta != null) {
            if (numeroTarjeta.length() > 6) {
                tarjeta = Utils.obtenerTarjeta(numeroTarjeta, 6);
            } else {
                tarjeta = numeroTarjeta;
            }
        }

        //(VISA LISTADO ESTADOS CUENTA) ESQUEMA DIFERENTE POR PAIS

        String query = "SELECT SUBSTR(iefp.date_file, 1, 4) AS anioEstadoCuenta, " +
                "SUBSTR(iefp.date_file, 5, 2) AS mesEstadoCuenta, " +
                "iefp.date_file AS fechaCorte " +
                "FROM " + esquemaEstcta + ".t_isim_estcta_files_pdf iefp " +
                "WHERE iefp.idcard = ? AND iefp.customerid = ? ORDER BY iefp.date_file DESC";

        System.out.println(remoteJndiOrion);
        System.out.println(esquemaEstcta);

        conexion = new ConnectionHandler().getConnection(remoteJndiOrion);
        sentencia = conexion.prepareStatement(query);

        String identificacionFormater = identificacion.replace("-", "").replace("_", "").replace(" ", "");
        sentencia.setString(1, tarjeta); // agregar parametros
        sentencia.setString(2, identificacionFormater);
        rs = sentencia.executeQuery();

        List<ListadoEstadoCuenta> listadoEstadoCuentaList = new ArrayList<>();
        int cont = 0;

        while (rs.next()) {
            ListadoEstadoCuenta listadoEstadoCuenta = new ListadoEstadoCuenta();
            listadoEstadoCuenta.setFechaCorte(rs.getString("fechaCorte"));
            listadoEstadoCuenta.setRuta(" ");
            listadoEstadoCuenta.setNombreArchivo(" ");
            listadoEstadoCuentaList.add(listadoEstadoCuenta);
            cont++;
            if (cont == 12) {
                break;
            }
        }
        conexion.close();
        System.out.println("response obtenerDatosTarjetaPrivada: " + listadoEstadoCuentaList);
        return listadoEstadoCuentaList;
    }

    public static List<ListadoEstadoCuenta> obtenerDatosTarjetaPrivadaWebService(String numeroTarjeta, String webService) throws Exception {
        System.out.println("metodo obtenerDatosTarjetaPrivadaWebService");
        System.out.println(numeroTarjeta);
        System.out.println(webService);

        List<ListadoEstadoCuenta> listadoEstadoCuentaList = new ArrayList<>();

        JSONObject jsonSend = new JSONObject(); //json a enviar
        jsonSend.put("cardid", numeroTarjeta);

        System.out.println("json send: " + jsonSend);

        HttpResponse<String> jsonResponse
                = Unirest.post(webService)
                .header("Content-Type", "application/json")
                .body(jsonSend.toString())
                .asString();
        System.out.println("http response" + jsonResponse.getBody());

        JSONArray jsonArray = new JSONArray(jsonResponse.getBody());
        System.out.println("json array body: " + jsonArray);

        if (jsonArray.length() == 0) {
            System.out.println("response obtenerDatosTarjetaPrivadaWebService: " + listadoEstadoCuentaList);
            return listadoEstadoCuentaList;
        } else {
            for (int j = 0; j < 12; j++) {
                ListadoEstadoCuenta listadoEstadoCuenta = new ListadoEstadoCuenta();
                JSONObject jsonObject = jsonArray.getJSONObject(j);

                SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
                Date date = parser.parse(jsonObject.getString("visfechafmt"));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                String formattedDate = formatter.format(date);

                listadoEstadoCuenta.setFechaCorte(formattedDate);
                listadoEstadoCuenta.setRuta(jsonObject.getString("visRuta"));
                listadoEstadoCuenta.setNombreArchivo(jsonObject.getString("visNombrearchivo"));
                listadoEstadoCuentaList.add(listadoEstadoCuenta);
            }
            System.out.println("response obtenerDatosTarjetaPrivadaWebService: " + listadoEstadoCuentaList);
            return listadoEstadoCuentaList;
        }
    }

    public static List<ListadoEstadoCuenta> obtenerDatosTarjetaVisa(String numeroTarjeta, String remoteJndiOrion,
                                                                    String identificacion, String pais, String esquemaOrion) throws SQLException {


        //obtener ultimos registros
        String tarjeta = null;
        if (numeroTarjeta != null) {
            if (numeroTarjeta.length() > 6) {
                tarjeta = Utils.obtenerTarjeta(numeroTarjeta, 6);
            } else {
                tarjeta = numeroTarjeta;
            }
        }

        //(VISA LISTADO ESTADOS CUENTA) ESQUEMA DIFERENTE POR PAIS

        String query = "SELECT EXTRACT(YEAR FROM eco.fecha_corte) AS anioEstadoCuenta, " +
                "eco.mes AS mesEstadoCuenta," +
                "TO_CHAR(eco.fecha_corte,'YYYYMMDD') AS fechaCorte " +
                "FROM " + esquemaOrion + ".als_estados_cuenta_orion eco " +
                "WHERE eco.digitos_tarjeta = ? AND eco.id_cliente = ? ORDER BY eco.fecha_corte DESC";


        System.out.println(remoteJndiOrion);
        System.out.println(esquemaOrion);

        conexion = new ConnectionHandler().getConnection(remoteJndiOrion);
        sentencia = conexion.prepareStatement(query);

        String identificacionFormater = identificacion.replace("-", "").replace("_", "").replace(" ", "");
        sentencia.setString(1, tarjeta); // agregar parametros
        sentencia.setString(2, identificacionFormater);
        rs = sentencia.executeQuery();
        List<ListadoEstadoCuenta> listadoEstadoCuentaList = new ArrayList<>();
        int cont = 0;

        while (rs.next()) {
            ListadoEstadoCuenta listadoEstadoCuenta = new ListadoEstadoCuenta();
            listadoEstadoCuenta.setFechaCorte(rs.getString("fechaCorte"));
            listadoEstadoCuenta.setNombreArchivo(" ");
            listadoEstadoCuenta.setRuta(" ");
            listadoEstadoCuentaList.add(listadoEstadoCuenta);
            cont++;
            if (cont == 12) {
                break;
            }
        }
        conexion.close();
        System.out.println("response obtenerDatosTarjetaVisa: " + listadoEstadoCuentaList);
        return listadoEstadoCuentaList;
    }

    public static XmlObject estructura(List<ListadoEstadoCuenta> response1, String origen) {
        System.out.println("metodo estructura");

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");

        for (ListadoEstadoCuenta fechaCorte : response1) {
            cursor.beginElement(new QName(namespace, "fechasCorte"));
            cursor.insertElementWithText(new QName(namespace, "fechaCorte"), fechaCorte.getFechaCorte());
            cursor.insertElementWithText(new QName(namespace, "ruta"), fechaCorte.getRuta());
            cursor.insertElementWithText(new QName(namespace, "nombreArchivo"), fechaCorte.getNombreArchivo());
            cursor.toParent();
        }
        cursor.insertElementWithText(new QName(namespace, "origen"), origen);
        cursor.toParent();
        System.out.println("ObtenerListadoEstadosCuenta response = [" + result + "]");
        return result;
    }
}