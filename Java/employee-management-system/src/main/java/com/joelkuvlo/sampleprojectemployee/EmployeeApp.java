package com.joelkuvlo.sampleprojectemployee;

import com.joelkuvlo.sampleprojectemployee.config.EmployeeAppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(EmployeeAppProperties.class)
@SpringBootApplication
public class EmployeeApp {

  public static void main(String[] args) {
    SpringApplication.run(EmployeeApp.class, args);
    //        ConfigurableApplicationContext context = SpringApplication.run(EmployeeApp.class, args);
    //        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
  }
}
