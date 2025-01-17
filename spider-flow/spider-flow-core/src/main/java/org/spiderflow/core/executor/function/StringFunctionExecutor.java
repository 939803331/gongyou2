package org.spiderflow.core.executor.function;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.spiderflow.Grammer;
import org.spiderflow.executor.FunctionExecutor;
import org.spiderflow.utils.Maps;
import org.springframework.stereotype.Component;

/**
 * String 工具类 防止NPE 
 * @author Administrator
 *
 */
@Component
public class StringFunctionExecutor implements FunctionExecutor,Grammer{
	
	@Override
	public String getFunctionPrefix() {
		return "string";
	}

	public static String substring(String content, int beginIndex) {
		return content != null ? content.substring(beginIndex) : null;
	}

	public static String substring(String content, int beginIndex, int endIndex) {
		return content != null ? content.substring(beginIndex, endIndex) : null;
	}

	public static String lower(String content) {
		return content != null ? content.toLowerCase() : null;
	}

	public static String upper(String content) {
		return content != null ? content.toUpperCase() : null;
	}

	public static int indexOf(String content, String str) {
		return content != null ? content.indexOf(str) : -1;
	}

	public static int indexOf(String content, String str, int fromIndex) {
		return content != null ? content.indexOf(str, fromIndex) : -1;
	}
	
	public static int toInt(String value){
		return Integer.parseInt(value);
	}
	
	public static Integer toInt(String value,Integer defaultValue){
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static String replace(String content,String source,String target){
		return content != null ? content.replace(source, target): null;
	}
	
	public static String replaceAll(String content,String regx,String target){
		return content != null ? content.replaceAll(regx, target): null;
	}
	
	public static String replaceFirst(String content,String regx,String target){
		return content != null ? content.replaceFirst(regx, target): null;
	}
	
	public static int length(String content){
		return content != null ? content.length() : -1;
	}
	
	public static String trim(String content){
		return content != null ? content.trim() : null;
	}
	
	public static List<String> split(String content,String regx){
		return content != null ? Arrays.asList(content.split(regx)) : new ArrayList<>(0);
	}
	
	public static byte[] bytes(String content){
		return content != null ? content.getBytes() : null;
	}
	
	public static byte[] bytes(String content,String charset){
		try {
			return content != null ? content.getBytes(charset) : null;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public static String newString(byte[] bytes){
		return bytes != null ? new String(bytes) : null;
	}
	
	public static String newString(byte[] bytes,String charset){
		try {
			return bytes != null ? new String(bytes,charset) : null;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public static boolean equals(String str1,String str2){
		return str1 == null ? false : str1.equals(str2);
	}
	
	/**
	 * 生成UUID
	 * @return String UUID
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static List<String> uuids(Integer size) {
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			ids.add(UUID.randomUUID().toString().replace("-", ""));
		}
		return ids;
	}
	
	@Override
	public Map<String, List<String>> getFunctionMap() {
		return Maps.newMap("string", Arrays.asList("bytes","equals","indexOf","length","lower","upper","newString","replace","replaceAll","replaceFirst","split","substring","trim","uuid","uuids"));
	}
}
