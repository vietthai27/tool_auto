package com.fis.tool.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "request_signup")

public class RequestSignup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "code_signup")
	private String codeSignup;

	@Column(name = "created_date")
	private String creadedDate;

	@Column(name = "username")
	private String username;

}
