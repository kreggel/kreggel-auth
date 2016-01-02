package de.kreggel.auth.core.api;

import de.kreggel.auth.core.persistence.jpa.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Created by oliverklette on 1/2/16.
 */
public class UserService {

    public User createUser(String username, String password, String email ) throws Exception{

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

        return user;
    }

    public User findUser(String username) throws Exception {

        EntityManager entityManager = Persistence.createEntityManagerFactory("kreggel-auth").createEntityManager();

        User foundUser = entityManager.createNamedQuery("User.findByName", User.class).setParameter("username", username).getSingleResult();

        System.out.println(foundUser);

        entityManager.close();

        return foundUser;
    }

    public void deletUser(String username) throws Exception {
        try {
            User user = this.findUser(username);

            EntityManager entityManager = Persistence.createEntityManagerFactory("kreggel-auth").createEntityManager();

            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("User " + username + " doesn't exist");
        }

    }
}
