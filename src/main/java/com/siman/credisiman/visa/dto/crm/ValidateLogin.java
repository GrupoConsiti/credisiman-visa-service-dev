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
public class ValidateLogin {
    @JsonProperty("error_code") @JsonAlias("error_code")
    private String error_code;

    @JsonProperty("error_message") @JsonAlias("error_message")
    private String error_message;

}
