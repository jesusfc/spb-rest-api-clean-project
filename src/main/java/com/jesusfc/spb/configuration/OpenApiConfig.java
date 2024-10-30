package com.jesusfc.spb.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${spring.application.name}") String appName) {
        return new OpenAPI()
                .info(new Info()
                        .title(appName)
                        .version("1.0")
                        .contact(new Contact().
                                name("Jesús Fdez. Caraballo")
                                .email("jesus.fdez.caraballo@gmail.com"))
                        .license(new License()
                                .name("Copyright (C) " + LocalDate.now().getYear() + " StarMed.es")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }


    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder().group("1-OpenApi-public")
                .pathsToMatch("/**").build();
    }

/*
    @Bean
    GroupedOpenApi userApis() { // group all APIs with `user` in the path
        return GroupedOpenApi.builder().group("2-User").pathsToMatch("/rest/v1/user/**").build();
    }

    @Bean
    GroupedOpenApi adminApis() { // group all APIs with `admin` in the path
        return GroupedOpenApi.builder().group("3-Admin").pathsToMatch("/rest/v1/admin/**").build();
    }

    @Bean
    GroupedOpenApi opsApis() { // group all APIs with `operation` in the path
        return GroupedOpenApi.builder().group("4-Operation").pathsToMatch("/rest/v1/operation/**").build();
    }
*/
}
