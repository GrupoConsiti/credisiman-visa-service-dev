package com.siman.credisiman.visa.service;

import com.siman.credisiman.visa.dto.productos.Producto;
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

public class ListadoProductos {
    //private static final Logger log = LoggerFactory.getLogger(ListadoProductos.class);
    private static final String namespace = "http://siman.com/ListadoProductos";
    private static final String operationResponse = "ObtenerListadoProductosResponse";

    public static XmlObject obtenerListadoProductos(String pais, String remoteJndiSunnel, String remoteJndiOrion,
                                                    String siscardUrl, String siscardUser, String binCredisiman,
                                                    String esquemaSunnel, String esquemaOrion, String esquemaEstcta) {

        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();
        List<Producto> listadoProductos = new ArrayList<>();

        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            //log.info("pais required");
            return message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse);
        }

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");

        String query1 = "SELECT TPRODUCTID, DESCRIPTION FROM " + esquemaOrion + ".T_CTPRODUCT WHERE STATE = 1";

        String query2 = "SELECT TPRODUCTID, DESCRIPTION FROM " + esquemaOrion + ".T_CTPRODUCT WHERE STATE = 1";

        String query3 = "SELECT TPRODUCTID, DESCRIPTION FROM " + esquemaOrion + ".T_CTPRODUCT WHERE STATE = 1";

        String query4 = "SELECT TPRODUCTID, DESCRIPTION FROM " + esquemaOrion + ".T_CTPRODUCT WHERE STATE = 1";

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
                default:
                    return message.genericMessage("ERROR", "400",
                            "El país ingresado no coincide.", namespace, operationResponse);
            }

            ResultSet rs = sentencia.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setCodigoProducto(rs.getString("TPRODUCTID"));
                producto.setDescripcion(rs.getString("DESCRIPTION"));
                listadoProductos.add(producto);
            }
            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
            return message.genericMessage("ERROR", "600",
                    "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }
        if (listadoProductos.size() > 0) {
            //log.info("PRODUCTOS ENCONTRADOS: " + listadoProductos.size());
            for (int i = 0; i < listadoProductos.size(); i++) {
                cursor.beginElement(new QName(namespace, "productos"));
                cursor.insertElementWithText(new QName(namespace, "codigoProducto"), listadoProductos.get(i).getCodigoProducto());
                cursor.insertElementWithText(new QName(namespace, "descripcion"), listadoProductos.get(i).getDescripcion());
                cursor.toParent();
            }

            cursor.toParent();
            //log.info("response = [" + result + "]");
            return result;
        } else {
            return message.genericMessage("ERROR", "400",
                    "No se encontraron productos disponibles", namespace, operationResponse);

        }
    }
}
