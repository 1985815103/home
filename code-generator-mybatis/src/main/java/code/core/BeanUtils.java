package code.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月3日 上午12:19:40
 */
public class BeanUtils {
	public static String fomartProperty(String column) {
		String pattern = "(\\_[a-zA-Z])";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(column);
		StringBuffer sb = new StringBuffer();

		while (m.find()) {
			String s = m.group();
			m.appendReplacement(sb, s.substring(1).toUpperCase());
		}
		m.appendTail(sb);

		return sb.toString();
	}
	
	public static String getSimpleClassName(String tableName) {
		String javaSimpleName = BeanUtils.fomartProperty(tableName);
		javaSimpleName = javaSimpleName.substring(0,1).toUpperCase() + javaSimpleName.substring(1);
		return  javaSimpleName;
	}
}
