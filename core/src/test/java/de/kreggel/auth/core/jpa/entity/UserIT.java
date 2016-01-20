package de.kreggel.auth.core.jpa.entity;

import de.kreggel.auth.core.api.UserService;
import de.kreggel.auth.core.persistence.jpa.entity.User;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserIT {
    @Test
    public void testNewUser() {

        try {
            EntityManager entityManager = Persistence.createEntityManagerFactory("kreggel-auth").createEntityManager();

            entityManager.getTransaction().begin();

            String username=Long.toString(new Date().getTime());
            username="junit";
            UserService userService=new UserService();

            User user=userService.createUser(username, "test", username + "@test.de");

            // see that the ID of the user was set by Hibernate
            System.out.println("user=" + user + ", user.id=" + user.getId());

            User foundUser = entityManager.find(User.class, user.getId());

            // note that foundUser is the same instance as user and is a concrete
            // class (not a JDX proxy)
            System.out.println("foundUser=" + foundUser);

            assertEquals(user.getUsername(), foundUser.getUsername());

            entityManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindUser() {
        try {
            String username=Long.toString(new Date().getTime());
            username="junit1";
            UserService userService=new UserService();

            User user=userService.createUser(username, "test", username + "@test.de");

            List<User> foundUsers = userService.findUsers(username);

            System.out.println("foundUsers size " + foundUsers.size());

            System.out.println("founduser" + foundUsers.get(0).getUsername());

            assertEquals(username, foundUsers.get(0).getUsername());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testDeleteUser() {
        try {
            String username="%junit%";
            UserService userService=new UserService();
            List<User> users=userService.findUsers(username);
            for(User user:users) {
                System.out.println(user.getUsername());
                userService.deletUser(user);
            }
            users=userService.findUsers(username);
            assertEquals(0, users.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

