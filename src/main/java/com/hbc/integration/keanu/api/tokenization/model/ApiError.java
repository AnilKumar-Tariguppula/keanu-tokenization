package com.hbc.integration.keanu.api.tokenization.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiError implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private boolean success;

	private String response_code;

	private String error_message;

}
