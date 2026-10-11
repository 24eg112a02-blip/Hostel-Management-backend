package com.smarthostel.controller;

import com.smarthostel.entity.Notice;
import com.smarthostel.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notices")
@CrossOrigin(origins = "*")
public class NoticeController {

    @Autowired
    private NoticeRepository noticeRepository;

    @GetMapping
    public List<Notice> getAllNotices() {
        return noticeRepository.findAllByOrderByPublicationDateDesc();
    }

    @PostMapping
    public ResponseEntity<?> createNotice(@RequestBody Notice notice) {
        if (notice.getTitle() == null || notice.getDescription() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Notice title and description are required."));
        }
        notice.setPublicationDate(LocalDate.now());
        Notice saved = noticeRepository.save(notice);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNotice(@PathVariable Long id, @RequestBody Notice noticeDetails) {
        return noticeRepository.findById(id).map(notice -> {
            if (noticeDetails.getTitle() != null) notice.setTitle(noticeDetails.getTitle());
            if (noticeDetails.getDescription() != null) notice.setDescription(noticeDetails.getDescription());
            if (noticeDetails.getCategory() != null) notice.setCategory(noticeDetails.getCategory());
            if (noticeDetails.getIsImportant() != null) notice.setIsImportant(noticeDetails.getIsImportant());

            Notice updated = noticeRepository.save(notice);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long id) {
        return noticeRepository.findById(id).map(notice -> {
            noticeRepository.delete(notice);
            return ResponseEntity.ok(Map.of("message", "Notice deleted successfully"));
        }).orElse(ResponseEntity.notFound().build());
    }
}
