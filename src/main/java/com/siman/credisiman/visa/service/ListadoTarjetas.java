package com.siman.credisiman.visa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.listadotarjeta.CuentasResponse;
import com.siman.credisiman.visa.dto.listadotarjeta.ListadoTarjetasResponse;
import com.siman.credisiman.visa.dto.listadotarjeta.Tarjetas;
import com.siman.credisiman.visa.dto.listadotarjeta.TarjetasResponse;
import com.siman.credisiman.visa.utils.ConnectionHandler;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Base64;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ListadoTarjetas {
    //private static Logger log = LoggerFactory.getLogger(ListadoTarjetas.class);
    private static final String namespace = "http://siman.com/ListadoTarjetas";
    private static final String operationResponse = "ObtenerListadoTarjetasResponse";

    public static Connection conexion;
    public static PreparedStatement sentencia = null;
    public static ResultSet rs;

    public static XmlObject obtenerListadoTarjetas(String pais, String identificacion, String remoteJndiSunnel,
                                                   String remoteJndiOrion, String siscardUrl, String siscardUser,
                                                   String binCredisiman, String esquemaSunnel, String esquemaOrion, String esquemaEstcta,
                                                   String usuarioSaldoWS, String claveSaldoWS, String ipSaldoWS,
                                                   String cadenaSaldoWS, String opcionSaldoWS, String consultaSaldosWSUrl) {
        Utils utils = new Utils();
        Message message = new Message();

        //validar campos requeridos
        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            return message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(identificacion) || utils.validateNotEmpty(identificacion)) {
            return message.genericMessage("ERROR", "400", "El campo identificacion es obligatorio", namespace, operationResponse);
        }

        //validar longitudes
        if (!utils.validateLongitude(pais, 3)) {
            //log.info("pais, size overload");
            return message.genericMessage("ERROR", "400", "La longitud del campo pais debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(identificacion, 19)) {
            //log.info("identificacion, size overload");
            return message.genericMessage("ERROR", "400", "La longitud del campo identificacion debe ser menor o igual a 19", namespace, operationResponse);
        }

        try {
            //all code here
            List<Tarjetas> response2 = obtenerDatosArca(identificacion, remoteJndiSunnel, pais, esquemaSunnel,
                    usuarioSaldoWS, claveSaldoWS, ipSaldoWS, cadenaSaldoWS, opcionSaldoWS, consultaSaldosWSUrl);
            List<Tarjetas> response3 = null;
            response3 = obtenerDatosSiscard(pais, identificacion, siscardUrl);

            if (response2 != null && response3 != null) {
                //log.info("ALL");
                response2.addAll(response3);
                return estructura(response2);
            }
            if (response2 != null) {
                //log.info("ARCA");
                return estructura(response2);
            }

            if (response3 != null) {
                //log.info("tarjetas evertec: " + response3.size());
                //log.info("EVERTEC");
                return estructura(response3);
            }

            //log.info(message.genericMessage("ERROR", "400", "La consulta no devolvio resultados.", namespace, operationResponse).toString());
            return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados.", namespace, operationResponse);
        } catch (SQLException e) {
            e.printStackTrace();
            //log.info("ObtenerListadoTarjetas response = [" + message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse) + "]");
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse);
        } catch (NullPointerException nul) {
            nul.printStackTrace();
            return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
            //log.info("ObtenerListadoTarjetas response = [" + message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse) + "]");
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }
    }

    public static XmlObject estructura(List<Tarjetas> response) {
        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);

        cursor.toNextToken();
        cursor.beginElement(responseQName);

        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        //Listado tarjetas

        for (Tarjetas tarjetas : response) {
            cursor.beginElement(new QName(namespace, "tarjetas"));
            cursor.insertElementWithText(new QName(namespace, "numeroTarjeta"), tarjetas.getNumeroTarjeta());
            cursor.insertElementWithText(new QName(namespace, "cuenta"), tarjetas.getCuenta());
            cursor.insertElementWithText(new QName(namespace, "tipoTarjeta"), tarjetas.getTipoTarjeta());
            cursor.insertElementWithText(new QName(namespace, "nombreTH"), tarjetas.getNombreTH());
            cursor.insertElementWithText(new QName(namespace, "estado"), tarjetas.getEstado());
            cursor.insertElementWithText(new QName(namespace, "limiteCreditoLocal"), tarjetas.getLimiteCreditoLocal());
            cursor.insertElementWithText(new QName(namespace, "limiteCreditoDolares"), tarjetas.getLimiteCreditoDolares());
            cursor.insertElementWithText(new QName(namespace, "saldoLocal"), tarjetas.getSaldoLocal());
            cursor.insertElementWithText(new QName(namespace, "saldoDolares"), tarjetas.getSaldoDolares());
            cursor.insertElementWithText(new QName(namespace, "disponibleLocal"), tarjetas.getDisponibleLocal());
            cursor.insertElementWithText(new QName(namespace, "disponibleDolares"), tarjetas.getDisponibleDolares());
            cursor.insertElementWithText(new QName(namespace, "pagoMinimoLocal"), tarjetas.getPagoMinimoLocal());
            cursor.insertElementWithText(new QName(namespace, "pagoMinimoDolares"), tarjetas.getPagoMinimoDolares());
            cursor.insertElementWithText(new QName(namespace, "pagoMinimoVencidoLocal"), tarjetas.getPagoMinimoVencidoLocal());
            cursor.insertElementWithText(new QName(namespace, "pagoMinimoVencidoDolares"), "");
            cursor.insertElementWithText(new QName(namespace, "pagoContadoLocal"), tarjetas.getPagoContadoLocal());
            cursor.insertElementWithText(new QName(namespace, "pagoContadoDolares"), tarjetas.getPagoContadoDolares());
            cursor.insertElementWithText(new QName(namespace, "fechaPago"), tarjetas.getFechaPago());
            cursor.insertElementWithText(new QName(namespace, "fechaUltimoCorte"), tarjetas.getFechaUltimoCorte());
            cursor.insertElementWithText(new QName(namespace, "saldoMonedero"), tarjetas.getSaldoMonedero());
            cursor.insertElementWithText(new QName(namespace, "rombosAcumulados"), tarjetas.getRombosAcumulados());
            cursor.insertElementWithText(new QName(namespace, "rombosDinero"), tarjetas.getRombosDinero());
            cursor.insertElementWithText(new QName(namespace, "fondosReservadosLocal"), tarjetas.getFondosReservadosLocal());
            cursor.insertElementWithText(new QName(namespace, "fondosReservadosDolares"), tarjetas.getFondosReservadosDolares());
            cursor.toParent();
        }
        cursor.toParent();
        //log.info("ObtenerListadoTarjetas response = [" + result + "]");
        return result;
    }

    public static List<Tarjetas> obtenerDatosArca(String identificacion, String remoteJndiSunnel, String pais, String esquemaSunnel,
                                                  String usuarioSaldoWS, String claveSaldoWS, String ipSaldoWS, String cadenaSaldoWS,
                                                  String opcionSaldoWS, String consultaSaldosWSUrl) throws Exception {
        //si no esta en siscard, buscar en arca
        String query = "SELECT c.cardid AS numeroTarjeta, " +
                "    cl.creditlineid AS cuenta, " +
                "    DECODE( " +
                "        c.CARDTYPE, " +
                "        'M', " +
                "        'TITULAR', " +
                "        'P', " +
                "        'TITULAR', " +
                "        'A', " +
                "        'ADICIONAL' " +
                "    ) AS tipoTarjeta, " +
                "    cu.aliasname AS nombreTH, " +
                "    CASE " +
                "    WHEN ( " +
                "        cl.blockedind = 'T' " +
                "        OR c.lostcardind = 'T' " +
                "        OR cl.uncollectableind = 'T' " +
                "        OR ( " +
                "            c.riskconditionind = 'T' " +
                "            AND c.riskcondreasoncodeid <> 1 " +
                "        ) " +
                "    ) THEN 'Bloqueada' " +
                "    WHEN ( " +
                "        c.riskconditionind = 'T' " +
                "        AND c.riskcondreasoncodeid = 1 " +
                "    ) THEN 'Bloqueada Temporalmente' " +
                "    WHEN cl.overlimitind = 'T' THEN 'Sobregirada' " +
                "    ELSE 'Activa' " +
                "END AS estado, " +
                "    CASE " +
                "        WHEN cl.currencycreditlimit <> 840 THEN cl.creditlimit " +
                "        ELSE 0 " +
                "    END AS limiteCreditoLocal, " +
                "    CASE " +
                "        WHEN cl.currencycreditlimit = 840 THEN cl.creditlimit " +
                "        ELSE 0 " +
                "    END AS limiteCreditoDolares, " +
                "    CASE " +
                "        WHEN fb.currencyid <> 840 THEN CASE " +
                "            WHEN NVL(fb.saldo, 0) = 0 " +
                "            AND NVL(cb.saldoAFavor, 0) > 0 THEN (cb.saldoAFavor * -1) " +
                "            ELSE NVL(fb.saldo, 0) " +
                "        END " +
                "        ELSE 0 " +
                "    END AS saldoLocal, " +
                "    CASE " +
                "        WHEN fb.currencyid = 840 THEN CASE " +
                "            WHEN NVL(fb.saldo, 0) = 0 " +
                "            AND NVL(cb.saldoAFavor, 0) > 0 THEN (cb.saldoAFavor * -1) " +
                "            ELSE NVL(fb.saldo, 0) " +
                "        END " +
                "        ELSE 0 " +
                "    END AS saldoDolares, " +
                "    CASE " +
                "        WHEN clp.currencycreditlimit <> 840 THEN clp.availablebalance " +
                "        ELSE 0 " +
                "    END AS disponibleLocal, " +
                "    CASE " +
                "        WHEN clp.currencycreditlimit = 840 THEN clp.availablebalance " +
                "        ELSE 0 " +
                "    END AS disponibleDolares, " +
                "    CASE " +
                "        WHEN fb.currencyid <> 840 THEN fb.pagoMinimo " +
                "        ELSE 0 " +
                "    END AS pagoMinimoLocal, " +
                "    CASE " +
                "        WHEN fb.currencyid = 840 THEN fb.pagoMinimo " +
                "        ELSE 0 " +
                "    END AS pagoMinimoDolares, " +
                "    CASE " +
                "        WHEN fb.currencyid <> 840 THEN fb.capitalVencido " +
                "        ELSE 0 " +
                "    END AS pagoMinimoVencidoLocal, " +
                "    CASE " +
                "        WHEN fb.currencyid = 840 THEN fb.capitalVencido " +
                "        ELSE 0 " +
                "    END AS pagoMinimoVencidoDolares, " +
                "    CASE " +
                "        WHEN fb.currencyid <> 840 THEN fb.pagoContado " +
                "        ELSE 0 " +
                "    END AS pagoContadoLocal, " +
                "    CASE " +
                "        WHEN fb.currencyid = 840 THEN fb.pagoContado " +
                "        ELSE 0 " +
                "    END AS pagoContadoDolares, " +
                "    TO_CHAR(bp.fechaPago,'YYYYMMDD') AS fechaPago, " +
                "    TO_CHAR(cl.lastinterestaccruingdate,'YYYYMMDD') AS fechaUltimoCorte, " +
                "    ' ' AS saldoMonedero, " +
                "    ' ' AS rombosAcumulados, " +
                "    ' ' AS rombosDinero, " +
                "    ' ' AS fondosReservadosLocal, " +
                "    ' ' AS fondosReservadosDolares " +
                "FROM " + esquemaSunnel + ".t_gcard c " +
                "    INNER JOIN " + esquemaSunnel + ".t_gcustomer cu ON cu.customerid = c.customerid " +
                "    INNER JOIN " + esquemaSunnel + ".t_gaccount a ON a.cardid = c.cardid " +
                "    INNER JOIN " + esquemaSunnel + ".t_gcreditline cl ON cl.creditlineid = a.accountid " +
                "    INNER JOIN " + esquemaSunnel + ".t_gcreditlinepartition clp ON cl.creditlineid = clp.creditlineid" +
                "    LEFT OUTER JOIN ( " +
                "        SELECT clt.creditlineid, " +
                "            MAX(bpt.paymentdate) AS fechaPago " +
                "        FROM " + esquemaSunnel + ".t_gbillingperiod bpt " +
                "            INNER JOIN " + esquemaSunnel + ".t_gcreditline clt ON clt.billingcycleid = bpt.billingcycleid " +
                "            AND clt.lastinterestaccruingdate = bpt.billingdate " +
                "        GROUP BY clt.creditlineid " +
                "    ) bp ON bp.creditlineid = cl.creditlineid " +
                "    LEFT OUTER JOIN ( " +
                "        SELECT fbt.creditlineid, " +
                "            fbt.currencyid, " +
                "            SUM( " +
                "                fbt.regularbalance + fbt.periodamountdue + fbt.regularinterest + fbt.regularinteresttax + fbt.overduebalance + fbt.overdueinterest + fbt.overdueinteresttax + fbt.contingentinterest " +
                "            ) AS saldo, " +
                "            SUM( " +
                "                fbt.periodamountdue + fbt.regularinterest + fbt.regularinteresttax + fbt.overduebalance + fbt.overdueinterest + fbt.overdueinteresttax + fbt.contingentinterest " +
                "            ) AS pagoMinimo, " +
                "            SUM( " +
                "                fbt.regularbalance + fbt.periodamountdue + fbt.regularinterest + fbt.regularinteresttax + fbt.overduebalance + fbt.overdueinterest + fbt.overdueinteresttax + fbt.contingentinterest " +
                "            ) AS pagoContado, " +
                "            SUM(fbt.regularbalance) AS capitalNoExigible, " +
                "            SUM(fbt.periodamountdue) AS capitalExigible, " +
                "            SUM(fbt.overduebalance) AS capitalVencido, " +
                "            SUM(regularinterest + fbt.regularinteresttax) AS interesCorriente, " +
                "            SUM(fbt.overdueinterest + fbt.overdueinteresttax) AS interesMoratorio, " +
                "            SUM(fbt.contingentinterest) AS interesContigente " +
                "        FROM " + esquemaSunnel + ".t_gfinancingbalance fbt " +
                "        GROUP BY fbt.creditlineid, " +
                "            fbt.currencyid " +
                "    ) fb ON fb.creditlineid = cl.creditlineid " +
                "    AND fb.currencyid = cl.currencycreditlimit " +
                "    LEFT OUTER JOIN ( " +
                "        SELECT cbt.creditlineid, " +
                "            cbt.currencyid, " +
                "            SUM(cbt.amountinexcess) AS saldoAFavor " +
                "        FROM " + esquemaSunnel + ".t_gcreditbalance cbt " +
                "        GROUP BY cbt.creditlineid, " +
                "            cbt.currencyid " +
                "    ) cb ON cb.creditlineid = cl.creditlineid " +
                "    AND cb.currencyid = cl.currencycreditlimit " +
                "WHERE c.closedind = 'F' AND c.deliveredind = 'T' AND cl.acceleratedbalanceind = 'F' AND cl.uncollectableind = 'F' AND cl.baddebtind = 'F'" +
                "AND (c.productid IN('10001','10002','10003','10020') AND clp.CREDITLINEPARTITIONTYPEID = '355' OR c.productid IN('10004') AND clp.CREDITLINEPARTITIONTYPEID = '356') " +
                "    AND cu.identificationnumber IN( ? , ? )" +
                " UNION " +
                " SELECT  " +
                " c_a.cardid AS numeroTarjeta, " +
                " cl.creditlineid AS cuenta, " +
                " DECODE(c_a.CARDTYPE, 'M', 'TITULAR', 'P', 'TITULAR', 'A', 'ADICIONAL') AS tipoTarjeta, " +
                " cu_a.aliasname AS nombreTH, " +
                " CASE " +
                "    WHEN ( " +
                "        cl.blockedind = 'T' " +
                "        OR c.lostcardind = 'T' " +
                "        OR cl.uncollectableind = 'T' " +
                "        OR ( " +
                "            c_a.riskconditionind = 'T' " +
                "            AND c_a.riskcondreasoncodeid <> 1 " +
                "        ) " +
                "    ) THEN 'Bloqueada' " +
                "    WHEN ( " +
                "        c_a.riskconditionind = 'T' " +
                "        AND c_a.riskcondreasoncodeid = 1 " +
                "    ) THEN 'Bloqueada Temporalmente' " +
                "    WHEN cl.overlimitind = 'T' THEN 'Sobregirada' " +
                "    ELSE 'Activa' " +
                "END AS estado, " +
                " CASE WHEN cl.currencycreditlimit <> 840 THEN cl.creditlimit ELSE 0 END AS limiteCreditoLocal, " +
                " CASE WHEN cl.currencycreditlimit = 840 THEN cl.creditlimit ELSE 0 END AS limiteCreditoDolares, " +
                " CASE WHEN fb.currencyid <> 840 THEN CASE WHEN NVL(fb.saldo, 0) = 0 AND NVL(cb.saldoAFavor, 0) > 0 THEN (cb.saldoAFavor * -1) ELSE NVL(fb.saldo, 0) END ELSE 0 END AS saldoLocal, " +
                " CASE WHEN fb.currencyid = 840 THEN CASE WHEN NVL(fb.saldo, 0) = 0 AND NVL(cb.saldoAFavor, 0) > 0 THEN (cb.saldoAFavor * -1) ELSE NVL(fb.saldo, 0) END ELSE 0 END AS saldoDolares, " +
                " CASE WHEN clp.currencycreditlimit <> 840 THEN clp.availablebalance ELSE 0 END AS disponibleLocal, " +
                " CASE WHEN clp.currencycreditlimit = 840 THEN clp.availablebalance ELSE 0 END AS disponibleDolares, " +
                " CASE WHEN fb.currencyid <> 840 THEN fb.pagoMinimo ELSE 0 END AS pagoMinimoLocal, " +
                " CASE WHEN fb.currencyid = 840 THEN fb.pagoMinimo ELSE 0 END AS pagoMinimoDolares, " +
                " CASE WHEN fb.currencyid <> 840 THEN fb.capitalVencido ELSE 0 END AS pagoMinimoVencidoLocal, " +
                " CASE WHEN fb.currencyid = 840 THEN fb.capitalVencido ELSE 0 END AS pagoMinimoVencidoDolares, " +
                " CASE WHEN fb.currencyid <> 840 THEN fb.pagoContado ELSE 0 END AS pagoContadoLocal, " +
                " CASE WHEN fb.currencyid = 840 THEN fb.pagoContado ELSE 0 END AS pagoContadoDolares, " +
                " TO_CHAR(bp.fechaPago,'YYYYMMDD') AS fechaPago, " +
                " TO_CHAR(cl.lastinterestaccruingdate,'YYYYMMDD') AS fechaUltimoCorte, " +
                " ' ' AS saldoMonedero, " +
                " ' ' AS rombosAcumulados, " +
                " ' ' AS rombosDinero, " +
                " ' ' AS fondosReservadosLocal, " +
                " ' ' AS fondosReservadosDolares " +
                " FROM " + esquemaSunnel + ".t_gcard c " +
                " INNER JOIN " + esquemaSunnel + ".t_gcustomer cu ON cu.customerid = c.customerid " +
                "INNER JOIN " + esquemaSunnel + ".t_gadditionalcard ac ON ac.cardid = c.cardid " +
                "INNER JOIN " + esquemaSunnel + ".t_gcard c_a ON ac.additionalcardid = c_a.cardid " +
                "INNER JOIN " + esquemaSunnel + ".t_gcustomer cu_a ON cu_a.customerid = c_a.customerid " +
                "INNER JOIN " + esquemaSunnel + ".t_gaccount a ON a.cardid = c.cardid " +
                "INNER JOIN " + esquemaSunnel + ".t_gcreditline cl ON cl.creditlineid = a.accountid " +
                "INNER JOIN " + esquemaSunnel + ".t_gcreditlinepartition clp ON cl.creditlineid = clp.creditlineid " +
                "LEFT OUTER JOIN (SELECT clt.creditlineid, MAX(bpt.paymentdate) AS fechaPago " +
                "                FROM " + esquemaSunnel + ".t_gbillingperiod bpt " +
                "                INNER JOIN " + esquemaSunnel + ".t_gcreditline clt ON clt.billingcycleid = bpt.billingcycleid AND clt.lastinterestaccruingdate = bpt.billingdate " +
                "                GROUP BY clt.creditlineid) bp ON bp.creditlineid = cl.creditlineid " +
                "LEFT OUTER JOIN (SELECT fbt.creditlineid, fbt.currencyid, " +
                "                SUM(fbt.regularbalance + fbt.periodamountdue + fbt.regularinterest + fbt.regularinteresttax + fbt.overduebalance + fbt.overdueinterest + fbt.overdueinteresttax + fbt.contingentinterest) AS saldo, " +
                "                SUM(fbt.periodamountdue + fbt.regularinterest + fbt.regularinteresttax + fbt.overduebalance + fbt.overdueinterest + fbt.overdueinteresttax + fbt.contingentinterest) AS pagoMinimo, " +
                "                SUM(fbt.regularbalance + fbt.periodamountdue + fbt.regularinterest + fbt.regularinteresttax + fbt.overduebalance + fbt.overdueinterest + fbt.overdueinteresttax + fbt.contingentinterest) AS pagoContado, " +
                "                SUM(fbt.regularbalance) AS capitalNoExigible, " +
                "                SUM(fbt.periodamountdue) AS capitalExigible, " +
                "                SUM(fbt.overduebalance) AS capitalVencido, " +
                "                SUM(regularinterest + fbt.regularinteresttax) AS interesCorriente, " +
                "                SUM(fbt.overdueinterest + fbt.overdueinteresttax) AS interesMoratorio, " +
                "                SUM(fbt.contingentinterest) AS interesContigente " +
                "                FROM " + esquemaSunnel + ".t_gfinancingbalance fbt " +
                "                GROUP BY fbt.creditlineid, fbt.currencyid) fb ON fb.creditlineid = cl.creditlineid AND fb.currencyid = cl.currencycreditlimit " +
                "LEFT OUTER JOIN (SELECT cbt.creditlineid, cbt.currencyid,  " +
                "                SUM(cbt.amountinexcess) AS saldoAFavor " +
                "                FROM " + esquemaSunnel + ".t_gcreditbalance cbt " +
                "                GROUP BY cbt.creditlineid, cbt.currencyid) cb ON cb.creditlineid = cl.creditlineid AND cb.currencyid = cl.currencycreditlimit " +
                "WHERE c.closedind = 'F' AND c.deliveredind = 'T' AND cl.acceleratedbalanceind = 'F' AND cl.uncollectableind = 'F' AND cl.baddebtind = 'F'" +
                "AND (c.productid IN('10001','10002','10003','10020') AND clp.CREDITLINEPARTITIONTYPEID = '355' OR c.productid IN('10004') AND clp.CREDITLINEPARTITIONTYPEID = '356') " +
                "AND cu.identificationnumber IN(?,?)";

        List<Tarjetas> tarjetasList = new ArrayList<>();

        conexion = new ConnectionHandler().getConnection(remoteJndiSunnel);
        sentencia = conexion.prepareStatement(query);

        //crear identificaci�n con formato sin(-,_ y espacios en blanco )

        String identificacionFormater = identificacion.replace("-", "").replace("_", "").replace(" ", "");
        sentencia.setString(1, identificacion);
        sentencia.setString(2, identificacionFormater);
        sentencia.setString(3, identificacion);
        sentencia.setString(4, identificacionFormater);
        rs = sentencia.executeQuery();
        int counter = 0;
        JSONObject jsonSend = new JSONObject(); //json a enviar
        HttpResponse<String> jsonResponse;

        while (rs.next()) {
            counter++;
            Tarjetas tarjeta = new Tarjetas();
            tarjeta.setNumeroTarjeta((rs.getString("numeroTarjeta") != null) ? rs.getString("numeroTarjeta") : "0");
            tarjeta.setCuenta((rs.getString("cuenta") != null ? rs.getString("cuenta") : "0"));
            tarjeta.setTipoTarjeta((rs.getString("tipoTarjeta") != null) ? rs.getString("tipoTarjeta") : " ");
            tarjeta.setNombreTH((rs.getString("nombreTH") != null) ? rs.getString("nombreTH") : " ");
            tarjeta.setEstado((rs.getString("estado") != null) ? rs.getString("estado") : " ");
            tarjeta.setLimiteCreditoLocal((rs.getString("limiteCreditoLocal") != null) ? rs.getString("limiteCreditoLocal") : "0");
            tarjeta.setLimiteCreditoDolares((rs.getString("limiteCreditoDolares") != null) ? rs.getString("limiteCreditoDolares") : "0");
            tarjeta.setSaldoLocal((rs.getString("saldoLocal") != null) ? rs.getString("saldoLocal") : "0");
            tarjeta.setSaldoDolares((rs.getString("saldoDolares") != null) ? rs.getString("saldoDolares") : "0");
            tarjeta.setDisponibleLocal((rs.getString("disponibleLocal") != null) ? rs.getString("disponibleLocal") : "0");
            tarjeta.setDisponibleDolares((rs.getString("disponibleDolares") != null) ? rs.getString("disponibleDolares") : "0");
            tarjeta.setPagoMinimoLocal((rs.getString("pagoMinimoLocal") != null) ? rs.getString("pagoMinimoLocal") : "0");
            tarjeta.setPagoMinimoDolares((rs.getString("pagoMinimoDolares") != null) ? rs.getString("pagoMinimoDolares") : "0");
            tarjeta.setPagoMinimoVencidoLocal((rs.getString("pagoMinimoVencidoLocal") != null) ? rs.getString("pagoMinimoVencidoLocal") : "0");
            tarjeta.setPagoMinimoVencidoDolares((rs.getString("pagoMinimoVencidoDolares") != null) ? rs.getString("pagoMinimoVencidoDolares") : "0");
            //tarjeta.setPagoContadoLocal((rs.getString("pagoContadoLocal") != null) ? rs.getString("pagoContadoLocal") : "0");
            //tarjeta.setPagoContadoDolares((rs.getString("pagoContadoDolares") != null) ? rs.getString("pagoContadoDolares") : "0");
            tarjeta.setFechaPago((rs.getString("fechaPago") != null) ? rs.getString("fechaPago") : " ");
            tarjeta.setFechaUltimoCorte((rs.getString("fechaUltimoCorte") != null) ? rs.getString("fechaUltimoCorte") : " ");
            tarjeta.setSaldoMonedero(" ");
            tarjeta.setRombosAcumulados(" ");
            tarjeta.setRombosDinero(" ");
            tarjeta.setFondosReservadosLocal("0");
            tarjeta.setFondosReservadosDolares("0");

            String tarjetaBase64 = convertirStringToBase64(rs.getString("numeroTarjeta"));
            jsonSend.put("numeroTarjeta", tarjetaBase64)
                    .put("pais", pais)
                    .put("usuario", usuarioSaldoWS)
                    .put("clave", claveSaldoWS)
                    .put("ip", ipSaldoWS)
                    .put("cadena", cadenaSaldoWS)
                    .put("opcion", opcionSaldoWS);
            jsonResponse = Unirest.post(consultaSaldosWSUrl.concat("/getConsultaSaldosCredisimanWs"))
                    .header("Content-Type", "application/json")
                    .body(jsonSend.toString())
                    .asString();
            JSONObject response = new JSONObject(jsonResponse.getBody());
            String pagoContado = convertirBase64ToString(response.getString("pagoContado"));
            tarjeta.setPagoContadoLocal(pagoContado);
            tarjeta.setPagoContadoDolares(pagoContado);

            tarjetasList.add(tarjeta);
        }
        //log.info("registros encontrados: " + counter);
        conexion.close();
        if (counter > 0) {
            return tarjetasList;
        } else {
            conexion.close();
            return null;
        }
    }

    public static List<Tarjetas> obtenerDatosSiscard(String pais, String identificacion, String siscardUrl) throws Exception {
        String identifacionFormater = identificacion.replace("-", "")
                .replace("_", "").replace(" ", "");

        ListadoTarjetasResponse response1 = new ListadoTarjetasResponse();
        JSONObject jsonSend = new JSONObject(); //json a enviar
        jsonSend.put("country", pais)
                .put("processIdentifier", "ConsultaCuentas")
                .put("cedula", identifacionFormater)
                .put("typeService", "");

        HttpResponse<String> jsonResponse //realizar petici?n demiante unirest
                = Unirest.post(siscardUrl.concat("/consultaCuenta"))
                .header("Content-Type", "application/json")
                .body(jsonSend.toString())
                .asString();

        //capturar respuesta
        JSONObject response = new JSONObject(jsonResponse.getBody());
        response1 = new ObjectMapper().readValue(response.toString(), ListadoTarjetasResponse.class);
        List<Tarjetas> responseList = new ArrayList<>();

        if (response1.getCode().equals("OSB-380000")) {
            return null;
        }

        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.DOWN);

        if (response1.getRespuestas() == null) {
            for (int i = 0; i < response1.getCuentas().size(); i++) {
                CuentasResponse cuentas = response1.getCuentas().get(i);

                JSONObject jsonSend2 = new JSONObject(); //json a enviar
                jsonSend2.put("country", pais)
                        .put("processIdentifier", "consultaInformacionSiscard")
                        .put("emisor", cuentas.getEmisor())
                        .put("cuenta", cuentas.getCuenta())
                        .put("cif", "")
                        .put("numeroTarjeta", "");

                HttpResponse<String> jsonResponse2 //realizar petici?n demiante unirest
                        = Unirest.post(siscardUrl.concat("/consultaInformacionSiscard"))
                        .header("Content-Type", "application/json")
                        .body(jsonSend2.toString())
                        .asString();

                JSONObject response2 = new JSONObject(jsonResponse2.getBody());
                JSONArray arrCuentas = response2.getJSONArray("cuentas");

                for (int j = 0; j < arrCuentas.length(); j++) {
                    JSONObject objTarjetas = arrCuentas.getJSONObject(j);
                    JSONArray arrTarjetas = objTarjetas.getJSONArray("tarjetas");

                    for (int k = 0; k < arrTarjetas.length(); k++) {
                        Tarjetas t = new Tarjetas();
                        JSONObject tarjeta = arrTarjetas.getJSONObject(k);

                        if (tarjeta.getString("estadoTarjeta").equals("00") || tarjeta.getString("estadoTarjeta").equals("01") ||
                                tarjeta.getString("estadoTarjeta").equals("02") || tarjeta.getString("estadoTarjeta").equals("05") ||
                                tarjeta.getString("estadoTarjeta").equals("08") || tarjeta.getString("estadoTarjeta").equals("09") ||
                                tarjeta.getString("estadoTarjeta").equals("11") || tarjeta.getString("estadoTarjeta").equals("13") ||
                                tarjeta.getString("estadoTarjeta").equals("16") || tarjeta.getString("estadoTarjeta").equals("27") ||
                                tarjeta.getString("estadoTarjeta").equals("28")) {

                            t.setNumeroTarjeta(tarjeta.getString("numeroTarjeta"));
                            t.setTipoTarjeta(tarjeta.getString("tipoTarjeta"));
                            t.setNombreTH(tarjeta.getString("nombreTH"));
                            for (TarjetasResponse tarjetasResponse : cuentas.getTarjetas()) {
                                if (tarjetasResponse.getNumeroTarjeta().equals(tarjeta.getString("numeroTarjeta"))) {
                                    t.setEstado(tarjetasResponse.getEstadoTarjeta());
                                    //log.info(tarjeta.getString("estadoTarjeta"));
                                    if (tarjetasResponse.getTipoTarjeta().equals("Titular")) {
                                        t.setLimiteCreditoLocal(cuentas.getLimiteCreditoLocal());
                                        t.setLimiteCreditoDolares(cuentas.getLimiteCreditoInter());
                                    }
                                    if (tarjetasResponse.getTipoTarjeta().equals("Adicional")) {
                                        if (Double.parseDouble(tarjetasResponse.getLimiteCreditoLocal()) == 0
                                                && Double.parseDouble(tarjetasResponse.getLimiteCreditoInter()) == 0) {
                                            t.setLimiteCreditoLocal(cuentas.getLimiteCreditoLocal());
                                            t.setLimiteCreditoDolares(cuentas.getLimiteCreditoInter());
                                        }

                                        if (Double.parseDouble(tarjetasResponse.getLimiteCreditoLocal()) > 0
                                                && Double.parseDouble(tarjetasResponse.getLimiteCreditoInter()) > 0) {
                                            t.setLimiteCreditoLocal(tarjetasResponse.getLimiteCreditoLocal());
                                            t.setLimiteCreditoDolares(tarjetasResponse.getLimiteCreditoInter());
                                        }
                                    }
                                }
                            }
                            t.setDisponibleLocal(String.format("%.2f", Double.parseDouble(tarjeta.getString("dispLocalTarjeta")) / 100));
                            t.setDisponibleDolares(String.format("%.2f", Double.parseDouble(tarjeta.getString("dispIntTarjeta")) / 100));
                            t.setCuenta(cuentas.getCuenta());
                            t.setSaldoLocal(String.format("%.2f", Double.parseDouble(tarjeta.getString("saldoLocal")) / 100));
                            t.setSaldoDolares(String.format("%.2f", Double.parseDouble(tarjeta.getString("saldoInter")) / 100));
                            t.setPagoMinimoLocal(String.format("%.2f", Double.parseDouble(tarjeta.getString("pagoMinimoLocal")) / 100));
                            t.setPagoMinimoDolares(String.format("%.2f", Double.parseDouble(tarjeta.getString("pagoMinimoInt")) / 100));
                            t.setPagoContadoLocal(String.format("%.2f", Double.parseDouble(tarjeta.getString("pagoContadoLocal")) / 100));
                            t.setPagoContadoDolares(String.format("%.2f", Double.parseDouble(tarjeta.getString("pagoContadoInt")) / 100));
                            t.setFechaPago(tarjeta.getString("fechaVencimientoPago"));
                            t.setRombosDinero(cuentas.getSaldoPremiacion());
                            t.setRombosAcumulados(" ");
                            t.setPagoMinimoVencidoLocal(" ");
                            t.setPagoMinimoVencidoDolares(" ");
                            t.setSaldoMonedero(" ");
                            t.setFondosReservadosLocal(String.format("%.2f", Double.parseDouble(tarjeta.getString("debitoTransitoLocal")) / 100));
                            t.setFondosReservadosDolares(String.format("%.2f", Double.parseDouble(tarjeta.getString("debitoTransitoInter")) / 100));
                            t.setFechaUltimoCorte(tarjeta.getString("fechaCorte"));
                            responseList.add(t);
                        }
                    }

                }
            }
        }
        return responseList;
    }

    public static String convertirStringToBase64(String tarjeta) {
        String base64Encode1 = Base64.getEncoder().encodeToString(tarjeta.getBytes());
        String base64Encode2 = Base64.getEncoder().encodeToString(base64Encode1.getBytes());
        return Base64.getEncoder().encodeToString(base64Encode2.getBytes());
    }

    public static String convertirBase64ToString(String pagoContado) {
        String decodedString1 = new String(Base64.getDecoder().decode(pagoContado));
        String decodedString2 = new String(Base64.getDecoder().decode(decodedString1));
        String decodedString3 = new String(Base64.getDecoder().decode(decodedString2));
        return decodedString3.substring(1);
    }
}
