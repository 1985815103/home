package code.core.parse0;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月30日 上午4:06:57
 */
public class ResourceContainer {
	static Map<String,CodeGeneratorConfig> gens = new HashMap<String, CodeGeneratorConfig>();
	static Map<String,String> fieldTypes = new HashMap<String, String>();
	static List<String> supports = new ArrayList<String>();
	
	public static String getFieldJavaType(String jdbcType) {
		return fieldTypes.get(jdbcType);
	}
	
	public static CodeGeneratorConfig getCodeGenerator(Generator name) {
		return gens.get(name.toString());
	}
	
	public static CodeGeneratorConfig getCodeGenerator(String name) {
		return gens.get(name);
	}
	
	public static boolean isSupportGenerator(String framework) {
		return supports.contains(framework);
	}
	
	public static Collection<CodeGeneratorConfig> getSupportGenerators() {
		return gens.values();
	}
}
