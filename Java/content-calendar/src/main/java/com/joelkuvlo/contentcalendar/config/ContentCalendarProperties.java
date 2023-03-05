package com.joelkuvlo.contentcalendar.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(value = "cc")
public record ContentCalendarProperties(String welcomeMessage, String about) {
}
