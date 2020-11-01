package com.exhanger.vvasiuk.repository;

import com.exhanger.vvasiuk.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentRepository extends JpaRepository<Document, Long> {

        @Query("SELECT doc FROM Document doc WHERE doc.documentName = ?1")
        Document findByName(String name);
}
