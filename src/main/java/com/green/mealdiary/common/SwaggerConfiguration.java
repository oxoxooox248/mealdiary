package com.green.mealdiary.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().components(new Components()).info(new Info().
                title("오늘 뭐 먹조").description("식사 다이어리").version("1.0.0"));
    }
}
