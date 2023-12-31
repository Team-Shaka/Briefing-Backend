package briefing.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.PathSelectors;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi v1Api() {

        return GroupedOpenApi.builder()
                .group("Briefing V1 API")
                .pathsToExclude("/v2/**")
                .build();
    }

    @Bean
    public GroupedOpenApi v2Api() {
        String[] paths = { "/v2/**" };

        return GroupedOpenApi.builder()
                .group("Briefing V2 API")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public OpenAPI breifingAPI() {
        Info info =
                new Info().title("Breifing API").description("Breifing API 명세서").version("1.0.0");

        String jwtSchemeName = "JWT TOKEN";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components =
                new Components()
                        .addSecuritySchemes(
                                jwtSchemeName,
                                new SecurityScheme()
                                        .name(jwtSchemeName)
                                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                                        .scheme("bearer")
                                        .bearerFormat("JWT"));

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
