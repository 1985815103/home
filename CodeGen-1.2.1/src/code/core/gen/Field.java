package code.core.gen;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月15日 下午8:57:43
 */
public class Field {
	String remarks;
	String javaType;
	String name;
	String column;
	
	public Field(String remarks, String javaType, String name, String column) {
		super();
		this.remarks = remarks;
		this.javaType = javaType;
		this.name = name;
		this.column = column;
	}
	public String getRemarks() {
		return remarks;
	}
	public String getJavaType() {
		return javaType;
	}
	public String getName() {
		return name;
	}
	public String getColumn() {
		return column;
	}
}
