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

public class EstadoSolicitudTransladoCompraenCuotas {
    //private static Logger log = LoggerFactory.getLogger(EstadoSolicitudTransladoCompraenCuotas.class);
    private static final String namespace = "http://siman.com/EstadoSolicitudTransladoCompraenCuotas";
    private static final String operationResponse = "ObtenerEstadoSolicitudTransladoCompraenCuotasResponse";

    public static XmlObject obtenerEstadoSolicitudTransladoCompraenCuotas(String pais, String numeroSolicitud, String remoteJndiSunnel, String remoteJndiOrion,
                                                                          String siscardUrl, String siscardUser, String binCredisiman,
                                                                          String esquemaSunnel, String esquemaOrion, String esquemaEstcta) {

        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();
        String estado = "";

        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            //log.info("pais required");
            return message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse);
        }

        if (utils.validateNotNull(numeroSolicitud) || utils.validateNotEmpty(numeroSolicitud)) {
            //log.info("numero de solicitud required");
            return message.genericMessage("ERROR", "400", "El campo numero de solicitud es obligatorio", namespace, operationResponse);
        }

        String query1 = "SELECT CASE " +
                "        WHEN (STATE = 1) THEN 'Pendiente' " +
                "        WHEN (STATE = 2) THEN 'Aceptada' " +
                "        ELSE 'Rechazada' " +
                "        END " +
                "        AS STATE, STATE AS ID " +
                "        FROM " + esquemaOrion + ".T_RELOCATED_PAYMENT " +
                "        WHERE ID_REL_PAYMENT = ? ";

        String query2 = "SELECT CASE " +
                "        WHEN (STATE = 1) THEN 'Pendiente' " +
                "        WHEN (STATE = 2) THEN 'Aceptada' " +
                "        ELSE 'Rechazada' " +
                "        END " +
                "        AS STATE, STATE AS ID " +
                "        FROM " + esquemaOrion + ".T_RELOCATED_PAYMENT " +
                "        WHERE ID_REL_PAYMENT = ? ";
        ;

        String query3 = "SELECT CASE " +
                "        WHEN (STATE = 1) THEN 'Pendiente' " +
                "        WHEN (STATE = 2) THEN 'Aceptada' " +
                "        ELSE 'Rechazada' " +
                "        END " +
                "        AS STATE, STATE AS ID " +
                "        FROM " + esquemaOrion + ".T_RELOCATED_PAYMENT " +
                "        WHERE ID_REL_PAYMENT = ? ";

        String query4 = "SELECT CASE " +
                "        WHEN (STATE = 1) THEN 'Pendiente' " +
                "        WHEN (STATE = 2) THEN 'Aceptada' " +
                "        ELSE 'Rechazada' " +
                "        END " +
                "        AS STATE, STATE AS ID " +
                "        FROM " + esquemaOrion + ".T_RELOCATED_PAYMENT " +
                "        WHERE ID_REL_PAYMENT = ? ";

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
            sentencia.setString(1, numeroSolicitud);

            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                estado = rs.getString("STATE");
            }

            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
            return message.genericMessage("ERROR", "600",
                    "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }

        if (!estado.equals("")) {
            XmlObject result = XmlObject.Factory.newInstance();
            XmlCursor cursor = result.newCursor();
            QName responseQName = new QName(namespace, operationResponse);
            cursor.toNextToken();
            cursor.beginElement(responseQName);
            cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
            cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
            cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
            cursor.insertElementWithText(new QName(namespace, "estado"), estado);

            cursor.toParent();
            //log.info("response = [" + result + "]");
            return result;
        } else {
            //log.info("400, No se encontro la solicitud con el identificador");
            return message.genericMessage("ERROR", "400",
                    "No se encontro la solicitud con el identificador: " + numeroSolicitud,
                    namespace, operationResponse);
        }
    }
}
