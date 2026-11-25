
package com.digitalbank.customer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customerApiDocumentation() {

        return new OpenAPI()
                .info(new Info()
                        .title("Digital Bank - Customer Auth API")
                        .version("1.0.0")
                        .description(
                                "API REST responsável pelo gerenciamento " +
                                        "e cadastro de clientes do Digital Bank."
                        )
                        .contact(new Contact()
                                .name("Digital Bank Development Team")
                                .email("dev@digitalbank.local")
                        )
                );
    }
}
