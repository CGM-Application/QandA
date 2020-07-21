package com.cgm.qanda.service;

import com.cgm.qanda.QnAApplication;
import com.cgm.qanda.dataaccessobject.QuestionRepository;
import com.cgm.qanda.dataobject.Answer;
import com.cgm.qanda.dataobject.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
public class QuestionAnswerServiceImplTest {

    @Autowired
    QuestionAnswerService service;

    @Mock
    QuestionRepository repo;

    @Before
    public void setup() {
        Question question = createQuestionEntity();
        repo.save(question);
    }

    private Question createQuestionEntity() {
        Question question = new Question();
        question.setQuestion("question1");
        Answer answer = new Answer();
        answer.setAnswer("answer1");
        Set<Answer> set = new HashSet<>();
        set.add(answer);
        return question;
    }


    @Test
    public void addQuestionTest() {
        Question q = createQuestionEntity();
        q.setQuestion("question");
        Mockito.when(repo.save(q)).thenReturn(q);
        Mockito.when(repo.findByQuestion("question")).thenReturn(Optional.ofNullable(q));
        service.addQuestion("question", "answer");
        List<String> answers = service.getAnswers("question");
        assertNotNull(answers);
        assertEquals("answer", answers.get(0));

    }
    
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test to verify if the answers is correct (as provided)
     */
    @Test
    public void answerTest() {
        Question q = createQuestionEntity();
        q.setQuestion("question");
        Mockito.when(repo.save(q)).thenReturn(q);
        Mockito.when(repo.findByQuestion("question")).thenReturn(Optional.ofNullable(q));
        service.addQuestion("question", "answer1");
        List<String> answers = service.getAnswers("question");
        assertNotNull(answers);
        assertNotEquals("\"" + "the answer to life, universe and everything is 42" + "\"" + " according to" + "\""
                + "The hitchhikers guide to the Galaxy" + "\"", answers.get(0));

    }
        
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test to verify if the answers are in the same order as provided while adding question 
     * Comments: This test fails occasionally. The answers should ideally be in the same order as provided.  
     */
    @Test
    public void answersSequenceTest() {
        Question q = createQuestionEntity();
        q.setQuestion("question");
        Mockito.when(repo.save(q)).thenReturn(q);
        Mockito.when(repo.findByQuestion("question")).thenReturn(Optional.ofNullable(q));
        service.addQuestion("question","answer1"+ "\""+ "answer2"+"\""+"answer3");
        List<String> answers = service.getAnswers("question");
        assertNotNull(answers);
        assertEquals("answer1", answers.get(0));
        assertEquals("answer2", answers.get(1));
        assertEquals("answer3", answers.get(2));

    }
    
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test to verify if the answers has the same number/size as provided while adding question 
     */
    @Test
    public void answersSizeTest() {
        Question q = createQuestionEntity();
        q.setQuestion("question");
        Mockito.when(repo.save(q)).thenReturn(q);
        Mockito.when(repo.findByQuestion("question")).thenReturn(Optional.ofNullable(q));
        service.addQuestion("question","answer1"+ "\""+ "answer2"+"\""+"answer3"+"\""+"answer4"+"\""+"answer5");
        List<String> answers = service.getAnswers("question");
        assertNotNull(answers);
        assertEquals(5, answers.size());

    }
    
    /**
     * Description: Similar to the above e.g. answersSizeTest() this test is to verify if the answers has the same number/size as according to given input
     */
    
    @Test
    public void testGetAnswers() {
        Question q = createQuestionEntity();
        Mockito.when(repo.findByQuestion("question1")).thenReturn(Optional.ofNullable(q));
        List<String> answers = service.getAnswers("question1");
        assertNotNull(answers);
        assertEquals(1, answers.size());
    }
    
    
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test to verify if user asks a question with a question mark "?" at the end the question, if it prints the corresponding answer" 
     * Comments: This test fails, it is common for uses to add question mark at the end of question however, our application fails to meet this requirement 
     */
    
    @Test
    public void askingQuestionWithQuestionMarkTest() {
        Question q = createQuestionEntity();
        q.setQuestion("question");
        Mockito.when(repo.save(q)).thenReturn(q);
        Mockito.when(repo.findByQuestion("question")).thenReturn(Optional.ofNullable(q));
        service.addQuestion("question", "answer");
        List<String> answers = service.getAnswers("question?");
        assertEquals("answer", answers.get(0));

    }
    
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test if user provides a similar question with updated answers does our application provides the updated/right answers 
     * Comments: This test fails, our application should provide updated values for the question 
     * 
     * */
    
    @Test
    public void answersLatestValuesTest() {
    	Question q = createQuestionEntity();
        q.setQuestion("question");
        Mockito.when(repo.save(q)).thenReturn(q);
        Mockito.when(repo.findByQuestion("question")).thenReturn(Optional.ofNullable(q));
        service.addQuestion("question","answer1");
        service.addQuestion("question","answer1"+ "\""+ "answer2"+"\""+"answer3");
        List<String> answers = service.getAnswers("question");
        assertEquals(3, answers.size());
    }
    
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test if the question's best match does not execute
     * */
    
    @Test
    public void questionBestMatchTest() {
    	Question q = createQuestionEntity();
        q.setQuestion("question");
        Mockito.when(repo.save(q)).thenReturn(q);
        Mockito.when(repo.findByQuestion("question")).thenReturn(Optional.ofNullable(q));
        service.addQuestion("question","answer1");
        List<String> answers = service.getAnswers("Question");
        assertNotEquals("answer1", answers.get(0));
    }
    
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test to verify if pass an empty answer while adding question if it prints "the answer to life...." 
     */
    @Test
    public void answersEmptyValueTest() {
        Question q = createQuestionEntity();
        q.setQuestion("question");
        Mockito.when(repo.save(q)).thenReturn(q);
        Mockito.when(repo.findByQuestion("question")).thenReturn(Optional.ofNullable(q));
        service.addQuestion("question","");
        List<String> answers = service.getAnswers("question");
        assertNotNull(answers);
        assertEquals("\"" + "the answer to life, universe and everything is 42" + "\"" + " according to" + "\""
                + "The hitchhikers guide to the Galaxy" + "\"", answers.get(0));

    }
    
    
    /**
     * Creator: Usman Ashraf
     * Date: 21.07.2020
     * Description: Test to verify if pass a null value as answer while adding question if it prints "the answer to life...." 
     */
    @Test
    public void answersNullValueTest() {
        Question q = createQuestionEntity();
        q.setQuestion("question");
        Mockito.when(repo.save(q)).thenReturn(q);
        Mockito.when(repo.findByQuestion("question")).thenReturn(Optional.ofNullable(q));
        service.addQuestion("question",null);
        List<String> answers = service.getAnswers("question");
        assertNotNull(answers);
        assertEquals("\"" + "the answer to life, universe and everything is 42" + "\"" + " according to" + "\""
                + "The hitchhikers guide to the Galaxy" + "\"", answers.get(0));
    }
      
   
}
