package com.smarthostel.repository;

import com.smarthostel.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByIsImportantTrue();
    List<Notice> findByCategory(String category);
    List<Notice> findAllByOrderByPublicationDateDesc();
}
