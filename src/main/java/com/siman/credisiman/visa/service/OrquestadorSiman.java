package com.siman.credisiman.visa.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.email.EmailTo;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

public class OrquestadorSiman {
    //private static Logger log = LoggerFactory.getLogger(OrquestadorSiman.class);
    private static final String namespace = "http://siman.com/OrquestadorSiman";
    private static final String operationResponse = "ObtenerOrquestadorSimanResponse";
    public static XmlObject obtenerOrquestadorSiman(String servicio, String param1, String param2, String param3, String param4, String param5, String param6,
                                                    String param7, String param8, String param9, String param10, String param11, String param12, String param13,
                                                    String param14, String param15, String headerAuthorization, String urlCredisiman) {
        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();

        if (utils.validateNotNull(servicio) || utils.validateNotEmpty(servicio)) {
            return message.genericMessage("ERROR", "400", "El campo del servicio a llamar es obligatorio.", namespace, operationResponse);
        }

        if (utils.validateNotNull(param1) || utils.validateNotEmpty(param1)) {
            return message.genericMessage("ERROR", "400", "Los datos a enviar son obligatorios.", namespace, operationResponse);
        }

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);

        JSONObject jsonSend = new JSONObject();
        HttpResponse<String> jsonResponse;
        JSONObject response, jsonObject;
        JSONArray jsonArray;

        try {
            switch (servicio) {
                case "ListadoSucursales":
                    try {
                        jsonSend.put("pais", param1);
                        jsonResponse = Unirest.post(urlCredisiman.concat("/listadoSucursales"))
                                .header("Content-Type", "application/json")
                                .header("Authorization", headerAuthorization)
                                .body(jsonSend.toString())
                                .asString();
                        response = new JSONObject(jsonResponse.getBody());

                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);

                        jsonArray = response.getJSONArray("sucursales");
                        //log.info(jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            cursor.beginElement(new QName(namespace, "sucursales"));
                            cursor.insertElementWithText(new QName(namespace, "codigoSucursal"), jsonObject.get("codigoSucursal").toString());
                            cursor.insertElementWithText(new QName(namespace, "nombre"), jsonObject.get("nombre").toString());
                            cursor.toParent();
                        }
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "ConsultaDatosCliente":
                    try {
                        jsonSend.put("pais", param1).put("identificacion", param2);
                        jsonResponse = Unirest.post(urlCredisiman.concat("/consultaDatosCliente"))
                                .header("Content-Type", "application/json")
                                .header("Authorization", headerAuthorization)
                                .body(jsonSend.toString())
                                .asString();
                        response = new JSONObject(jsonResponse.getBody().replaceAll("\u200B", ""));

                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);

                        cursor.insertElementWithText(new QName(namespace, "primerNombre"), response.get("primerNombre").toString());
                        cursor.insertElementWithText(new QName(namespace, "segundoNombre"), response.get("segundoNombre").toString());
                        cursor.insertElementWithText(new QName(namespace, "primerApellido"), response.get("primerApellido").toString());
                        cursor.insertElementWithText(new QName(namespace, "segundoApellido"), response.get("segundoApellido").toString());
                        cursor.insertElementWithText(new QName(namespace, "apellidoCasada"), response.get("apellidoCasada").toString());
                        cursor.insertElementWithText(new QName(namespace, "fechaNacimiento"), response.get("fechaNacimiento").toString());
                        cursor.insertElementWithText(new QName(namespace, "tipoIdentificacion"), response.get("tipoIdentificacion").toString());
                        cursor.insertElementWithText(new QName(namespace, "identificacion"), response.get("identificacion").toString());
                        cursor.insertElementWithText(new QName(namespace, "correo"), !response.get("correo").equals(null) ? response.get("correo").toString() : " ");
                        cursor.insertElementWithText(new QName(namespace, "celular"), response.get("celular").toString());
                        cursor.insertElementWithText(new QName(namespace, "lugarTrabajo"), response.get("lugarTrabajo").toString());
                        cursor.insertElementWithText(new QName(namespace, "direccion"), response.get("direccion").toString());
                        cursor.insertElementWithText(new QName(namespace, "direccionPatrono"), response.get("direccionPatrono").toString());
                        cursor.insertElementWithText(new QName(namespace, "correoSiscard"), !response.get("correoSiscard").equals(null) ? response.get("correoSiscard").toString() : " ");
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "ConsultaMovimientos":
                    jsonSend.put("pais", param1).put("numeroTarjeta", param2).put("fechaInicial", param3).put("fechaFinal", param4).put("tipoTarjeta", param5);
                    jsonResponse = Unirest.post(urlCredisiman.concat("/consultaMovimientos"))
                            .header("Content-Type", "application/json")
                            .header("Authorization", headerAuthorization)
                            .body(jsonSend.toString())
                            .asString();
                    response = new JSONObject(jsonResponse.getBody());

                    if (response.get("statusCode").equals("400")) {
                        //log.info(message.genericMessage(response.get("status").toString(), response.get("statusCode").toString(), response.get("statusMessage").toString(), namespace, operationResponse).toString());
                        return message.genericMessage(response.get("status").toString(), response.get("statusCode").toString(), response.get("statusMessage").toString(), namespace, operationResponse);
                    }

                    try {
                        jsonArray = response.getJSONArray("movimientos");
                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            cursor.beginElement(new QName(namespace, "movimientos"));
                            cursor.insertElementWithText(new QName(namespace, "tipoMovimiento"), jsonObject.get("tipoMovimiento").toString());
                            cursor.insertElementWithText(new QName(namespace, "fechaMovimiento"), jsonObject.get("fechaMovimiento").toString());
                            cursor.insertElementWithText(new QName(namespace, "fechaAplicacion"), jsonObject.get("fechaAplicacion").toString());
                            cursor.insertElementWithText(new QName(namespace, "moneda"), jsonObject.get("moneda").toString());
                            cursor.insertElementWithText(new QName(namespace, "monto"), jsonObject.get("monto").toString());
                            cursor.insertElementWithText(new QName(namespace, "numeroAutorizacion"), jsonObject.get("numeroAutorizacion").toString());
                            cursor.insertElementWithText(new QName(namespace, "comercio"), jsonObject.get("comercio").toString());
                            cursor.insertElementWithText(new QName(namespace, "concepto"), jsonObject.get("concepto").toString());
                            cursor.insertElementWithText(new QName(namespace, "tipoMovimientoTH"), jsonObject.get("tipoMovimientoTH").toString());
                            cursor.toParent();
                        }
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "ConsultaSaldoMonedero":
                    try {
                        jsonSend.put("pais", param1).put("numeroTarjeta", param2).put("cuenta", param3)
                                .put("fechaCorte", param4).put("codigoEmisor", param5);
                        jsonResponse = Unirest.post(urlCredisiman.concat("/consultaSaldoMonedero"))
                                .header("Content-Type", "application/json")
                                .header("Authorization", headerAuthorization)
                                .body(jsonSend.toString())
                                .asString();
                        response = new JSONObject(jsonResponse.getBody());

                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);

                        cursor.insertElementWithText(new QName(namespace, "saldoInicial"), response.get("saldoInicial").toString());
                        cursor.insertElementWithText(new QName(namespace, "puntosGanados"), response.get("puntosGanados").toString());
                        cursor.insertElementWithText(new QName(namespace, "puntosCanjeados"), response.get("puntosCanjeados").toString());
                        cursor.insertElementWithText(new QName(namespace, "saldoFinal"), response.get("saldoFinal").toString());
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "ListadoTarjetas":
                    jsonSend.put("pais", param1).put("identificacion", param2);
                    jsonResponse = Unirest.post(urlCredisiman.concat("/listadoTarjetas"))
                            .header("Content-Type", "application/json")
                            .header("Authorization", headerAuthorization)
                            .body(jsonSend.toString())
                            .asString();
                    response = new JSONObject(jsonResponse.getBody());

                    if (response.get("statusCode").equals("400")) {
                        //log.info(message.genericMessage(response.get("status").toString(), response.get("statusCode").toString(), response.get("statusMessage").toString(), namespace, operationResponse).toString());
                        return message.genericMessage(response.get("status").toString(), response.get("statusCode").toString(), response.get("statusMessage").toString(), namespace, operationResponse);
                    }
                    try {
                        jsonArray = response.getJSONArray("tarjetas");

                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            cursor.beginElement(new QName(namespace, "tarjetas"));
                            cursor.insertElementWithText(new QName(namespace, "numeroTarjeta"), jsonObject.get("numeroTarjeta").toString());
                            cursor.insertElementWithText(new QName(namespace, "cuenta"), jsonObject.get("cuenta").toString());
                            cursor.insertElementWithText(new QName(namespace, "tipoTarjeta"), jsonObject.get("tipoTarjeta").toString());
                            cursor.insertElementWithText(new QName(namespace, "nombreTH"), jsonObject.get("nombreTH").toString());
                            cursor.insertElementWithText(new QName(namespace, "estado"), jsonObject.get("estado").toString());
                            cursor.insertElementWithText(new QName(namespace, "limiteCreditoLocal"), jsonObject.get("limiteCreditoLocal").toString());
                            cursor.insertElementWithText(new QName(namespace, "limiteCreditoDolares"), jsonObject.get("limiteCreditoDolares").toString());
                            cursor.insertElementWithText(new QName(namespace, "saldoLocal"), jsonObject.get("saldoLocal").toString());
                            cursor.insertElementWithText(new QName(namespace, "saldoDolares"), jsonObject.get("saldoDolares").toString());
                            cursor.insertElementWithText(new QName(namespace, "disponibleLocal"), jsonObject.get("disponibleLocal").toString());
                            cursor.insertElementWithText(new QName(namespace, "disponibleDolares"), jsonObject.get("disponibleDolares").toString());
                            cursor.insertElementWithText(new QName(namespace, "pagoMinimoLocal"), jsonObject.get("pagoMinimoLocal").toString());
                            cursor.insertElementWithText(new QName(namespace, "pagoMinimoDolares"), jsonObject.get("pagoMinimoDolares").toString());
                            cursor.insertElementWithText(new QName(namespace, "pagoMinimoVencidoLocal"), jsonObject.get("pagoMinimoVencidoLocal").toString());
                            cursor.insertElementWithText(new QName(namespace, "pagoMinimoVencidoDolares"), jsonObject.get("pagoMinimoVencidoDolares").toString());
                            cursor.insertElementWithText(new QName(namespace, "pagoContadoLocal"), jsonObject.get("pagoContadoLocal").toString());
                            cursor.insertElementWithText(new QName(namespace, "pagoContadoDolares"), jsonObject.get("pagoContadoDolares").toString());
                            cursor.insertElementWithText(new QName(namespace, "fechaPago"), jsonObject.get("fechaPago").toString());
                            cursor.insertElementWithText(new QName(namespace, "fechaUltimoCorte"), jsonObject.get("fechaUltimoCorte").toString());
                            cursor.insertElementWithText(new QName(namespace, "saldoMonedero"), jsonObject.get("saldoMonedero").toString());
                            cursor.insertElementWithText(new QName(namespace, "rombosAcumulados"), jsonObject.get("rombosAcumulados").toString());
                            cursor.insertElementWithText(new QName(namespace, "rombosDinero"), jsonObject.get("rombosDinero").toString());
                            cursor.insertElementWithText(new QName(namespace, "fondosReservadosLocal"), jsonObject.get("fondosReservadosLocal").toString());
                            cursor.insertElementWithText(new QName(namespace, "fondosReservadosDolares"), jsonObject.get("fondosReservadosDolares").toString());
                            cursor.toParent();
                        }
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "ConsultaPolizas":

                    jsonSend.put("pais", param1).put("numeroTarjeta", param2).put("tipoTarjeta", param3);
                    jsonResponse = Unirest.post(urlCredisiman.concat("/consultaPolizas"))
                            .header("Content-Type", "application/json")
                            .header("Authorization", headerAuthorization)
                            .body(jsonSend.toString())
                            .asString();
                    response = new JSONObject(jsonResponse.getBody());

                    if (response.get("statusCode").equals("400")) {
                        //log.info(message.genericMessage(response.get("status").toString(), response.get("statusCode").toString(), response.get("statusMessage").toString(), namespace, operationResponse).toString());
                        return message.genericMessage(response.get("status").toString(), response.get("statusCode").toString(), response.get("statusMessage").toString(), namespace, operationResponse);
                    }

                    try {
                        jsonArray = response.getJSONArray("polizas");
                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            cursor.beginElement(new QName(namespace, "polizas"));
                            cursor.insertElementWithText(new QName(namespace, "tipoPoliza"), jsonObject.get("tipoPoliza").toString());
                            cursor.insertElementWithText(new QName(namespace, "nombrePoliza"), jsonObject.get("nombrePoliza").toString());
                            cursor.insertElementWithText(new QName(namespace, "estadoPoliza"), jsonObject.get("estadoPoliza").toString());
                            cursor.toParent();
                        }
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "ListarPlanesPorProducto":
                    try {
                        jsonSend.put("pais", param1).put("codigoProducto", param2);
                        jsonResponse = Unirest.post(urlCredisiman.concat("/listarPlanesPorProducto"))
                                .header("Content-Type", "application/json")
                                .header("Authorization", headerAuthorization)
                                .body(jsonSend.toString())
                                .asString();
                        response = new JSONObject(jsonResponse.getBody());
                        jsonArray = response.getJSONArray("planes");

                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            cursor.beginElement(new QName(namespace, "planes"));
                            cursor.insertElementWithText(new QName(namespace, "codigoPlan"), jsonObject.get("codigoPlan").toString());
                            cursor.insertElementWithText(new QName(namespace, "nombre"), jsonObject.get("nombre").toString());
                            cursor.toParent();
                        }
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "ConsultaEstadoCuenta":
                    if (param2.equals("P")) {
                        try {
                            jsonSend.put("pais", param1).put("numeroTarjeta", "").put("fechaCorte", "")
                                    .put("tipoTarjeta", param2).put("identificacion", "").put("nombreArchivo", param3).put("ruta", param4);
                            jsonResponse = Unirest.post(urlCredisiman.concat("/consultaEstadoCuenta"))
                                    .header("Content-Type", "application/json")
                                    .header("Authorization", headerAuthorization)
                                    .body(jsonSend.toString())
                                    .asString();
                            response = new JSONObject(jsonResponse.getBody());

                            cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                            cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                            cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                            cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);

                            cursor.insertElementWithText(new QName(namespace, "correo"), response.get("correo").toString());
                            cursor.insertElementWithText(new QName(namespace, "archivo"), response.get("archivo").toString());
                            cursor.insertElementWithText(new QName(namespace, "origen"), response.get("origen").toString());
                            cursor.toParent();
                        } catch (Exception e) {
                            return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                        }
                    } else {
                        try {
                            jsonSend.put("pais", param1).put("numeroTarjeta", param2).put("fechaCorte", param3)
                                    .put("tipoTarjeta", param4).put("identificacion", param5).put("nombreArchivo", "").put("ruta", "");
                            jsonResponse = Unirest.post(urlCredisiman.concat("/consultaEstadoCuenta"))
                                    .header("Content-Type", "application/json")
                                    .header("Authorization", headerAuthorization)
                                    .body(jsonSend.toString())
                                    .asString();
                            response = new JSONObject(jsonResponse.getBody());

                            cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                            cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                            cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                            cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);

                            cursor.insertElementWithText(new QName(namespace, "correo"), response.get("correo").toString());
                            cursor.insertElementWithText(new QName(namespace, "archivo"), response.get("archivo").toString());
                            cursor.insertElementWithText(new QName(namespace, "origen"), response.get("origen").toString());
                            cursor.toParent();
                        } catch (Exception e) {
                            return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                        }
                    }
                    break;
                case "ConsultaSubproductos":
                    jsonSend.put("pais", param1).put("numeroTarjeta", param2).put("tipoTarjeta", param3);
                    jsonResponse = Unirest.post(urlCredisiman.concat("/consultaSubproductos"))
                            .header("Content-Type", "application/json")
                            .header("Authorization", headerAuthorization)
                            .body(jsonSend.toString())
                            .asString();
                    response = new JSONObject(jsonResponse.getBody());

                    if (response.get("statusCode").equals("400")) {
                        //log.info(message.genericMessage(response.get("status").toString(), response.get("statusCode").toString(), response.get("statusMessage").toString(), namespace, operationResponse).toString());
                        return message.genericMessage(response.get("status").toString(), response.get("statusCode").toString(), response.get("statusMessage").toString(), namespace, operationResponse);
                    }

                    try {
                        jsonArray = response.getJSONArray("subproductos");
                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            cursor.beginElement(new QName(namespace, "subproductos"));
                            cursor.insertElementWithText(new QName(namespace, "tipoSubproducto"), jsonObject.get("tipoSubproducto").toString());
                            cursor.insertElementWithText(new QName(namespace, "fechaCompra"), jsonObject.get("fechaCompra").toString());
                            cursor.insertElementWithText(new QName(namespace, "moneda"), jsonObject.get("moneda").toString());
                            cursor.insertElementWithText(new QName(namespace, "montoCompra"), jsonObject.get("montoCompra").toString());
                            cursor.insertElementWithText(new QName(namespace, "montoCuota"), jsonObject.get("montoCuota").toString());
                            cursor.insertElementWithText(new QName(namespace, "fechaPago"), jsonObject.get("fechaPago").toString());
                            cursor.insertElementWithText(new QName(namespace, "saldoActual"), jsonObject.get("saldoActual").toString());
                            cursor.toParent();
                        }
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "ListadoEstadosCuenta":
                    try {
                        jsonSend.put("pais", param1).put("numeroTarjeta", param2).put("tipoTarjeta", param3).put("identificacion", param4);
                        //log.info(jsonSend.toString());
                        jsonResponse = Unirest.post(urlCredisiman.concat("/listadoEstadosCuenta"))
                                .header("Content-Type", "application/json")
                                .header("Authorization", headerAuthorization)
                                .body(jsonSend.toString())
                                .asString();
                        response = new JSONObject(jsonResponse.getBody());
                        //log.info(response.toString());
                        jsonArray = response.getJSONArray("fechasCorte");
                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            cursor.beginElement(new QName(namespace, "fechasCorte"));
                            cursor.insertElementWithText(new QName(namespace, "fechaCorte"), jsonObject.get("fechaCorte").toString());
                            cursor.insertElementWithText(new QName(namespace, "nombreArchivo"), jsonObject.get("nombreArchivo").toString());
                            cursor.insertElementWithText(new QName(namespace, "ruta"), jsonObject.get("ruta").toString());
                            cursor.toParent();
                        }
                        cursor.insertElementWithText(new QName(namespace, "origen"), response.get("origen").toString());
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "BloqueoDesbloqueoTarjeta":
                    try {
                        jsonSend.put("pais", param1).put("numeroTarjeta", param2).put("estadoDeseado", param3)
                                .put("motivo", param4).put("tipoTarjeta", param5);
                        jsonResponse = Unirest.post(urlCredisiman.concat("/bloqueoDesbloqueoTarjeta"))
                                .header("Content-Type", "application/json")
                                .header("Authorization", headerAuthorization)
                                .body(jsonSend.toString())
                                .asString();
                        response = new JSONObject(jsonResponse.getBody());
                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "CambioFechaCorte":
                    try {
                        jsonSend.put("pais", param1).put("numeroTarjeta", param2).put("cuenta", param3)
                                .put("diaCorte", param4).put("tipoTarjeta", param5);
                        jsonResponse = Unirest.post(urlCredisiman.concat("/cambioFechaCorte"))
                                .header("Content-Type", "application/json")
                                .header("Authorization", headerAuthorization)
                                .body(jsonSend.toString())
                                .asString();
                        response = new JSONObject(jsonResponse.getBody());
                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "CambioPIN":
                    try {
                        jsonSend.put("pais", param1).put("numeroTarjeta", param2).put("nip", param3)
                                .put("tipoTarjeta", param4);
                        jsonResponse = Unirest.post(urlCredisiman.concat("/cambioPIN"))
                                .header("Content-Type", "application/json")
                                .header("Authorization", headerAuthorization)
                                .body(jsonSend.toString())
                                .asString();
                        response = new JSONObject(jsonResponse.getBody());
                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "ModificarLimiteTarjeta":
                    try {
                        jsonSend.put("pais", param1).put("numeroTarjeta", param2).put("monto", param3)
                                .put("tipoTarjeta", param4);
                        jsonResponse = Unirest.post(urlCredisiman.concat("/modificarLimiteTarjeta"))
                                .header("Content-Type", "application/json")
                                .header("Authorization", headerAuthorization)
                                .body(jsonSend.toString())
                                .asString();
                        response = new JSONObject(jsonResponse.getBody());
                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "RegistroUsuario":
                    try {
                        jsonSend.put("pais", param1).put("primerNombre", param2).put("segundoNombre", param3).put("primerApellido", param4)
                                .put("segundoApellido", param5).put("apellidoCasada", param6).put("paisResidencia", param7).put("fechaNacimiento", param8)
                                .put("tipoDocumento", param9).put("numeroDocumento", param10).put("correo", param11).put("celular", param12)
                                .put("correoNotificacion", param13).put("celularNotificacion", param14);
                        jsonResponse = Unirest.post(urlCredisiman.concat("/registroUsuario"))
                                .header("Content-Type", "application/json")
                                .header("Authorization", headerAuthorization)
                                .body(jsonSend.toString())
                                .asString();
                        response = new JSONObject(jsonResponse.getBody());
                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "SolicitudReposicionTarjeta":
                    try {
                        jsonSend.put("pais", param1).put("numeroTarjeta", param2).put("nombreEmbozar", param3)
                                .put("razonReposicion", param4).put("tipoTarjeta", param5);
                        jsonResponse = Unirest.post(urlCredisiman.concat("/solicitudReposicionTarjeta"))
                                .header("Content-Type", "application/json")
                                .header("Authorization", headerAuthorization)
                                .body(jsonSend.toString())
                                .asString();
                        response = new JSONObject(jsonResponse.getBody());
                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);

                        cursor.insertElementWithText(new QName(namespace, "nuevoNumeroTarjeta"), response.get("nuevoNumeroTarjeta").toString());
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "SolicitudTrasladoCompraEnCuotas":
                    try {
                        jsonSend.put("pais", param1).put("identificacion", param2).put("numeroTarjeta", param3).put("correo", param4).put("telefono", param5)
                                .put("codigoProducto", param6).put("codigoPlan", param7).put("montoTransaccion", param8).put("fechaCompra", param9)
                                .put("codigoSucursal", param10).put("codigoMoneda", param11).put("tipoDocumento", param12).put("nombre", param13);
                        jsonResponse = Unirest.post(urlCredisiman.concat("/solicitudTrasladoCompraEnCuotas"))
                                .header("Content-Type", "application/json")
                                .header("Authorization", headerAuthorization)
                                .body(jsonSend.toString())
                                .asString();
                        response = new JSONObject(jsonResponse.getBody());
                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);

                        cursor.insertElementWithText(new QName(namespace, "numeroSolicitud"), response.get("numeroSolicitud").toString());
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "EnvioCorreoElectronico":
                    try {
                        JSONArray jsonArray1 = new JSONArray();
                        String lista[] = param15.split(";");
                        String usuario[];
                        List<EmailTo> correos = new ArrayList<>();
                        EmailTo emailT = new EmailTo();

                        for (String s : lista) {
                            usuario = s.split(",");
                            emailT = new EmailTo();
                            emailT.setName(usuario[1].trim());
                            emailT.setEmail(usuario[0].trim());
                            correos.add(emailT);
                        }
                        //log.info(correos.toString());
                        correos.forEach(emailTo -> {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("email", emailTo.getEmail()).put("name", emailTo.getName());
                            jsonArray1.put(jsonObject1);
                        });
                        //log.info(jsonArray1.toString());
                        jsonSend.put("correoOrigen", param1).put("nombreOrigen", param2).put("asunto", param3)
                                .put("flagImportante", (param4.equals("true") ? true : false)).put("html", param5)
                                .put("correosDestino", jsonArray1);
                        //log.info(jsonSend.toString());

                        jsonResponse = Unirest.post(urlCredisiman.concat("/EnvioCorreoElectronico"))
                                .header("Content-Type", "application/json")
                                .header("Authorization", headerAuthorization)
                                .body(jsonSend.toString())
                                .asString();
                        response = new JSONObject(jsonResponse.getBody());
                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);
                        cursor.toParent();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return message.genericMessage("ERROR", "600", "No se pudo completar la operación, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "EstadoSolicitudTransladoCompraenCuotas":
                    try {
                        jsonSend.put("pais", param1).put("numeroSolicitud", param2);
                        jsonResponse = Unirest.post(urlCredisiman.concat("/estadoSolicitudTransladoCompraenCuotas"))
                                .header("Content-Type", "application/json")
                                .header("Authorization", headerAuthorization)
                                .body(jsonSend.toString())
                                .asString();
                        response = new JSONObject(jsonResponse.getBody());
                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);

                        cursor.insertElementWithText(new QName(namespace, "estado"), response.get("estado").toString());
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                case "ListadoProductos":
                    try {
                        jsonSend.put("pais", param1);
                        jsonResponse = Unirest.post(urlCredisiman.concat("/listadoProductos"))
                                .header("Content-Type", "application/json")
                                .header("Authorization", headerAuthorization)
                                .body(jsonSend.toString())
                                .asString();
                        response = new JSONObject(jsonResponse.getBody());

                        cursor.insertElementWithText(new QName(namespace, "statusCode"), response.get("statusCode").toString());
                        cursor.insertElementWithText(new QName(namespace, "status"), response.get("status").toString());
                        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response.get("statusMessage").toString());
                        cursor.insertElementWithText(new QName(namespace, "servicio"), servicio);

                        jsonArray = response.getJSONArray("productos");
                        //log.info(jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            cursor.beginElement(new QName(namespace, "productos"));
                            cursor.insertElementWithText(new QName(namespace, "codigoProducto"), jsonObject.get("codigoProducto").toString());
                            cursor.insertElementWithText(new QName(namespace, "descripcion"), jsonObject.get("descripcion").toString());
                            cursor.toParent();
                        }
                        cursor.toParent();
                    } catch (Exception e) {
                        return message.genericMessage("ERROR", "600", "No se pudo completar la peticion, contacte al administrador del sistema.", namespace, operationResponse);
                    }
                    break;
                default:
                    //log.info(message.genericMessage("ERROR", "400", "El servicio solicitado no existe.", namespace, operationResponse).toString());
                    return message.genericMessage("ERROR", "400", "El servicio solicitado no existe.", namespace, operationResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return message.genericMessage("ERROR", "600",
                    "Error general contacte al administrador del sistema ...", namespace, operationResponse);
        }
        //log.info(result.toString());
        return result;
    }
}