package automotora.financiamiento_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class FinanciamientoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanciamientoServiceApplication.class, args);
	}

}
