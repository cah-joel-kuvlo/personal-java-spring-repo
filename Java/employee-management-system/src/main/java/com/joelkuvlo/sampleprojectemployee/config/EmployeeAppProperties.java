package com.joelkuvlo.sampleprojectemployee.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "db")
public record EmployeeAppProperties(String url, String username, String password) {
}
