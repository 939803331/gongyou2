package com.ph.generate4java.generator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ph.generate4java.constant.MySQLConstant;
import com.ph.generate4java.entity.Columns;
import com.ph.generate4java.entity.Tables;
import com.ph.generate4java.repository.ColumnsReposiroty;
import com.ph.generate4java.repository.TablesRepository;
import com.ph.generate4java.utils.GenerateUtils;
import com.ph.generate4java.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.jenesis4java.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @PACKAGE_NAME: com.ph.generate4java.generator
 * @NAME: GenerateEntity
 * @Description:
 * @Author ph
 * @DATE: 2019-08-23 15:23
 **/
@Component
public class GenerateEntity {
	// init
	public static final VirtualMachine vm = VirtualMachine.getVirtualMachine();

	@Autowired
	private GeneratorProperties generatorProperties;

	@Autowired
	private TablesRepository tablesRepository;

	@Autowired
	private ColumnsReposiroty columnsReposiroty;

	public void generator() {
		Tables table = tablesRepository.findByTableName(generatorProperties.getTable(), generatorProperties.getSchema());
		List<Columns> columns = columnsReposiroty.findByTableName(generatorProperties.getTable(), generatorProperties.getSchema());
		String className = StringUtils.underline2Camel(table.getTableName(), false);
		CompilationUnit unit = vm.newCompilationUnit(generatorProperties.getPath());
		unit.setNamespace(generatorProperties.getPackagesEntity());
		PackageClass clazz = unit.newClass(className);
		clazz.setAccess(Access.PUBLIC);
		clazz.setComment(Comment.DOCUMENTATION, GenerateUtils.documentation(className, generatorProperties.getPackagesEntity(), table.getTableComment()));
		clazz.addSerializable();

		clazz.addImport(ApiModel.class);
		clazz.addImport(ApiModelProperty.class);
		clazz.addImport(Getter.class);
		clazz.addImport(Setter.class);
		clazz.addImport(Serializable.class);
		clazz.addImport(Date.class);
		clazz.addImport(DateTimeFormat.class);
		clazz.addImport(JsonFormat.class);
		clazz.addImport(NotNull.class);
		clazz.addImport(NotBlank.class);

		clazz.addAnnotation("Getter");
		clazz.addAnnotation("Setter");
		clazz.addAnnotation("ApiModel")
				.addAnnotationAttribute("value", vm.newString(table.getTableComment()));

		for (Columns column : columns) {
			String columnName = StringUtils.underline2Camel(column.getColumnName());
			ClassField field = clazz.newField(vm.newType(MySQLConstant.getFieldTypeByDataType(column.getDataType())), columnName);
			field.setAccess(Access.PRIVATE);

			if (column.getDataType()
					.equals("datetime") || column.getDataType()
					.equals("date")) {
				Annotation dateTimeFormat = vm.newAnnotation(DateTimeFormat.class);
				dateTimeFormat.addAnnotationAttribute("pattern", vm.newString("yyyy-MM-dd HH:mm:ss"));
				Annotation jsonFormat = vm.newAnnotation(JsonFormat.class);
				jsonFormat.addAnnotationAttribute("pattern", vm.newString("yyyy-MM-dd HH:mm:ss"));
				jsonFormat.addAnnotationAttribute("timezone", vm.newString("GMT+8"));
				field.addAnnotation(dateTimeFormat);
				field.addAnnotation(jsonFormat);
			}

			if (column.getIsNullable()
					.equals("NO")) {
				Class typeClass = MySQLConstant.getFieldTypeByDataType(column.getDataType());
				if (typeClass.equals(String.class)) {
					field.addAnnotation("NotBlank")
							.addAnnotationAttribute("message", vm.newString("{" + table.getTableName() + "." + column.getColumnName() + ".notBlank}"));
				} else {
					field.addAnnotation("NotNull")
							.addAnnotationAttribute("message", vm.newString("{" + table.getTableName() + "." + column.getColumnName() + ".notNull}"));
				}
			}

			field.addAnnotation("ApiModelProperty")
					.addDefaultValueAttribute(vm.newString(column.getColumnComment()));
		}

		vm.encode();
	}

}






















