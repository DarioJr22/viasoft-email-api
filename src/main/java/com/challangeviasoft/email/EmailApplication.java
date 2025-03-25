package com.challangeviasoft.email;

import com.challangeviasoft.email.config.MailConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableConfigurationProperties(MailConfig.class)
public class EmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailApplication.class, args);
		//Loading Env Variables
		Dotenv dotenv = Dotenv.load();
		String redisHost = dotenv.get("REDIS_HOST");
		String redisPort = dotenv.get("REDIS_PORT");
	}

}
