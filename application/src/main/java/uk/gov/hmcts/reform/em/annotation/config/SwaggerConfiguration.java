package uk.gov.hmcts.reform.em.annotation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import uk.gov.hmcts.reform.em.annotation.EmAnnotationApp;

@Configuration
@EnableSwagger2
@ComponentScan("uk.gov.hmcts.reform.em.annotation.controllers")
public class SwaggerConfiguration {

    @Value("${api.version}")
    private String apiVersion;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage(EmAnnotationApp.class.getPackage().getName() + ".controllers"))
            .paths(PathSelectors.any())
            .build();
    }

}
