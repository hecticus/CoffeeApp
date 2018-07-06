package com.hecticus.eleta.model_new;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityReference(alwaysAsId = true)
public class AuthorizationRequest implements Serializable {

    public String grant_type = "password";
    public String username;
    public String password;
    public String client_id="android_app";
    public String client_secret;

    public AuthorizationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }


}
