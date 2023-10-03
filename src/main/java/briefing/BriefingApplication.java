package briefing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableFeignClients
@EnableRedisRepositories
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class BriefingApplication {

  public static void main(final String[] args) {
    SpringApplication.run(BriefingApplication.class, args);
  }

}
