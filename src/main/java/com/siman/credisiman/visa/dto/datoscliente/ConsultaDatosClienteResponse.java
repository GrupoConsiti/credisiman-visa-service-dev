package com.siman.credisiman.visa.dto.datoscliente;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultaDatosClienteResponse {

    @JsonProperty("statusCode")
    @JsonAlias("statusCode")
    private String statusCode;

    @JsonProperty("status")
    @JsonAlias("status")
    private String status;

    @JsonProperty("statusMessage")
    @JsonAlias("statusMessage")
    private String statusMessage;

    @JsonProperty("nombre")
    @JsonAlias("nombre")
    private String nombre;

    @JsonProperty("segundoNombre")
    @JsonAlias("segundoNombre")
    private String segundoNombre;

    @JsonProperty("primerApellido")
    @JsonAlias("primerApellido")
    private String primerApellido;

    @JsonProperty("segundoApellido")
    @JsonAlias("segundoApellido")
    private String segundoApellido;

    @JsonProperty("apellidoCasada")
    @JsonAlias("apellidoCasada")
    private String apellidoCasada;

    @JsonProperty("tipoIdentificacion")
    @JsonAlias("tipoIdentificacion")
    private String tipoIdentificacion;

    @JsonProperty("identificacion")
    @JsonAlias("identificacion")
    private String identificacion;

    @JsonProperty("correoElectronico")
    @JsonAlias("correoElectronico")
    private String correoElectronico;

    @JsonProperty("celular")
    @JsonAlias("celular")
    private String celular;

    @JsonProperty("direccion")
    @JsonAlias("direccion")
    private String direccion;

    @JsonProperty("direccionPatrono")
    @JsonAlias("direccionPatrono")
    private String direccionPatrono;

    @JsonProperty("correoSiscard")
    @JsonAlias("correoSiscard")
    private String correoSiscard;

    //others
    private String code;
    private String message;
    private String tipoMensaje;
    private String tipoPersoneria;
    private String nacimiento;
    private String estadoCivil;
    private String sexo;
    private String codigoNacionalidad;
    private String telefono1;
    private String direccion2;
    private String direccion3;
    private String ciudad;
    private String estado;
    private String codigoPostal;
    private String apartado;
    private String codigoRegion;
    private String codigoZona;
    private String codigoSector;
    private String codigoProfesion;
    private String claseEnte;
    private String telefonoCelular;
    private String filler4;
    private String numeroCliente;
    private String vivienda;
    private String parienteCercano;
    private String parentesco;
    private String telefonoPariente;
    private String direccionPariente;
    private String ciudadPariente;
    private String codigoPostalPariente;
    private String apartadoPariente;
    private String nombrePatrono;
    private String codigoAreaPatrono;
    private String codigoActividadPatrono;
    private String telefonoPatrono;
    private String ciudadPatrono;
    private String estadoPatrono;
    private String codigoPostalPatron;
    private String apartadoPatrono;
    private String fechaIngresoTrabajo;
    private String puestoActual;
    private String sueldoActual;
    private String origenFondosPatrono;
    private String promedioIngresoPatrono;
    private String promedioEgresoPatrono;
    private String rangoSueldo;
    private String registroMercantil;
    private String fechaInicioEmpresa;
    private String representanteLegal;
    private String identidadRepLegal;
    private String correoElectronicoAdicional;
    private String envioEstado;
    private String numeroNIT;
    private String lugarEmisionNIT;
    private String categoriaCliente;
    private String tomo;
    private String folio;
    private String asiento;
    private String codigoArea;
    private String codigoActividad;
    private String codigoVendedor;
    private String origenFondos;
    private String promedioIngreso;
    private String promedioEgreso;
    private String nombreComercialEmp;
    private String tipoEntidad;
    private String fechaCreacion;
    private String lugarCreacion;
    private String representanteLegalAnterior;
    private String representanteLegalActual;

}