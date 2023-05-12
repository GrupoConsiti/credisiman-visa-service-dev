package com.siman.credisiman.visa.service;

import com.siman.credisiman.visa.utils.ConnectionHandler;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class SolicitudTrasladoCompraEnCuotas {
    //private static Logger log = LoggerFactory.getLogger(SolicitudTrasladoCompraEnCuotas.class);
    private static final String namespace = "http://siman.com/SolicitudTrasladoCompraEnCuotas";
    private static final String operationResponse = "ObtenerSolicitudTrasladoCompraEnCuotasResponse";

    public static XmlObject obtenerSolicitudTrasladoCompraEnCuotas(String pais, String identificacion, String numeroTarjeta,
                                                                   String correo, String telefono, String codigoProducto, String codigoPlan,
                                                                   String montoTransaccion, String fechaCompra, String codigoSucursal,
                                                                   String codigoMonedo, String tipoDocumento, String nombre,
                                                                   String remoteJndiSunnel, String remoteJndiOrion,
                                                                   String siscardUrl, String siscardUser, String binCredisiman,
                                                                   String esquemaSunnel, String esquemaOrion, String esquemaEstcta) {
        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();
        int id = 0;
        int resultado = 0;

        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            //log.info("pais required");
            return message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(numeroTarjeta) || utils.validateNotEmpty(numeroTarjeta)) {
            //log.info("numero tarjeta required");
            return message.genericMessage("ERROR", "400", "El campo número tarjeta es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(identificacion) || utils.validateNotEmpty(identificacion)) {
            //log.info("identificacion required");
            return message.genericMessage("ERROR", "400", "El campo identificacion es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(codigoProducto) || utils.validateNotEmpty(codigoProducto)) {
            //log.info("codigoProducto required");
            return message.genericMessage("ERROR", "400", "El campo codigo producto es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(codigoPlan) || utils.validateNotEmpty(codigoPlan)) {
            //log.info("codigoPlan required");
            return message.genericMessage("ERROR", "400", "El campo codigo plan es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(montoTransaccion) || utils.validateNotEmpty(montoTransaccion)) {
            //log.info("montoTransaccion required");
            return message.genericMessage("ERROR", "400", "El campo monto transaccion es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(codigoSucursal) || utils.validateNotEmpty(codigoSucursal)) {
            //log.info("codigoSucursal required");
            return message.genericMessage("ERROR", "400", "El campo codigo sucursal es obligatorio", namespace, operationResponse);
        }

        String query1 = "SELECT MAX(ID_REL_PAYMENT) AS ID FROM " + esquemaOrion + ".T_RELOCATED_PAYMENT ";

        String query2 = "SELECT MAX(ID_REL_PAYMENT) AS ID FROM " + esquemaOrion + ".T_RELOCATED_PAYMENT ";

        String query3 = "SELECT MAX(ID_REL_PAYMENT) AS ID FROM " + esquemaOrion + ".T_RELOCATED_PAYMENT ";

        String query4 = "SELECT MAX(ID_REL_PAYMENT) AS ID FROM " + esquemaOrion + ".T_RELOCATED_PAYMENT ";

        ConnectionHandler connectionHandler = new ConnectionHandler();
        Connection conexion = connectionHandler.getConnection(remoteJndiOrion);
        PreparedStatement sentencia = null;

        try {
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
            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                id = rs.getInt("ID");
            }
            sentencia.close();
            rs.close();
            if (id > 0) {
                String tarjeta = null;
                if (numeroTarjeta != null) {
                    if (numeroTarjeta.length() > 6) {
                        tarjeta = Utils.obtenerTarjeta(numeroTarjeta, 6);
                    }
                }
                //log.info("insertando a la db");
                id++;
                String queryInsertSV = " INSERT INTO " + esquemaOrion + ".T_RELOCATED_PAYMENT " +
                        "   (ID_REL_PAYMENT, COUNTRY_CODE, DOCUMENT_TYPE, DOCUMENT_NUMBER, CARD_DIGITS,  " +
                        "    TELEPHONE, ID_PLANS_PRODUCT, BUY_DATE, BRANCHOFFICEID, COMMENTS,  " +
                        "    STATE, DATE_CREATION, UPDATE_DATE, USER_UPDATE, MONTO,  " +
                        "    EMAIL,  NAME, CURRENCYID) " +
                        " VALUES ( ?, ? , ? , ? , ? ,  " +
                        "    ?, ? , ? , ?, ?,  " + //telefono, producto, fecha de compra, sucursal, comentario
                        "    1, SYSDATE , NULL, NULL, ?,  " + //estado, fecha de creacion, fecha de actualicacion, usuario, monto
                        "    ? , ? , ? ) "; //correo, nombre , codigomoneda

                String queryInsertGT = " INSERT INTO " + esquemaOrion + ".T_RELOCATED_PAYMENT " +
                        "   (ID_REL_PAYMENT, COUNTRY_CODE, DOCUMENT_TYPE, DOCUMENT_NUMBER, CARD_DIGITS,  " +
                        "    TELEPHONE, ID_PLANS_PRODUCT, BUY_DATE, BRANCHOFFICEID, COMMENTS,  " +
                        "    STATE, DATE_CREATION, UPDATE_DATE, USER_UPDATE, MONTO,  " +
                        "    EMAIL,  NAME, CURRENCYID) " +
                        " VALUES ( ?, ? , ? , ? , ? ,  " +
                        "    ?, ? , ? , ?, ?,  " + //telefono, producto, fecha de compra, sucursal, comentario
                        "    1, SYSDATE , NULL, NULL, ?,  " + //estado, fecha de creacion, fecha de actualicacion, usuario, monto
                        "    ? , ? , ? ) "; //correo, nombre , codigomoneda

                String queryInsertNI = " INSERT INTO " + esquemaOrion + ".T_RELOCATED_PAYMENT " +
                        "   (ID_REL_PAYMENT, COUNTRY_CODE, DOCUMENT_TYPE, DOCUMENT_NUMBER, CARD_DIGITS,  " +
                        "    TELEPHONE, ID_PLANS_PRODUCT, BUY_DATE, BRANCHOFFICEID, COMMENTS,  " +
                        "    STATE, DATE_CREATION, UPDATE_DATE, USER_UPDATE, MONTO,  " +
                        "    EMAIL,  NAME, CURRENCYID) " +
                        " VALUES ( ?, ? , ? , ? , ? ,  " +
                        "    ?, ? , ? , ?, ?,  " + //telefono, producto, fecha de compra, sucursal, comentario
                        "    1, SYSDATE , NULL, NULL, ?,  " + //estado, fecha de creacion, fecha de actualicacion, usuario, monto
                        "    ? , ? , ? ) ";

                String queryInsertCR = " INSERT INTO " + esquemaOrion + ".T_RELOCATED_PAYMENT " +
                        "   (ID_REL_PAYMENT, COUNTRY_CODE, DOCUMENT_TYPE, DOCUMENT_NUMBER, CARD_DIGITS,  " +
                        "    TELEPHONE, ID_PLANS_PRODUCT, BUY_DATE, BRANCHOFFICEID, COMMENTS,  " +
                        "    STATE, DATE_CREATION, UPDATE_DATE, USER_UPDATE, MONTO,  " +
                        "    EMAIL,  NAME, CURRENCYID) " +
                        " VALUES ( ?, ? , ? , ? , ? ,  " +
                        "    ?, ? , ? , ?, ?,  " + //telefono, producto, fecha de compra, sucursal, comentario
                        "    1, SYSDATE , NULL, NULL, ?,  " + //estado, fecha de creacion, fecha de actualicacion, usuario, monto
                        "    ? , ? , ? ) ";
                //logica insertar datos

                PreparedStatement preparedStatement = null;

                //probar sacar todos los pametros aqui
                switch (pais) {
                    case "SV":
                        preparedStatement = conexion.prepareStatement(queryInsertSV);
                        preparedStatement.setInt(1, id);
                        preparedStatement.setString(2, pais);
                        preparedStatement.setString(3, tipoDocumento);
                        preparedStatement.setString(4, identificacion);
                        preparedStatement.setString(5, tarjeta);
                        preparedStatement.setString(6, telefono);
                        preparedStatement.setInt(7, Integer.parseInt(codigoProducto));
                        preparedStatement.setDate(8, new java.sql.Date(new SimpleDateFormat("YYYYMMDD").parse(fechaCompra).getTime()));
                        preparedStatement.setString(9, codigoSucursal);
                        preparedStatement.setString(10, "");
                        preparedStatement.setDouble(11, Double.parseDouble(montoTransaccion));
                        preparedStatement.setString(12, correo);
                        preparedStatement.setString(13, nombre);
                        preparedStatement.setInt(14, Integer.parseInt(codigoMonedo));
                        resultado = preparedStatement.executeUpdate();
                        preparedStatement.close();
                        break;
                    case "GT":
                        preparedStatement = conexion.prepareStatement(queryInsertGT);
                        preparedStatement.setInt(1, id);
                        preparedStatement.setString(2, pais);
                        preparedStatement.setString(3, tipoDocumento);
                        preparedStatement.setString(4, identificacion);
                        preparedStatement.setString(5, tarjeta);
                        preparedStatement.setString(6, telefono);
                        preparedStatement.setInt(7, Integer.parseInt(codigoProducto));
                        preparedStatement.setDate(8, new java.sql.Date(new SimpleDateFormat("YYYYMMDD").parse(fechaCompra).getTime()));
                        preparedStatement.setString(9, codigoSucursal);
                        preparedStatement.setString(10, "");
                        preparedStatement.setDouble(11, Double.parseDouble(montoTransaccion));
                        preparedStatement.setString(12, correo);
                        preparedStatement.setString(13, nombre);
                        preparedStatement.setInt(14, Integer.parseInt(codigoMonedo));
                        resultado = preparedStatement.executeUpdate();
                        preparedStatement.close();
                        break;
                    case "NI":
                        preparedStatement = conexion.prepareStatement(queryInsertNI);
                        preparedStatement.setInt(1, id);
                        preparedStatement.setString(2, pais);
                        preparedStatement.setString(3, tipoDocumento);
                        preparedStatement.setString(4, identificacion);
                        preparedStatement.setString(5, tarjeta);
                        preparedStatement.setString(6, telefono);
                        preparedStatement.setInt(7, Integer.parseInt(codigoProducto));
                        preparedStatement.setDate(8, new java.sql.Date(new SimpleDateFormat("YYYYMMDD").parse(fechaCompra).getTime()));
                        preparedStatement.setString(9, codigoSucursal);
                        preparedStatement.setString(10, "");
                        preparedStatement.setDouble(11, Double.parseDouble(montoTransaccion));
                        preparedStatement.setString(12, correo);
                        preparedStatement.setString(13, nombre);
                        preparedStatement.setInt(14, Integer.parseInt(codigoMonedo));
                        resultado = preparedStatement.executeUpdate();
                        preparedStatement.close();
                        break;
                    case "CR":
                        preparedStatement = conexion.prepareStatement(queryInsertCR);
                        preparedStatement.setInt(1, id);
                        preparedStatement.setString(2, pais);
                        preparedStatement.setString(3, tipoDocumento);
                        preparedStatement.setString(4, identificacion);
                        preparedStatement.setString(5, tarjeta);
                        preparedStatement.setString(6, telefono);
                        preparedStatement.setInt(7, Integer.parseInt(codigoProducto));
                        preparedStatement.setDate(8, new java.sql.Date(new SimpleDateFormat("YYYYMMDD").parse(fechaCompra).getTime()));
                        preparedStatement.setString(9, codigoSucursal);
                        preparedStatement.setString(10, "");
                        preparedStatement.setDouble(11, Double.parseDouble(montoTransaccion));
                        preparedStatement.setString(12, correo);
                        preparedStatement.setString(13, nombre);
                        preparedStatement.setInt(14, Integer.parseInt(codigoMonedo));
                        resultado = preparedStatement.executeUpdate();
                        preparedStatement.close();
                        break;
                }

            } else {
                return message.genericMessage("ERROR", "600",
                        "Error general contacte al administrador del sistema...", namespace, operationResponse);
            }

            conexion.commit();
            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
            return message.genericMessage("ERROR", "600",
                    "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }

        if (resultado > 0) {
            XmlObject result = XmlObject.Factory.newInstance();
            XmlCursor cursor = result.newCursor();
            QName responseQName = new QName(namespace, operationResponse);
            cursor.toNextToken();
            cursor.beginElement(responseQName);
            cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
            cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
            cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
            cursor.insertElementWithText(new QName(namespace, "numeroSolicitud"), String.valueOf(id));
            cursor.toParent();
            //log.info("response = [" + result + "]");
            return result;
        } else {
            return message.genericMessage("ERROR", "600",
                    "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }
    }
}
