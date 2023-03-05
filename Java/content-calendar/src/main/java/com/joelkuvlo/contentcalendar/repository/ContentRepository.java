package com.joelkuvlo.contentcalendar.repository;

import com.joelkuvlo.contentcalendar.model.Content;
import com.joelkuvlo.contentcalendar.model.Status;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends ListCrudRepository<Content, Integer> {
  List<Content> findAllByTitleContains(String keyword);

  //Use @Query to list content by status
  @Query("""
      SELECT * FROM Content WHERE status = :status
      """)
  List<Content> listByStatus(@Param("status") Status status);
}
