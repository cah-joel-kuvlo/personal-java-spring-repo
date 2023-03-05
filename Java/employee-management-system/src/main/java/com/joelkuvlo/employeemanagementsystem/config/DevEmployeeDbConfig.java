package com.joelkuvlo.employeemanagementsystem.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
@Configuration
@Profile("dev")
@EnableAutoConfiguration
@PropertySource("classpath:application-dev.properties")
public class DevEmployeeDbConfig extends BaseEmployeeAppDbConfig {
}
