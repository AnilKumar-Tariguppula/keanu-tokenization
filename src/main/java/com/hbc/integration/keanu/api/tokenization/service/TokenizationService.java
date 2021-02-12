package com.hbc.integration.keanu.api.tokenization.service;

import org.springframework.stereotype.Service;

import com.hbc.integration.keanu.api.tokenization.model.TokenizationRequest;
import com.hbc.integration.keanu.api.tokenization.model.TokenizationResponse;

@Service
public interface TokenizationService {

	public TokenizationResponse tokenize(TokenizationRequest tokenizationRequest) throws Exception;

}
