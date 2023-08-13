package com.doceasy.middler.dto;

import java.util.UUID;

import com.doceasy.middler.entity.Document;
import com.doceasy.middler.enums.DocumentStatusEnum;

import lombok.Data;

@Data
public class DocumentDTO {

	private UUID uuid;
	private String hash;
	private Integer status;
	private byte[] content;
	
	public static Document toEntity(DocumentDTO dto) {
		Document document = new Document();
		
		document.setUuid(dto.getUuid());
		document.setHash(dto.getHash());
		document.setStatus(dto.getStatus());
		document.setContent(dto.getContent());
		
		return document;
	}
	
}
