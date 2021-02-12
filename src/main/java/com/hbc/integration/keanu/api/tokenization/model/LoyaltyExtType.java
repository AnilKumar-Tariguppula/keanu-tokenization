package com.hbc.integration.keanu.api.tokenization.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class LoyaltyExtType implements Serializable {

	private static final long serialVersionUID = 1L;

	Long loyaltyId;

	private Long id;

	private Long billingCycle;

	private Date cardOpenDate;

	private Date cardClosedDate;

	private String idTypeDesc;

	private boolean employeeFlag;

	private Long creditAccount;

	private String idTypeCode;

	private Date lastModDate;

	private String lastModId;

	private boolean primaryCardHolder;

	private boolean authorizedSecCardHolder;

	private char primaryHolder = '1';

	private String first_name;

	private String last_name;

	private String email;

	private String phone;

	private String postal;

	private Date dob;

	private Date expiry_date;

	private String card_provider;

}
