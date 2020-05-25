package com.gdu.cashbook;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
@PropertySource("classpath:google.properties")
public class CashbookApplication {
	@Value( "${google.username}" )
	public String username;
	@Value( "${google.password}" )
	public String password;

	public static void main(String[] args) {
		SpringApplication.run(CashbookApplication.class, args);
	}
	@Bean
	public JavaMailSender getjaJavaMailSender() {// 이해 1도안됨 잘하는애한테 물어보기
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		 javaMailSender.setHost("smtp.gamil.com"); //메일 서버 이름 
		 javaMailSender.setPort(587);
		 
		 javaMailSender.setUsername("odooda09189@gmail.com");
		 javaMailSender.setPassword("");
		 
		 
		 Properties prop = new Properties(); // 이해 1도안됨 잘하는애한테 물어보기
		 prop.setProperty("mail.smtp.auth", "true");
		 prop.setProperty("mail.smtp.starttls.enable", "true");
		 
		 
		 javaMailSender.setJavaMailProperties(prop);		 
		 return javaMailSender;
}
}
