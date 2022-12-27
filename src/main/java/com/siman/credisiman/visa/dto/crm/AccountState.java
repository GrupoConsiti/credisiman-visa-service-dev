package com.siman.credisiman.visa.dto.crm;

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
public class AccountState {
    @JsonProperty("estadoCuenta") @JsonAlias("estadoCuenta")
    public String estadoCuenta;
    @JsonProperty("correo") @JsonAlias("correo")
    public String correo;
}
