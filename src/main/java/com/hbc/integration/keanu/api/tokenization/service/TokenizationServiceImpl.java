package com.hbc.integration.keanu.api.tokenization.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.hbc.integration.keanu.api.tokenization.exception.TokenizationException;
import com.hbc.integration.keanu.api.tokenization.model.LoyaltyExtType;
import com.hbc.integration.keanu.api.tokenization.model.TokenExUcidResponse;
import com.hbc.integration.keanu.api.tokenization.model.TokenexRequest;
import com.hbc.integration.keanu.api.tokenization.model.TokenexResponse;
import com.hbc.integration.keanu.api.tokenization.model.TokenizationRequest;
import com.hbc.integration.keanu.api.tokenization.model.TokenizationResponse;

@Service
public class TokenizationServiceImpl implements TokenizationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenizationServiceImpl.class);

	@Value("${tokenex.apikey}")
	private String TOKENEX_APIKEY;

	@Value("${tokenex.id}")
	private String TOKENEX_ID;

	@Value("${tokenex.scheme}")
	private Long TOKENEX_SCHEME;

	@Value("${tokenex.url}")
	private String TOKENEX_URL;

	@Value("${ucid.url}")
	private String UCID_ENDPOINT;

	private final String TOKENEX_TOKENIZE_PATHSEGMENT = "/TokenServices.svc/REST/TokenizeFromEncryptedValue";

	private final String UCID_SAVE_EXTIDTYPE_PATHSEGMENT = "/saveExtidtype";

//	private final String ID_TYPE_DESC = "TOKENX";

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public TokenizationResponse tokenize(TokenizationRequest tokenizationRequest) throws Exception {
		LOGGER.info("tokenize request {} ", tokenizationRequest);
		TokenizationResponse tokenizationResponse = null;
		TokenexRequest tokenexRequest = new TokenexRequest();

		tokenexRequest.setApi_key(TOKENEX_APIKEY);
		tokenexRequest.setToken_scheme(TOKENEX_SCHEME);
		tokenexRequest.setTokenex_id(TOKENEX_ID);
		tokenexRequest.setEncrypted_data(tokenizationRequest.getId_type_code());
		TokenexResponse tokenexResponse = tokenexTrigger(tokenexRequest);
		if (tokenexResponse.isSuccess()) {
			tokenizationRequest.setId_type_code(tokenexResponse.getToken());
			LoyaltyExtType loyaltyExtType = new LoyaltyExtType();
			loyaltyExtType.setLoyaltyId(tokenizationRequest.getLoyalty_id());
			loyaltyExtType.setBillingCycle(tokenizationRequest.getBilling_cycle());
			loyaltyExtType.setCardOpenDate(tokenizationRequest.getCard_open_date());
			loyaltyExtType.setCardClosedDate(tokenizationRequest.getCard_closed_date());
			loyaltyExtType.setIdTypeDesc(tokenizationRequest.getId_type_desc());
			loyaltyExtType.setCreditAccount(tokenizationRequest.getCredit_account());
			loyaltyExtType.setIdTypeCode(tokenizationRequest.getId_type_code());
			loyaltyExtType.setPrimaryCardHolder(tokenizationRequest.isPrimary_card_holder());
			loyaltyExtType.setAuthorizedSecCardHolder(tokenizationRequest.isAuthorized_sec_card_holder());
			loyaltyExtType.setFirst_name(tokenizationRequest.getFirst_name().trim().toUpperCase());
			loyaltyExtType.setLast_name(tokenizationRequest.getLast_name().trim().toUpperCase());
			loyaltyExtType.setEmail(tokenizationRequest.getEmail().trim().toUpperCase());
			loyaltyExtType.setPhone(tokenizationRequest.getPhone().trim());
			loyaltyExtType.setDob(tokenizationRequest.getDob());
			loyaltyExtType.setPostal(tokenizationRequest.getPostal().trim().toUpperCase());
			loyaltyExtType.setExpiry_date(tokenizationRequest.getExpiry_date());
			loyaltyExtType.setCard_provider(tokenizationRequest.getCard_provider().trim().toUpperCase());

			TokenExUcidResponse ucidSaveResponse = saveLoyaltyExtIdType(loyaltyExtType);
			if (!ucidSaveResponse.isSuccess())
				throw new TokenizationException(ucidSaveResponse.getError_message(),
						ucidSaveResponse.getResponse_code());
		} else
			throw new TokenizationException(tokenexResponse.getError(), "100");

		tokenizationResponse = new TokenizationResponse();
		tokenizationResponse.setSuccess(true);
		tokenizationResponse.setResponse_code("01");
		LOGGER.info("tokenize response {} ", tokenizationResponse);
		return tokenizationResponse;
	}

	private TokenexResponse tokenexTrigger(TokenexRequest tokenexRequest) throws Exception {
		HttpEntity<TokenexRequest> requestEntity = new HttpEntity<TokenexRequest>(tokenexRequest, getHeaders());
		LOGGER.info("tokenexTrigger request {} ", requestEntity);
		ResponseEntity<TokenexResponse> response = null;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TOKENEX_URL).path(TOKENEX_TOKENIZE_PATHSEGMENT);
		response = restTemplate.postForEntity(builder.toUriString(), requestEntity, TokenexResponse.class);
		if (response.getStatusCode().is5xxServerError())
			throw new TokenizationException(response.getBody().getError(), "100");
		LOGGER.info("tokenexTrigger response {} ", response);
		return response.getBody();

	}

	// saves in UCID schema
	private TokenExUcidResponse saveLoyaltyExtIdType(LoyaltyExtType loyaltyExtType) throws Exception {
		HttpEntity<LoyaltyExtType> requestEntity = new HttpEntity<LoyaltyExtType>(loyaltyExtType, getHeaders());
		LOGGER.info("saveLoyaltyExtIdType request {} ", requestEntity);
		ResponseEntity<TokenExUcidResponse> response = null;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(UCID_ENDPOINT)
				.path(UCID_SAVE_EXTIDTYPE_PATHSEGMENT);
		response = restTemplate.postForEntity(builder.toUriString(), requestEntity, TokenExUcidResponse.class);
		LOGGER.info("saveLoyaltyExtIdType response {} ", response);
		if (response.getStatusCode().is5xxServerError())
			throw new TokenizationException(response.getBody().getError_message(),
					response.getBody().getResponse_code());
		return response.getBody();
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<MediaType> accepts = new ArrayList<MediaType>();
		accepts.add(MediaType.APPLICATION_JSON);
		headers.setAccept(accepts);
		return headers;
	}
}
