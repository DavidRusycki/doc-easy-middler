package com.doceasy.middler.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class HashCalculatorUtils {

	/**
	 * Cria um hash para a lista.
	 * @param list
	 * @return
	 */
	public String hashFromList(List<Long> list) {
		byte[] hash = null;
		String result = "";
		
		for (Long size : list) {
			result += size.toString();
		}
				
		try {
			MessageDigest digest;
			digest = MessageDigest.getInstance("SHA-256");
			hash = digest.digest(result.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return toHexString(hash);
	}
	
	private String toHexString(byte[] bytes) {
        Formatter result = new Formatter();
        try (result) {
            for (var b : bytes) {
                result.format("%02x", b & 0xff);
            }
            return result.toString();
        }
    }
	
}
