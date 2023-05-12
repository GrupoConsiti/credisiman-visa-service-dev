package com.siman.credisiman.visa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.consultamonedero.ConsultaSaldoMonederoResponse;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class ConsultaSaldoMonedero {
    //private static Logger log = LoggerFactory.getLogger(ConsultaSaldoMonedero.class);

    public static XmlObject obtenerConsultaSaldoMonedero(String pais, String numeroTarjeta, String cuenta,
                                                         String fechaCorte, String remoteJndiSunnel, String remoteJndiOrion, String siscardUrl, String siscardUser,
                                                         String binCredisiman, String codigoEmisor, String tipoTarjeta) {

        String namespace = "http://siman.com/ConsultaSaldoMonedero";
        String operationResponse = "ObtenerConsultaSaldoMonederoResponse";

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        ConsultaSaldoMonederoResponse response1 = new ConsultaSaldoMonederoResponse();

        Utils utils = new Utils();
        Message message = new Message();

        //validar campos requeridos
        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            return message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(numeroTarjeta) || utils.validateNotEmpty(numeroTarjeta)) {
            return message.genericMessage("ERROR", "400", "El campo número tarjeta es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(cuenta) || utils.validateNotEmpty(cuenta)) {
            return message.genericMessage("ERROR", "400", "El campo cuenta es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(fechaCorte) || utils.validateNotEmpty(fechaCorte)) {
            return message.genericMessage("ERROR", "400", "El campo fecha corte es obligatorio", namespace, operationResponse);
        }

        //validar longitudes
        if (!utils.validateLongitude(pais, 3)) {
            return message.genericMessage("ERROR", "400", "La longitud del campo pais debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(numeroTarjeta, 16)) {
            return message.genericMessage("ERROR", "400", "La longitud del campo número tarjeta debe ser menor o igual a 16", namespace, operationResponse);
        }
        if (!utils.validateLongitude(cuenta, 20)) {
            return message.genericMessage("ERROR", "400", "La longitud del campo cuenta debe ser menor o igual a 20", namespace, operationResponse);
        }
        if (!utils.validateLongitude(fechaCorte, 8)) {
            return message.genericMessage("ERROR", "400", "La longitud del campo número tarjeta debe ser menor o igual a 8", namespace, operationResponse);
        }

        //OBTENER DATOS TARJETA CREDISIMAN
        try {
            JSONObject jsonSend = new JSONObject(); //json a enviar
            jsonSend.put("country", pais)
                    .put("processIdentifier", "ConsultaPremiacion")
                    .put("fechaCorte", fechaCorte)
                    .put("numeroCuenta", cuenta)
                    .put("codigoEmisor", codigoEmisor);

            HttpResponse<String> jsonResponse //realizar petición demiante unirest
                    = Unirest
                    .post(siscardUrl.concat("/consultaPremiacion"))
                    .header("Content-Type", "application/json")
                    .body(jsonSend.toString())
                    .asString();
            //capturar respuesta
            JSONObject response = new JSONObject(jsonResponse
                    .getBody()
                    .replaceAll("\u200B", ""));
            response1 = new ObjectMapper()
                    .readValue(response.toString(), ConsultaSaldoMonederoResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            //log.info(e.getMessage());
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse);

        }

        if (response1.getCode().equals("200")) {
            cursor.toNextToken();
            cursor.beginElement(responseQName);
            cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
            cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
            cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
            cursor.insertElementWithText(new QName(namespace, "saldoInicial"), response1.getSaldoInicial());
            cursor.insertElementWithText(new QName(namespace, "puntosGanados"), response1.getPuntosGanados());
            cursor.insertElementWithText(new QName(namespace, "puntosCanjeados"), response1.getPuntosCanjeados());
            cursor.insertElementWithText(new QName(namespace, "saldoFinal"), response1.getSaldoFinal());
            cursor.toParent();
            //log.info("obtenerConsultaSaldoMonedero response = [" + result + "]");
            return result;
        }
        //log.info("obtenerConsultaSaldoMonedero response = [" + message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse) + "]");
        return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
    }
}