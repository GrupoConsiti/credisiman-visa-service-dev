package com.siman.credisiman.visa.service;

import com.siman.credisiman.visa.dto.planes.Plan;
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
import java.util.ArrayList;
import java.util.List;

public class ListarPlanesPorProducto {
    //private static Logger log = LoggerFactory.getLogger(ListarPlanesPorProducto.class);
    private static final String namespace = "http://siman.com/ListarPlanesPorProducto";
    private static final String operationResponse = "ObtenerListarPlanesPorProductoResponse";

    public static XmlObject obtenerListarPlanesPorProducto(String pais, String codigoProducto,
                                                           String remoteJndiSunnel, String remoteJndiOrion,
                                                           String siscardUrl, String siscardUser, String binCredisiman,
                                                           String esquemaSunnel, String esquemaOrion, String esquemaEstcta) {

        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();
        List<Plan> listadoPlanes = new ArrayList<>();

        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            //log.info("pais required");
            return message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(codigoProducto) || utils.validateNotEmpty(codigoProducto)) {
            //log.info("codigoProducto required");
            return message.genericMessage("ERROR", "400", "El campo codigo producto es obligatorio", namespace, operationResponse);
        }

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");

        String query1 = " SELECT TP.ID_PLAN, TP.NAME_PLAN FROM " + esquemaOrion + ".T_PLAN TP, " + esquemaOrion + ".T_PLANS_PRODUCTS TPP " +
                "        WHERE TP.ID_PLAN = TPP.ID_PLAN  AND TPP.ID_TPRODUCT = ?  ";

        String query2 = " SELECT TP.ID_PLAN, TP.NAME_PLAN FROM " + esquemaOrion + ".T_PLAN TP, " + esquemaOrion + ".T_PLANS_PRODUCTS TPP " +
                "        WHERE TP.ID_PLAN = TPP.ID_PLAN  AND TPP.ID_TPRODUCT = ? ";

        String query3 = " SELECT TP.ID_PLAN, TP.NAME_PLAN FROM " + esquemaOrion + ".T_PLAN TP, " + esquemaOrion + ".T_PLANS_PRODUCTS TPP " +
                "        WHERE TP.ID_PLAN = TPP.ID_PLAN  AND TPP.ID_TPRODUCT = ? ";

        String query4 = " SELECT TP.ID_PLAN, TP.NAME_PLAN FROM " + esquemaOrion + ".T_PLAN TP, " + esquemaOrion + ".T_PLANS_PRODUCTS TPP " +
                "        WHERE TP.ID_PLAN = TPP.ID_PLAN  AND TPP.ID_TPRODUCT = ? ";

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
            sentencia.setString(1, codigoProducto);
            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                Plan plan = new Plan();
                plan.setCodigoPlan(rs.getString("ID_PLAN"));
                plan.setNombre(rs.getString("NAME_PLAN"));
                listadoPlanes.add(plan);
            }

            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
            return message.genericMessage("ERROR", "600",
                    "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }
        if (listadoPlanes.size() > 0) {
            for (int i = 0; i < listadoPlanes.size(); i++) {
                cursor.beginElement(new QName(namespace, "sucursales"));
                cursor.insertElementWithText(new QName(namespace, "codigoSucursal"), listadoPlanes.get(i).getCodigoPlan());
                cursor.insertElementWithText(new QName(namespace, "nombre"), listadoPlanes.get(i).getNombre());
                cursor.toParent();
            }

            for (int i = 0; i < listadoPlanes.size(); i++) {
                cursor.beginElement(new QName(namespace, "planes"));
                cursor.insertElementWithText(new QName(namespace, "codigoPlan"), listadoPlanes.get(i).getCodigoPlan());
                cursor.insertElementWithText(new QName(namespace, "nombre"), listadoPlanes.get(i).getNombre());
                cursor.toParent();
            }

            cursor.toParent();
            //log.info("response = [" + result + "]");
            return result;
        } else {
            return message.genericMessage("ERROR", "400",
                    "No se encontraron planes disponibles con el producto " + codigoProducto, namespace, operationResponse);

        }
    }
}
