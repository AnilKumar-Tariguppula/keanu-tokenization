package com.hbc.integration.keanu.api.tokenization.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class TokenExUcidResponse implements Serializable {

	private static final long serialVersionUID = 701219775728291093L;

	private boolean success;

	private String error_message;

	private String response_code;

}
