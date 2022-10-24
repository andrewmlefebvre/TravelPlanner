package Integration.sql;

import Entity.User;
import Integration.JDBC.SQLHelper;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

public class UserTest {

    SQLHelper sql;

    @Before
    public void before(){
        sql = new SQLHelper();
        sql.wipe();
    }


    @Test
    public void testCreateAndGetUser(){
        User user = new User(null, "Andrew", "Lefebvre", new Date(1999, 05, 03), "21 Test Ave Kingston RI 02882", "User1");
        SQLHelper sql = new SQLHelper();
        sql.addUser(user);
        List<User> recievedUser = sql.getUsersWithName("Andrew", "Lefebvre");

       assertTrue(recievedUser.stream().filter(u -> u.getID() == user.getID()).count() == 1);
    }

    @Test
    public void testGetAllUsers(){
        User user1 = new User(null, "Test1", "Lefebvre", new Date(1999, 05, 03), "21 Test Ave Kingston RI 02882", "User1");
        User user2 = new User(null, "Test2", "Lefebvre", new Date(1999, 05, 03), "21 Test Ave Kingston RI 02882", "User2");
        User user3 = new User(null, "Test3", "Lefebvre", new Date(1999, 05, 03), "21 Test Ave Kingston RI 02882", "User3");

        sql.addUser(user1);
        sql.addUser(user2);
        sql.addUser(user3);

        assertTrue(sql.getAllUsers().size() == 3);
    }

    @Test
    public void testGetFriends(){
        User user1 = new User(null, "Test1", "TEsTest", new Date(1999, 05, 03), "21 Test Ave Kingston RI 02882", "User1");
        User user2 = new User(null, "Test2", "Lefebvre", new Date(1999, 05, 03), "21 Test Ave Kingston RI 02882", "User2");
        User user3 = new User(null, "Test3", "Lefebvre", new Date(1999, 05, 03), "21 Test Ave Kingston RI 02882", "User3");

        sql.addUser(user1);
        sql.addUser(user2);
        sql.addUser(user3);
        sql.setFriend(user1, user2);
        sql.setFriend(user2, user3);
        sql.setFriend(user3, user1);
        sql.setFriend(user3, user2);

        assertTrue(sql.getFriendsOfUser(user1).get(0).getLastName().equalsIgnoreCase("Lefebvre"));
        assertTrue(sql.getFriendsOfUser(user2).get(0).getFirstName().equalsIgnoreCase("Test3"));
        assertTrue(sql.getFriendsOfUser(user3).size() == 2);
    }

    @Test
    public void testUserName(){
        User user1 = new User(null, "Test1", "TEsTest", new Date(1999, 05, 03), "21 Test Ave Kingston RI 02882", "User1");
        User user2 = new User(null, "Test2", "Lefebvre", new Date(1999, 05, 03), "21 Test Ave Kingston RI 02882", "User2");
        sql.addUser(user1);
        sql.addUser(user2);

        assertTrue(sql.getUserWithID(1).getUserName().equalsIgnoreCase("User1"));
        assertTrue(sql.getUserWithID(2).getUserName().equalsIgnoreCase("User2"));

        assertTrue(sql.getUserWithUserName("User1").getID() == 1);
        assertTrue(sql.getUserWithUserName("User2").getID() == 2);
        assertTrue(sql.getUserWithUserName("Test") == null);
    }

}
