package usertest;

import dao.UserDao;
import dao.UserDaoImpl;

import dbconfig.ConnectionUtil;
import models.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

// Using a local test database with the same structure as the production database
public class UserDaoTest {
    @Mock
    Connection connection;
    @Mock
    ResultSet resultSet;
    @Mock
    Statement statement;
    @Mock
    PreparedStatement prepStmt;
    @InjectMocks
    public UserDaoImpl userDaoImpl;


    @BeforeClass
    public void setUp() throws SQLException {
        userDaoImpl = new UserDaoImpl();
        MockitoAnnotations.openMocks(this);

    }
    @Test
    public void testGetAllUsers(){
        Map<Integer, User> users = new HashMap<>();
        Map<Integer, User> expectedUsers;
        Map<Integer, User> actualUsers;

        try{
            // This is the local data base url, username and password for testing
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/","postgres","kent123q");
            final String SQL = "select * from users";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);

            System.out.println(resultSet);

            while(resultSet.next()) {
                User retrievedUser = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5));
                users.put(resultSet.getInt(1), retrievedUser);
            }

        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        expectedUsers = users;

        actualUsers = userDaoImpl.getAllUsers();
        // Assertions - getting the ID, First name, last name, email, and password
        for(Map.Entry<Integer, User> getUsers: actualUsers.entrySet()){
            Assert.assertEquals(expectedUsers.get(getUsers.getKey()).getId(), getUsers.getValue().getId());
            Assert.assertEquals(expectedUsers.get(getUsers.getKey()).getFirstName(), getUsers.getValue().getFirstName());
            Assert.assertEquals(expectedUsers.get(getUsers.getKey()).getLastName(), getUsers.getValue().getLastName());
            Assert.assertEquals(expectedUsers.get(getUsers.getKey()).getEmail(), getUsers.getValue().getEmail());
            Assert.assertEquals(expectedUsers.get(getUsers.getKey()).getPassword(), getUsers.getValue().getPassword());
        }
    }
    @Test
    public void registerUser(){
        User expectedUser = new User(10,"Slime","Monkey","isekai@lakers.com","slime");
        // The same procedure is being used in the actual database to avoid cluttering the actual database the data
        // being created is stored in the test database
        try{
            // This is the local data base url, username and password for testing
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/","postgres","kent123q");
            final String SQL = "insert into users values(default, ?, ?, ?, ?) RETURNING(user_id)";

            prepStmt = connection.prepareStatement(SQL);

            //Since the SQL statement is parameterized, I need to set the values of
            //the parameters.
            prepStmt.setString(1, expectedUser.getFirstName());
            prepStmt.setString(2, expectedUser.getLastName());
            prepStmt.setString(3, expectedUser.getEmail());
            prepStmt.setString(4, expectedUser.getPassword());

            //And of course, execute the SQL statement once you have set your parameters.
            resultSet = prepStmt.executeQuery();
            // Assertion to ensure the user is created, check if there's something in the result set
            Assert.assertTrue(resultSet.next());
            // Then we need to delete to avoid affecting future tests.
            final String SQLTwo = "delete from users where user_email = ?";
            prepStmt = connection.prepareStatement(SQLTwo);
            prepStmt.setString(1,expectedUser.getEmail());
            prepStmt.execute();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        System.out.println("This is for registering the user");
    }

    @Test
    public void userLogin(){
        User actualUser = null;
        User expectedUser = null;
        User oneUser = null;
        String expectedEmail = "tt@lakers.com";
        String expectedPassword = "ttjerry";
        try{
            // This is the local data base url, username and password for testing
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/","postgres","kent123q");
            final String SQL = "select * from users where user_email = ? and user_password = ?";

            prepStmt = connection.prepareStatement(SQL); // Prepare statement
            prepStmt.setString(1, expectedEmail);
            prepStmt.setString(2, expectedPassword);

            resultSet = prepStmt.executeQuery(); // Execute sql
            while(resultSet.next()) {
                // put(key, value) - the key is the user_id and the value is the whole row
                oneUser = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5));
            }
            if (oneUser == null) {
                throw new IllegalArgumentException();
            }

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        expectedUser = oneUser;
        actualUser = userDaoImpl.userLogin(expectedEmail,expectedPassword);

        // Assertions - getting the ID, First name, last name, email, and password for the user log in
        assert expectedUser != null;
        Assert.assertEquals(actualUser.getId(),expectedUser.getId());
        Assert.assertEquals(actualUser.getFirstName(),expectedUser.getFirstName());
        Assert.assertEquals(actualUser.getLastName(),expectedUser.getLastName());
        Assert.assertEquals(actualUser.getEmail(),expectedUser.getEmail());
        Assert.assertEquals(actualUser.getPassword(),expectedUser.getPassword());

        System.out.println("This is for user login");
    }
}
