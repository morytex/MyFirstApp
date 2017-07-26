package br.com.moryta.myfirstapp.model.mapping;

import br.com.moryta.myfirstapp.model.Login;
import br.com.moryta.myfirstapp.model.LoginDTO;

/**
 * Created by moryta on 08/07/2017.
 */
public abstract class LoginMapper {
    public static LoginDTO toDto(Login login) {
        if (login == null) {
            return null;
        };

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(login.getEmail());
        loginDTO.setPassword(login.getPassword());

        return loginDTO;
    }


    public static Login toEntity(LoginDTO loginDTO) {
        if (loginDTO == null) {
            return null;
        }

        Login login = new Login();
        login.setEmail(loginDTO.getEmail());
        login.setPassword(loginDTO.getPassword());

        return login;
    };
}
