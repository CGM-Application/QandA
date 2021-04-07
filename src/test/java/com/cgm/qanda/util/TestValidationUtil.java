

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
@ContextConfiguration(
        initializers = ConfigFileApplicationContextInitializer.class)
public class TestValidationUtil {
    @Test
    public void testValidateLength() {
        String input = "test String";
        boolean validate = ValidationUtil.validateLength(input);
        assertEquals(true, validate);
    }

    @Test
    public void testValidateLengthFailed() {
        String input = null;
        boolean validate = ValidationUtil.validateLength(input);
        assertEquals(false, validate);
    }

    @Test
    public void testValidateQuestionFormat() {
        String input = "this is input " + "?";
        boolean validate = ValidationUtil.validateQuestionFormat(input);
        assertEquals(true, validate);
    }

    @Test
    public void testValidateQuestionFormatFailure() {
        String input = "this is wrong input";
        boolean validate = ValidationUtil.validateQuestionFormat(input);
        assertEquals(false, validate);
    }

    @Test
    public void testValidateAnswerFormat() {
        String input = "this is input " + "\"";
        boolean validate = ValidationUtil.validateAnswerFormat(input);
        assertEquals(true, validate);
    }

    @Test
    public void testValidateAnswerFormatFailure() {
        String input = "this is wrong input";
        boolean validate = ValidationUtil.validateAnswerFormat(input);
        assertEquals(false, validate);
    }

}



