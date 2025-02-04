package com.pk.ask;

import com.pk.app.chatservice.ask.Answer;
import com.pk.app.chatservice.ask.Question;
import com.pk.app.chatservice.controllers.AskController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@Disabled("This test is failing because of the missing dependencies")

class AskControllerTest {

    @Mock
    private ChatClient.Builder chatClientBuilder;

    @Mock
    private ChatClient chatClient;

    @Mock
    private VectorStore vectorStore;

    @InjectMocks
    private AskController askController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(chatClientBuilder.defaultAdvisors((Advisor) any())).thenReturn(chatClientBuilder);

        when(chatClientBuilder.defaultAdvisors((Advisor[]) any())).thenReturn(chatClientBuilder);
        when(chatClientBuilder.build()).thenReturn(chatClient);

    }

    @Test
    void askReturnsAnswerSuccessfully() {
        Question question = new Question("What is the capital of France?");
        Answer expectedAnswer = new Answer("Paris");
        when(chatClient.prompt().user(anyString()).advisors((Consumer<ChatClient.AdvisorSpec>) any()).call().entity(Answer.class)).thenReturn(expectedAnswer);

        Answer answer = askController.ask(question, "defaultConversation");

        assertEquals(expectedAnswer, answer);
    }

    @Test
    void askWithDifferentConversationId() {
        Question question = new Question("What is the capital of France?");
        Answer expectedAnswer = new Answer("Paris");
        when(chatClient.prompt().user(anyString()).advisors((Consumer<ChatClient.AdvisorSpec>) any()).call().entity(Answer.class)).thenReturn(expectedAnswer);

        Answer answer = askController.ask(question, "newConversation");

        assertEquals(expectedAnswer, answer);
    }

    @Test
    void askHandlesNullQuestion() {
        assertThrows(NullPointerException.class, () -> askController.ask(null, "defaultConversation"));
    }

    @Test
    void askHandlesEmptyConversationId() {
        Question question = new Question("What is the capital of France?");
        Answer expectedAnswer = new Answer("Paris");
        when(chatClient.prompt().user(anyString()).advisors((Consumer<ChatClient.AdvisorSpec>) any()).call().entity(Answer.class)).thenReturn(expectedAnswer);

        Answer answer = askController.ask(question, "");

        assertEquals(expectedAnswer, answer);
    }
}