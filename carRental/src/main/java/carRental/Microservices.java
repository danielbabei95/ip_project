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

/*
import airportmanagement.AirportGatherInfo;
import microservices.GatherInfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan({"airportmanagement","microservices"})
@EnableJpaRepositories({"airportmanagement.repositories","microservices.repositories"})
@SpringBootApplication
@EnableSwagger2
@EntityScan({ "airportmanagement","microservices"})
@EnableAutoConfiguration
public class AirportManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirportManagementApplication.class, args);
		microservices.GatherInfo.main(null);
		AirportGatherInfo.main(null);
	}
}
*/