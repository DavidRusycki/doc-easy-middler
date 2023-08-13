package com.doceasy.middler.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doceasy.middler.entity.Document;
import com.doceasy.middler.entity.SubDocument;
import com.doceasy.middler.repository.SubDocumentRepository;

@Service
public class SubDocumentService {

	@Autowired
	private SubDocumentRepository repository;
	
	/**
	 * Insere os sub documentos
	 * @param list
	 * @param uuid
	 * @return
	 * @throws IOException 
	 */
	public Boolean insertSubDocumentsFromDocument(List<InputStream> list, Document document) throws IOException {
		for (InputStream inputStream : list) {
			SubDocument sub = new SubDocument();
			sub.setUuidDocumento(document.getUuid());
			sub.setContent(inputStream.readAllBytes());
			repository.save(sub);
		}	
		
		return true;
	}
	
}
