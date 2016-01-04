package de.kreggel.auth.core.api;

import de.kreggel.auth.core.api.UserService;
import de.kreggel.auth.core.persistence.jpa.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Date;

public class Registration {

    public void createUser(String username, String password, String email) {

        try {
            EntityManager entityManager = Persistence.createEntityManagerFactory("kreggel-auth").createEntityManager();

            entityManager.getTransaction().begin();

            UserService userService = new UserService();

            User user = userService.createUser(username, "test", username + "@test.de");

            // see that the ID of the user was set by Hibernate
            System.out.println("user=" + user + ", user.id=" + user.getId());

            User foundUser = entityManager.find(User.class, user.getId());

            // note that foundUser is the same instance as user and is a concrete
            // class (not a JDX proxy)
            System.out.println("foundUser=" + foundUser);

            entityManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}