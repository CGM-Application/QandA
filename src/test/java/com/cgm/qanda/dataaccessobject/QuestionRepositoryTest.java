package com.cgm.qanda.dataaccessobject;

import com.cgm.qanda.QnAApplication;
import com.cgm.qanda.dataobject.Answer;
import com.cgm.qanda.dataobject.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = QnAApplication.class,
        initializers = ConfigFileApplicationContextInitializer.class)
public class QuestionRepositoryTest {

    @Autowired
    QuestionRepository repository;

    @Test
    public void TestRepositoryInjected() {
        assertNotNull(repository);
    }

    @Test
    public void testSave() {
        Question question = createUserEntity();
        repository.save(question);
        Optional<Question> q = repository.findByQuestion("question1");
        Question qq = q.get();
        assertEquals("question1", qq.getQuestion());
        repository.flush();
    }
    
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test if the question's with empty strings are added or not
     * Comments: This test fails, Our application should not allow and empty string as a question
     * */
    
    @Test
    public void questionWithEmptyStringTest() {
    	Question question = createUserEntity("");
        repository.save(question);
        Optional<Question> q = repository.findByQuestion("");
        Question qq = q.get();
        assertNotEquals("", qq.getQuestion());
        repository.flush();
    }
    
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test if the question's with more than 255 characters are added or not
     * Comment: Question string length = 370 characters
     * */
    
    @Test
    public void questionWithLongStringTest() {
    	String str = "Optimisation and performance, If you limit the number of characters to 255 this requires only 2 operations on the CPU where as comparison of variable length strings takes many more steps on the CPU, because you have to repeatedly compare across 255 char widths Programming languages like VBA obscure this a lot because all of the sub-operations are taken care for you";
    	Question question = createUserEntity(str);
    	repository.save(question);
        Optional<Question> q = repository.findByQuestion(str);
        Question qq = q.get();
        assertEquals(0, qq.getQuestion().length());
        repository.flush();
    }
    

    private Question createUserEntity() {
        Question question = new Question();
        question.setQuestion("question1");
        Answer answer = new Answer();
        answer.setAnswer("answer1");
        Set<Answer> set = new HashSet<>();
        set.add(answer);
        return question;
    }
    
    private Question createUserEntity(String str) {
        Question question = new Question();
        question.setQuestion(str);
        Answer answer = new Answer();
        answer.setAnswer("answer1");
        Set<Answer> set = new HashSet<>();
        set.add(answer);
        return question;
    }
}
