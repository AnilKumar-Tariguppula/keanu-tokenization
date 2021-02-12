package com.hbc.integration.keanu.api.tokenization.exception;

import lombok.Data;

@Data
public class TokenizationException extends Exception {

	private static final long serialVersionUID = 1L;

	private String code;

	public TokenizationException(String message, String code) {
		super(message);
		this.code = code;
	}

}
