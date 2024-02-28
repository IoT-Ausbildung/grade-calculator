import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.yourpackage.repository")
@EntityScan(basePackages = "com.yourpackage.entity")
public class GradeCalculatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(YourApplication.class, args);
	}
}

