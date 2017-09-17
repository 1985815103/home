package code.core.parse0;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月30日 上午5:28:10
 */
public enum Generator {
	XmlMapperGenerator("XmlMapperGenerator"),
	IMapperGenerator("IMapperGenerator"),
	SpringMVCGenerator("SpringMVCGenerator"),
	BeanGenerator("BeanGenerator"),
	IServiceGenerator("IServiceGenerator"),
	ServiceGenerator("ServiceGenerator"),
	Para2EnumGenerator("Para2EnumGenerator");

	String name;
	Generator(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
