package briefing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BriefingApplication {

  public static void main(final String[] args) {
    SpringApplication.run(BriefingApplication.class, args);
  }

}
