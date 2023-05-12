package com.siman.credisiman.visa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.crm.LoginResponse;
import com.siman.credisiman.visa.dto.crm.Productos;
import com.siman.credisiman.visa.dto.crm.ValidateToken;
import com.siman.credisiman.visa.dto.subproductos.ConsultaSubproductosResponse;
import com.siman.credisiman.visa.dto.subproductos.SubProducto;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

public class ConsultaSubproductos {
    //private static Logger log = LoggerFactory.getLogger(ConsultaSubproductos.class);
    private static final String namespace = "http://siman.com/ConsultaSubproductos";
    private static final String operationResponse = "ObtenerConsultaSubproductosResponse";

    public static XmlObject obtenerConsultaSubproductos(String pais, String numeroTarjeta, String remoteJndiSunnel,
                                                        String remoteJndiOrion, String siscardUrl, String siscardUser,
                                                        String binCredisiman, String tipoTarjeta,
                                                        String urlCrm, String usuarioCrm, String passwordCrm) {

        Utils utils = new Utils();
        Message message = new Message();

        //validar campos requeridos
        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            return message.genericMessage("ERROR", "025", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(numeroTarjeta) || utils.validateNotEmpty(numeroTarjeta)) {
            return message.genericMessage("ERROR", "025", "El campo número tarjeta es obligatorio", namespace, operationResponse);
        }

        //validar longitudes
        if (!utils.validateLongitude(pais, 3)) {
            return message.genericMessage("ERROR", "025", "La longitud del campo pais debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(numeroTarjeta, 16)) {
            return message.genericMessage("ERROR", "025", "La longitud del campo número tarjeta debe ser menor o igual a 16", namespace, operationResponse);
        }
        //OBTENER DATOS TARJETA CREDISIMAN
        try {
            switch (tipoTarjeta) {
                case "P":
                    //datos tarjeta privada
                    //log.info("tipo tarjeta: Privada");
                    ConsultaSubproductosResponse response1 = null;
                    response1 = obtenerSubProductosPrivada(usuarioCrm, passwordCrm, urlCrm, numeroTarjeta, pais);
                    if (response1 != null) {
                        return estructura(response1);
                    } else {
                        return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
                    }

                case "V":
                    //log.info("tipo tarjeta: Visa");
                    //datos tarjeta visa
                    ConsultaSubproductosResponse response2 = obtenerDatosSiscard(pais, numeroTarjeta, siscardUrl);
                    if (response2.getCode().equals("OSB-380000")) {
                        //log.info(message.genericMessage("ERROR", "400",
                        //"Error de comunicación con siscard: " + response2.getMessage(), namespace, operationResponse).toString());
                        return message.genericMessage("ERROR", "400",
                                "Error de comunicación con siscard: " + response2.getMessage(), namespace, operationResponse);
                    }

                    if (response2 != null) {
                        return estructura(response2);
                    } else {
                        return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //log.info(e.getMessage());
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }

        //log.info("NOT FOUND");
        return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
    }

    public static XmlObject estructura(ConsultaSubproductosResponse response1) {
        //OBTENER DATOS
        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();

        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), response1.getStatusCode());
        cursor.insertElementWithText(new QName(namespace, "status"), response1.getStatus());
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response1.getStatusMessage());

        for (int i = 0; i < response1.subproductos.size(); i++) {
            SubProducto subproductos = response1.subproductos.get(i);
            cursor.beginElement(new QName(namespace, "subproductos"));
            cursor.insertElementWithText(new QName(namespace, "tipoSubproducto"), subproductos.getTipoSubProducto());
            cursor.insertElementWithText(new QName(namespace, "fechaCompra"), subproductos.getFechaCreacion());
            cursor.insertElementWithText(new QName(namespace, "moneda"), subproductos.getCodigoMoneda());
            cursor.insertElementWithText(new QName(namespace, "montoCompra"), subproductos.getMontoInicial());
            cursor.insertElementWithText(new QName(namespace, "montoCuota"), subproductos.getMontoCuotaActual());
            cursor.insertElementWithText(new QName(namespace, "fechaPago"), subproductos.getFechaFinalizacion());
            cursor.insertElementWithText(new QName(namespace, "saldoActual"), subproductos.getSaldoActual());
            cursor.toParent();
        }
        cursor.toParent();
        //log.info("obtenerSubproductos response = [" + result + "]");
        return result;
    }

    public static ConsultaSubproductosResponse obtenerDatosSiscard(String pais, String numeroTarjeta,
                                                                   String siscardUrl) throws Exception {

        JSONObject jsonSend = new JSONObject(); //json a enviar
        jsonSend.put("country", pais)
                .put("processIdentifier", "ConsultaSubProductos")
                .put("tipoMensaje", 3000)
                .put("codigoEmisor", "")
                .put("numeroCuenta", "")
                .put("numeroTarjeta", numeroTarjeta);

        HttpResponse<String> jsonResponse //realizar petición demiante unirest
                = Unirest.post(siscardUrl.concat("/ConsultaSubProductosIntraExtra"))
                .header("Content-Type", "application/json")
                .body(jsonSend.toString())
                .asString();

        //capturar respuesta
        JSONObject response = new JSONObject(jsonResponse
                .getBody());
        ConsultaSubproductosResponse response1 = new ObjectMapper()
                .readValue(response.toString(), ConsultaSubproductosResponse.class);
        return response1;
    }

    public static ConsultaSubproductosResponse obtenerSubProductosPrivada(String usuarioCrm, String passwordCrm,
                                                                          String urlCrm, String numeroTarjeta,
                                                                          String pais) throws Exception {

        LoginResponse response = new LoginResponse();
        response = loginCrm(usuarioCrm, passwordCrm, urlCrm);
        ValidateToken validation = new ValidateToken();
        Productos subproductos = new Productos();

        if (response.getAccess_token() != null) {
            validation = ValidateloginCrm(response.getAccess_token(), urlCrm);
            if (validation.getResponse().getError_message().equals("Sucess")) {
                //log.info("token valid");
                subproductos = consultaproductosCrm(pais, numeroTarjeta, response.getAccess_token(), urlCrm);

                if (subproductos.getErrorMessage().equals("No data found")) {
                    return null;
                }
                if (subproductos.getErrorMessage().equals("Bad request.Tarjeta Invalida")) {
                    return null;
                }

                ConsultaSubproductosResponse consultaSubproductosResponse = new ConsultaSubproductosResponse();
                List<SubProducto> subProductosList = new ArrayList<>();

                //VALIDAR SI LA RESPUESTA DEVUELVE PRODUCTOS
                if (subproductos.getOtherProducts().size() > 0) {
                    consultaSubproductosResponse.setStatus("SUCCESS");
                    consultaSubproductosResponse.setStatusMessage("Proceso exitoso");
                    consultaSubproductosResponse.setStatusCode("00");
                }

                for (int i = 0; i < subproductos.getOtherProducts().size(); i++) {
                    SubProducto subProducto = new SubProducto();
                    subProducto.setTipoSubProducto(subproductos.getOtherProducts().get(i).getTipoDePoliza());
                    subProducto.setFechaCreacion(subproductos.getOtherProducts().get(i).getFechaDeInclusion());
                    subProducto.setCodigoMoneda(subproductos.getOtherProducts().get(i).getMoneda());
                    subProducto.setMontoInicial(subproductos.getOtherProducts().get(i).getPrecio());
                    subProductosList.add(subProducto);
                }

                consultaSubproductosResponse.setSubproductos(subProductosList);
                return consultaSubproductosResponse;
            } else {
                //log.info("invalid token");
                subproductos = new Productos();
                subproductos.setErrorMessage(validation.getResponse().getError_message());
                subproductos.setErrorMessage(validation.getResponse().getError_code());
                return null;
            }
        }
        return null;
    }

    public static Productos consultaproductosCrm(String pais, String numeroTarjeta, String token, String url)
            throws Exception {
        JSONObject jsonSend = new JSONObject(); //json a enviar
        jsonSend.put("numeroTarjeta", numeroTarjeta)
                .put("token", token)
                .put("pais", pais);

        HttpResponse<String> jsonResponse //realizar petición demiante unirest
                = Unirest.post(url.concat("/crm/api/v1.0.0/arca/getProducts"))
                .header("Content-Type", "application/json")
                .body(jsonSend.toString())
                .asString();

        //capturar respuesta
        JSONObject response = new JSONObject(jsonResponse.getBody());

        return new ObjectMapper().readValue(response.toString(), Productos.class);
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