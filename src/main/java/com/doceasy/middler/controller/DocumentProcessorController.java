package com.doceasy.middler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.doceasy.middler.dto.DocumentDTO;
import com.doceasy.middler.service.DocumentService;

import lombok.Data;

@CrossOrigin(origins = "*")
@Data
@RequestMapping("/process")
@RestController
public class DocumentProcessorController {

	@Autowired
	private DocumentService service;
	
	@PostMapping
	public ResponseEntity<DocumentDTO> process(@RequestPart(name = "files", required = true) List<MultipartFile> listMultipartFiles) {
		try {
			DocumentDTO dto = null;
			dto = service.process(listMultipartFiles);
			
			return ResponseEntity.ok(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity.internalServerError().build();
		}
	}
	
}
