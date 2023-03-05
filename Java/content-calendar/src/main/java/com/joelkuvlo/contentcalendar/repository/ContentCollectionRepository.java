package com.joelkuvlo.contentcalendar.repository;

import com.joelkuvlo.contentcalendar.model.Content;
import com.joelkuvlo.contentcalendar.model.Status;
import com.joelkuvlo.contentcalendar.model.Type;
import jakarta.annotation.PostConstruct;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContentCollectionRepository {
  private final List<Content> contentList = new ArrayList<>();

  public ContentCollectionRepository() {

  }

  // List of all the content in the system
  public List<Content> findAll() {
    return contentList;
  }

  // Find a specific piece of content by id
  public Optional<Content> findById(Integer id) {
//    return contentList.stream().filter(content -> content.id().equals(id)).findFirst();
    for (Content content : contentList) {
      if (content.id().equals(id)) {
        return Optional.of(content);
      }
    }
    return Optional.empty();
  }

  public void save(Content content) {
    //    contentList.removeIf(c1 -> c1.id().equals(content.id()));
    //    contentList.add(content);
    Integer contentId = content.id();
    for (int i = 0; i < contentList.size(); i++) {
      Content currentContent = contentList.get(i);
      if (currentContent.id().equals(contentId)) {
        contentList.remove(i);
        break;
      }
    }
    contentList.add(content);
  }

  @PostConstruct
  public void init() {
    Content content =
        new Content(1, "My First Blog Post", "My first blog post", Status.IDEA, Type.ARTICLE,
            LocalDateTime.now(), null, "");

    contentList.add(content);
  }


  public boolean existsById(Integer id) {
    return contentList.stream().filter(content -> content.id().equals(id)).count() == 1;
  }

  public void deleteById(Integer id) {
    contentList.removeIf(content -> content.id().equals(id));
  }

}
