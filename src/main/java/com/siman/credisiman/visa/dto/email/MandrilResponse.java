package com.siman.credisiman.visa.dto.email;

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
public class MandrilResponse {
    @JsonProperty("status") @JsonAlias("status")
    private String status;
    @JsonProperty("code") @JsonAlias("code")
    private int code;
    @JsonProperty("name") @JsonAlias("name")
    private String name;
    @JsonProperty("message") @JsonAlias("message")
    private String message;
    @JsonProperty("_id") @JsonAlias("_id")
    private String _id;
    @JsonProperty("reject_reason") @JsonAlias("reject_reason")
    private String reject_reason;
    @JsonProperty("email") @JsonAlias("email")
    private String email;
    @JsonProperty("queued_reason") @JsonAlias("queued_reason")
    private String queued_reason;
}
