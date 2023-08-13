package com.doceasy.middler.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.doceasy.middler.dto.DocumentDTO;
import com.doceasy.middler.entity.Document;
import com.doceasy.middler.enums.DocumentStatusEnum;
import com.doceasy.middler.repository.DocumentRepository;
import com.doceasy.middler.util.HashCalculatorUtils;

@Service
public class DocumentService {

	@Autowired
	private HashCalculatorUtils hashCalculator;
	
	@Autowired
	private DocumentRepository repository;
	
	@Autowired
	private SubDocumentService subDocumentService;
	
	/**
	 * Realiza o processamento de arquivos
	 * @param listMultipart
	 * @return
	 * @throws IOException 
	 */
	public DocumentDTO process(List<MultipartFile> listMultipart) throws IOException {
		DocumentDTO result = null;
		
		List<Long> listSize = listMultipart.stream().map(x -> x.getSize()).collect(Collectors.toList());
		String hash = hashCalculator.hashFromList(listSize);
		
		Document document = repository.findByHash(hash);
		if (document == null) {
			document = this.insertDocument(hash);
			List<InputStream> list = Document.getListInputStream(listMultipart);
			subDocumentService.insertSubDocumentsFromDocument(list, document);
		}
		
		result = Document.toDTO(document);
		
		return result;
	}
	
	/**
	 * Insere o documento com os sub documentos.
	 * @param list
	 * @param hash
	 * @return
	 */
	public Document insertDocument(String hash) {
		Document document = new Document();
		document.setHash(hash);
		document.setStatus(DocumentStatusEnum.EM_FILA.getValue());
		
		repository.save(document);
		
		return document;
	}
	
}
