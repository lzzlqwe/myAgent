package com.example.myagent;

import com.example.myagent.app.PsychiatristApp;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class PsychiatristAppTest {

    @Resource
    private PsychiatristApp psychiatristApp;

    @Test
    void testChat() {
        String chatId = UUID.randomUUID().toString();

        String message = "你好，我是大学生迈克";
        String answer = psychiatristApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);

        message = "我想让我朋友（小明）能理解我";
        answer = psychiatristApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);

        message = "我的朋友叫什么来着？刚跟你说过，帮我回忆一下";
        answer = psychiatristApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }
}
