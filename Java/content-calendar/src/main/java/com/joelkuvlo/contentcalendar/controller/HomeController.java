package com.joelkuvlo.contentcalendar.controller;

import com.joelkuvlo.contentcalendar.config.ContentCalendarProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  //  @Value("${cc.welcomeMessage: Default Welcome Message}")
  //  public String welcomeMessage;
  //
  //  @Value("${cc.about}")
  //  private String about;
  //
  //  @GetMapping("/")
  //  public Map<String, String> home() {
  //    return Map.of("welcomeMessage", welcomeMessage, "about", about);
  //  }

  private final ContentCalendarProperties properties;

  public HomeController(ContentCalendarProperties properties) {this.properties = properties;}

  @GetMapping("/")
  public ContentCalendarProperties home() {
    return properties;
  }
}
