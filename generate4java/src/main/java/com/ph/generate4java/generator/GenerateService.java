package com.ph.generate4java.generator;

import com.ph.generate4java.entity.Tables;
import com.ph.generate4java.repository.TablesRepository;
import com.ph.generate4java.utils.GenerateUtils;
import com.ph.generate4java.utils.StringUtils;
import net.sourceforge.jenesis4java.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @PACKAGE_NAME: com.ph.generate4java.generator
 * @NAME: GenerateService
 * @Description:
 * @Author ph
 * @DATE: 2019-08-23 15:23
 **/
@Component
public class GenerateService {
	// init
	public static final VirtualMachine vm = VirtualMachine.getVirtualMachine();

	@Autowired
	private GeneratorProperties generatorProperties;

	@Autowired
	private TablesRepository tablesRepository;

	public void generator() {
		Tables table = tablesRepository.findByTableName(generatorProperties.getTable(), generatorProperties.getSchema());
		String className = StringUtils.underline2Camel(table.getTableName(), false) + "Service";
		CompilationUnit unit = vm.newCompilationUnit(generatorProperties.getPath());
		unit.setNamespace(generatorProperties.getPackagesService());

		unit.addImport(GenerateUtils.RESPONSE_MESSAGE);
		unit.addImport(generatorProperties.getPackagesEntity() + "." + StringUtils.underline2Camel(table.getTableName(), false));
		unit.addImport(generatorProperties.getPackagesVo() + "." + StringUtils.underline2Camel(table.getTableName(), false) + "Vo");

		Interface inface = unit.newPublicInterface(className);
		inface.setComment(Comment.DOCUMENTATION, GenerateUtils.documentation(className, generatorProperties.getPackagesService(), table.getTableComment() + "service"));


		insert(inface, table);
		delete(inface);
		update(inface, table);
		findById(inface, table);
		findAll(inface, table);
		vm.encode();
	}

	private void insert(Interface inface, Tables table) {
		AbstractMethod insertMethod = inface.newMethod(vm.newType("ResponseMessage"), "insert");
		StringBuilder sb = new StringBuilder();
		sb.append("新增")
				.append("\n\n")
				.append("@param: ")
				.append(StringUtils.underline2Camel(table.getTableName()))
				.append("\n")
				.append("@return: ");
		insertMethod.setComment(Comment.DOCUMENTATION, sb.toString());
		insertMethod.addParameter(vm.newType(StringUtils.underline2Camel(table.getTableName(), false)), StringUtils.underline2Camel(table.getTableName()));
	}

	private void delete(Interface inface) {
		AbstractMethod deleteMethod = inface.newMethod(vm.newType("ResponseMessage"), "delete");
		deleteMethod.addParameter(vm.newType("Integer"), "id");
		StringBuilder sb = new StringBuilder();
		sb.append("删除")
				.append("\n\n")
				.append("@param: id\n")
				.append("@return: ");
		deleteMethod.setComment(Comment.DOCUMENTATION, sb.toString());
	}

	private void update(Interface inface, Tables table) {
		AbstractMethod updateMethod = inface.newMethod(vm.newType("ResponseMessage"), "update");
		StringBuilder sb = new StringBuilder();
		sb.append("修改")
				.append("\n\n")
				.append("@param: ")
				.append(StringUtils.underline2Camel(table.getTableName()))
				.append("\n")
				.append("@return: ");
		updateMethod.setComment(Comment.DOCUMENTATION, sb.toString());
		updateMethod.addParameter(vm.newType(StringUtils.underline2Camel(table.getTableName(), false)), StringUtils.underline2Camel(table.getTableName(), false));
	}

	private void findById(Interface inface, Tables table) {
		AbstractMethod findByIdMethod = inface.newMethod(vm.newType("ResponseMessage"), "findById");
		StringBuilder sb = new StringBuilder();
		sb.append("根据id查询")
				.append("\n\n")
				.append("@param: id\n")
				.append("@return: ");
		findByIdMethod.setComment(Comment.DOCUMENTATION, sb.toString());
		findByIdMethod.addParameter(vm.newType("Integer"), "id");
	}

	private void findAll(Interface inface, Tables table) {
		AbstractMethod findAllMethod = inface.newMethod(vm.newType("ResponseMessage"), "findAll");
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
	}
}



























