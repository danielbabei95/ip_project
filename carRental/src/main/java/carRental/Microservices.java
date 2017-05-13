package carRental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Microservices {

	public static void main(String[] args) {
		SpringApplication.run(Microservices.class, args);
		GatherInfo.main(null);
	}
}
