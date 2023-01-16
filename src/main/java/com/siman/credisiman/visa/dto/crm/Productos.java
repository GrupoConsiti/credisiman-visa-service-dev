package com.siman.credisiman.visa.dto.crm;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Productos {
    @JsonProperty("errorCode") @JsonAlias("errorCode")
    public String errorCode;
    @JsonProperty("errorMessage") @JsonAlias("errorMessage")
    public String errorMessage;
    @JsonProperty("otherProducts") @JsonAlias("otherProducts")
    public List<OtherProduct> otherProducts;
}
