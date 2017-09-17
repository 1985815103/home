package code.core.parse0;

import java.util.HashMap;
import java.util.Map;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月30日 上午4:05:20
 */
public class CodeGeneratorConfig {
	String name;
	String clazz;
	String template;
	Map<String,String> props = new HashMap<String,String>();
	Map<String,String> extProps = new HashMap<String,String>();
	String support;
	
	public String getName() {
		return name;
	}
	public String getClazz() {
		return clazz;
	}
	public String getTemplate() {
		return template;
	}
	public Map<String, String> getProps() {
		return props;
	}
	
	public void setExtProps(Map<String, String> extProps) {
		this.extProps = extProps;
	}
	
	public Map<String, String> getExtProps() {
		return extProps;
	}
	public String getForFramework() {
		return support;
	}
	
	public void addExtProperty(String name, String value) {
		this.extProps.put(name, value);
	}
	
	public String getExtProperty(String name) {
		return this.extProps.get(name);
	}
	
	public String getProperty(String name) {
		return this.props.get(name);
	}
}
