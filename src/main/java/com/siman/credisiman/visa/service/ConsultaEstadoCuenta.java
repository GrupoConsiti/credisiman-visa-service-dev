package com.siman.credisiman.visa.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.crm.AccountState;
import com.siman.credisiman.visa.dto.crm.EstadoCuenta;
import com.siman.credisiman.visa.utils.ConnectionHandler;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ConsultaEstadoCuenta {
    //private static Logger log = LoggerFactory.getLogger(ConsultaEstadoCuenta.class);
    private static final String namespace = "http://siman.com/ConsultaEstadoCuenta";
    private static final String operationResponse = "ObtenerConsultaEstadoCuentaResponse";

    public static Connection conexion;
    public static PreparedStatement sentencia = null;
    public static ResultSet rs;

    public static XmlObject obtenerConsultaEstadoCuenta(String pais, String numeroTarjeta, String fechaCorte, String remoteJndiSunnel,
                                                        String remoteJndiOrion, String siscardUrl, String siscardUser, String binCredisiman,
                                                        String tipoTarjeta, String identificacion, String esquemaSunnel, String esquemaOrion,
                                                        String esquemaEstcta, String webService, String nombreArchivo, String ruta) {
        Utils utils = new Utils();
        Message message = new Message();

        if (utils.validateNotNull(tipoTarjeta) || utils.validateNotEmpty(tipoTarjeta)) {
            return message.genericMessage("ERROR", "400", "El campo tipo tarjeta es obligatorio", namespace, operationResponse);
        }

        try {
            switch (tipoTarjeta) {
                case "P":
                    //datos tarjeta privada
                    if (nombreArchivo.equals("") && ruta.equals("")) {
                        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
                            //log.info(message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse).toString());
                            return message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse);
                        }
                        if (utils.validateNotNull(numeroTarjeta) || utils.validateNotEmpty(numeroTarjeta)) {
                            //log.info(message.genericMessage("ERROR", "400", "El campo numero tarjeta es obligatorio", namespace, operationResponse).toString());
                            return message.genericMessage("ERROR", "400", "El campo numero tarjeta es obligatorio", namespace, operationResponse);
                        }
                        if (utils.validateNotNull(fechaCorte) || utils.validateNotEmpty(fechaCorte)) {
                            //log.info(message.genericMessage("ERROR", "400", "El campo fecha de corte es obligatorio", namespace, operationResponse).toString());
                            return message.genericMessage("ERROR", "400", "El campo fecha de corte es obligatorio", namespace, operationResponse);
                        }
                        if (utils.validateNotNull(identificacion) || utils.validateNotEmpty(identificacion)) {
                            //log.info(message.genericMessage("ERROR", "400", "El campo identificacion es obligatorio", namespace, operationResponse).toString());
                            return message.genericMessage("ERROR", "400", "El campo identificacion es obligatorio", namespace, operationResponse);
                        }

                        EstadoCuenta response1 = null;
                        response1 = obtenerEstadoCuentaPrivada(numeroTarjeta, identificacion, fechaCorte, pais, remoteJndiOrion, esquemaEstcta);

                        if (response1 != null) {
                            return estructura(response1, "ORION_PRIVADA");
                        }
                    } else {
                        EstadoCuenta response3 = null;
                        response3 = obtenerEstadoCuentaPrivadaWebService(nombreArchivo, ruta, webService);

                        if (response3 != null) {
                            return estructura(response3, "PRIVADA");
                        } else {
                            return message.genericMessage("ERROR", "204", "La consulta no devolvio contenido.", namespace, operationResponse);
                        }
                    }
                case "V":
                    //datos tarjeta visa
                    EstadoCuenta response2 = null;
                    response2 = EstadoCuentaOrionVisa(remoteJndiOrion, pais, identificacion, numeroTarjeta, fechaCorte, esquemaOrion);

                    if (response2 != null) {
                        return estructura(response2, "ORION_VISA");
                    }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //log.info(message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse).toString());
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema..." + e.getMessage(), namespace, operationResponse);
        }
        //log.info(message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse).toString());
        return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
    }

    public static EstadoCuenta obtenerEstadoCuentaPrivada(String numeroTarjeta, String identificacion,
                                                          String fechaCorte, String pais,
                                                          String remoteJndiOrion, String esquemaEstcta) throws Exception {

        String tarjeta = null;
        if (numeroTarjeta != null) {
            if (numeroTarjeta.length() > 6) {
                tarjeta = Utils.obtenerTarjeta(numeroTarjeta, 6);
            } else {
                tarjeta = numeroTarjeta;
            }
        }

        //log.info(tarjeta);

        //(PRIVADA ESTADO CUENTA) ESQUEMA DIFERENTE POR PAIS

        String query = "SELECT iefp.pdffile as archivo_pdf " +
                "FROM " + esquemaEstcta + ".t_isim_estcta_files_pdf iefp " +
                "WHERE iefp.idcard = ? AND iefp.customerid = ? AND date_file = ?";


        conexion = new ConnectionHandler().getConnection(remoteJndiOrion);
        sentencia = conexion.prepareStatement(query);

        sentencia.setString(1, tarjeta); // agregar parametros
        sentencia.setString(2, identificacion);
        sentencia.setString(3, fechaCorte);
        rs = sentencia.executeQuery();

        AccountState accountState = new AccountState();
        List<AccountState> accountStateList = new ArrayList<>();
        EstadoCuenta estadoCuenta = new EstadoCuenta();

        if (rs.next()) {
            Clob clob = rs.getClob("archivo_pdf");
            String wholeClob = clob.getSubString(1, (int) clob.length());
            accountState.setEstadoCuenta(wholeClob);
            accountState.setCorreo(" ");
            accountStateList.add(accountState);
            estadoCuenta.setAccountStates(accountStateList);
            conexion.close();
            //log.info(estadoCuenta.toString());
            return estadoCuenta;
        }
        conexion.close();
        return null;
    }

    public static EstadoCuenta obtenerEstadoCuentaPrivadaWebService(String nombreArchivo, String ruta, String webService) throws Exception {

        AccountState accountState = new AccountState();
        List<AccountState> accountStateList = new ArrayList<>();
        EstadoCuenta estadoCuenta = new EstadoCuenta();

        JSONObject jsonSend = new JSONObject(); //json a enviar
        jsonSend.put("nombrearchivo", nombreArchivo)
                .put("ruta", ruta);

        HttpResponse<String> jsonResponse
                = Unirest.post(webService)
                .header("Content-Type", "application/json")
                .body(jsonSend.toString())
                .asString();

        String statusResponse = String.valueOf(jsonResponse.getStatus());
        if (statusResponse.equals("204")) {
            return null;
        }

        InputStream inputStream = jsonResponse.getRawBody();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int r = inputStream.read(buffer);
            if (r == -1) break;
            out.write(buffer, 0, r);
        }
        byte[] ret = out.toByteArray();
        String encodedPdf = Base64.getEncoder().encodeToString(ret);
        accountState.setEstadoCuenta(encodedPdf);
        accountState.setCorreo(" ");
        accountStateList.add(accountState);
        estadoCuenta.setAccountStates(accountStateList);
        //log.info(estadoCuenta.toString());
        return estadoCuenta;
    }

    public static EstadoCuenta EstadoCuentaOrionVisa(String remoteOrionJndi, String pais, String identificacion,
                                                     String numeroTarjeta, String fechaCorte, String esquemaOrion) throws Exception {

        String query = "SELECT eco.archivo_pdf AS archivo_pdf FROM " + esquemaOrion + ".als_estados_cuenta_orion eco " +
                "WHERE eco.digitos_tarjeta = ? AND eco.id_cliente = ? " +
                "AND eco.fecha_corte = TO_DATE(?, 'YYYYMMDD')";

        conexion = new ConnectionHandler().getConnection(remoteOrionJndi);
        sentencia = conexion.prepareStatement(query);

        String tarjeta = null;
        if (numeroTarjeta != null) {
            if (numeroTarjeta.length() > 6) {
                tarjeta = Utils.obtenerTarjeta(numeroTarjeta, 6);
            } else {
                tarjeta = numeroTarjeta;
            }
        }

        sentencia.setString(1, tarjeta); // agregar parametros
        sentencia.setString(2, identificacion);
        sentencia.setString(3, fechaCorte);
        rs = sentencia.executeQuery();

        EstadoCuenta response = new EstadoCuenta();
        AccountState accountState = new AccountState();
        List<AccountState> lista = new ArrayList<>();
        int counter = 0;

        while (rs.next()) {
            Blob blob = rs.getBlob("archivo_pdf");
            byte[] bytes = blob.getBytes(1, (int) blob.length());
            String base64 = Base64.getEncoder().encodeToString(bytes);
            accountState.setEstadoCuenta(base64);
            accountState.setCorreo(" ");
            counter++;
        }
        lista.add(accountState);
        response.setAccountStates(lista);
        conexion.close();

        if (counter > 0) {
            return response;
        } else {
            conexion.close();
            return null;
        }
    }

    public static XmlObject estructura(EstadoCuenta response1, String origen) {
        //OBTENER DATOS
        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);

        //OBTENER DATOS
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        cursor.insertElementWithText(new QName(namespace, "correo"), response1.getAccountStates().get(0).getCorreo());
        cursor.insertElementWithText(new QName(namespace, "archivo"), response1.getAccountStates().get(0).getEstadoCuenta());
        cursor.insertElementWithText(new QName(namespace, "origen"), origen);
        cursor.toParent();

        //log.info("obtenerConsultaEstadoCuenta response = [" + result + "]");
        return result;
    }
}
