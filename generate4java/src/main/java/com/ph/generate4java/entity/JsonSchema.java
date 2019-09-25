package com.ph.generate4java.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @PACKAGE_NAME: com.ph.generate4java.entity
 * @NAME: JsonSchema
 * @Description:
 * @Author ph
 * @DATE: 2019-09-04 11:29
 **/
@Data
public class JsonSchema {
	private String $schema = "http://json-schema.org/draft-04/schema#";

	private String type = "object";

	private JSONObject properties;

	private List<String> required;
}
