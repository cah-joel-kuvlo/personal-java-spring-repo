package com.joelkuvlo.contentcalendar.repository;

import com.joelkuvlo.contentcalendar.model.Content;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContentCollectionRepository {
  private final List<Content> content = new ArrayList<>();
  public ContentCollectionRepository() {

  }

  public List<Content> findAll() {
    return content;
  }

  public Optional<Content> findById(Integer id) {
    return content.stream().filter(c -> c.id().equals(id)).findFirst();
  }

  @PostConstruct
  public void init() {
    Content c = new Content(1, "My First Blog Post", "My firs ", Content.Status.DRAFT, Content.Type.BLOG,
        null, null, "https://www.google.com");
  }
}
