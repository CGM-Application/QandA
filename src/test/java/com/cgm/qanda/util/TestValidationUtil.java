package com.cgm.qanda.util;

import com.cgm.qanda.QnAApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = QnAApplication.class,
        initializers = ConfigFileApplicationContextInitializer.class)
public class TestValidationUtil {
    @Test
    public void testValidateLength() {
        String input = "test String";
        boolean validate = ValidationUtil.validateLength(input);
        assertEquals(true, validate);
    }
    
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test if the input string is more than 255 characters our application should returns false  
     * Input string length = 370 characters
     * */
    
    @Test
    public void testValidateLengthBigTestString() {
        String input = " Optimisation and performance. If you limit the number of characters to 255 this requires only 2 operations on the CPU where as comparison of variable length strings takes many more steps on the CPU, because you have to repeatedly compare across 255 char widths. Programming languages like VBA obscure this a lot because all of the sub-operations are taken care for you.";
        boolean validate = ValidationUtil.validateLength(input);
        assertEquals(false, validate);
    }
    
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test if a double value is provided as a string how our application behaves
     * */
    
    @Test
    public void testValidateForADoubleValue() {
    	double input = 1000000000000303030303030.10;
        boolean validate = ValidationUtil.validateLength( Double.toString(input));
        assertEquals(true, validate);
    }
    
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test for an empty string as an input
     * Comment: This test fails. We need to handle situation where there is an empty string as input
     * */
    
    @Test
    public void testValidateForEmptyString() {
    	String input = "";
        boolean validate = ValidationUtil.validateLength(input);
        assertEquals(false, validate);
    }
    
  
    @Test
    public void testValidateLengthFailed() {
        String input = null;
        boolean validate = ValidationUtil.validateLength(input);
        assertEquals(false, validate);
    }

    @Test
    public void testValidateAnswerFormat() {
        String input = "this is input " + "\"" + "test";
        boolean validate = ValidationUtil.validateAnswerFormat(input);
        assertEquals(true, validate);
    }

    @Test
    public void testValidateAnswerFormatFailure() {
        String input = "this is wrong input";
        boolean validate = ValidationUtil.validateAnswerFormat(input);
        assertEquals(false, validate);
    }
    
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test for an answer which is more than 255 characters long
     * Comment: This test fails. Despite having "\" character We did not handle situation where there is string or more than 255 characters
     * */
    
    @Test
    public void testValidateAnswerFormatLongString() {
        String input = "this is input " + "\"" +" Optimisation and performance. If you limit the number of characters to 255 this requires only 2 operations on the CPU where as comparison of variable length strings takes many more steps on the CPU, because you have to repeatedly compare across 255 char widths. Programming languages like VBA obscure this a lot because all of the sub-operations are taken care for you.";;
        boolean validate = ValidationUtil.validateAnswerFormat(input);
        assertEquals(false, validate);
    }
    
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test for special characters as an input
     * */
    @Test
    public void testValidateAnswerFormatWithSpecialCharacters() {
        String input = "&&?%§*@ " + "\"" + "?&/()";
        boolean validate = ValidationUtil.validateAnswerFormat(input);
        assertEquals(true, validate);
    }
    
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test for an null as an input
     * Comment: This test fails. We need to handle situation where there is a null as input
     * */
    
    @Test
    public void testValidateAnswerForNullValue() {
        String input = null;
        boolean validate = ValidationUtil.validateAnswerFormat(input);
        assertEquals(false, validate);
    }
    
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test for an empty string as input
     * Comment: This test fails. We need to handle situation where there is an empty string as input
     * */
    
    @Test
    public void testValidateAnswerForEmptyValue() {
        String input = "\"" ;
        boolean validate = ValidationUtil.validateAnswerFormat(input);
        assertEquals(false, validate);
    }
}
