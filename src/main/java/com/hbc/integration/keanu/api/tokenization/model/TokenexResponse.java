package com.hbc.integration.keanu.api.tokenization.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TokenexResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "Error")
	private String error;

	@JsonProperty(value = "ReferenceNumber")
	private String reference_number;

	@JsonProperty(value = "Success")
	private boolean success;

	@JsonProperty(value = "Token")
	private String token;

}
