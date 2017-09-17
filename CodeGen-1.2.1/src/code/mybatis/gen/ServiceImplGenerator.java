package code.mybatis.gen;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import code.core.gen.AbstractBaseGenerator;
import code.core.gen.AbstractJavaGenerator;
import code.core.parse0.Generator;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月4日 下午11:50:30
 */
@Component
@Scope("prototype")
public class ServiceImplGenerator extends AbstractBaseGenerator {
	public ServiceImplGenerator() {
		super("BaseServiceImpl", "MybatisBaseServiceImplTemplate.ftl");
	}
	
	@Override
	public void process() {
		//输出基本的文件
		super.process();
		
		//输出base文件
		processBase();
	}
	
	@Override
	public Map<String, Object> getContents() {
		Map<String, Object> contents = new HashMap<>();
		AbstractJavaGenerator beanClass = context.getGenerator(Generator.BeanGenerator.toString());
		AbstractJavaGenerator iServiceClass = context.getGenerator(Generator.IServiceGenerator.toString());
		AbstractJavaGenerator iMapperClass = context.getGenerator(Generator.IMapperGenerator.toString());
		
		String baseMapperPackage = iMapperClass.getPackageName();
		String module = context.getxTable().getProperty("module");
		if(module != null) {
			baseMapperPackage = baseMapperPackage.replace("."+ module, "");
		}
		//for base
		contents.put("baseServiceImplPackage", getBasePackageName());
		contents.put("iServiceBasePackage",iServiceClass.getBasePackageName());
		contents.put("iMapperBasePackage", iMapperClass.getBasePackageName());

		//for self
		contents.put("packageName", getPackageName());
		contents.put("date", new Date());
		contents.put("fullBeanName", beanClass.getGeneratorClassName());
		contents.put("iMapperFullClassName", iMapperClass.getGeneratorClassName());
		contents.put("iServiceFullClassName", iServiceClass.getGeneratorClassName());
		contents.put("servicePackage", iServiceClass.getPackageName());
		contents.put("classDesc", "");
		contents.put("className", getGeneratorSimpleClassName());
		contents.put("simpleBeanName", beanClass.getGeneratorSimpleClassName());
		//contents.put("primaryPropertyType", getPrimaryKeyContents()); 父类实现
		contents.put("iMapperSimpleClassName", iMapperClass.getGeneratorSimpleClassName());
		contents.put("iServiceSimplClassName", iServiceClass.getGeneratorSimpleClassName());
	
		return contents;
	}
}
