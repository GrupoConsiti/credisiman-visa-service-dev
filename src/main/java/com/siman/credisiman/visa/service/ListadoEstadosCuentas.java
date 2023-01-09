package com.siman.credisiman.visa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.crm.FechaCorte;
import com.siman.credisiman.visa.dto.crm.LoginResponse;
import com.siman.credisiman.visa.dto.crm.ValidateToken;
import com.siman.credisiman.visa.utils.ConnectionHandler;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListadoEstadosCuentas {
    private static final Logger log = LoggerFactory.getLogger(ListadoEstadosCuentas.class);
    private static final String namespace = "http://siman.com/ListadoEstadosCuenta";
    private static final String operationResponse = "ObtenerListadoEstadosCuentaResponse";

    public static XmlObject obtenerListadoEstadosCuenta(String pais, String numeroTarjeta, String remoteJndiSunnel,
                                                        String remoteJndiOrion, String siscardUrl, String siscardUser,
                                                        String binCredisiman, String tipoTarjeta, String urlCrm,
                                                        String usuarioCrm, String passwordCrm,
                                                        String identificacion) {

        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();
        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            log.info("pais required");
            return message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(numeroTarjeta) || utils.validateNotEmpty(numeroTarjeta)) {
            log.info("numero tarjeta required");
            return message.genericMessage("ERROR", "400", "El campo número tarjeta es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(identificacion) || utils.validateNotEmpty(identificacion)) {
            return message.genericMessage("ERROR", "400", "El campo identificación es obligatorio", namespace, operationResponse);
        }

        //validar longitudes
        if (!utils.validateLongitude(pais, 3)) {
            log.info("pais, size overload");
            return message.genericMessage("ERROR", "400", "La longitud del campo pais debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(numeroTarjeta, 16)) {
            log.info("identificacion, size overload");
            return message.genericMessage("ERROR", "400",
                    "La longitud del campo número tarjeta debe ser menor o igual a 16", namespace, operationResponse);
        }

        //OBTENER DATOS
        try {
            switch (tipoTarjeta) {
                case "P":
                    //datos tarjeta privada
                    ArrayList<String> response1;
                    response1 = obtenerDatosTarjetaPrivada(numeroTarjeta, remoteJndiOrion, identificacion, pais);

                    ArrayList<String> response3;
                    response3 = obtenerFechaCortePrivada(usuarioCrm, passwordCrm, urlCrm, numeroTarjeta, pais);

                    if (response1.size() > 0) {
                        return estructura(response1, "ORION_PRIVADA");
                    }
                    if (response3 != null) {
                        return estructura(response3, "CRM_PRIVADA");
                    }
                    log.info("obtenerConsultaMovimientos response = [" + message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse) + "]");
                    return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
                case "V":
                    //datos tarjeta visa
                    ArrayList<String> response2 = obtenerDatosTarjetaVisa(numeroTarjeta, remoteJndiOrion, identificacion, pais);
                    if (response2.size() > 0) {
                        return estructura(response2, "ORION_VISA");
                    } else {
                        log.info("obtenerConsultaMovimientos response = [" + message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse) + "]");
                        return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
                    }
            }
        } catch (Exception ex) {
            log.info("obtenerConsultaMovimientos response = [" + message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse) + "]");
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }

        log.info("obtenerConsultaMovimientos response = [" + message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse) + "]");
        return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
    }

    public static ArrayList<String> obtenerDatosTarjetaVisa(String numeroTarjeta, String remoteJndiOrion,
                                                            String identificacion, String pais) throws SQLException {
        ArrayList<String> response = new ArrayList<String>();
        //obtener ultimos registros
        String tarjeta = null;
        if (numeroTarjeta != null) {
            if (numeroTarjeta.length() > 6) {
                tarjeta = Utils.obtenerTarjeta(numeroTarjeta, 6);
            }
        }

        //(VISA LISTADO ESTADOS CUENTA) ESQUEMA DIFERENTE POR PAIS

//        EL SALVADOR
        String query1 = "SELECT " +
                "        TO_CHAR(eco.fecha_corte,'YYYYMMDD') AS fechaCorte " +
                "        FROM ORIONREPOSV.als_estados_cuenta_orion eco " +
                "        WHERE eco.digitos_tarjeta = ? AND eco.id_cliente = ? " +
                "        ORDER BY eco.fecha_corte DESC ";

//        GUATEMALA
        String query2 = "SELECT " +
                "        TO_CHAR(eco.fecha_corte,'YYYYMMDD') AS fechaCorte " +
                "        FROM ORIONREPOGT.als_estados_cuenta_orion eco " +
                "        WHERE eco.digitos_tarjeta = ? AND eco.id_cliente = ? " +
                "        ORDER BY eco.fecha_corte DESC ";

//        NICARAGUA
        String query3 = "SELECT " +
                "        TO_CHAR(eco.fecha_corte,'YYYYMMDD') AS fechaCorte " +
                "        FROM ORIONREPOGT.als_estados_cuenta_orion eco " +
                "        WHERE eco.digitos_tarjeta = ? AND eco.id_cliente = ? " +
                "        ORDER BY eco.fecha_corte DESC ";

//        COSTA RICA
        String query4 = "SELECT " +
                "        TO_CHAR(eco.fecha_corte,'YYYYMMDD') AS fechaCorte " +
                "        FROM ORIONREPOCR.als_estados_cuenta_orion eco " +
                "        WHERE eco.digitos_tarjeta = ? AND eco.id_cliente = ? " +
                "        ORDER BY eco.fecha_corte DESC ";

        Connection conexion = new ConnectionHandler().getConnection(remoteJndiOrion);
        PreparedStatement sentencia = null;
        switch (pais) {
            case "SV":
                sentencia = conexion.prepareStatement(query1);
                break;
            case "GT":
                sentencia = conexion.prepareStatement(query2);
                break;
            case "NI":
                sentencia = conexion.prepareStatement(query3);
                break;
            case "CR":
                sentencia = conexion.prepareStatement(query4);
                break;
        }

        sentencia.setString(1, tarjeta); // agregar parametros
        sentencia.setString(2, identificacion);
        ResultSet rs = sentencia.executeQuery();

        while (rs.next()) {
            response.add(rs.getString("fechaCorte"));
        }
        return response;
    }

    public static ArrayList<String> obtenerDatosTarjetaPrivada(String numeroTarjeta, String remoteJndiOrion,
                                                               String identificacion, String pais) throws Exception {
        ArrayList<String> response = new ArrayList<String>();
        String tarjeta = null;
        if (numeroTarjeta != null) {
            if (numeroTarjeta.length() > 6) {
                tarjeta = Utils.obtenerTarjeta(numeroTarjeta, 6);
            }
        }

//        (PRIVADA LISTADO ESTADOS CUENTA) ESQUEMA DIFERENTE POR PAIS

//EL SALVADOR
        String query1 = "SELECT  " +
                "TO_DATE(iefp.date_file, 'YYYYMMDD') AS fechaCorte " +
                "FROM ESTCTASV.t_isim_estcta_files_pdf iefp " +
                "WHERE iefp.idcard = ? AND iefp.customerid = ? " +
                "ORDER BY iefp.date_file DESC ";
//GUATEMALA
        String query2 = "SELECT  " +
                "TO_DATE(iefp.date_file, 'YYYYMMDD') AS fechaCorte " +
                "FROM ESTCTAGT.t_isim_estcta_files_pdf iefp " +
                "WHERE iefp.idcard = ? AND iefp.customerid = ? " +
                "ORDER BY iefp.date_file DESC ";
//NICARAGUA
        String query3 = "SELECT  " +
                "TO_DATE(iefp.date_file, 'YYYYMMDD') AS fechaCorte " +
                "FROM ESTCTANI.t_isim_estcta_files_pdf iefp " +
                "WHERE iefp.idcard = ? AND iefp.customerid = ? " +
                "ORDER BY iefp.date_file DESC ";
//COSTA RICA
        String query4 = "SELECT  " +
                "TO_DATE(iefp.date_file, 'YYYYMMDD') AS fechaCorte " +
                "FROM ESTCTACR.t_isim_estcta_files_pdf iefp " +
                "WHERE iefp.idcard = ? AND iefp.customerid = ? " +
                "ORDER BY iefp.date_file DESC ";

        Connection conexion = new ConnectionHandler().getConnection(remoteJndiOrion);
        PreparedStatement sentencia = null;
        switch (pais) {
            case "SV":
                sentencia = conexion.prepareStatement(query1);
                break;
            case "GT":
                sentencia = conexion.prepareStatement(query2);
                break;
            case "NI":
                sentencia = conexion.prepareStatement(query3);
                break;
            case "CR":
                sentencia = conexion.prepareStatement(query4);
                break;
        }

        sentencia.setString(1, tarjeta); // agregar parametros
        sentencia.setString(2, identificacion);
        ResultSet rs = sentencia.executeQuery();

        while (rs.next()) {
            response.add(rs.getString("fechaCorte"));
        }
        conexion.close();
        return response;

    }

    public static XmlObject estructura(ArrayList<String> response1, String origen) {
        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        for (String fechaCorte : response1) {
            cursor.beginElement(new QName(namespace, "fechasCorte"));
            cursor.insertElementWithText(new QName(namespace, "fechaCorte"), fechaCorte);
            cursor.toParent();
        }
        cursor.insertElementWithText(new QName(namespace, "origen"), origen);
        cursor.toParent();

        log.info("ObtenerListadoEstadosCuenta response = [" + result + "]");
        return result;
    }

    public static ArrayList<String> obtenerFechaCortePrivada(String usuarioCrm, String passwordCrm,
                                                             String urlCrm, String numeroTarjeta, String pais) throws Exception {
        LoginResponse response = new LoginResponse();
        response = loginCrm(usuarioCrm, passwordCrm, urlCrm);
        ValidateToken validation = new ValidateToken();
        FechaCorte fechaCorte = new FechaCorte();
        ArrayList<String> fechasCorte = new ArrayList<>();


        if (response.getAccess_token() != null) {
            validation = ValidateloginCrm(response.getAccess_token(), urlCrm);
            if (validation.getResponse().getError_message().equals("Sucess")) {
                log.info("token valid");
                fechaCorte = estadosCuentaFechaCrm(numeroTarjeta, response.getAccess_token(), pais, urlCrm);
                log.info(fechaCorte.getErrorMessage());
                if (fechaCorte.getErrorMessage().equals("No data found")) {
                    return null;
                }
                if (fechaCorte.getErrorMessage().equals("Bad request.Tarjeta Invalida")) {
                    return null;
                }

                fechasCorte.addAll(fechaCorte.getFechaCorte());
                return fechasCorte;
            } else {
                log.info("invalid token");
                fechaCorte = new FechaCorte();
                fechaCorte.setErrorMessage(validation.getResponse().getError_message());
                fechaCorte.setErrorMessage(validation.getResponse().getError_code());
                return null;
            }
        }
        return null;
    }

    public static FechaCorte estadosCuentaFechaCrm(String numeroTarjeta, String token, String pais, String url)
            throws Exception {
        JSONObject jsonSend = new JSONObject(); //json a enviar
        jsonSend.put("numeroTarjeta", numeroTarjeta)
                .put("token", token)
                .put("pais", pais);

        HttpResponse<String> jsonResponse //realizar petición demiante unirest
                = Unirest.post(url.concat("/crm/api/v1.0.0/arca/getEstadosCuentaFecha"))
                .header("Content-Type", "application/json")
                .body(jsonSend.toString())
                .asString();

        //capturar respuesta
        JSONObject response = new JSONObject(jsonResponse.getBody());

        return new ObjectMapper().readValue(response.toString(), FechaCorte.class);
    }

    public static LoginResponse loginCrm(String username, String password, String url) throws Exception {

        JSONObject jsonSend = new JSONObject(); //json a enviar
        jsonSend.put("username", username)
                .put("password", password);

        HttpResponse<String> jsonResponse //realizar petición demiante unirest
                = Unirest.post(url.concat("/api/security/v1.0.0/login"))
                .header("Content-Type", "application/json")
                .body(jsonSend.toString())
                .asString();

        //capturar respuesta
        JSONObject response = new JSONObject(jsonResponse.getBody());

        return new ObjectMapper().readValue(response.toString(), LoginResponse.class);
    }

    public static ValidateToken ValidateloginCrm(String token, String url) throws Exception {
        HttpResponse<String> jsonResponse //realizar petición demiante unirest
                = Unirest.get(url.concat("/api/security/v1.0.0/token/validate"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer ".concat(token))
                .asString();

        //capturar respuesta
        JSONObject response = new JSONObject(jsonResponse.getBody());
        return new ObjectMapper().readValue(response.toString(), ValidateToken.class);
    }

}