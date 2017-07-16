package br.com.moryta.myfirstapp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by moryta on 08/07/2017.
 */
@Entity
public class Login {
    @Id
    private Long id;

    @Index(unique = true)
    private String username;

    @NotNull
    private String password;

    @Generated(hash = 940089093)
    public Login(Long id, String username, @NotNull String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    @Generated(hash = 1827378950)
    public Login() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
