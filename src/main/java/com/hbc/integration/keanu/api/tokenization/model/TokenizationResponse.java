package com.hbc.integration.keanu.api.tokenization.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenizationResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean success;

	private String error_message;

	private String response_code;

}
