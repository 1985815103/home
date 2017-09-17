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
public class IMapperGenerator extends AbstractBaseGenerator{
	public IMapperGenerator() {
		super("BaseMapper","IMybatisBaseMapperTemplate.ftl");
	}
	
	@Override
	public void process() {
		//输出基本的文件
		super.process();
		//输出base文件
		super.processBase();
	}
	
	@Override
	public Map<String, Object> getContents() {
		AbstractJavaGenerator beanClass = context.getGenerator(Generator.BeanGenerator.toString());
		String baseMapperPackage = getPackageName();
		String module = context.getxTable().getProperty("module");
		if(module != null) {
			baseMapperPackage = baseMapperPackage.replace("."+ module, "");
		}
		
		Map<String, Object> contents = new HashMap<>();
		//for base
		contents.put("basePackageName", getBasePackageName());
		//for this
		contents.put("date", new Date());
		contents.put("packageName", getPackageName());
		contents.put("mapperPackage", baseMapperPackage);
		contents.put("fullBeanName", beanClass.getGeneratorClassName());
		contents.put("simpleBeanName", beanClass.getGeneratorSimpleClassName());
		contents.put("classDesc", "");
		contents.put("className", getGeneratorSimpleClassName());
		
		return contents;
	}
}
