package swagger

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.grails.SpringfoxGrailsIntegrationConfiguration
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.Contact
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
@Import([SpringfoxGrailsIntegrationConfiguration])
class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }

    @Bean
    Docket api() {


        new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(MetaClass)
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex('^(?!auth).*$'))
//                .paths(PathSelectors.any())
//                .paths(not(ant("/error")))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
    }

    private List<ApiKey> securitySchemes() {
        def arr = new ArrayList()
        arr.add(new ApiKey("Authorization", "Authorization", "header"))
        return arr
    }
    private List<SecurityContext> securityContexts() {
        def arr = new ArrayList()
        arr.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex('^(?!auth).*$'))
                .build())
        return arr
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        def arr = new ArrayList()
        arr.add(new SecurityReference("Authorization", authorizationScopes))
        return arr
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("grails swagger plugin demo")
                .description("grails swagger demo")
                .contact(new Contact("songtao", "", "923174530@qq.com"))
                .version("1.0")
                .build()
    }

    @Bean
    static WebMvcConfigurerAdapter webConfigurer() {
        new WebMvcConfigurerAdapter() {
            @Override
            void addResourceHandlers(ResourceHandlerRegistry registry) {
                if (!registry.hasMappingForPattern("/webjars/**")) {
                    registry
                            .addResourceHandler("/webjars/**")
                            .addResourceLocations("classpath:/META-INF/resources/webjars/")
                }
                if (!registry.hasMappingForPattern("/swagger-ui.html")) {
                    registry
                            .addResourceHandler("/swagger-ui.html")
                            .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html")
                }
            }
        }
    }
}