package com.doceasy.middler.enums;

public enum DocumentStatusEnum {

	EM_FILA(1),
	FINALIZADO(2),
	ERRO(3);

	private int status;
	
	DocumentStatusEnum(int i) {
		status = i;
	}
	
	public int getValue() {
		return status;
	}
	
	public static DocumentStatusEnum getFromInteger(int i) {
		DocumentStatusEnum result = null;
		
		switch (i) {
			case 1:
				result = EM_FILA;
				break;
				
			case 2:
				result = FINALIZADO;
				break;

			case 3:
				result = ERRO;
				break;
				
			default:
				break;
		}
		
		return result;
	}
	
}
