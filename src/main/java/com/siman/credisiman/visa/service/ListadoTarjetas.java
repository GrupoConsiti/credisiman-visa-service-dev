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
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListadoTarjetas {
    private static final Logger log = LoggerFactory.getLogger(ListadoTarjetas.class);
    private static final String namespace = "http://siman.com/ListadoTarjetas";
    private static final String operationResponse = "ObtenerListadoTarjetasResponse";


    public static XmlObject obtenerListadoTarjetas(String pais, String identificacion, String remoteJndiSunnel,
                                                   String remoteJndiOrion, String siscardUrl, String siscardUser,
                                                   String binCredisiman) {
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
            log.info("pais, size overload");
            return message.genericMessage("ERROR", "400", "La longitud del campo pais debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(identificacion, 19)) {
            log.info("identificacion, size overload");
            return message.genericMessage("ERROR", "400", "La longitud del campo identificacion debe ser menor o igual a 19", namespace, operationResponse);
        }

        try {
            //all code here
            List<Tarjetas> response2 = obtenerDatosArca(identificacion, remoteJndiSunnel, pais);
            if (response2 != null) {
                log.info("ARCA");
                return estructura(response2);
            }
            List<Tarjetas> response3 = null;
            response3 = obtenerDatosSiscard(pais, identificacion, siscardUrl);
            if (response3 != null) {
                log.info("tarjetas evertec: " + response3.size());
                log.info("EVERTEC");
                return estructura(response3);
            }

            return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados.", namespace, operationResponse);

        } catch (SQLException e) {
            e.printStackTrace();
            log.info("ObtenerListadoTarjetas response = [" + message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse) + "]");
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse);
        } catch (NullPointerException nul) {
            nul.printStackTrace();
            return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
        } catch (Exception ex) {
            log.info("ObtenerListadoTarjetas response = [" + message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse) + "]");
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
            cursor.insertElementWithText(new QName(namespace, "fondosReservados"), tarjetas.getFondosReservados());
            cursor.toParent();
        }
        cursor.toParent();
        log.info("ObtenerListadoTarjetas response = [" + result + "]");
        return result;
    }

    public static List<Tarjetas> obtenerDatosArca(String identificacion, String remoteJndiSunnel, String pais) throws Exception {
        //si no esta en siscard, buscar en arca
        String query1 = "SELECT c.cardid AS numeroTarjeta, " +
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
                "        WHEN cl.blockedind = 'T' THEN 'Bloqueada' " +
                "        ELSE 'Activa' " +
                "    END AS estado, " +
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
                "    ' ' AS fondosReservados " +
                "FROM SUNNELP3.t_gcard c " +
                "    INNER JOIN SUNNELP3.t_gcustomer cu ON cu.customerid = c.customerid " +
                "    INNER JOIN SUNNELP3.t_gaccount a ON a.cardid = c.cardid " +
                "    INNER JOIN SUNNELP3.t_gcreditline cl ON cl.creditlineid = a.accountid " +
                "    INNER JOIN SUNNELP3.t_gcreditlinepartition clp ON cl.creditlineid = clp.creditlineid " +
                "    AND clp.creditlinepartitiontypeid <> 356 " +
                "    LEFT OUTER JOIN ( " +
                "        SELECT clt.creditlineid, " +
                "            MAX(bpt.paymentdate) AS fechaPago " +
                "        FROM SUNNELP3.t_gbillingperiod bpt " +
                "            INNER JOIN SUNNELP3.t_gcreditline clt ON clt.billingcycleid = bpt.billingcycleid " +
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
                "        FROM SUNNELP3.t_gfinancingbalance fbt " +
                "        GROUP BY fbt.creditlineid, " +
                "            fbt.currencyid " +
                "    ) fb ON fb.creditlineid = cl.creditlineid " +
                "    AND fb.currencyid = cl.currencycreditlimit " +
                "    LEFT OUTER JOIN ( " +
                "        SELECT cbt.creditlineid, " +
                "            cbt.currencyid, " +
                "            SUM(cbt.amountinexcess) AS saldoAFavor " +
                "        FROM SUNNELP3.t_gcreditbalance cbt " +
                "        GROUP BY cbt.creditlineid, " +
                "            cbt.currencyid " +
                "    ) cb ON cb.creditlineid = cl.creditlineid " +
                "    AND cb.currencyid = cl.currencycreditlimit " +
                "WHERE c.closedind = 'F' " +
                "    AND c.riskconditionind = 'F' " +
                "    AND c.lostcardind = 'F' " +
                "    AND cu.identificationnumber IN( ? , ? ) ";//TODO obtener query arca


        String query2 = "SELECT c.cardid AS numeroTarjeta, " +
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
                "        WHEN cl.blockedind = 'T' THEN 'Bloqueada' " +
                "        ELSE 'Activa' " +
                "    END AS estado, " +
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
                "    ' ' AS fondosReservados " +
                "FROM SUNNELGTP4.t_gcard c " +
                "    INNER JOIN SUNNELGTP4.t_gcustomer cu ON cu.customerid = c.customerid " +
                "    INNER JOIN SUNNELGTP4.t_gaccount a ON a.cardid = c.cardid " +
                "    INNER JOIN SUNNELGTP4.t_gcreditline cl ON cl.creditlineid = a.accountid " +
                "    INNER JOIN SUNNELGTP4.t_gcreditlinepartition clp ON cl.creditlineid = clp.creditlineid " +
                "    AND clp.creditlinepartitiontypeid <> 356 " +
                "    LEFT OUTER JOIN ( " +
                "        SELECT clt.creditlineid, " +
                "            MAX(bpt.paymentdate) AS fechaPago " +
                "        FROM SUNNELGTP4.t_gbillingperiod bpt " +
                "            INNER JOIN SUNNELGTP4.t_gcreditline clt ON clt.billingcycleid = bpt.billingcycleid " +
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
                "        FROM SUNNELGTP4.t_gfinancingbalance fbt " +
                "        GROUP BY fbt.creditlineid, " +
                "            fbt.currencyid " +
                "    ) fb ON fb.creditlineid = cl.creditlineid " +
                "    AND fb.currencyid = cl.currencycreditlimit " +
                "    LEFT OUTER JOIN ( " +
                "        SELECT cbt.creditlineid, " +
                "            cbt.currencyid, " +
                "            SUM(cbt.amountinexcess) AS saldoAFavor " +
                "        FROM SUNNELGTP4.t_gcreditbalance cbt " +
                "        GROUP BY cbt.creditlineid, " +
                "            cbt.currencyid " +
                "    ) cb ON cb.creditlineid = cl.creditlineid " +
                "    AND cb.currencyid = cl.currencycreditlimit " +
                "WHERE c.closedind = 'F' " +
                "    AND c.riskconditionind = 'F' " +
                "    AND c.lostcardind = 'F' " +
                "    AND cu.identificationnumber IN( ? , ? ) ";

        String query3 = " SELECT c.cardid AS numeroTarjeta, " +
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
                "        WHEN cl.blockedind = 'T' THEN 'Bloqueada' " +
                "        ELSE 'Activa' " +
                "    END AS estado, " +
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
                "    ' ' AS fondosReservados " +
                "FROM SUNNELNIP1.t_gcard c " +
                "    INNER JOIN SUNNELNIP1.t_gcustomer cu ON cu.customerid = c.customerid " +
                "    INNER JOIN SUNNELNIP1.t_gaccount a ON a.cardid = c.cardid " +
                "    INNER JOIN SUNNELNIP1.t_gcreditline cl ON cl.creditlineid = a.accountid " +
                "    INNER JOIN SUNNELNIP1.t_gcreditlinepartition clp ON cl.creditlineid = clp.creditlineid " +
                "    AND clp.creditlinepartitiontypeid <> 356 " +
                "    LEFT OUTER JOIN ( " +
                "        SELECT clt.creditlineid, " +
                "            MAX(bpt.paymentdate) AS fechaPago " +
                "        FROM SUNNELNIP1.t_gbillingperiod bpt " +
                "            INNER JOIN SUNNELNIP1.t_gcreditline clt ON clt.billingcycleid = bpt.billingcycleid " +
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
                "        FROM SUNNELNIP1.t_gfinancingbalance fbt " +
                "        GROUP BY fbt.creditlineid, " +
                "            fbt.currencyid " +
                "    ) fb ON fb.creditlineid = cl.creditlineid " +
                "    AND fb.currencyid = cl.currencycreditlimit " +
                "    LEFT OUTER JOIN ( " +
                "        SELECT cbt.creditlineid, " +
                "            cbt.currencyid, " +
                "            SUM(cbt.amountinexcess) AS saldoAFavor " +
                "        FROM SUNNELNIP1.t_gcreditbalance cbt " +
                "        GROUP BY cbt.creditlineid, " +
                "            cbt.currencyid " +
                "    ) cb ON cb.creditlineid = cl.creditlineid " +
                "    AND cb.currencyid = cl.currencycreditlimit " +
                "WHERE c.closedind = 'F' " +
                "    AND c.riskconditionind = 'F' " +
                "    AND c.lostcardind = 'F' " +
                "    AND cu.identificationnumber IN( ? , ? ) ";

        String query4 = " SELECT c.cardid AS numeroTarjeta, " +
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
                "        WHEN cl.blockedind = 'T' THEN 'Bloqueada' " +
                "        ELSE 'Activa' " +
                "    END AS estado, " +
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
                "    ' ' AS fondosReservados " +
                "FROM SUNNELCRP4.t_gcard c " +
                "    INNER JOIN SUNNELCRP4.t_gcustomer cu ON cu.customerid = c.customerid " +
                "    INNER JOIN SUNNELCRP4.t_gaccount a ON a.cardid = c.cardid " +
                "    INNER JOIN SUNNELCRP4.t_gcreditline cl ON cl.creditlineid = a.accountid " +
                "    INNER JOIN SUNNELCRP4.t_gcreditlinepartition clp ON cl.creditlineid = clp.creditlineid " +
                "    AND clp.creditlinepartitiontypeid <> 356 " +
                "    LEFT OUTER JOIN ( " +
                "        SELECT clt.creditlineid, " +
                "            MAX(bpt.paymentdate) AS fechaPago " +
                "        FROM SUNNELCRP4.t_gbillingperiod bpt " +
                "            INNER JOIN SUNNELCRP4.t_gcreditline clt ON clt.billingcycleid = bpt.billingcycleid " +
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
                "        FROM SUNNELCRP4.t_gfinancingbalance fbt " +
                "        GROUP BY fbt.creditlineid, " +
                "            fbt.currencyid " +
                "    ) fb ON fb.creditlineid = cl.creditlineid " +
                "    AND fb.currencyid = cl.currencycreditlimit " +
                "    LEFT OUTER JOIN ( " +
                "        SELECT cbt.creditlineid, " +
                "            cbt.currencyid, " +
                "            SUM(cbt.amountinexcess) AS saldoAFavor " +
                "        FROM SUNNELCRP4.t_gcreditbalance cbt " +
                "        GROUP BY cbt.creditlineid, " +
                "            cbt.currencyid " +
                "    ) cb ON cb.creditlineid = cl.creditlineid " +
                "    AND cb.currencyid = cl.currencycreditlimit " +
                "WHERE c.closedind = 'F' " +
                "    AND c.riskconditionind = 'F' " +
                "    AND c.lostcardind = 'F' " +
                "    AND cu.identificationnumber IN( ? , ? ) ";
        List<Tarjetas> tarjetasList = new ArrayList<>();
        ConnectionHandler connectionHandler = new ConnectionHandler();
        Connection conexion = connectionHandler.getConnection(remoteJndiSunnel);

        PreparedStatement sentencia = null;
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
        //crear identificación con formato sin(-,_ y espacios en blanco )

        String identificacionFormater = identificacion.replace("-", "").replace("_", "").replace(" ", "");
        sentencia.setString(1, identificacion); //TODO agregar parametros
        sentencia.setString(2, identificacionFormater);
        ResultSet rs = sentencia.executeQuery();
        int counter = 0;

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
            tarjeta.setPagoContadoLocal((rs.getString("pagoContadoLocal") != null) ? rs.getString("pagoContadoLocal") : "0");
            tarjeta.setPagoContadoDolares((rs.getString("pagoContadoDolares") != null) ? rs.getString("pagoContadoDolares") : "0");
            tarjeta.setFechaPago((rs.getString("fechaPago") != null) ? rs.getString("fechaPago") : " ");
            tarjeta.setFechaUltimoCorte((rs.getString("fechaUltimoCorte") != null) ? rs.getString("fechaUltimoCorte") : " ");
            tarjeta.setSaldoMonedero(" ");
            tarjeta.setRombosAcumulados(" ");
            tarjeta.setRombosDinero(" ");
            tarjeta.setFondosReservados(" ");
            tarjetasList.add(tarjeta);
        }
        log.info("registros encontrados: " + counter);
        conexion.close();
        if (counter > 0) {
            return tarjetasList;
        } else {
            return null;
        }
    }

    public static List<Tarjetas> obtenerDatosSiscard(String pais, String identificacion, String siscardUrl) throws Exception {
        if (pais.equals("SV")) {

            String identifacionFormater = identificacion.replace("-", "")
                    .replace("_", "").replace(" ", "");

            ListadoTarjetasResponse response1 = new ListadoTarjetasResponse();
            JSONObject jsonSend = new JSONObject(); //json a enviar
            jsonSend.put("country", pais)
                    .put("processIdentifier", "ConsultaCuentas")
                    .put("cedula", identifacionFormater)
                    .put("typeService", "");

            HttpResponse<String> jsonResponse //realizar petición demiante unirest
                    = Unirest.post(siscardUrl.concat("/consultaCuenta"))
                    .header("Content-Type", "application/json")
                    .body(jsonSend.toString())
                    .asString();

            //capturar respuesta
            JSONObject response = new JSONObject(jsonResponse.getBody());
            response1 = new ObjectMapper().readValue(response.toString(), ListadoTarjetasResponse.class);
            List<Tarjetas> responseList = new ArrayList<>();

            for (int i = 0; i < response1.getCuentas().size(); i++) {
                CuentasResponse cuentas = response1.getCuentas().get(i);
                for (TarjetasResponse tarjetas : cuentas.getTarjetas()) {
                    Tarjetas tarjeta = new Tarjetas();

                    tarjeta.setNumeroTarjeta(tarjetas.getNumeroTarjeta());
                    tarjeta.setCuenta(cuentas.getCuenta());
                    tarjeta.setTipoTarjeta(tarjetas.getTipoTarjeta());
                    tarjeta.setNombreTH(tarjetas.getNombreTH());
                    tarjeta.setEstado(tarjetas.getEstadoTarjeta());
                    tarjeta.setLimiteCreditoLocal(tarjetas.getLimiteCreditoLocal());
                    tarjeta.setLimiteCreditoDolares(tarjetas.getLimiteCreditoInter());
                    tarjeta.setSaldoLocal(cuentas.getSaldoLocal());
                    tarjeta.setSaldoDolares(cuentas.getSaldoInter());
                    tarjeta.setDisponibleLocal(tarjetas.getDispLocalTarjeta());
                    tarjeta.setDisponibleDolares(tarjetas.getDispIntTarjeta());
                    tarjeta.setPagoMinimoLocal(cuentas.getPagoMinimoLocal());
                    tarjeta.setPagoMinimoDolares(cuentas.getPagoMinimoInt());
                    tarjeta.setPagoMinimoVencidoLocal(" ");
                    tarjeta.setPagoMinimoVencidoDolares(" ");
                    tarjeta.setPagoContadoLocal(cuentas.getPagoContadoLocal());
                    tarjeta.setPagoContadoDolares(cuentas.getPagoContInt());
                    tarjeta.setFechaPago(cuentas.getFechaVencimientoPago());
                    tarjeta.setFechaUltimoCorte(" ");
                    tarjeta.setSaldoMonedero(" ");
                    tarjeta.setRombosAcumulados(" ");
                    tarjeta.setRombosDinero(cuentas.getSaldoPremiacion());
                    tarjeta.setFondosReservados(" ");

                    responseList.add(tarjeta);
                }
            }
            return responseList;
        }
        return null;
    }
}
