package com.siman.credisiman.visa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import com.siman.credisiman.visa.dto.email.EmailTo;
import com.siman.credisiman.visa.dto.email.MandrilResponse;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;


public class EnvioCorreoElectronico {

    private static Logger log = LoggerFactory.getLogger(EnvioCorreoElectronico.class);
    private static final String namespace = "http://siman.com/EnvioCorreoElectronico";
    private static final String operationResponse = "ObtenerEnvioCorreoElectronicoResponse";

    private static final String url="https://mandrillapp.com/api/1.0/messages/send.json";

    private static final String mandrilTag="NotificacionCredisiman";

    private  static final String apiKey = "md-jHsAPNoQFFypCiypPY0Flw";

    public static XmlObject send(String correoOrigen, String nombreOrigen, String asunto,
                                 boolean flagImportante, String html, String listaCorreos) {
        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();

        if (utils.validateNotNull(correoOrigen) || utils.validateNotEmpty(correoOrigen)) {
            log.info("correo origen required");
            return message.genericMessage("ERROR", "025", "El campo correo origen es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(nombreOrigen) || utils.validateNotEmpty(nombreOrigen)) {
            log.info("nombre origen required");
            return message.genericMessage("ERROR", "025", "El campo nombre origen es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(asunto) || utils.validateNotEmpty(asunto)) {
            log.info("asunto required");
            return message.genericMessage("ERROR", "025", "El campo asunto es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(html) || utils.validateNotEmpty(html)) {
            log.info("html required");
            return message.genericMessage("ERROR", "025", "El campo html es obligatorio", namespace, operationResponse);
        }

        try {

            String lista[] = listaCorreos.split(";");
            String usuario[];
            List<EmailTo> correos= new ArrayList<>();
            EmailTo emailT = new EmailTo();

            for (String s: lista) {
                usuario =  s.split(",");

                emailT = new EmailTo();
                emailT.setName(usuario[1].trim());
                emailT.setEmail(usuario[0].trim());
                correos.add(emailT);
            }

            JSONObject json = new JSONObject();//raiz
            JSONObject msg = new JSONObject();
            JSONArray to = new JSONArray();
            JSONArray tags = new JSONArray();


            json.put("key", apiKey)
                    .put("message", msg);

            msg.put("html", html)
                    .put("subject", asunto)
                    .put("from_email", correoOrigen)
                    .put("from_name", nombreOrigen)
                    .put("important", flagImportante);

            correos.forEach(emailTo -> {
                JSONObject dest = new JSONObject();
                dest.put("email", emailTo.getEmail())
                        .put("name", emailTo.getName())
                        .put("type", "to");
                to.put(dest);
            });

            msg.put("to", to);
            tags.put(mandrilTag);
            msg.put("tags", tags);

            log.info(json.toString());

            HttpResponse<String> jsonResponse //realizar petición demiante unirest
                    = Unirest.post(url)
                    .header("Content-Type", "application/json")
                    .body(json.toString())
                    .asString();

            //capturar respuesta
            MandrilResponse response1;
            log.info(jsonResponse
                    .getBody().replace("[","").replace("]",""));
            JSONObject response = new JSONObject(jsonResponse
                    .getBody().replace("[","").replace("]",""));
            response1 = new ObjectMapper()
                    .readValue(response.toString(), MandrilResponse.class);

            log.info("respuesta: " + new ObjectMapper().writeValueAsString(response1));

            String statusCode;
            String status;
            String statusMessage;

            //evaluar resultados
            switch (response1.getStatus()) {
                case "error":
                    statusCode = "500";
                    status = "ERROR";
                    statusMessage = response1.getMessage();
                    break;
                case "queued":
                    statusCode = "500";
                    status = "ERROR";
                    statusMessage = response1.getQueued_reason();
                    break;
                case "invalid":
                    statusCode = "500";
                    status = "ERROR";
                    statusMessage = response1.getQueued_reason();
                    break;
                default:
                    statusCode = "00";
                    status = "SUCCESS";
                    statusMessage = "Proceso exitoso";
                    break;
            }

            //RESPUESTAS
            XmlObject result = XmlObject.Factory.newInstance();
            XmlCursor cursor = result.newCursor();
            QName responseQName = new QName(namespace, operationResponse);
            cursor.toNextToken();
            cursor.beginElement(responseQName);
            cursor.insertElementWithText(new QName(namespace, "statusCode"), statusCode);
            cursor.insertElementWithText(new QName(namespace, "status"), status);
            cursor.insertElementWithText(new QName(namespace, "statusMessage"), statusMessage);
            cursor.toParent();

            log.info("obtenerEnvioCorreoElectronico response = [" + result + "]");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }
    }
}
