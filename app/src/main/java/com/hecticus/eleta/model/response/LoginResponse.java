package com.hecticus.eleta.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by roselyn545 on 15/9/17.
 */

public class LoginResponse {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private Result result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getToken() {
        return result.getToken();
    }

    public String getName() {
        return result.getFirstName() + " " + result.getLastName();
    }

    public int getUserId() {
        return result.getUserId();
    }

    public String getEmail() {
        return result.getEmail();
    }


    private class Result {

        @SerializedName("idUser")
        @Expose
        private int userId = -1;

        @SerializedName("name")
        @Expose
        private String name = "";

        @SerializedName("password")
        @Expose
        private String password = "";

        @SerializedName("firstName")
        @Expose
        private String firstName = "";

        @SerializedName("lastName")
        @Expose
        private String lastName = "";

        @SerializedName("email")
        @Expose
        private String email = "";

        @SerializedName("emailValidated")
        @Expose
        private int emailValidated = -1;

        @SerializedName("token")
        @Expose
        private String token = "";

        @SerializedName("role")
        @Expose
        private Role role;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getEmailValidated() {
            return emailValidated;
        }

        public void setEmailValidated(int emailValidated) {
            this.emailValidated = emailValidated;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    private class Role {

        @SerializedName("idRole")
        @Expose
        private int roleId = -1;

        @SerializedName("name")
        @Expose
        private String name = "";

        @SerializedName("description")
        @Expose
        private String description = "";

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}