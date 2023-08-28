package com.doceasy.middler.dto;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class FileProcessQueueDTO {

	private List<UUID> listUuidSubDocuments;
	private UUID uuidDocument;
	
	public void addUuidSubDocument(UUID uuid) {
		listUuidSubDocuments.add(uuid);
	}
	
	public void removeUuidSubDocument(UUID uuid) {
		listUuidSubDocuments.remove(uuid);
	}
	
}
