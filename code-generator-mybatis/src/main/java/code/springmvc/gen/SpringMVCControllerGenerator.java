package code.springmvc.gen;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import code.core.gen.AbstractJavaGenerator;
import code.core.parse0.Generator;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月4日 下午11:50:30
 */
@Component
@Scope("prototype")
public class SpringMVCControllerGenerator extends AbstractJavaGenerator{

	@Override
	public Map<String, Object> getContents() {
		AbstractJavaGenerator beanClass = context.getGenerator(Generator.BeanGenerator.toString());
		AbstractJavaGenerator iServiceClass = context.getGenerator(Generator.IServiceGenerator.toString());
		
		Map<String, Object> contents = new HashMap<>();
		contents.put("packageName", getPackageName());
		contents.put("date", new Date());
		contents.put("requestMapping", beanClass.getGeneratorSimpleClassName());
		contents.put("className", getGeneratorSimpleClassName());
		contents.put("iServiceSimpleClassName", iServiceClass.getGeneratorSimpleClassName());
		contents.put("iServiceFullClassName", iServiceClass.getGeneratorClassName());
		
		return contents;
	}
}
