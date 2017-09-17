package code.core.parser1;

import java.util.HashMap;
import java.util.Map;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月3日 下午8:14:15
 */
public class XmlEntityRoot {
	boolean override;
	String outputHomeDir;
	String outputDefaultEncoding;
	Map<String,XmlEntityTable> entities = new HashMap<String, XmlEntityTable>();
	Map<String,XmlEntityGenerator> codeGens = new HashMap<String,XmlEntityGenerator>();
	
	public boolean isOverride() {
		return override;
	}
	
	public String getOutputHomeDir() {
		return outputHomeDir;
	}
	
	public String getOutputDefaultEncoding() {
		return outputDefaultEncoding;
	}
	
	public Map<String, XmlEntityTable> getEntities() {
		return entities;
	}
	
	public Map<String, XmlEntityGenerator> getCodeGens() {
		return codeGens;
	}
	
	public XmlEntityGenerator getRefCodeGen(String refClassName) {
		return codeGens.get(refClassName);
	}
}