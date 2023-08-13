package com.doceasy.middler.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doceasy.middler.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {

	Document findByHash(String hash);
	
}
