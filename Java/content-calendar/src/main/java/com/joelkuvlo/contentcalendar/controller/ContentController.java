package com.joelkuvlo.contentcalendar.controller;

import com.joelkuvlo.contentcalendar.model.Content;
import com.joelkuvlo.contentcalendar.model.Status;
import com.joelkuvlo.contentcalendar.repository.ContentRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/content")
public class ContentController {
  //  private final ContentCollectionRepository repository;
  private final ContentRepository repository;

  public ContentController(ContentRepository repository) {
    this.repository = repository;
  }

  //make a request and find all the pieces of content in the system
  @GetMapping("")
  public List<Content> findAll() {
    return repository.findAll();
  }

  //make a request and find a specific piece of content by id
  @GetMapping("/{id}")
  public Content findById(@PathVariable Integer id) {
    Optional<Content> content = repository.findById(id);
    if (content.isPresent()) {
      return content.get();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found");
    }
  }

  //make a request and create a new piece of content and save it to the system
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public void create(@Valid @RequestBody Content content) {
    if (content.title() == null || content.title().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title cannot be empty");
    }
    repository.save(content);
  }

  //make a request and update a specific piece of content by id
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping("/{id}")
  public void update(@PathVariable Integer id, @RequestBody Content content) {
    if (!repository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found");
    } else {
      repository.save(content);
    }
  }

  //make a request and delete a specific piece of content by id
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Integer id) {
    repository.deleteById(id);
  }

  //make a request to list specific pieces of content by a keyword in the title
  @GetMapping("/filter/{keyword}")
  public List<Content> findByTitle(@PathVariable String keyword) {
    return repository.findAllByTitleContains(keyword);
  }

  //make a request to list specific pieces of content by status
  @GetMapping("/filter/status/{status}")
  public List<Content> findByStatus(@PathVariable Status status) {
    return repository.listByStatus(status);
  }
}
