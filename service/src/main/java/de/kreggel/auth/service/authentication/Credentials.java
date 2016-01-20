package de.kreggel.auth.service.authentication;

import java.io.Serializable;

/**
 * Created by oliverklette on 1/1/16.
 */
public class Credentials implements Serializable {

    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
