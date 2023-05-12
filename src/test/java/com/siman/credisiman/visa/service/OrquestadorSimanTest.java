package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrquestadorSimanTest {
    private static final String NS = "http://siman.com/OrquestadorSiman";
    private static final String headerAuthorization = "Basic Y3JlZGlzaW1hbi12aXNhOlZndiVIUzM4ZFM3Mg==";
    //private static final String urlCredisiman = "http://soaprd.siman.com:7003/api/v1/credisimanVisa";
    private static final String urlCredisiman = "http://soauat.siman.com:7003/api/v1/credisimanVisa";

    @Test
    public void orquestadorSimanObtenerListadoSucursales() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ListadoSucursales", "SV", "", "", "", "", "",
                "", "", "", "", "", "", "", "",
                "", headerAuthorization, urlCredisiman);
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerDatosCliente() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ConsultaDatosCliente", "SV", "048382810", "", "", "",
                "", "", "", "", "", "", "", "", "",
                "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerConsultaMovimientos() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ConsultaMovimientos", "SV", "6008314502274917", "20180101",
                "20221231", "P", "", "", "", "", "", "", "", "", "",
                "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerConsultaSaldoMonedero() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ConsultaSaldoMonedero", "SV", "4000123456780000", "18140",
                "20211009", "45738400", "", "", "", "", "", "", "", "",
                "", "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerListadoTarjetas() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ListadoTarjetas", "SV", "333214569",
                "", "", "", "", "", "", "", "", "", "", "",
                "", "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerConsultaPolizas() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ConsultaPolizas", "SV", "4573840094950811", "V", "",
                "", "", "", "", "", "", "", "", "", "", "",
                headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerListarPlanesPorProducto() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ListarPlanesPorProducto", "SV", "45738400", "",
                "", "", "", "", "", "", "", "", "", "", "",
                "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerObtenerConsultaEstadoCuenta() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ConsultaEstadoCuenta", "SV", "4573840083740702",
                "12345", "20220709", "P", "010161290", "", "",
                "", "", "", "", "", "", "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerConsultaSubproductos() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ConsultaSubproductos", "SV", "4573840094950811",
                "V", "", "", "", "", "", "", "", "", "", "",
                "", "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerListadoEstadosCuenta() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ListadoEstadosCuenta", "SV", "400012345507007",
                "V", "010161290", "", "", "", "", "", "", "", "",
                "", "", "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerBloqueoDesbloqueoTarjeta() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("BloqueoDesbloqueoTarjeta", "SV", "6008315500062519",
                "1", "M6", "P", "", "", "", "", "", "", "",
                "", "", "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerCambioFechaCorte() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("CambioFechaCorte", "SV", "4000123456780000",
                "12345", "25", "P", "", "", "", "", "", "", "",
                "", "", "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanCambioPIN() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("CambioPIN", "SV", "4000123456780000",
                "xOCcB1WjksWWqbK/o8taUQ==", "V", "", "", "", "", "", "", "", "",
                "", "", "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanModificarLimiteTarjeta() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ModificarLimiteTarjeta", "SLV", "0398765432",
                "000000500000", "P", "", "", "", "", "", "", "",
                "", "", "", "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanRegistroUsuario() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("RegistroUsuario", "SV", "prueba",
                "", "prueba", "", "", "SV", "20000907", "DUI", "001000105", "prueba@gmail.com",
                "77776666", "prueba@gmail.com", "60607070", "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanCrearSolicitudReposicionTarjeta() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("SolicitudReposicionTarjeta", "SV", "4573840026044337",
                "CindySolisFernandez", "1", "P", "", "", "", "", "", "", "",
                "", "", "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerSolicitudTrasladoCompraEnCuotas() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("SolicitudTrasladoCompraEnCuotas", "SV", "442427223",
                "6008314502274917", "rafael_najarro@siman.com", "22224444", "57", "63", "0.10", "20230126",
                "02", "840", "NIT", "Rafael", "", "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanEnvioCorreoElectronico() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("EnvioCorreoElectronico", "no-reply@simaninternet.net",
                "CREDISIMAN EL SALVADOR", "prueba en postman",
                "false", "<p>prueba en postman</p>",
                "", "", "", "", "", "", "",
                "", "", "rafael_najarro@siman.com, rafael najarro;", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerEstadoSolicitudTransladoCompraenCuotas() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("EstadoSolicitudTransladoCompraenCuotas", "SV", "95",
                "", "", "", "", "", "", "", "", "", "", "",
                "", "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerListadoProductos() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ListadoProductos", "SV", "",
                "", "", "", "", "", "", "", "", "", "", "",
                "", "", headerAuthorization, urlCredisiman);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}