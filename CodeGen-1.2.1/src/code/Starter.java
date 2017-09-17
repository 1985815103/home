package code;

import java.io.IOException;

import code.core.RunBySingle;
import code.core.RunByXml;
import code.enums.CURD;
import code.enums.Generator;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年9月16日 上午2:07:26
 */
public class Starter {
	public static void main(String[] args) throws IOException {
		RunBySingle();
//		RunByXml();
	}
	
	public static void RunBySingle() throws IOException {
		RunBySingle b = RunBySingle.create("tb_asso_staff_lic", "staff_id");
		b.enableGenerator(Generator.Para2EnumGenerator)
		 .enableGenerator(Generator.BeanGenerator)
		 .enableGenerator(Generator.XmlMapperGenerator)
//		 .enableGenerator(Generator.IMapperGenerator)
//		 .enableGenerator(Generator.IServiceGenerator)
//		 .enableGenerator(Generator.ServiceGenerator)
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
	
	public static void RunByXml() throws IOException {
		RunByXml.start();
	}
}
