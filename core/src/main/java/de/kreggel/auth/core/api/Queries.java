package de.kreggel.auth.core.api;

import de.kreggel.auth.core.persistence.jpa.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Created by oliverklette on 1/1/16.
 */
public class Queries {
    public User findUser(String username) throws Exception {

        EntityManager entityManager = Persistence.createEntityManagerFactory("kreggel-auth").createEntityManager();

        User foundUser = entityManager.createNamedQuery("User.findByName", User.class).setParameter("username", username)
                .getSingleResult();

        System.out.println(foundUser);

        entityManager.close();

        return foundUser;
    }
}
