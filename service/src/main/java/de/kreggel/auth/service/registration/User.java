package de.kreggel.auth.service.registration;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by oliverklette on 1/2/16.
 */
@JsonRootName("user")
public class User implements Serializable{

    private String username;
    private String password;
    private String email;
    private String id;

    public User() {

    }

    public User(String username, String password, String email) {
        this.username=username;
        this.password=password;
        this.email=email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
