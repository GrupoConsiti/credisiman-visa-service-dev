package com.siman.credisiman.visa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.crm.AccountState;
import com.siman.credisiman.visa.dto.crm.EstadoCuenta;
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
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConsultaEstadoCuenta {
    private static final Logger log = LoggerFactory.getLogger(ConsultaEstadoCuenta.class);
    private static final String namespace = "http://siman.com/ConsultaEstadoCuenta";
    private static final String operationResponse = "ObtenerConsultaEstadoCuentaResponse";

    public static XmlObject obtenerConsultaEstadoCuenta(String pais, String numeroTarjeta, String cuenta,
                                                        String fechaCorte, String remoteJndiSunnel, String remoteJndiOrion,
                                                        String siscardUrl, String siscardUser, String binCredisiman,
                                                        String tipoTarjeta, String urlCrm, String usuarioCrm, String passwordCrm,
                                                        String identificacion) {
        Utils utils = new Utils();
        Message message = new Message();

        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            return message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(numeroTarjeta) || utils.validateNotEmpty(numeroTarjeta)) {
            return message.genericMessage("ERROR", "400", "El campo numero tarjeta es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(fechaCorte) || utils.validateNotEmpty(fechaCorte)) {
            return message.genericMessage("ERROR", "400", "El campo fecha de corte es obligatorio", namespace, operationResponse);
        }


        try {
            switch (tipoTarjeta) {
                case "P":
                    //datos tarjeta privada
                    EstadoCuenta response1 = null;
                    response1 = obtenerEstadoCuentaPrivada(usuarioCrm, passwordCrm, urlCrm, numeroTarjeta, fechaCorte, pais);

                    EstadoCuenta response3 = null;
                    response3 = EstadoCuentaOrionPrivada(remoteJndiOrion, pais, identificacion, numeroTarjeta, fechaCorte);

                    if (response1 != null) {
                        log.info("RESPONSE" + estructura(response1, "CRM"));
                        return estructura(response1, "CRM");
                    }
                    if (response3 != null) {
                        log.info("RESPONSE" + estructura(response3, "ORION_PRIVADA"));
                        return estructura(response3, "ORION_PRIVADA");
                    }
                case "V":
                    //datos tarjeta visa
                    EstadoCuenta response2 = null;
                    response2 = EstadoCuentaOrionVisa(remoteJndiOrion, pais, identificacion, numeroTarjeta, fechaCorte);

                    if (response2 != null) {
                        return estructura(response2, "ORION_VISA");
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }
        log.info("NOT FOUND");
        return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
    }

    public static XmlObject estructura(EstadoCuenta response1, String origen) {
        //OBTENER DATOS
        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);

        //OBTENER DATOS
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"),
                response1.getErrorCode().equals("0") ? "00" : response1.getErrorMessage());
        cursor.insertElementWithText(new QName(namespace, "status"),
                response1.getErrorMessage().equals("Success") ? "SUCCESS" : response1.getErrorMessage());
        cursor.insertElementWithText(new QName(namespace, "statusMessage"),
                response1.getErrorMessage().equals("Success") ? "Proceso exitoso" : response1.getErrorMessage());
        cursor.insertElementWithText(new QName(namespace, "correo"),
                response1.getAccountStates().get(0).getCorreo());
        cursor.insertElementWithText(new QName(namespace, "archivo"),
                response1.getAccountStates().get(0).getEstadoCuenta());
        cursor.insertElementWithText(new QName(namespace, "origen"), origen);
        cursor.toParent();

        log.info("obtenerConsultaEstadoCuenta response = [" + result + "]");
        return result;
    }


    public static EstadoCuenta obtenerEstadoCuentaPrivada(String usuarioCrm, String passwordCrm,
                                                          String urlCrm, String numeroTarjeta,
                                                          String fechaCorte, String pais) throws Exception {
        LoginResponse response = new LoginResponse();
        response = loginCrm(usuarioCrm, passwordCrm, urlCrm);
        ValidateToken validation = new ValidateToken();
        EstadoCuenta estadocuenta = new EstadoCuenta();

        if (response.getAccess_token() != null) {
            validation = ValidateloginCrm(response.getAccess_token(), urlCrm);
            if (validation.getResponse().getError_message().equals("Sucess")) {
                log.info("token valid");
                estadocuenta = consultaEstadoCuentaCrm(numeroTarjeta, fechaCorte, response.getAccess_token(), pais, urlCrm);
                log.info(estadocuenta.getErrorMessage());
                if (estadocuenta.getErrorMessage().equals("No data found")) {
                    return null;
                }
                if (estadocuenta.getErrorMessage().equals("Bad request.Tarjeta Invalida")) {
                    return null;
                }
                return estadocuenta;
            } else {
                log.info("invalid token");
                estadocuenta = new EstadoCuenta();
                estadocuenta.setErrorMessage(validation.getResponse().getError_message());
                estadocuenta.setErrorMessage(validation.getResponse().getError_code());
                return estadocuenta;
            }
        }
        return null;
    }

    public static EstadoCuenta EstadoCuentaOrionVisa(String remoteOrionJndi, String pais, String identificacion,
                                                     String numeroTarjeta, String fechaCorte) throws Exception {
        String query1 = "SELECT (eco.archivo_pdf) as archivo_pdf, ' ' as correo," +
                " dbms_lob.getlength(eco.archivo_pdf) as archivo_pdf_length  " +
                "  FROM ORIONREPOSV.als_estados_cuenta_orion eco " +
                " WHERE     eco.digitos_tarjeta = ? " +
                "       AND eco.id_cliente = ? " +
                "       AND eco.fecha_corte = TO_DATE (? , 'YYYYMMDD') ";

        String query2 = "SELECT base64encode(eco.archivo_pdf) as archivo_pdf, ' ' as correo," +
                " dbms_lob.getlength(eco.archivo_pdf) as archivo_pdf_length  " +
                "  FROM ORIONREPOGT.als_estados_cuenta_orion eco " +
                " WHERE     eco.digitos_tarjeta = ? " +
                "       AND eco.id_cliente = ? " +
                "       AND eco.fecha_corte = TO_DATE (? , 'YYYYMMDD') ";

        String query3 = "SELECT base64encode(eco.archivo_pdf) as archivo_pdf, ' ' as correo, " +
                "dbms_lob.getlength(eco.archivo_pdf) as archivo_pdf_length  " +
                "  FROM ORIONREPONI.als_estados_cuenta_orion eco " +
                " WHERE     eco.digitos_tarjeta = ? " +
                "       AND eco.id_cliente = ? " +
                "       AND eco.fecha_corte = TO_DATE (? , 'YYYYMMDD') ";

        String query4 = "SELECT base64encode(eco.archivo_pdf) as archivo_pdf, ' ' as correo," +
                " dbms_lob.getlength(eco.archivo_pdf) as archivo_pdf_length " +
                "  FROM ORIONREPOCR.als_estados_cuenta_orion eco " +
                " WHERE     eco.digitos_tarjeta = ? " +
                "       AND eco.id_cliente = ? " +
                "       AND eco.fecha_corte = TO_DATE (? , 'YYYYMMDD') ";

        ConnectionHandler connectionHandler = new ConnectionHandler();

        Connection conexion = connectionHandler.getConnection(remoteOrionJndi);

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

        String tarjeta = null;
        if (numeroTarjeta != null) {
            if (numeroTarjeta.length() > 6) {
                tarjeta = Utils.obtenerTarjeta(numeroTarjeta, 6);
            }
        }
        log.info("tarjeta visa: " + tarjeta);
        sentencia.setString(1, tarjeta); // agregar parametros
        sentencia.setString(2, identificacion);
        sentencia.setString(3, fechaCorte);
        ResultSet rs = sentencia.executeQuery();

        EstadoCuenta response = new EstadoCuenta();
        AccountState accountState = new AccountState();
        List<AccountState> lista = new ArrayList<>();
        int counter = 0;

        while (rs.next()) {
            counter++;
            InputStream pdf = rs.getBinaryStream("archivo_pdf");
            int longitud = rs.getInt("archivo_pdf_length");

            InputStream finput = rs.getBinaryStream("archivo_pdf");
            String pdf_final= Utils.blob_to_base64(finput, longitud);

            accountState.setEstadoCuenta(pdf_final);
            accountState.setCorreo(rs.getString("correo"));
        }
        lista.add(accountState);
        response.setErrorMessage("Success");
        response.setErrorCode("0");
        response.setAccountStates(lista);

        conexion.close();
        if (counter > 0) {
            return response;
        } else {
            return null;
        }

    }

    public static EstadoCuenta EstadoCuentaOrionPrivada(String remoteOrionJndi, String pais, String identificacion,
                                                        String numeroTarjeta, String fechaCorte) throws Exception {
        String query1 = " SELECT   iefp.pdffile as archivo_pdf , ' ' as correo," +
                "dbms_lob.getlength(iefp.pdffile) as archivo_pdf_length  " +
                "FROM ESTCTASV.t_isim_estcta_files_pdf iefp " +
                "WHERE iefp.idcard = ? AND iefp.customerid = ? AND date_file = ? ";
        String query2 = " SELECT   iefp.pdffile as archivo_pdf , ' ' as correo," +
                "dbms_lob.getlength(iefp.pdffile) as archivo_pdf_length  " +
                "FROM ESTCTAGT.t_isim_estcta_files_pdf iefp " +
                "WHERE iefp.idcard = ? AND iefp.customerid = ? AND date_file = ? ";
        String query3 = " SELECT  iefp.pdffile as archivo_pdf , ' ' as correo ," +
                "dbms_lob.getlength(iefp.pdffile) as archivo_pdf_length  " +
                "FROM ESTCTANI.t_isim_estcta_files_pdf iefp " +
                "WHERE iefp.idcard = ? AND iefp.customerid = ? AND date_file = ? ";
        String query4 = " SELECT  iefp.pdffile as archivo_pdf , ' ' as correo , " +
                "dbms_lob.getlength(iefp.pdffile) as archivo_pdf_length   " +
                "FROM ESTCTACR.t_isim_estcta_files_pdf iefp " +
                "WHERE iefp.idcard = ? AND iefp.customerid = ? AND date_file = ? ";
        ConnectionHandler connectionHandler = new ConnectionHandler();

        Connection conexion = connectionHandler.getConnection(remoteOrionJndi);

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

        String tarjeta = null;
        if (numeroTarjeta != null) {
            if (numeroTarjeta.length() > 6) {
                tarjeta = Utils.obtenerTarjeta(numeroTarjeta, 6);
            }
        }

        sentencia.setString(1, tarjeta); // agregar parametros
        sentencia.setString(2, identificacion);
        sentencia.setString(3, fechaCorte);
        ResultSet rs = sentencia.executeQuery();

        EstadoCuenta response = new EstadoCuenta();
        AccountState accountState = new AccountState();
        List<AccountState> lista = new ArrayList<>();
        int counter = 0;

        while (rs.next()) {
            counter++;

            Blob pdf = rs.getBlob("archivo_pdf");
            accountState.setEstadoCuenta(pdf.toString());
            accountState.setCorreo(rs.getString("correo"));
        }
        lista.add(accountState);
        response.setAccountStates(lista);

        conexion.close();
        if (counter > 0) {
            return response;
        } else {
            return null;
        }

    }

    public static EstadoCuenta consultaEstadoCuentaCrm(String numeroTarjeta, String fechaCorte, String token, String pais, String url)
            throws Exception {
        JSONObject jsonSend = new JSONObject(); //json a enviar
        jsonSend.put("numeroTarjeta", numeroTarjeta)
                .put("fechaCorte", fechaCorte)
                .put("token", token)
                .put("pais", pais);

        HttpResponse<String> jsonResponse //realizar petición demiante unirest
                = Unirest.post(url.concat("/crm/api/v1.0.0/arca/getEstadosCuenta"))
                .header("Content-Type", "application/json")
                .body(jsonSend.toString())
                .asString();

        //capturar respuesta
        JSONObject response = new JSONObject(jsonResponse.getBody());

        return new ObjectMapper().readValue(response.toString(), EstadoCuenta.class);
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
