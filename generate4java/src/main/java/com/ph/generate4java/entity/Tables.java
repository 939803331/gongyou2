package com.ph.generate4java.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @PACKAGE_NAME: com.ph.generate4java.entity
 * @NAME: Tables
 * @Description:
 * @Author ph
 * @DATE: 2019-08-23 14:29
 **/
@Getter
@Setter
@Entity
@Table(name = "tables", schema = "information_schema")
public class Tables {
	@Id
	@Column(name = "TABLE_NAME")
	private String tableName;

	@Column(name = "TABLE_COMMENT")
	private String tableComment;
}
