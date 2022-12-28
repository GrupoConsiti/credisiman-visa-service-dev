package com.siman.credisiman.visa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.consultapolizas.ConsultaPolizasResponse;
import com.siman.credisiman.visa.dto.consultapolizas.ListaDePolizasResponse;
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
import java.util.List;

public class ConsultaPolizas {
    private static final Logger log = LoggerFactory.getLogger(ConsultaPolizas.class);
    private static final String namespace = "http://siman.com/ConsultaPolizas";
    private static final String operationResponse = "ObtenerConsultaPolizasResponse";

    public static XmlObject obtenerConsultaPolizas(String pais, String numeroTarjeta, String remoteJndiSunnel,
                                                   String remoteJndiOrion, String siscardUrl, String siscardUser,
                                                   String binCredisiman, String tipoTarjeta) {
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
        if (utils.validateNotNull(tipoTarjeta) || utils.validateNotEmpty(tipoTarjeta)) {
            log.info("tipoTarjeta required");
            return message.genericMessage("ERROR", "400", "El campo número tipoTarjeta es obligatorio", namespace, operationResponse);
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

        ConsultaPolizasResponse response1;
        try {
            switch (tipoTarjeta) {
                case "P":
                    //datos tarjeta privada
                    response1 = obtenerDatosArca(remoteJndiSunnel, numeroTarjeta, pais);
                    if (response1.getListaDePolizas().size()>0  &&response1 != null) {
                        return estructura(response1);
                    } else {
                        log.info("obtenerConsultaPolizas response = [" + message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse) + "]");
                        return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
                    }
                case "V":
                    //datos tarjeta visa
                    ConsultaPolizasResponse response2 = obtenerDatosSiscard(pais, numeroTarjeta, siscardUrl);
                    if (response2.getListaDePolizas().size() > 0) {
                        return estructura(response2);
                    } else {
                        log.info("obtenerConsultaPolizas response = [" + message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse) + "]");
                        return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
                    }
            }
        } catch (SQLException e) {
            log.error("SQL ERROR, " + e.getMessage());
            log.info("obtenerConsultaPolizas response = [" + message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse) + "]");
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse);
        } catch (NullPointerException nul) {
            return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("SERVICE ERROR, " + ex.getMessage());
            log.info("obtenerConsultaPolizas response = [" + message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse) + "]");
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }
        log.info("obtenerConsultaPolizas response = [" + message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse) + "]");
        return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);

    }

    public static XmlObject estructura(ConsultaPolizasResponse response1) {
        //OBTENER DATOS
        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");


        for (int i = 0; i < response1.getListaDePolizas().size(); i++) {
            cursor.beginElement(new QName(namespace, "polizas"));
            cursor.insertElementWithText(new QName(namespace, "tipoPoliza"), response1.getListaDePolizas().get(i).getTipoPoliza());
            cursor.insertElementWithText(new QName(namespace, "nombrePoliza"), response1.getListaDePolizas().get(i).getNombrePoliza());
            cursor.insertElementWithText(new QName(namespace, "estadoPoliza"), response1.getListaDePolizas().get(i).getEstadoPoliza());
            cursor.toParent();
        }

        cursor.toParent();
        log.info("ObtenerConsultaPolizas response = [" + result + "]");
        return result;
    }

    public static ConsultaPolizasResponse obtenerDatosArca(String remoteJndiSunnel, String numeroTarjeta, String pais) throws Exception {
        String query1 = "SELECT A.CARDID, " +
                "                       AT.AUTOMATEDCHARGETYPEID tipoPoliza, " +
                "                AT.DESCRIPTION nombrePoliza, A.STATUS estadoPoliza  " +
                "                  FROM SUNNELP3.T_GAUTOMATEDCHARGE a, SUNNELP3.T_GAUTOMATEDCHARGETYPE at " +
                "                 WHERE A.AUTOMATEDCHARGETYPEID = AT.AUTOMATEDCHARGETYPEID " +
                "                AND A.CARDID = ? ";

        String query2 = " SELECT A.CARDID, " +
                "       AT.AUTOMATEDCHARGETYPEID tipoPoliza, " +
                "       AT.DESCRIPTION nombrePoliza, " +
                "       A.STATUS estadoPoliza " +
                "  FROM SUNNELGTP4.T_GAUTOMATEDCHARGE a, SUNNELGTP4.T_GAUTOMATEDCHARGETYPE at " +
                " WHERE     A.AUTOMATEDCHARGETYPEID = AT.AUTOMATEDCHARGETYPEID " +
                "       AND A.CARDID = ? ";

        String query3 = " SELECT A.CARDID, " +
                "       AT.AUTOMATEDCHARGETYPEID tipoPoliza, " +
                "       AT.DESCRIPTION nombrePoliza, " +
                "       A.STATUS estadoPoliza " +
                "  FROM SUNNELNIP1.T_GAUTOMATEDCHARGE a, SUNNELNIP1.T_GAUTOMATEDCHARGETYPE at " +
                " WHERE     A.AUTOMATEDCHARGETYPEID = AT.AUTOMATEDCHARGETYPEID " +
                "       AND A.CARDID = ? ";

        String query4 = " SELECT A.CARDID, " +
                "       AT.AUTOMATEDCHARGETYPEID tipoPoliza, " +
                "       AT.DESCRIPTION nombrePoliza, " +
                "       A.STATUS estadoPoliza " +
                "  FROM SUNNELCRP4.T_GAUTOMATEDCHARGE a, SUNNELCRP4.T_GAUTOMATEDCHARGETYPE at " +
                " WHERE     A.AUTOMATEDCHARGETYPEID = AT.AUTOMATEDCHARGETYPEID " +
                "       AND A.CARDID = ? ";

        ConnectionHandler connectionHandler = new ConnectionHandler();
        Connection conexion = connectionHandler.getConnection(remoteJndiSunnel);

        PreparedStatement sentencia = null;
        switch (pais){
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

        //parametros
        sentencia.setString(1, numeroTarjeta);
        ResultSet rs = sentencia.executeQuery();
        List<ListaDePolizasResponse> lista = new ArrayList<>();
        ConsultaPolizasResponse response = new ConsultaPolizasResponse();


        while (rs.next()) {
            ListaDePolizasResponse consultaPolizas = new ListaDePolizasResponse();
            consultaPolizas.setTipoPoliza(rs.getString("tipoPoliza"));
            consultaPolizas.setNombrePoliza(rs.getString("nombrePoliza"));
            consultaPolizas.setEstadoPoliza(rs.getString("estadoPoliza"));
            lista.add(consultaPolizas);
        }
        conexion.close();
        response.setListaDePolizas(lista);
        return response;
    }

    public static ConsultaPolizasResponse obtenerDatosSiscard(String pais, String numeroTarjeta, String siscardUrl) throws Exception {
        ConsultaPolizasResponse response1 = new ConsultaPolizasResponse();
        //OBTENER DATOS
        JSONObject jsonSend = new JSONObject(); //json a enviar
        jsonSend.put("country", pais)
                .put("processIdentifier", "ConsultaPoliza")
                .put("tipoMensaje", 3700)
                .put("numeroTarjeta", numeroTarjeta)
                .put("tipoPolizaEntrada", "");

        HttpResponse<String> jsonResponse //realizar petición demiante unirest
                = Unirest.post(siscardUrl.concat("/consultaPoliza"))
                .header("Content-Type", "application/json")
                .body(jsonSend.toString())
                .asString();

        //capturar respuesta
        JSONObject response = new JSONObject(jsonResponse
                .getBody()
                .replaceAll("\u200B", ""));
        response1 = new ObjectMapper()
                .readValue(response.toString(), ConsultaPolizasResponse.class);

        log.info(new ObjectMapper().writeValueAsString(response1));

        return response1;
    }
}
