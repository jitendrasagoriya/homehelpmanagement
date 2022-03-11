package com.jitendra.homehelp.config;

import com.jitendra.homehelp.endpoint.HomeEndpoint;
import com.jitendra.homehelp.interceptor.AuthenticatorInterceptor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebMvc
@EnableSwagger2
@EnableAutoConfiguration
public class SpringFoxConfig  extends WebMvcConfigurerAdapter {

    @Autowired
    AuthenticatorInterceptor authenticatorInterceptor;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                //.globalOperationParameters(globalParameterList())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                    .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Home Help Management")
                .version("1.0")
                .build();

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticatorInterceptor);
    }

    private List<RequestParameter> globalParameterList() {
        val authTokenHeader =
                new RequestParameterBuilder()
                        .name("AUTH-TOKEN") // name of the header
                        //.modelRef(new ModelRef("string")) // data-type of the header
                        .required(true) // required/optional
                        .in("header") // for query-param, this value can be 'query'
                        .description("Basic Auth Token")
                        .build();

        return Collections.singletonList(authTokenHeader);
    }


    public static String[] SWAGGER_URL_PATHS = new String[] { "/swagger-ui.html**", "/swagger-resources/**",
            "/v2/api-docs**", "/webjars/**" };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
