package com.siman.credisiman.visa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.cambiopin.CambioPinResponse;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class CambioPIN {
    private static final Logger log = LoggerFactory.getLogger(CambioPIN.class);
    private static final String namespace = "http://siman.com/CambioPIN";
    private static final String operationResponse = "CambioPINResponse";

    public static XmlObject cambioPIN(String pais, String numeroTarjeta, String nip, String remoteJndiSunnel,
                                      String remoteJndiOrion, String siscardUrl, String siscardUser, String binCredisiman, String tipoTarjeta) {

        //OBTENER DATOS

        Utils utils = new Utils();
        Message message = new Message();


        //validar campos requeridos
        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            return message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(numeroTarjeta) || utils.validateNotEmpty(numeroTarjeta)) {
            return message.genericMessage("ERROR", "400", "El campo número tarjeta es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(nip) || utils.validateNotEmpty(nip)) {
            return message.genericMessage("ERROR", "400", "El campo NIP monto es obligatorio", namespace, operationResponse);
        }


        //validar longitudes
        if (!utils.validateLongitude(pais, 3)) {
            log.info("pais, size overload");
            return message.genericMessage("ERROR", "400", "La longitud del campo pais debe ser menor o igual a 3", namespace, operationResponse);
        }

        try {
            switch (tipoTarjeta) {
                case "P":
                    //datos tarjeta privada
                    CambioPinResponse response1 = new CambioPinResponse();
                    response1 = obtenerDatosEvertec(pais, numeroTarjeta, nip, siscardUrl);
                    if (response1 != null) {
                        return estructura(response1);
                    }
                    break;
                case "V":
                    //datos tarjeta visa
                    CambioPinResponse response2 = obtenerDatosEvertec(pais, numeroTarjeta, nip, siscardUrl);
                    if (response2 != null) {
                        return estructura(response2);
                    }
                default: message.genericMessage("ERROR", "400", "Tipo tarjeta no valida", namespace, operationResponse);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }
        log.info("CambioPIN response = [" + message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse) + "]");
        return message.genericMessage("ERROR", "400", "No se encontro el numero de tarjeta", namespace, operationResponse);
    }


    public static XmlObject estructura(CambioPinResponse response1) {
        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);

        cursor.toNextToken();
        cursor.beginElement(responseQName);

        String mensaje = null, estado = null, codigo = null;

        if (response1.getRespuestas() != null) {
            mensaje = response1.getRespuestas().get(0).getStatusMessage();
            estado = response1.getRespuestas().get(0).getStatus();
            codigo = response1.getRespuestas().get(0).getStatusCode();
        }
        if (mensaje != null) {
            cursor.insertElementWithText(new QName(namespace, "statusCode"), codigo);
            cursor.insertElementWithText(new QName(namespace, "status"), estado);
            cursor.insertElementWithText(new QName(namespace, "statusMessage"), mensaje);
        } else if(response1.getCode().equals("200")) {
            cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
            cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
            cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Operación Exitosa");
        }else {
            cursor.insertElementWithText(new QName(namespace, "statusCode"), response1.getCode());
            cursor.insertElementWithText(new QName(namespace, "status"), "ERROR");
            cursor.insertElementWithText(new QName(namespace, "statusMessage"), response1.getRespuestas().get(0).getStatusMessage());
        }
        cursor.toParent();
        log.info("obtenerCambioPIN response = [" + result.toString() + "]");
        return result;
    }

    public static CambioPinResponse obtenerDatosEvertec(String pais, String numeroTarjeta, String nip, String siscardUrl) throws Exception {
        JSONObject jsonSend = new JSONObject(); //json a enviar
        CambioPinResponse response1 = new CambioPinResponse();

        jsonSend.put("country", pais)
                .put("processIdentifier", "cambioPIN")
                .put("numeroTarjeta", numeroTarjeta)
                .put("nip", nip);

        HttpResponse<String> jsonResponse //realizar petición mediante unirest
                = Unirest.post(siscardUrl.concat("/cambioPIN"))
                .header("Content-Type", "application/json")
                .body(jsonSend.toString())
                .asString();

        //capturar respuesta
        JSONObject response = new JSONObject(jsonResponse
                .getBody());
        response1 = new ObjectMapper()
                .readValue(response.toString(), CambioPinResponse.class);

        log.info(new ObjectMapper().writeValueAsString(response1));
        return response1;
    }
}
