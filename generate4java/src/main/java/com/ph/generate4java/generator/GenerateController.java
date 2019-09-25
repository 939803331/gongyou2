package com.ph.generate4java.generator;

import com.ph.generate4java.entity.Tables;
import com.ph.generate4java.repository.TablesRepository;
import com.ph.generate4java.utils.GenerateUtils;
import com.ph.generate4java.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sourceforge.jenesis4java.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @PACKAGE_NAME: com.ph.generate4java.generator
 * @NAME: GenerateController
 * @Description:
 * @Author ph
 * @DATE: 2019-08-23 15:24
 **/
@Component
public class GenerateController {
	// init
	public static final VirtualMachine vm = VirtualMachine.getVirtualMachine();

	@Autowired
	private GeneratorProperties generatorProperties;

	@Autowired
	private TablesRepository tablesRepository;

	public void generator() {
		Tables table = tablesRepository.findByTableName(generatorProperties.getTable(), generatorProperties.getSchema());
		String className = StringUtils.underline2Camel(table.getTableName(), false) + "Controller";
		CompilationUnit unit = vm.newCompilationUnit(generatorProperties.getPath());
		unit.setNamespace(generatorProperties.getPackagesController());

		unit.addImport(GenerateUtils.RESPONSE_MESSAGE);
		unit.addImport("org.springframework.web.bind.annotation.*");
		unit.addImport(GenerateUtils.ERROR_CODE_CONSTANTS);

		unit.addImport(generatorProperties.getPackagesEntity() + "." + StringUtils.underline2Camel(table.getTableName(), false));
		unit.addImport(generatorProperties.getPackagesVo() + "." + StringUtils.underline2Camel(table.getTableName(), false) + "Vo");
		unit.addImport(generatorProperties.getPackagesService() + "." + StringUtils.underline2Camel(table.getTableName(), false) + "Service");
		unit.addImport(Api.class);
		unit.addImport(ApiOperation.class);
		unit.addImport(Autowired.class);

		PackageClass clazz = unit.newClass(className);
		clazz.setAccess(Access.PUBLIC);
		clazz.setComment(Comment.DOCUMENTATION, GenerateUtils.documentation(className, generatorProperties.getPackagesController(), table.getTableComment() + "Controller"));

		Annotation apiAnno = vm.newAnnotation(Api.class);
		apiAnno.addAnnotationAttribute("tags", vm.newString(table.getTableComment()));
		clazz.addAnnotation(apiAnno);
		clazz.addAnnotation(vm.newAnnotation("RestController"));
		Annotation requestMapping = vm.newAnnotation("RequestMapping");
		requestMapping.addDefaultValueAttribute(vm.newString(StringUtils.underline2Camel(table.getTableName())));
		clazz.addAnnotation(requestMapping);
		ClassField classField = clazz.newField(vm.newType(StringUtils.underline2Camel(table.getTableName(), false) + "Service"), StringUtils.underline2Camel(table.getTableName()) + "Service");
		classField.addAnnotation(vm.newAnnotation(Autowired.class));
		classField.setAccess(Access.PRIVATE);

		insert(clazz, table);
		delete(clazz, table);
		update(clazz, table);
		findById(clazz, table);
		findAll(clazz, table);
		vm.encode();
	}


	private void insert(PackageClass clazz, Tables table) {
		ClassMethod insertMethod = clazz.newMethod(vm.newType("ResponseMessage"), "insert");
		insertMethod.setAccess(Access.PUBLIC);

		Annotation apiOperation = vm.newAnnotation(ApiOperation.class);
		apiOperation.addAnnotationAttribute("httpMethod", vm.newString("POST"));
		apiOperation.addAnnotationAttribute("value", vm.newString("新增"));
		insertMethod.addAnnotation(apiOperation);
		Annotation postMapping = vm.newAnnotation("PostMapping");
		postMapping.addDefaultValueAttribute(vm.newString("/insert"));
		insertMethod.addAnnotation(postMapping);

		insertMethod.addParameter(vm.newType("@RequestBody " + StringUtils.underline2Camel(table.getTableName(), false)), StringUtils.underline2Camel(table.getTableName()));

		Return aReturn = insertMethod.newReturn();
		Invoke rtn = vm.newInvoke(StringUtils.underline2Camel(table.getTableName()) + "Service.insert");
		rtn.addArg(vm.newVar(StringUtils.underline2Camel(table.getTableName())));
		aReturn.setExpression(rtn);
	}


	private void delete(PackageClass clazz, Tables table) {
		ClassMethod deleteMethod = clazz.newMethod(vm.newType("ResponseMessage"), "delete");
		deleteMethod.setAccess(Access.PUBLIC);

		Annotation apiOperation = vm.newAnnotation(ApiOperation.class);
		apiOperation.addAnnotationAttribute("httpMethod", vm.newString("GET"));
		apiOperation.addAnnotationAttribute("value", vm.newString("删除"));
		deleteMethod.addAnnotation(apiOperation);
		Annotation getMapping = vm.newAnnotation("GetMapping");
		getMapping.addDefaultValueAttribute(vm.newString("/delete"));
		deleteMethod.addAnnotation(getMapping);

		deleteMethod.addParameter(vm.newType(Integer.class), "id");

		If anIf = deleteMethod.newIf(vm.newVar("id == null"));
		Return aReturn1 = anIf.newReturn();
		Invoke idInvoke = vm.newInvoke("new ResponseMessage");
		idInvoke.addArg(vm.newVar("ErrorCodeConstants.PARAM_EMPTY.getId()"));
		idInvoke.addArg(vm.newString("id不能为空"));
		aReturn1.setExpression(idInvoke);

		Return aReturn = deleteMethod.newReturn();
		Invoke rtn = vm.newInvoke(StringUtils.underline2Camel(table.getTableName()) + "Service.delete");
		rtn.addArg(vm.newVar("id"));
		aReturn.setExpression(rtn);
	}

	private void update(PackageClass clazz, Tables table) {
		ClassMethod updateMethod = clazz.newMethod(vm.newType("ResponseMessage"), "update");
		updateMethod.setAccess(Access.PUBLIC);

		Annotation apiOperation = vm.newAnnotation(ApiOperation.class);
		apiOperation.addAnnotationAttribute("httpMethod", vm.newString("POST"));
		apiOperation.addAnnotationAttribute("value", vm.newString("修改"));
		updateMethod.addAnnotation(apiOperation);
		Annotation postMapping = vm.newAnnotation("PostMapping");
		postMapping.addDefaultValueAttribute(vm.newString("/update"));
		updateMethod.addAnnotation(postMapping);

		updateMethod.addParameter(vm.newType("@RequestBody " + StringUtils.underline2Camel(table.getTableName(), false)), StringUtils.underline2Camel(table.getTableName()));

		If anIf = updateMethod.newIf(vm.newInvoke(StringUtils.underline2Camel(table.getTableName()) + " == null || null == " + StringUtils.underline2Camel(table.getTableName()) + ".getId"));
		Return aReturn1 = anIf.newReturn();
		Invoke idInvoke = vm.newInvoke("new ResponseMessage");
		idInvoke.addArg(vm.newVar("ErrorCodeConstants.PARAM_EMPTY.getId()"));
		idInvoke.addArg(vm.newString("id不能为空"));
		aReturn1.setExpression(idInvoke);

		Return aReturn = updateMethod.newReturn();
		Invoke rtn = vm.newInvoke(StringUtils.underline2Camel(table.getTableName()) + "Service.update");
		rtn.addArg(vm.newVar(StringUtils.underline2Camel(table.getTableName())));
		aReturn.setExpression(rtn);
	}

	private void findById(PackageClass clazz, Tables table) {
		ClassMethod findByIdMethod = clazz.newMethod(vm.newType("ResponseMessage"), "findById");
		findByIdMethod.setAccess(Access.PUBLIC);

		Annotation apiOperation = vm.newAnnotation(ApiOperation.class);
		apiOperation.addAnnotationAttribute("httpMethod", vm.newString("GET"));
		apiOperation.addAnnotationAttribute("value", vm.newString("根据id查询"));
		findByIdMethod.addAnnotation(apiOperation);
		Annotation postMapping = vm.newAnnotation("GetMapping");
		postMapping.addDefaultValueAttribute(vm.newString("/findById"));
		findByIdMethod.addAnnotation(postMapping);

		findByIdMethod.addParameter(vm.newType(Integer.class), "id");

		If anIf = findByIdMethod.newIf(vm.newVar("null == id"));
		Return aReturn1 = anIf.newReturn();
		Invoke idInvoke = vm.newInvoke("new ResponseMessage");
		idInvoke.addArg(vm.newVar("ErrorCodeConstants.PARAM_EMPTY.getId()"));
		idInvoke.addArg(vm.newString("id不能为空"));
		aReturn1.setExpression(idInvoke);

		Return aReturn = findByIdMethod.newReturn();
		Invoke rtn = vm.newInvoke(StringUtils.underline2Camel(table.getTableName()) + "Service.findById");
		rtn.addArg(vm.newVar("id"));
		aReturn.setExpression(rtn);
	}

	private void findAll(PackageClass clazz, Tables table) {
		ClassMethod findAllMethod = clazz.newMethod(vm.newType("ResponseMessage"), "findAll");
		findAllMethod.setAccess(Access.PUBLIC);

		Annotation apiOperation = vm.newAnnotation(ApiOperation.class);
		apiOperation.addAnnotationAttribute("httpMethod", vm.newString("POST"));
		apiOperation.addAnnotationAttribute("value", vm.newString("查询列表"));
		findAllMethod.addAnnotation(apiOperation);
		Annotation postMapping = vm.newAnnotation("PostMapping");
		postMapping.addDefaultValueAttribute(vm.newString("/findAll"));
		findAllMethod.addAnnotation(postMapping);

		findAllMethod.addParameter(vm.newType("@RequestBody " + StringUtils.underline2Camel(table.getTableName(), false) + "Vo"), StringUtils.underline2Camel(table.getTableName()) + "Vo");

		Return aReturn = findAllMethod.newReturn();
		Invoke rtn = vm.newInvoke(StringUtils.underline2Camel(table.getTableName()) + "Service.findAll");
		rtn.addArg(vm.newVar(StringUtils.underline2Camel(table.getTableName()) + "Vo"));
		aReturn.setExpression(rtn);
	}
}
















