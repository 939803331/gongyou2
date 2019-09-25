package com.ph.generate4java.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @PACKAGE_NAME: com.ph.generate4java.entity
 * @NAME: Columns
 * @Description:
 * @Author ph
 * @DATE: 2019-08-23 14:33
 **/
@Getter
@Setter
@Entity
@Table(name = "colums", schema = "information_schema")
public class Columns {

	@Id
	@Column(name = "COLUMN_NAME")
	private String columnName;

	@Column(name = "DATA_TYPE")
	private String dataType;

	@Column(name = "COLUMN_COMMENT")
	private String columnComment;

	@Column(name = "IS_NULLABLE")
	private String isNullable;
}
