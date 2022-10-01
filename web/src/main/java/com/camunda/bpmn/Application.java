package com.camunda.bpmn;

import com.javastudio.tutorial.twitter.oauth.web.config.OAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableConfigurationProperties({OAuthProperties.class})
@EnableSwagger2
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
//        new SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE).run(args);
        SpringApplication.run(Application.class, args);
    }
}
