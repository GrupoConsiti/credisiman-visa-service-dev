package com.siman.credisiman.visa.dto.crm;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FechaCorte {
    @JsonProperty("errorCode") @JsonAlias("errorCode")
    public String errorCode;
    @JsonProperty("errorMessage") @JsonAlias("errorMessage")
    public String errorMessage;
    @JsonProperty("fechaCorte") @JsonAlias("fechaCorte")
    public ArrayList<String> fechaCorte;
}
