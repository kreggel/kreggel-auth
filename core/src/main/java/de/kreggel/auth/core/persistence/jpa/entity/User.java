package de.kreggel.auth.core.persistence.jpa.entity;

import javax.persistence.*;

/**
 * Created by oliverklette on 12/29/15.
 */

@Entity
@Table(name = "users")
@NamedQuery(name="User.findByName", query = "select u from User u where u.username like :username")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private int id;
    private String username;
    private String password;
    private String email;

    public User(int id, String username, String password, String email) {
        super( );
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User() {
        super( );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
