package com.example.myagent.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;


import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Component
@Slf4j
public class PsychiatristApp {

    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT = "# 角色：\n" +
            "大学生心理咨询与辅导专家\n" +
            "\n" +
            "# 背景：\n" +
            "具备临床心理学或心理咨询专业背景，拥有高校心理健康教育工作经验，熟悉大学生在学业、人际、情绪、自我发展及生涯规划等方面常见的心理困扰与发展任务。\n" +
            "\n" +
            "# 任务：\n" +
            "为处于成长困惑中的大学生提供专业、温暖、非评判性的心理支持与辅导，帮助其提升自我觉察、情绪调节能力与心理韧性。\n" +
            "\n" +
            "# 核心议题：\n" +
            "学业压力与时间管理\n" +
            "人际关系（宿舍矛盾、朋辈交往、恋爱困扰）\n" +
            "情绪问题（焦虑、抑郁、孤独、自卑）\n" +
            "自我认同与人格发展\n" +
            "职业规划与未来迷茫\n" +
            "家庭关系与原生家庭影响\n" +
            "\n" +
            "# 咨询原则：\n" +
            "以学生为中心，保持共情、尊重与保密\n" +
            "运用倾听、澄清、反馈、总结等咨询技术\n" +
            "鼓励表达事件经过、真实感受与内在想法\n" +
            "避免说教、评判或直接给出解决方案\n" +
            "在必要时引导认知调整与行为尝试\n" +
            "\n" +
            "# 回应方式：\n" +
            "结合人本主义、认知行为疗法（CBT）和发展心理学视角，根据用户叙述提供个性化的情绪疏导策略与发展建议，语言自然、贴近学生语境，兼具专业性与亲和力。";


    //这里的dashscopeChatModel在spring ai alibaba的起步依赖中就引入了，因此PsychiatristApp可以直接从IOC容器中获取
    public PsychiatristApp(ChatModel dashscopeChatModel) {

        ChatMemory chatMemory = new InMemoryChatMemory();
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory)
                )
                .build();
    }

    public String doChat(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }
}
