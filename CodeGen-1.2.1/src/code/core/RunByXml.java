package code.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import code.core.gen.AbstractJavaGenerator;
import code.core.gen.GeneratorContext;
import code.core.jdbc.TableMetaManager;
import code.core.parse0.CodeGeneratorConfig;
import code.core.parse0.CoreXmlParser;
import code.core.parse0.Generator;
import code.core.parse0.ResourceContainer;
import code.core.parser1.XmlEntityGenerator;
import code.core.parser1.XmlEntityParser;
import code.core.parser1.XmlEntityRoot;
import code.core.parser1.XmlEntityTable;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月3日 上午12:19:40
 */
@Component
public class RunByXml {
	@Autowired
	private XmlEntityParser parser;

	@Autowired
	private TableMetaManager tableMetaManager;
	
	protected XmlEntityRoot cm;
	
	private ApplicationContext context;
	
	private Configuration cfg;
	
	private Logger logger = Logger.getLogger(RunByXml.class);
	
	public void start(ApplicationContext context) throws IOException {
		this.context = context;
		this.cm = parser.parse();
		this.cfg = new Configuration(Configuration.VERSION_2_3_25);
		CoreXmlParser.parse();
		ClassTemplateLoader classpathTemplateLoader1 = new ClassTemplateLoader(this.getClass(), "/code/core/gen");
		ClassTemplateLoader classpathTemplateLoader2 = new ClassTemplateLoader(this.getClass(), "/code/mybatis/gen");
		ClassTemplateLoader classpathTemplateLoader3 = new ClassTemplateLoader(this.getClass(), "/code/hibernate/gen");
		ClassTemplateLoader classpathTemplateLoader4 = new ClassTemplateLoader(this.getClass(), "/code/springmvc/gen");
		ClassTemplateLoader classpathTemplateLoader5 = new ClassTemplateLoader(this.getClass(), "/code/struts2/gen");
		List<TemplateLoader> tLoaders = new ArrayList<TemplateLoader>();
		tLoaders.add(classpathTemplateLoader1);
		tLoaders.add(classpathTemplateLoader2);
		tLoaders.add(classpathTemplateLoader3);
		tLoaders.add(classpathTemplateLoader4);
		tLoaders.add(classpathTemplateLoader5);
		
		//支持本地加载
		File localTemplateDir = new File("templates");
		if(localTemplateDir.exists()) {
			FileTemplateLoader localFileTemplateLoader = new FileTemplateLoader(localTemplateDir);
			tLoaders.add(localFileTemplateLoader);
		}
		MultiTemplateLoader mtl = new MultiTemplateLoader(tLoaders.toArray(new TemplateLoader[]{}));
		cfg.setTemplateLoader(mtl);
		//默认情况变量为null则替换为空字符串
		cfg.setClassicCompatible(true);
		cfg.setDefaultEncoding(cm.getOutputDefaultEncoding());
		
		careateAll();
	}
	
	public void create(String tableName) {
		try {
			tableName = tableName.toLowerCase();
			XmlEntityTable ce = cm.getEntities().get(tableName);
			Map<String,AbstractJavaGenerator> allGenPools = new HashMap<String, AbstractJavaGenerator>();
			
			//build context
			for (XmlEntityGenerator xmlEntityGenerator : cm.getCodeGens().values()) {
				String genName = xmlEntityGenerator.getName();
				CodeGeneratorConfig codeGenerator = ResourceContainer.getCodeGenerator(genName);
				if(codeGenerator == null) {
					logger.warn("Not support the generator:" + genName + ", Please check the support config!");
					continue;
				}
				
				Class<?> codeGenClazz = Class.forName(codeGenerator.getClazz());
				AbstractJavaGenerator gen = (AbstractJavaGenerator) context.getBean(codeGenClazz);
				GeneratorContext gContext = context.getBean(GeneratorContext.class);
				gContext.setFreemakerConfig(cfg);
				gContext.setxTable(ce);
				gContext.setxRoot(cm);
				gContext.setTableMeta(tableMetaManager.getTableMeta(tableName));
				gContext.setCodeGenerator(codeGenerator);
				gen.setContext(gContext);
				gen.setCodeGenerator(codeGenerator);
				codeGenerator.setExtProps(xmlEntityGenerator.getProps());
				
				allGenPools.put(genName, gen);
			}
			
			for (XmlEntityGenerator xmlEntityGenerator : cm.getCodeGens().values()) {
				if(Generator.Para2EnumGenerator.toString().equals(xmlEntityGenerator.getName())) {
					continue;
				}
				AbstractJavaGenerator gen =	allGenPools.get(xmlEntityGenerator.getName());
				if(gen == null) {
					continue;
				}
				GeneratorContext gContext = gen.getContext();
				gContext.setGenPools(allGenPools);
				gen.init();
				gen.process();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void careateAll() {
		Map<String,XmlEntityTable> entities = cm.getEntities();
		for (Entry<String,XmlEntityTable> entry : entities.entrySet()) {
			create(entry.getValue().getTableName());
		}

		//创建枚举
		XmlEntityGenerator xmlEntityGenerator = cm.getCodeGens().get(Generator.Para2EnumGenerator.toString());
		if(xmlEntityGenerator != null) {
			try {
				CodeGeneratorConfig codeGenerator = ResourceContainer.getCodeGenerator(xmlEntityGenerator.getName());
				Class<?> codeGenClazz = Class.forName(codeGenerator.getClazz());
				AbstractJavaGenerator gen = (AbstractJavaGenerator) context.getBean(codeGenClazz);
				GeneratorContext gContext = context.getBean(GeneratorContext.class);
				gContext.setFreemakerConfig(cfg);
				gContext.setxRoot(cm);
				gContext.setCodeGenerator(codeGenerator);
				gen.setCodeGenerator(codeGenerator);
				gen.setContext(gContext);
				gen.init();
				gen.process();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void start() throws IOException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		context.start();
		RunByXml main = (RunByXml) context.getBean("runByXml");
		main.start(context);
	}
}
