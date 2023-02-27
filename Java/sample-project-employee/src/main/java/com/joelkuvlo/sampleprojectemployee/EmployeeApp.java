package com.joelkuvlo.sampleprojectemployee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeApp {

  public static void main(String[] args) {
    SpringApplication.run(EmployeeApp.class, args);
    //        ConfigurableApplicationContext context = SpringApplication.run(EmployeeApp.class, args);
    //        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
  }
}
