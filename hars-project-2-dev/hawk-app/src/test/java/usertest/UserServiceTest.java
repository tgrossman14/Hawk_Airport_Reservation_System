package usertest;

import dao.UserDao;
import models.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import services.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class UserServiceTest {
    @Mock
    public UserDao userDaoImpl;

    @InjectMocks
    public UserServiceImpl userServiceImpl;

    public HashMap<Integer, User> dummyUsers;


    User mockUser;

    @BeforeClass
    public void beforeClassSetUp(){
        userServiceImpl = new UserServiceImpl();

        MockitoAnnotations.openMocks(this);

        dummyUsers = new HashMap<>();
        dummyUsers.put(1, new User(1, "Honey", "Bunny",
                "hb@lakers.com","yessir"));
        dummyUsers.put(2, new User(2, "James", "Harden",
                "jamesharden@lakers.com","threep"));
        dummyUsers.put(3, new User(3, "Dwayne", "Wade",
                "dream@lakers.com","miami"));
        dummyUsers.put(4, new User(4, "Rob", "Can",
                "rdc@lakers.com","colony"));
        dummyUsers.put(5, new User(5, "Wong", "Shun",
                "corona@lakers.com","virus123"));

        Mockito.when(userDaoImpl.getAllUsers()).thenReturn(dummyUsers);


        mockUser = new User(1, "Chris", "Sal",
                "heybabe@laker.com","loyal321");

        String dummyEmail = "heybabe@laker.com";
        String dummyPassword = "loyal321";

        Mockito.when(userDaoImpl.userLogin(dummyEmail,dummyPassword)).thenReturn(mockUser);
    }

    @Test
    public void testGetAllUsers(){
        Map<Integer, User> retrievedUsers = userServiceImpl.getAllUsers();
        Assert.assertEquals(retrievedUsers.get(1).getId(),1 );
        Assert.assertEquals(retrievedUsers.get(2).getFirstName(),"James" );
        Assert.assertEquals(retrievedUsers.get(3).getLastName(),"Wade" );
        Assert.assertEquals(retrievedUsers.get(4).getEmail(),"rdc@lakers.com" );
        Assert.assertEquals(retrievedUsers.get(5).getPassword(),"virus123" );
    }

    @Test
    public void testUserLogin(){
        String emailTest = "heybabe@laker.com";
        String passwordTest= "loyal321";
        User retrievedUser = userServiceImpl.userLogIn(emailTest,passwordTest);
        Assert.assertEquals(retrievedUser.getId(),1);
        Assert.assertEquals(retrievedUser.getFirstName(),"Chris");
        Assert.assertEquals(retrievedUser.getLastName(),"Sal");
        Assert.assertEquals(retrievedUser.getEmail(),"heybabe@laker.com");
        Assert.assertEquals(retrievedUser.getPassword(),"loyal321");
    }

}
