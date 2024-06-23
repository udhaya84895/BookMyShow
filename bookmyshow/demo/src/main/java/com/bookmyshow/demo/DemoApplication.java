package com.bookmyshow.demo;

import com.bookmyshow.demo.controllers.UserController;
import com.bookmyshow.demo.dtos.SignupUserRequestDto;
import com.bookmyshow.demo.models.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private UserController userController;
	public static void main(String[] args) {

//		BaseModel baseModel = new BaseModel();
//		baseModel.setId(1);
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SignupUserRequestDto request = new SignupUserRequestDto();
		request.setEmail("tapaskumarswain77@gmail.com");
		request.setName("Tapas Kumar Swain");
		request.setPassword("tapaskumar7");
		userController.signUp(request);

		userController.login("tapaskumarswain77@gmail.com", "tapaskumar7");
	}
}
