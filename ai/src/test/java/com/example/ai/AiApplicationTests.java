package com.example.ai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ai.ollama.OllamaChatModel;




@SpringBootTest
class AiApplicationTests {
	@Autowired
	private final OllamaChatModel chatModel;

    AiApplicationTests(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Test
	void contextLoads() {
		String message = """
    	给我讲一个java程序员的笑话吧
				""";
		System.out.println(chatModel.call(message));
	}
}
