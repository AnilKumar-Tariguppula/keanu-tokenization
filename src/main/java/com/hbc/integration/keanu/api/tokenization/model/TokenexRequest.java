package com.hbc.integration.keanu.api.tokenization.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TokenexRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "APIKey")
	private String api_key;

	@JsonProperty(value = "TokenExID")
	private String tokenex_id;

	@JsonProperty(value = "EcryptedData")
	private String encrypted_data;

	@JsonProperty(value = "TokenScheme")
	private Long token_scheme;

}
