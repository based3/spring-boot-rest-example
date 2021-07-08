
https://restfulapi.net/

https://spring.io/guides/tutorials/rest/*

# Spring boot exception handling
https://zetcode.com/springboot/controlleradvice/

https://stackoverflow.com/questions/29075160/no-responsebody-returned-from-exceptionhandler-in-spring-boot-app-deployed-in

https://www.baeldung.com/spring-httpmessageconverter-rest

https://mkyong.com/spring-boot/spring-rest-error-handling-example/

# Migration spring-boot 1 to 2
https://dzone.com/articles/spring-boot-migration-from-15-to-205-release

https://stackoverflow.com/questions/13649015/error-in-java-import-statement-the-import-javax-validation-constraints-notnull

https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Migration-Guide#migrating-custom-countersgauges

https://github.com/jrcodeza/spring-openapi generator

https://medium.com/@remenec.jakub/openapi-3-spec-and-client-generation-on-java-spring-application-38a9ba5a2932

https://swagger.io/blog/news/whats-new-in-openapi-3-0/

https://spring.io/blog/2018/03/16/micrometer-spring-boot-2-s-new-application-metrics-collector
https://speakerdeck.com/michaelsimons/micrometer-new-insights-into-your-spring-boot-application?slide=11
  /actuator/metrics

https://springdoc.org/migrating-from-springfox.html

https://www.javacodemonk.com/migrating-spring-boot-tests-from-junit-4-to-junit-5-00aa2839

# H2 database UI

http://localhost:8080/h2-console/
spring.h2.console.enabled=true

# Test spring mock
https://stackoverflow.com/questions/29587958/how-to-treat-controller-exception-with-mockmvc

# junit 4 to junit 5
https://www.baeldung.com/junit-5-migration
https://www.baeldung.com/spring-webappconfiguration
https://developer.okta.com/blog/2019/03/28/test-java-spring-boot-junit5

https://www.yawintutor.com/using-generated-security-password-spring-boot/
1)
spring.security.user.name = username
spring.security.user.password = password
2)
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration
SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SpringBootSecuritySimpleApplication {
public static void main(String[] args) {
SpringApplication.run(SpringBootSecuritySimpleApplication.class, args);
}
}
3)
@Configuration
public class SpringBootSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user").password("{noop}password").roles("USER");
	}
}



# Tests with Karate
https://github.com/intuit/karate

# Error prone issue
Caused by: java.lang.NoSuchMethodError: com.sun.tools.javac.util.JavacMessages.add(Lcom/sun/tools/javac/util/JavacMessages$ResourceBundleHelper;)V
at com.google.errorprone.BaseErrorProneJavaCompiler.setupMessageBundle(BaseErrorProneJavaCompiler.java:209)

https://github.com/google/error-prone/issues/535


# Check
http://localhost:8090/actuator



