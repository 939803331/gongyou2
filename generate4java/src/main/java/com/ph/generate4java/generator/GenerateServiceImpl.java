package com.ph.generate4java.generator;

import com.ph.generate4java.entity.Tables;
import com.ph.generate4java.repository.TablesRepository;
import com.ph.generate4java.utils.GenerateUtils;
import com.ph.generate4java.utils.StringUtils;
import net.sourceforge.jenesis4java.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @PACKAGE_NAME: com.ph.generate4java.generator
 * @NAME: GenerateServiceImpl
 * @Description:
 * @Author ph
 * @DATE: 2019-08-23 15:23
 **/
@Component
public class GenerateServiceImpl {
	// init
	public static final VirtualMachine vm = VirtualMachine.getVirtualMachine();

	@Autowired
	private GeneratorProperties generatorProperties;

	@Autowired
	private TablesRepository tablesRepository;

	public void generator() {
		Tables table = tablesRepository.findByTableName(generatorProperties.getTable(), generatorProperties.getSchema());
		String className = StringUtils.underline2Camel(table.getTableName(), false) + "ServiceImpl";
		CompilationUnit unit = vm.newCompilationUnit(generatorProperties.getPath());
		unit.setNamespace(generatorProperties.getPackagesServiceImpl());

		unit.addImport(GenerateUtils.RESPONSE_MESSAGE);
		unit.addImport(GenerateUtils.RESPONSE_MESSAGE_FACTORY);
		unit.addImport(GenerateUtils.ERROR_CODE_CONSTANTS);
		unit.addImport(GenerateUtils.ASSERT_UTIL);
		unit.addImport(GenerateUtils.PAGE_HELPER);
		unit.addImport(GenerateUtils.PAGE_INFO);
		unit.addImport(GenerateUtils.PAGE_UTILS);
		unit.addImport(GenerateUtils.LIST);
		unit.addImport(generatorProperties.getPackagesEntity() + "." + StringUtils.underline2Camel(table.getTableName(), false));
		unit.addImport(generatorProperties.getPackagesVo() + "." + StringUtils.underline2Camel(table.getTableName(), false) + "Vo");
		unit.addImport(generatorProperties.getPackagesService() + "." + StringUtils.underline2Camel(table.getTableName(), false) + "Service");
		unit.addImport(generatorProperties.getPackagesMapper() + "." + StringUtils.underline2Camel(table.getTableName(), false) + "Mapper");
		unit.addImport(Service.class);
		unit.addImport(Autowired.class);

		PackageClass clazz = unit.newClass(className);
		clazz.setAccess(Access.PUBLIC);
		clazz.setComment(Comment.DOCUMENTATION, GenerateUtils.documentation(className, generatorProperties.getPackagesServiceImpl(), table.getTableComment() + "serviceImpl"));
		clazz.addImplements(StringUtils.underline2Camel(table.getTableName(), false) + "Service");
		clazz.addAnnotation(vm.newAnnotation(Service.class));
		ClassField classField = clazz.newField(vm.newType(StringUtils.underline2Camel(table.getTableName(), false) + "Mapper"), StringUtils.underline2Camel(table.getTableName()) + "Mapper");
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
		StringBuilder sb = new StringBuilder();
		sb.append("新增")
				.append(table.getTableComment())
				.append("\n\n")
				.append("@param: ")
				.append(StringUtils.underline2Camel(table.getTableName()))
				.append("\n")
				.append("@return: ");
		insertMethod.setComment(Comment.DOCUMENTATION, sb.toString());
		insertMethod.addParameter(vm.newType(StringUtils.underline2Camel(table.getTableName(), false)), StringUtils.underline2Camel(table.getTableName()));

		Let responseMessage = insertMethod.newLet(vm.newType("ResponseMessage"));
		responseMessage.addAssign("result", vm.newInvoke("ResponseMessageFactory.newInstance"));

		Let res = insertMethod.newLet(vm.newType("int"));
		Invoke resInvoke = vm.newInvoke(StringUtils.underline2Camel(table.getTableName()) + "Mapper.insert");
		resInvoke.addArg(vm.newVar(StringUtils.underline2Camel(table.getTableName())));
		res.addAssign("res", resInvoke);

		Let letass = insertMethod.newLet(vm.type_void());
		Invoke assertUtil = vm.newInvoke("AssertUtil.numberGtZero");
		assertUtil.addArg(vm.newVar("res"));
		assertUtil.addArg(vm.newVar("ErrorCodeConstants.ADD_ERORR.getDescript()"));
		assertUtil.addArg(vm.newVar("ErrorCodeConstants.ADD_ERORR.getId()"));
		letass.addAssign("s", assertUtil);

		Return aReturn = insertMethod.newReturn();
		aReturn.setExpression(vm.newVar("result"));

	}

	private void delete(PackageClass clazz, Tables table) {
		ClassMethod deleteMethod = clazz.newMethod(vm.newType("ResponseMessage"), "delete");
		deleteMethod.setAccess(Access.PUBLIC);
		StringBuilder sb = new StringBuilder();
		sb.append("删除")
				.append(table.getTableComment())
				.append("\n\n")
				.append("@param: id")
				.append("\n")
				.append("@return: ");
		deleteMethod.setComment(Comment.DOCUMENTATION, sb.toString());
		deleteMethod.addParameter(vm.newType("Integer"), "id");

		Let responseMessage = deleteMethod.newLet(vm.newType("ResponseMessage"));
		responseMessage.addAssign("result", vm.newInvoke("ResponseMessageFactory.newInstance"));

		Let res = deleteMethod.newLet(vm.newType("int"));
		Invoke resInvoke = vm.newInvoke(StringUtils.underline2Camel(table.getTableName()) + "Mapper.delete");
		resInvoke.addArg(vm.newVar("id"));
		res.addAssign("res", resInvoke);

		Let letass = deleteMethod.newLet(vm.type_void());
		Invoke assertUtil = vm.newInvoke("AssertUtil.numberGtZero");
		assertUtil.addArg(vm.newVar("res"));
		assertUtil.addArg(vm.newVar("ErrorCodeConstants.DELETE_ERORR.getDescript()"));
		assertUtil.addArg(vm.newVar("ErrorCodeConstants.DELETE_ERORR.getId()"));
		letass.addAssign("s", assertUtil);

		Return aReturn = deleteMethod.newReturn();
		aReturn.setExpression(vm.newVar("result"));
	}

	private void update(PackageClass clazz, Tables table) {
		ClassMethod updateMethod = clazz.newMethod(vm.newType("ResponseMessage"), "update");
		updateMethod.setAccess(Access.PUBLIC);
		StringBuilder sb = new StringBuilder();
		sb.append("修改")
				.append("\n\n")
				.append("@param: ")
				.append(StringUtils.underline2Camel(table.getTableName()))
				.append("\n")
				.append("@return: ");
		updateMethod.setComment(Comment.DOCUMENTATION, sb.toString());
		updateMethod.addParameter(vm.newType(StringUtils.underline2Camel(table.getTableName(), false)), StringUtils.underline2Camel(table.getTableName()));

		Let responseMessage = updateMethod.newLet(vm.newType("ResponseMessage"));
		responseMessage.addAssign("result", vm.newInvoke("ResponseMessageFactory.newInstance"));

		Let res = updateMethod.newLet(vm.newType("int"));
		Invoke resInvoke = vm.newInvoke(StringUtils.underline2Camel(table.getTableName()) + "Mapper.update");
		resInvoke.addArg(vm.newVar(StringUtils.underline2Camel(table.getTableName())));
		res.addAssign("res", resInvoke);

		Let letass = updateMethod.newLet(vm.type_void());
		Invoke assertUtil = vm.newInvoke("AssertUtil.numberGtZero");
		assertUtil.addArg(vm.newVar("res"));
		assertUtil.addArg(vm.newVar("ErrorCodeConstants.EDIT_ERORR.getDescript()"));
		assertUtil.addArg(vm.newVar("ErrorCodeConstants.EDIT_ERORR.getId()"));
		letass.addAssign("s", assertUtil);

		Return aReturn = updateMethod.newReturn();
		aReturn.setExpression(vm.newVar("result"));
	}

	private void findById(PackageClass clazz, Tables table) {
		ClassMethod findByIdMethod = clazz.newMethod(vm.newType("ResponseMessage"), "findById");
		findByIdMethod.setAccess(Access.PUBLIC);
		StringBuilder sb = new StringBuilder();
		sb.append("根据id查询")
				.append("\n\n")
				.append("@param: id\n")
				.append("@return: ");
		findByIdMethod.setComment(Comment.DOCUMENTATION, sb.toString());
		findByIdMethod.addParameter(vm.newType("Integer"), "id");

		Let responseMessage = findByIdMethod.newLet(vm.newType("ResponseMessage"));
		responseMessage.addAssign("result", vm.newInvoke("ResponseMessageFactory.newInstance"));

		Let model = findByIdMethod.newLet(vm.newType(StringUtils.underline2Camel(table.getTableName(), false) + "Vo"));
		Invoke resInvoke = vm.newInvoke(StringUtils.underline2Camel(table.getTableName()) + "Mapper.findById");
		resInvoke.addArg(vm.newVar("id"));
		model.addAssign("model", resInvoke);

		Let assertLet = findByIdMethod.newLet(vm.type_void());
		Invoke assertUtil = vm.newInvoke("AssertUtil.notNull");
		assertUtil.addArg(vm.newVar("model"));
		assertUtil.addArg(vm.newVar("ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript()"));
		assertUtil.addArg(vm.newVar("ErrorCodeConstants.QUERY_EMPTY_DATA.getId()"));
		assertLet.addAssign("s", assertUtil);

		Let resultLet = findByIdMethod.newLet(vm.type_void());
		Invoke setData = vm.newInvoke("result.setData");
		setData.addArg(vm.newVar("model"));
		resultLet.addAssign("s", setData);

		Return aReturn = findByIdMethod.newReturn();
		aReturn.setExpression(vm.newVar("result"));
	}

	//ResponseMessage result = ResponseMessageFactory.newInstance();
	//PageHelper.startPage(jxcSiteAppUser.getPageNo(), jxcSiteAppUser.getPageSize());
	//List<JxcSiteAppUser> list = this.jxcSiteAppUserMapper.selectListByConditions(jxcSiteAppUser);
	//PageInfo<JxcSiteAppUser> page = new PageInfo<>(list);
	//result.setData(new PageUtils<>(page).getPageViewDatatable());
	//return result;

	private void findAll(PackageClass clazz, Tables table) {
		ClassMethod findAllMethod = clazz.newMethod(vm.newType("ResponseMessage"), "findAll");
		findAllMethod.setAccess(Access.PUBLIC);
		StringBuilder sb = new StringBuilder();
		sb.append("查询列表")
				.append("\n\n")
				.append("@param: ")
				.append(StringUtils.underline2Camel(table.getTableName()))
				.append("Vo")
				.append("\n")
				.append("@return: ");
		findAllMethod.setComment(Comment.DOCUMENTATION, sb.toString());
		findAllMethod.addParameter(vm.newType(StringUtils.underline2Camel(table.getTableName(), false) + "Vo"), StringUtils.underline2Camel(table.getTableName()) + "Vo");

		Let responseMessage = findAllMethod.newLet(vm.newType("ResponseMessage"));
		responseMessage.addAssign("result", vm.newInvoke("ResponseMessageFactory.newInstance"));

		Let assertLet = findAllMethod.newLet(vm.type_void());
		Invoke assertUtil = vm.newInvoke("PageHelper.startPage");
		assertUtil.addArg(vm.newVar(StringUtils.underline2Camel(table.getTableName()) + "Vo" + ".getPageNo()"));
		assertUtil.addArg(vm.newVar(StringUtils.underline2Camel(table.getTableName()) + "Vo" + ".getPageSize()"));
		assertLet.addAssign("s", assertUtil);

		Let list = findAllMethod.newLet(vm.newType("List<" + StringUtils.underline2Camel(table.getTableName(), false) + "Vo>"));
		Invoke resInvoke = vm.newInvoke(StringUtils.underline2Camel(table.getTableName()) + "Mapper.findAll");
		resInvoke.addArg(vm.newVar(StringUtils.underline2Camel(table.getTableName()) + "Vo"));
		list.addAssign("list", resInvoke);

		Let page = findAllMethod.newLet(vm.newType("PageInfo<" + StringUtils.underline2Camel(table.getTableName(), false) + "Vo>"));
		Invoke pageInfo = vm.newInvoke("new PageInfo<>");
		pageInfo.addArg(vm.newVar("list"));
		page.addAssign("page", pageInfo);

		Let resultLet = findAllMethod.newLet(vm.type_void());
		Invoke setData = vm.newInvoke("result.setData");
		setData.addArg(vm.newVar("new PageUtils<>(page).getPageViewDatatable()"));
		resultLet.addAssign("s", setData);

		Return aReturn = findAllMethod.newReturn();
		aReturn.setExpression(vm.newVar("result"));
	}
}



























