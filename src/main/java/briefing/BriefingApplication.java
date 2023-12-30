package briefing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@OpenAPIDefinition(servers = {@Server(url = "http://localhost:8080", description = "local server"),
        @Server(url = "https://dev.briefing.store", description = "dev server"),
        @Server(url = "https://api.newsbreifing.store", description = "release server")
})

@SpringBootApplication
@EnableFeignClients
@EnableRedisRepositories
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class BriefingApplication {

    public static void main(final String[] args) {
        SpringApplication.run(BriefingApplication.class, args);
    }
}
