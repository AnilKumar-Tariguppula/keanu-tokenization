package com.hbc.integration.keanu.api.tokenization.resource;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hbc.integration.keanu.api.tokenization.model.TokenizationRequest;
import com.hbc.integration.keanu.api.tokenization.model.TokenizationResponse;

public interface TokenizationResource {

	@PostMapping(path = "/tokenize")
	public TokenizationResponse doTokenize(@Valid @RequestBody TokenizationRequest tokenizationRequest) throws Exception;

}