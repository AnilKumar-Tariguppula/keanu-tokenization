package com.hbc.integration.keanu.api.tokenization.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hbc.integration.keanu.api.tokenization.model.ApiError;

@ControllerAdvice
public class TokenizationExceptionHandler extends ResponseEntityExceptionHandler {

	Logger LOGGER = LoggerFactory.getLogger(TokenizationExceptionHandler.class);

	/**
	 * handles all validation errors from input json request
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("success", false);
		body.put("response_code", "error details");
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
		body.put("error_message", errors);
		return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles all runtime exceptions
	 */
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ApiError> handleRunTime(Throwable ex) {
		LOGGER.info(ex.getMessage(), ex);
		ApiError error = new ApiError();
		error.setSuccess(false);
		error.setError_message("Tokenization service internal server error");
		error.setResponse_code("09");
		LOGGER.info("Tokenization service exception stack trace", ex);
		return new ResponseEntity<ApiError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handles all custom exceptions
	 */
	@ExceptionHandler(TokenizationException.class)
	public ResponseEntity<ApiError> handleLsUpdateException(TokenizationException ex) {

		ApiError error = new ApiError();
		error.setSuccess(false);
		error.setError_message(ex.getMessage());
		error.setResponse_code(ex.getCode());
		return new ResponseEntity<ApiError>(error, HttpStatus.OK);
	}
}
