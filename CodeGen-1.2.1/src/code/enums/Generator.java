package code.enums;
public enum Generator {
	Para2EnumGenerator(0),//独立项目，不被其他生成器依赖
	BeanGenerator(1),
	XmlMapperGenerator(2),
	IMapperGenerator(3),
	IServiceGenerator(4),
	ServiceGenerator(5),
	SpringMVCGenerator(6);
	
	int dependence;
	
	private Generator(int dependence) {
		this.dependence = dependence;
	}
	
	public int getDependence() {
		return dependence;
	}
}