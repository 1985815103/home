package code.core.gen;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import code.core.jdbc.TableMeta;
import code.core.parse0.CodeGeneratorConfig;
import code.core.parser1.XmlEntityRoot;
import code.core.parser1.XmlEntityTable;
import freemarker.template.Configuration;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月14日 下午10:41:01
 */
@Component
@Scope("prototype")
public class GeneratorContext {
	private TableMeta tableMeta;
	
	private XmlEntityTable xTable;
	
	private XmlEntityRoot xRoot;
	
	private Configuration freemakerConfig;
	
	private Map<String,AbstractJavaGenerator> genPools = new HashMap<String, AbstractJavaGenerator>();
	
	private CodeGeneratorConfig codeGenerator;
	
	public XmlEntityTable getxTable() {
		return xTable;
	}

	public void setxTable(XmlEntityTable xTable) {
		this.xTable = xTable;
	}

	public void setxRoot(XmlEntityRoot xRoot) {
		this.xRoot = xRoot;
	}
	
	public XmlEntityRoot getxRoot() {
		return xRoot;
	}
	
	public Configuration getFreemakerConfig() {
		return freemakerConfig;
	}

	public void setFreemakerConfig(Configuration freemakerConfig) {
		this.freemakerConfig = freemakerConfig;
	}

	public void setGenPools(Map<String, AbstractJavaGenerator> genPools) {
		this.genPools = genPools;
	}
	
	public AbstractJavaGenerator getGenerator(String name) {
		return genPools.get(name);
	}
	
	public void setTableMeta(TableMeta tableMeta) {
		this.tableMeta = tableMeta;
	}
	
	public TableMeta getTableMeta() {
		return tableMeta;
	}
	
	public void setCodeGenerator(CodeGeneratorConfig codeGenerator) {
		this.codeGenerator = codeGenerator;
	}
	
	public CodeGeneratorConfig getCodeGenerator() {
		return codeGenerator;
	}
}
