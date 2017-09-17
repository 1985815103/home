package code.core;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import code.core.parser1.XmlEntityGenerator;
import code.core.parser1.XmlEntityTable;
import code.enums.CURD;
import code.enums.Generator;


/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年9月15日 下午11:43:09
 */
@Component("Bootstrap")
public class RunBySingle extends RunByXml{
	private XmlEntityTable ce;
	private Set<Generator> bizGens;
	private Set<CURD> curds;
	private AnnotationConfigApplicationContext context;
	
	public static void main(String[] args) throws IOException {
		RunBySingle b = RunBySingle.create("tb_asso_staff_lic", "staff_id");
		b.enableGenerator(Generator.Para2EnumGenerator)
		 .enableGenerator(Generator.BeanGenerator)
		 .enableGenerator(Generator.XmlMapperGenerator)
//		 .enableGenerator(Generator.IMapperGenerator)
//		 .enableGenerator(Generator.IServiceGenerator)
//		 .enableGenerator(Generator.SpringMVCGenerator)
//		 .enableGenerator(Generator.SpringMVCGenerator)
		 
//		 .enableXmlMapperCRUD(CURD.Insert)
		 .enableXmlMapperCRUD(CURD.Get)
//		 .enableXmlMapperCRUD(CURD.List)
//		 .enableXmlMapperCRUD(CURD.ListByMap)
//		 .enableXmlMapperCRUD(CURD.Delete)
//		 .enableXmlMapperCRUD(CURD.DeleteByMap)
//		 .enableXmlMapperCRUD(CURD.Update)
//		 .enableXmlMapperCRUD(CURD.UpdateByMap)
		 .overriderWhenExists(true)
		 .build();
	}
	
	public static RunBySingle create(String tableName, String primaryKey) {
		try {
			Assert.hasText(tableName, "表明不能为空！");
			Assert.hasText(primaryKey, "主键不能为空，如果没有主键请随便设置一个字段！");
			@SuppressWarnings("resource")
			AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
			context.start();
			RunBySingle main = context.getBean(RunBySingle.class);
			main.ce = new XmlEntityTable();
			main.ce.setTableName(tableName);
			main.ce.setProperty("primaryKey", primaryKey);
			main.bizGens = new HashSet<Generator>();
			main.curds = new HashSet<CURD>();
			main.context = context;
			return main;
		} catch (BeansException e) {
			e.printStackTrace();
		} 
		
		return new RunBySingle();
	}
	
	@Override
	protected void careateAll() {
		cm.getEntities().clear();
		cm.getEntities().put(ce.getTableName(), ce);
		Map<String, XmlEntityGenerator> gens = cm.getCodeGens();
		Map<String, XmlEntityGenerator> gensNew = new HashMap<String, XmlEntityGenerator>();
		
		for (Entry<String, XmlEntityGenerator> gen : gens.entrySet()) {
			if(bizGens.contains(Generator.valueOf(gen.getKey()))) {
				gensNew.put(gen.getKey(), gen.getValue());
			}
		}
		gens.clear();
		gens.putAll(gensNew);
		
		int crudValue = 0;
		for(CURD crud : curds) {
			crudValue += crud.getValue();
		}
		ce.setProperty("crud", Integer.toBinaryString(crudValue));
		
		super.careateAll();
	} 
	
	public RunBySingle setModule(String module) {
		ce.setProperty("module", module);
		return this;
	}
	
	public RunBySingle enableGenerator(Generator generatorType) {
		bizGens.add(generatorType);
		
		for(int i=0; i<Generator.values().length; i++) {
			int dependence = Generator.values()[i].getDependence();
			if(dependence >0 && dependence < generatorType.getDependence()) {
				bizGens.add(Generator.values()[i]);
			}
		}
		
		return this;
	}
	
	public RunBySingle enableXmlMapperCRUD(CURD crudType) {
		enableGenerator(Generator.XmlMapperGenerator);
		curds.add(crudType);
		return this;
	}
	
	public void build() throws IOException {
		Assert.notEmpty(bizGens,"至少设置一个代码Generator！");
		Assert.notEmpty(curds,"至少指定一个CRUD操作！");
		this.start(context);
		context.close();
	}

	public RunBySingle overriderWhenExists(boolean override) {
		ce.setProperty("override", String.valueOf(override));
		return this;
	}
}


