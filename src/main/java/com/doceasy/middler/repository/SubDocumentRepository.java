package com.doceasy.middler.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doceasy.middler.entity.SubDocument;

@Repository
public interface SubDocumentRepository extends JpaRepository<SubDocument, UUID>{

}
