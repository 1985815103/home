package code.core.gen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import code.core.BeanUtils;
import code.core.parse0.CodeGeneratorConfig;
import code.core.parse0.ResourceContainer;
import code.core.parser1.XmlEntityTable;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月4日 下午11:50:30
 */
public abstract class AbstractJavaGenerator {
	private Logger logger;
	protected GeneratorContext context;
	protected CodeGeneratorConfig codeGenerator;
	protected String prefix;
	protected String suffix;
	protected String fileType;
	private boolean init;
	
	public AbstractJavaGenerator() {
		logger = Logger.getLogger(this.getClass());
	}
	
	public void init() {
		prefix = codeGenerator.getProperty("prefix") == null ? "" : codeGenerator.getProperty("prefix");
		suffix = codeGenerator.getProperty("suffix") == null ? "" : codeGenerator.getProperty("suffix");
		fileType = codeGenerator.getProperty("fileType") == null ? ".java" : codeGenerator.getProperty("fileType");
		init = true;
	}
	
	public abstract Map<String, Object> getContents();
	
	public String getGeneratorSimpleClassName() {
		if(!init) {
			init();
		}
		return prefix + BeanUtils.getSimpleClassName(context.getxTable().getTableName()) + suffix;
	}
	
	/**
	 * 获取带有模块名的包路径
	 * @return
	 */
	public String getPackageName() {
		String packageName = getBasePackageName();
		XmlEntityTable xTable = context.getxTable();
		if(xTable != null && xTable.getProperty("module") != null) {
			packageName += "." + xTable.getProperty("module");
		}
		
		return packageName;
	}

	/**
	 * 获取基本的包路径
	 * @return
	 */
	public String getBasePackageName() {
		return codeGenerator.getExtProperty("targetPackage");
	}
	
	public String getGeneratorClassName() {
		return String.format("%s.%s", getPackageName(),getGeneratorSimpleClassName());
	}
	
	protected String getTargetProjectDir() {
		String outputHomeDir = context.getxRoot().getOutputHomeDir();
		if(!outputHomeDir.endsWith("/") && !outputHomeDir.endsWith("\\")) {
			outputHomeDir = outputHomeDir + "/";
		}
		return outputHomeDir;
	}
	
	protected String getOutpathFilePath() {
		return getTargetProjectDir() + String.format("src/%s%s", getGeneratorClassName().replace(".", "/"), fileType);
	}
	public void setContext(GeneratorContext context) {
		this.context = context;
	}
	public GeneratorContext getContext() {
		return context;
	}
	
	protected void processExists(File oldFile) {
		File oOldFile = new File(oldFile.getParentFile(),oldFile.getName() + ".old");
		//处理file.old.java等
		if(oOldFile.exists()) {
			long fileModifyTime = oOldFile.lastModified();
			String modifyTimeStr = ".old." +new SimpleDateFormat("MMddHHmm").format(new Date(fileModifyTime));
			File oOldFileNew = new File(oldFile.getParent(),oldFile.getName() + modifyTimeStr);
			oOldFile.renameTo(oOldFileNew);
		}
		oldFile.renameTo(oOldFile);
	}
	
	public void process() {
		String option = "Create";
		
		try {
			Configuration config = context.getFreemakerConfig(); 
			String outpathFilePath = getOutpathFilePath();
			Map<String, Object> contents = getContents();
			Template template = config.getTemplate(context.getCodeGenerator().getTemplate());
			File newFile = new File(outpathFilePath);
			contents.putAll(getPrimaryKeyContents());
			
			if(!newFile.getParentFile().exists()) {
				newFile.getParentFile().mkdirs();
			}
			
			if(newFile.exists()) {
				//强制覆盖
				if("true".equals(context.getxTable().getProperty("override"))) {
					option = "Update";
					processExists(newFile);
				}
				else {
					return;
				}
			}
			
			OutputStream fos = new FileOutputStream(new File(outpathFilePath));
			Writer out = new OutputStreamWriter(fos,context.getxRoot().getOutputDefaultEncoding());
			template.process(contents, out);
			fos.close();
			out.close();
		} catch (Throwable e) {
			logger.error(e);
		}
		
		String info = String.format("%s%s(%s)", getGeneratorClassName(),fileType, option);
		System.out.println(info);
	}
	
	protected Map<String, Object> getPrimaryKeyContents() {
		Map<String, Object> keyContents = new HashMap<String, Object>();
		String primaryKey = context.getxTable().getProperty("primaryKey");
		if(primaryKey != null) {
			keyContents.put("primaryProperty", BeanUtils.fomartProperty(primaryKey));
			String primaryKeyJdbcType = context.getTableMeta().getMetalColumn(primaryKey).getColumnType();
			String primaryPropertyType = ResourceContainer.getFieldJavaType(primaryKeyJdbcType);
			keyContents.put("primaryPropertyType", primaryPropertyType);
		}
		
		return keyContents;
	}

	public void setCodeGenerator(CodeGeneratorConfig codeGenerator) {
		this.codeGenerator = codeGenerator;
	}
}
