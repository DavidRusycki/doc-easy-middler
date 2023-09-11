package com.doceasy.middler.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doceasy.middler.entity.Document;
import com.doceasy.middler.service.DocumentService;

@CrossOrigin(origins = "*")
@RequestMapping("/download")
@RestController
public class DownloadController {

	private Logger log = LoggerFactory.getLogger(DownloadController.class); 
			
	@Autowired
	private DocumentService documentService;

	@GetMapping("/{uuid}")
	public ResponseEntity<byte[]> download(@PathVariable(name = "uuid") UUID uuid) {
		log.debug("iniciando download de arquivo");
		
		try {
			Document document = documentService.findById(uuid);
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(document.getContent());
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
}
