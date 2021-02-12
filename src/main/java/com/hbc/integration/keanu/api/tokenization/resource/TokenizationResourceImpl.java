package com.hbc.integration.keanu.api.tokenization.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.integration.keanu.api.tokenization.model.TokenizationRequest;
import com.hbc.integration.keanu.api.tokenization.model.TokenizationResponse;
import com.hbc.integration.keanu.api.tokenization.service.TokenizationService;

@RestController
public class TokenizationResourceImpl implements TokenizationResource{

	@Autowired
	private TokenizationService tokenizationService;

	public TokenizationResponse doTokenize(@RequestBody TokenizationRequest tokenizationRequest) throws Exception {
		return tokenizationService.tokenize(tokenizationRequest);
	}

}
