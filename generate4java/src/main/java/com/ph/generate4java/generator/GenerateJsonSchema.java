package com.ph.generate4java.generator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.ph.generate4java.constant.MySQLConstant;
import com.ph.generate4java.entity.Columns;
import com.ph.generate4java.entity.Tables;
import com.ph.generate4java.repository.ColumnsReposiroty;
import com.ph.generate4java.repository.TablesRepository;
import com.ph.generate4java.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @PACKAGE_NAME: com.ph.generate4java.generator
 * @NAME: GenerateJsonSchema
 * @Description:
 * @Author ph
 * @DATE: 2019-09-04 11:25
 **/
@Component
public class GenerateJsonSchema {

	private static final Path path = Paths.get("D:\\appStore\\temp", "jsonShema.txt");

	@Autowired
	private GeneratorProperties generatorProperties;

	@Autowired
	private TablesRepository tablesRepository;

	@Autowired
	private ColumnsReposiroty columnsReposiroty;

	public void generator() throws IOException {
		Tables table = tablesRepository.findByTableName(generatorProperties.getTable(), generatorProperties.getSchema());
		List<Columns> columns = columnsReposiroty.findByTableName(generatorProperties.getTable(), generatorProperties.getSchema());

		JSONObject insertObject = new JSONObject(true);
		insertObject.put("$schema", "http://json-schema.org/draft-04/schema#");
		insertObject.put("type", "object");
		insertObject.put("description", table.getTableComment());
		JSONObject properties = new JSONObject();
		List<String> required = Lists.newArrayList();
		for (Columns column : columns) {
			String columnName = StringUtils.underline2Camel(column.getColumnName());
			required.add(columnName);
			JSONObject field = new JSONObject();
			field.put("type", MySQLConstant.getFieldTypeByDataType(column.getDataType())
					.getSimpleName());
			field.put("description", column.getColumnComment());
			properties.put(columnName, field);
		}
		insertObject.put("properties", properties);
		insertObject.put("required", required);

		if (Files.deleteIfExists(path)) {
			Files.createFile(path);
		}

		JSONObject findAllObject = JSONObject.parseObject("{\"$schema\":\"http://json-schema.org/draft-04/schema#\",\"type\":\"object\",\"properties\":{\"code\":{\"type\":\"number\"},\"message\":{\"type\":\"string\"},\"data\":{\"type\":\"object\",\"properties\":{\"pageNo\":{\"type\":\"number\"},\"pageSize\":{\"type\":\"number\"},\"pageCount\":{\"type\":\"number\"},\"rowCount\":{\"type\":\"number\"},\"startRowNo\":{\"type\":\"number\"},\"endRowNo\":{\"type\":\"number\"},\"iDisplayStart\":{\"type\":\"number\"},\"iDisplayLength\":{\"type\":\"number\"},\"jqueryMode\":{\"type\":\"boolean\"},\"queryResult\":{\"type\":\"array\"},\"iTotalDisplayRecords\":{\"type\":\"number\"},\"iTotalRecords\":{\"type\":\"number\"}}}}}");
		findAllObject.getJSONObject("properties")
				.getJSONObject("data")
				.getJSONObject("properties")
				.getJSONObject("queryResult")
				.put("items", insertObject);

		JSONObject findByIdObject = JSONObject.parseObject("{\"$schema\":\"http://json-schema.org/draft-04/schema#\",\"type\":\"object\",\"properties\":{\"code\":{\"type\":\"number\"},\"message\":{\"type\":\"string\"}}}");
		findByIdObject.getJSONObject("properties")
				.put("data", insertObject);

		JSONObject responseMessageObject = new JSONObject(true);
		responseMessageObject.put("$schema", "http://json-schema.org/draft-04/schema#");
		responseMessageObject.put("type", "object");
		responseMessageObject.put("description", "返回结果");
		responseMessageObject.put("properties", ImmutableMap.of("code", ImmutableMap.of("type", "Integer"), "message", ImmutableMap.of("type", "String")));

		PrintWriter pw = new PrintWriter(Files.newOutputStream(path));
		pw.println("/***********insert/update args**************/");
		pw.println(JSON.toJSONString(insertObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat));
		pw.println();
		pw.println("/***********findAll results**************/");
		pw.println(JSON.toJSONString(findAllObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat));
		pw.println();
		pw.println("/***********findById results**************/");
		pw.println(JSON.toJSONString(findByIdObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat));
		pw.println();
		pw.println("/***********responseMessage args**************/");
		pw.println(JSON.toJSONString(responseMessageObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat));
		pw.flush();
		pw.close();
		System.out.println(path);
	}
}




















