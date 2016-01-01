package de.kreggel.auth.core.api;

import de.kreggel.auth.core.persistence.jpa.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Registration {

    public void createUser(String username, String password, String email ) {

        EntityManager entityManager = Persistence.createEntityManagerFactory("kreggel-auth").createEntityManager();

        entityManager.getTransaction().begin();

        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        entityManager.persist(user);

        entityManager.getTransaction().commit();

        // see that the ID of the user was set by Hibernate
        System.out.println("user=" + user + ", user.id=" + user.getId());

        User foundUser = entityManager.find(User.class, user.getId());

        System.out.println("foundUser=" + foundUser);

        entityManager.close();
    }
}