package com.doceasy.middler.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.doceasy.middler.dto.DocumentDTO;
import com.doceasy.middler.dto.ErrorResponseDTO;
import com.doceasy.middler.dto.FileProcessQueueDTO;
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
	
	@Autowired
	private FileProcessQueueService fileProcessQueueService;
	
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
			List<UUID> listInserts = subDocumentService.insertSubDocumentsFromDocument(list, document);
			
			FileProcessQueueDTO queueDto = new FileProcessQueueDTO();
			queueDto.setUuidDocument(document.getUuid());
			queueDto.setListUuidSubDocuments(listInserts);
			fileProcessQueueService.publish(queueDto);
		}
		
		result = Document.toDTO(document);
		
		return result;
	}
	
	public Document findById(UUID uuid) throws Exception {
		Optional<Document> optional = repository.findById(uuid);
		Document document = optional.get();
		
		return document;
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
	
	public ResponseEntity getResponse(UUID uuid) throws Exception {
		Document document = this.findById(uuid);
		
		ResponseEntity response = null;	
				
		switch (DocumentStatusEnum.getFromInteger(document.getStatus())) {
			case FINALIZADO:
				response = ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(document.getContent());
				break;
	
			case EM_FILA:
				response = ResponseEntity.ok("O documento ainda está em processamento. Tente novamente mais tarde.");
				break;
			
			case ERRO:
				response = ResponseEntity.ok("Houve um erro ao processar o documento. Tente enviar novamente.");
				break;
				
			default:
				break;
		}
		
		return response;
		
	}
	
}
