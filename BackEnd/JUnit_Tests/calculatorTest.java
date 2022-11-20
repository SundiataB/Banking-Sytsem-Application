import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;

import org.junit.After;

import com.testingdocs.calculator.*;

public class calculatorTest {
    // create a calculator member variable to 
    private final calculator Calculator = new calculator();

    @Before
    public void testInit() {
        System.out.println("Beginning Calculator Tests...");
    }

    @Test
    public void addition(){
        assertEquals(2, Calculator.add(1, 1));
    }

    @Test
    public void subtract(){
        assertEquals(4, Calculator.subtract(5, 1));
    }

    @Test
    public void multiply(){
        assertEquals(20, Calculator.multiply(5, 4));
    }

    @Test
    public void division(){
        //assertEquals(4, Calculator.divide(20, 5));
    }

    @After
    public void testEnd() {
        System.out.println("Ending Calculator Tests...");
    }
}
