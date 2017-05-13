package airportmanagement;
import airportmanagement.AirportGatherInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan(basePackages = {"airportmanagement"})
@EnableJpaRepositories(basePackages = "airportmanagement.repositories")
@SpringBootApplication
@EnableSwagger2
@EntityScan(basePackages ={ "airportmanagement"})
@EnableAutoConfiguration
public class AirportManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirportManagementApplication.class, args);
		AirportGatherInfo.main(null);
	}
}
