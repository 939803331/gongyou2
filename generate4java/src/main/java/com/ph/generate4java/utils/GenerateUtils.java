package com.ph.generate4java.utils;

import net.sourceforge.jenesis4java.Access;
import net.sourceforge.jenesis4java.ClassField;
import net.sourceforge.jenesis4java.PackageClass;
import net.sourceforge.jenesis4java.VirtualMachine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @PACKAGE_NAME: com.ph.generate4java.utils
 * @NAME: GenerateUtils
 * @Description:
 * @Author ph
 * @DATE: 2019-08-26 13:58
 **/
public class GenerateUtils {
	// init
	public static final VirtualMachine vm = VirtualMachine.getVirtualMachine();

	//public static final String RESPONSE_MESSAGE = "com.jxc.common.ResponseMessage";
	public static final String RESPONSE_MESSAGE = "com.weiwei.landfill.utils.response.ResponseMessage";

	//public static final String RESPONSE_MESSAGE_FACTORY = "com.jxc.common.ResponseMessageFactory";
	public static final String RESPONSE_MESSAGE_FACTORY = "com.weiwei.landfill.utils.response.ResponseMessageFactory";

	//public static final String ERROR_CODE_CONSTANTS = "com.jxc.common.ErrorCodeConstants";
	public static final String ERROR_CODE_CONSTANTS = "com.weiwei.landfill.utils.constant.ErrorCodeConstants";

	//public static final String ASSERT_UTIL = "com.jxc.common.AssertUtil";
	public static final String ASSERT_UTIL = "com.weiwei.landfill.common.utils.verifica.AssertUtil";

	public static final String PAGE_HELPER = "com.github.pagehelper.PageHelper";

	public static final String PAGE_INFO = "com.github.pagehelper.PageInfo";

	//public static final String PAGE_UTILS = "com.jxc.common.PageUtils";
	public static final String PAGE_UTILS = "com.weiwei.landfill.utils.page.PageUtils";

	public static final String LIST = "java.util.List";

	public static final String PARAM = "org.apache.ibatis.annotations.Param";

	public static String documentation(String className, String packagesName, String description) {
		StringBuilder sb = new StringBuilder();
		sb.append("@PACKAGE_NAME: ").append(packagesName).append("\n")
				.append("@NAME: ").append(className).append("\n")
				.append("@Description: ").append(description).append("\n")
				.append("@Author ph\n")
				.append("@DATE: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		return sb.toString();
	}

	public static void setSerializable(PackageClass clazz) {
		ClassField serialVersionUID = clazz.newField(vm.newType(long.class), "serialVersionUID");
		serialVersionUID.setAccess(Access.PRIVATE);
		serialVersionUID.isStatic(true);
		serialVersionUID.isFinal(true);
		serialVersionUID.setExpression(vm.newLong(-1L));
	}
}
