package br.com.moryta.myfirstapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moryta on 02/07/2017.
 */

public class LoginDTO {
    @SerializedName("usuario")
    private String email;

    @SerializedName("senha")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
