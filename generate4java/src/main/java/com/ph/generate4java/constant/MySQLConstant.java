package com.ph.generate4java.constant;

import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @PACKAGE_NAME: com.ph.generate4java.constant
 * @NAME: MySQLConstant
 * @Description:
 * @Author ph
 * @DATE: 2019-08-23 17:00
 **/
public class MySQLConstant {
	public static final Map<String ,Class> COLUMN_MAPPING = new HashMap<>();

	static {
		COLUMN_MAPPING.put("varchar", String.class);
		COLUMN_MAPPING.put("text", String.class);
		COLUMN_MAPPING.put("int", Integer.class);
		COLUMN_MAPPING.put("bigint", Long.class);
		COLUMN_MAPPING.put("tinyint", Integer.class);
		COLUMN_MAPPING.put("date", Date.class);
		COLUMN_MAPPING.put("datetime", Date.class);
		COLUMN_MAPPING.put("float", BigDecimal.class);
		COLUMN_MAPPING.put("decimal", BigDecimal.class);
		COLUMN_MAPPING.put("double", BigDecimal.class);
	}

	public static Class getFieldTypeByDataType(String dataType) {
		return COLUMN_MAPPING.get(dataType);
	}
}
