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
public class LoginResponse {
    @JsonProperty("success") @JsonAlias("success")
    private String success;

    @JsonProperty("message") @JsonAlias("message")
    private String message;

    @JsonProperty("access_token") @JsonAlias("access_token")
    private String access_token;

    @JsonProperty("refresh_token") @JsonAlias("refresh_token")
    private String refresh_token;
}
