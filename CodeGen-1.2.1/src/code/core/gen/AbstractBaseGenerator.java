package code.core.gen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月29日 下午10:36:41
 */
public abstract class AbstractBaseGenerator extends AbstractJavaGenerator{
	private Logger logger = Logger.getLogger(AbstractBaseGenerator.class);
	String baseClassName;
	String baseClassTemplate;
	
	public AbstractBaseGenerator (String baseClassName, String baseClassTemplate) {
		this.baseClassName = baseClassName;
		this.baseClassTemplate = baseClassTemplate;
	}
	
	public void processBase() {
		Configuration config = getContext().getFreemakerConfig(); 
		String outputHomeDir = getTargetProjectDir();
		String outpathFilePath = outputHomeDir + String.format("src/%s%s", getBasePackageName().replace(".", "/") + "/" + baseClassName, fileType);
		File newFile = new File(outpathFilePath);
		
		if(!newFile.getParentFile().exists()) {
			newFile.getParentFile().mkdirs();
		}
		if(newFile.exists()) {
			return;
		}
		try {
			Map<String, Object> contents = getContents();
			Template template = config.getTemplate(baseClassTemplate);
			contents.putAll(getPrimaryKeyContents());
			OutputStream fos = new FileOutputStream(new File(outpathFilePath));
			Writer out = new OutputStreamWriter(fos,getContext().getxRoot().getOutputDefaultEncoding());
			template.process(contents, out);
			fos.close();
			out.close();
		} catch (Throwable e) {
			logger.error(e);
		}
		
		String info = String.format("%s.%s(Create)", getBasePackageName(),baseClassName);
		System.out.println(info);
	}

}
