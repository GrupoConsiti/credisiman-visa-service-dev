package com.siman.credisiman.visa.dto.listadotarjeta;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class CuentasResponse {

    @JsonProperty("emisor")
    @JsonAlias("emisor")
    private String emisor;

    @JsonProperty("cuenta")
    @JsonAlias("cuenta")
    private String cuenta;

    @JsonProperty("nombreTH")
    @JsonAlias("nombreTH")
    private String nombreTH;

    @JsonProperty("cedula")
    @JsonAlias("cedula")
    private String cedula;

    @JsonProperty("estadoCuenta")
    @JsonAlias("estadoCuenta")
    private String estadoCuenta;

    @JsonProperty("diaCorte")
    @JsonAlias("diaCorte")
    private String diaCorte;

    @JsonProperty("importeVencidoInter")
    @JsonAlias("importeVencidoInter")
    private String importeVencidoInter;

    @JsonProperty("importeVencidoLocal")
    @JsonAlias("importeVencidoLocal")
    private String importeVencidoLocal;

    @JsonProperty("fechaVencimientoCuenta")
    @JsonAlias("fechaVencimientoCuenta")
    private String fechaVencimientoCuenta;

    @JsonProperty("fechaVencimientoPago")
    @JsonAlias("fechaVencimientoPago")
    private String fechaVencimientoPago;

    @JsonProperty("montoImporteInter")
    @JsonAlias("montoImporteInter")
    private String montoImporteInter;

    @JsonProperty("montoImporteLocal")
    @JsonAlias("montoImporteLocal")
    private String montoImporteLocal;

    @JsonProperty("pagoContInt")
    @JsonAlias("pagoContInt")
    private String pagoContInt;

    @JsonProperty("pagoContadoLocal")
    @JsonAlias("pagoContadoLocal")
    private String pagoContadoLocal;

    @JsonProperty("pagoMinimoInt")
    @JsonAlias("pagoMinimoInt")
    private String pagoMinimoInt;

    @JsonProperty("pagoMinimoLocal")
    @JsonAlias("pagoMinimoLocal")
    private String pagoMinimoLocal;

    @JsonProperty("saldoInter")
    @JsonAlias("saldoInter")
    private String saldoInter;

    @JsonProperty("saldoLocal")
    @JsonAlias("saldoLocal")
    private String saldoLocal;

    @JsonProperty("saldoPremiacion")
    @JsonAlias("saldoPremiacion")
    private String saldoPremiacion;

    @JsonProperty("marca")
    @JsonAlias("marca")
    private String marca;

    @JsonProperty("salTotCorteLocal")
    @JsonAlias("salTotCorteLocal")
    private String salTotCorteLocal;

    @JsonProperty("salTotCorteInter")
    @JsonAlias("salTotCorteInter")
    private String salTotCorteInter;

    @JsonProperty("tasaInteresMoratoriaLocal")
    @JsonAlias("tasaInteresMoratoriaLocal")
    private String tasaInteresMoratoriaLocal;

    @JsonProperty("tasaInteresMoratoriaInter")
    @JsonAlias("tasaInteresMoratoriaInter")
    private String tasaInteresMoratoriaInter;

    @JsonProperty("tasaInteresMensualLocal")
    @JsonAlias("tasaInteresMensualLocal")
    private String tasaInteresMensualLocal;

    @JsonProperty("tasaInteresMensualInter")
    @JsonAlias("tasaInteresMensualInter")
    private String tasaInteresMensualInter;

    @JsonProperty("limiteCreditoLocal")
    @JsonAlias("limiteCreditoLocal")
    private String limiteCreditoLocal;

    @JsonProperty("limiteCreditoInter")
    @JsonAlias("limiteCreditoInter")
    private String limiteCreditoInter;

    @JsonProperty("tarjetas")
    @JsonAlias("tarjetas")
    private List<TarjetasResponse> tarjetas;

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombreTH() {
        return nombreTH;
    }

    public void setNombreTH(String nombreTH) {
        this.nombreTH = nombreTH;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public String getDiaCorte() {
        return diaCorte;
    }

    public void setDiaCorte(String diaCorte) {
        this.diaCorte = diaCorte;
    }

    public String getImporteVencidoInter() {
        return importeVencidoInter;
    }

    public void setImporteVencidoInter(String importeVencidoInter) {
        this.importeVencidoInter = importeVencidoInter;
    }

    public String getImporteVencidoLocal() {
        return importeVencidoLocal;
    }

    public void setImporteVencidoLocal(String importeVencidoLocal) {
        this.importeVencidoLocal = importeVencidoLocal;
    }

    public String getFechaVencimientoCuenta() {
        return fechaVencimientoCuenta;
    }

    public void setFechaVencimientoCuenta(String fechaVencimientoCuenta) {
        this.fechaVencimientoCuenta = fechaVencimientoCuenta;
    }

    public String getFechaVencimientoPago() {
        return fechaVencimientoPago;
    }

    public void setFechaVencimientoPago(String fechaVencimientoPago) {
        this.fechaVencimientoPago = fechaVencimientoPago;
    }

    public String getMontoImporteInter() {
        return montoImporteInter;
    }

    public void setMontoImporteInter(String montoImporteInter) {
        this.montoImporteInter = montoImporteInter;
    }

    public String getMontoImporteLocal() {
        return montoImporteLocal;
    }

    public void setMontoImporteLocal(String montoImporteLocal) {
        this.montoImporteLocal = montoImporteLocal;
    }

    public String getPagoContInt() {
        return pagoContInt;
    }

    public void setPagoContInt(String pagoContInt) {
        this.pagoContInt = pagoContInt;
    }

    public String getPagoContadoLocal() {
        return pagoContadoLocal;
    }

    public void setPagoContadoLocal(String pagoContadoLocal) {
        this.pagoContadoLocal = pagoContadoLocal;
    }

    public String getPagoMinimoInt() {
        return pagoMinimoInt;
    }

    public void setPagoMinimoInt(String pagoMinimoInt) {
        this.pagoMinimoInt = pagoMinimoInt;
    }

    public String getPagoMinimoLocal() {
        return pagoMinimoLocal;
    }

    public void setPagoMinimoLocal(String pagoMinimoLocal) {
        this.pagoMinimoLocal = pagoMinimoLocal;
    }

    public String getSaldoInter() {
        return saldoInter;
    }

    public void setSaldoInter(String saldoInter) {
        this.saldoInter = saldoInter;
    }

    public String getSaldoLocal() {
        return saldoLocal;
    }

    public void setSaldoLocal(String saldoLocal) {
        this.saldoLocal = saldoLocal;
    }

    public String getSaldoPremiacion() {
        return saldoPremiacion;
    }

    public void setSaldoPremiacion(String saldoPremiacion) {
        this.saldoPremiacion = saldoPremiacion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSalTotCorteLocal() {
        return salTotCorteLocal;
    }

    public void setSalTotCorteLocal(String salTotCorteLocal) {
        this.salTotCorteLocal = salTotCorteLocal;
    }

    public String getSalTotCorteInter() {
        return salTotCorteInter;
    }

    public void setSalTotCorteInter(String salTotCorteInter) {
        this.salTotCorteInter = salTotCorteInter;
    }

    public String getTasaInteresMoratoriaLocal() {
        return tasaInteresMoratoriaLocal;
    }

    public void setTasaInteresMoratoriaLocal(String tasaInteresMoratoriaLocal) {
        this.tasaInteresMoratoriaLocal = tasaInteresMoratoriaLocal;
    }

    public String getTasaInteresMoratoriaInter() {
        return tasaInteresMoratoriaInter;
    }

    public void setTasaInteresMoratoriaInter(String tasaInteresMoratoriaInter) {
        this.tasaInteresMoratoriaInter = tasaInteresMoratoriaInter;
    }

    public String getTasaInteresMensualLocal() {
        return tasaInteresMensualLocal;
    }

    public void setTasaInteresMensualLocal(String tasaInteresMensualLocal) {
        this.tasaInteresMensualLocal = tasaInteresMensualLocal;
    }

    public String getTasaInteresMensualInter() {
        return tasaInteresMensualInter;
    }

    public void setTasaInteresMensualInter(String tasaInteresMensualInter) {
        this.tasaInteresMensualInter = tasaInteresMensualInter;
    }

    public String getLimiteCreditoLocal() {
        return limiteCreditoLocal;
    }

    public void setLimiteCreditoLocal(String limiteCreditoLocal) {
        this.limiteCreditoLocal = limiteCreditoLocal;
    }

    public String getLimiteCreditoInter() {
        return limiteCreditoInter;
    }

    public void setLimiteCreditoInter(String limiteCreditoInter) {
        this.limiteCreditoInter = limiteCreditoInter;
    }

    public List<TarjetasResponse> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(List<TarjetasResponse> tarjetas) {
        this.tarjetas = tarjetas;
    }

    public CuentasResponse() {

    }

    public CuentasResponse(String emisor, String cuenta, String nombreTH, String cedula, String estadoCuenta, String diaCorte, String importeVencidoInter, String importeVencidoLocal, String fechaVencimientoCuenta, String fechaVencimientoPago, String montoImporteInter, String montoImporteLocal, String pagoContInt, String pagoContadoLocal, String pagoMinimoInt, String pagoMinimoLocal, String saldoInter, String saldoLocal, String saldoPremiacion, String marca, String salTotCorteLocal, String salTotCorteInter, String tasaInteresMoratoriaLocal, String tasaInteresMoratoriaInter, String tasaInteresMensualLocal, String tasaInteresMensualInter, String limiteCreditoLocal, String limiteCreditoInter, List<TarjetasResponse> tarjetas) {
        this.emisor = emisor;
        this.cuenta = cuenta;
        this.nombreTH = nombreTH;
        this.cedula = cedula;
        this.estadoCuenta = estadoCuenta;
        this.diaCorte = diaCorte;
        this.importeVencidoInter = importeVencidoInter;
        this.importeVencidoLocal = importeVencidoLocal;
        this.fechaVencimientoCuenta = fechaVencimientoCuenta;
        this.fechaVencimientoPago = fechaVencimientoPago;
        this.montoImporteInter = montoImporteInter;
        this.montoImporteLocal = montoImporteLocal;
        this.pagoContInt = pagoContInt;
        this.pagoContadoLocal = pagoContadoLocal;
        this.pagoMinimoInt = pagoMinimoInt;
        this.pagoMinimoLocal = pagoMinimoLocal;
        this.saldoInter = saldoInter;
        this.saldoLocal = saldoLocal;
        this.saldoPremiacion = saldoPremiacion;
        this.marca = marca;
        this.salTotCorteLocal = salTotCorteLocal;
        this.salTotCorteInter = salTotCorteInter;
        this.tasaInteresMoratoriaLocal = tasaInteresMoratoriaLocal;
        this.tasaInteresMoratoriaInter = tasaInteresMoratoriaInter;
        this.tasaInteresMensualLocal = tasaInteresMensualLocal;
        this.tasaInteresMensualInter = tasaInteresMensualInter;
        this.limiteCreditoLocal = limiteCreditoLocal;
        this.limiteCreditoInter = limiteCreditoInter;
        this.tarjetas = tarjetas;
    }
}
