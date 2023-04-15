package eed.app;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import appdev.firebase.config.FirebaseConfig;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) throws IOException {
		FirebaseConfig.configureFirebaseConnection();
		SpringApplication.run(AppApplication.class, args);
	}

}
