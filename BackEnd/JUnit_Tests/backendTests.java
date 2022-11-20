// **This is the beginning of JUnit testing for this program. If JUnit is not configured on your device, please click on the testing tube icon
//  on the left bar on vscode's window, click 'Enable Tests', and select 'JUnit'**

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;

import org.junit.After;

public class backendTests {

    private final String username = new String("SoftwareEngineeringStudent");
    private final String password = new String("12345");
    private final users user = new users(username, password);
    
    @Before
    public void testInit() {
        System.out.println("Beginning Backend Tests...");
    }
    
    // A simple JUnit test that validates the username and password functionality
    @Test
    public void userValidation() {
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }

    // @Test
    // public void failingTest() {
    //     fail("This is just to prove that tests CAN fail");
    // }

    @After
    public void testEnd() {
        System.out.println("Ending Tests...");
    }
}
