package com.noticiacerta.bot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection; // Importante
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer; // Importante
import org.testcontainers.containers.RabbitMQContainer; // Importante
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers; // Importante

@SpringBootTest(properties = {
		"telegram.bot.token=token_falso_de_teste",
		"telegram.bot.username=BotTeste"
})
@ActiveProfiles("test")
@Testcontainers
class NoticiaCertaBotApplicationTests {

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13-alpine");

	@Container
	@ServiceConnection
	static RabbitMQContainer rabbitMQ = new RabbitMQContainer("rabbitmq:3-management-alpine");

	@Test
	void contextLoads() {
	}
}