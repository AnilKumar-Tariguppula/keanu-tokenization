package com.hbc.integration.keanu.api.tokenization.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenizationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "loyalty_id is mandatory")
	private long loyalty_id;

	@NotNull(message = "id_type_desc is mandatory")
	private String id_type_desc;

	@NotNull(message = "id_type_code is mandatory")
	private String id_type_code;

	@NotNull(message = "primary_card_holder is mandatory")
	private boolean primary_card_holder;

	@NotNull(message = "authorized_sec_card_holder is mandatory")
	private boolean authorized_sec_card_holder;

	@NotNull(message = "billing_cycle is mandatory")
	private Long billing_cycle;

	@NotNull(message = "card_open_date is mandatory")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date card_open_date;

	@NotNull(message = "card_closed_date is mandatory")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date card_closed_date;

	@NotNull(message = "employee_flag is mandatory")
	private boolean employee_flag;

	@NotNull(message = "credit_account is mandatory")
	private Long credit_account;

	@NotNull(message = "first_name is mandatory")
	private String first_name;

	@NotNull(message = "last_name is mandatory")
	private String last_name;

	@NotNull(message = "phone is mandatory")
	private String phone;

	@NotNull(message = "email is mandatory")
	private String email;

	@NotNull(message = "dob is mandatory")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date dob;

	@NotNull(message = "postal is mandatory")
	private String postal;

	@NotNull(message = "expiry_date is mandatory")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date expiry_date;

	@NotNull(message = "card_provider is mandatory")
	private String card_provider;

	@JsonFormat(pattern = "dd-MM-yyyy")
	@NotNull(message = "last_purchase_date is mandatory")
	private Date last_purchase_date;

}
