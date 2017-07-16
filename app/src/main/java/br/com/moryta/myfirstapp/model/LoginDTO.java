package br.com.moryta.myfirstapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moryta on 02/07/2017.
 */

public class LoginDTO {
    @SerializedName("usuario")
    private String username;

    @SerializedName("senha")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
