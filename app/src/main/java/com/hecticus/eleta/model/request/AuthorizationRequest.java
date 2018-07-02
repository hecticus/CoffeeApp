package com.hecticus.eleta.model.request;

import java.io.Serializable;

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
