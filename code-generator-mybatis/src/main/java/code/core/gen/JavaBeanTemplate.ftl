package ${packageName};
/**
 * Created by CodeGen v1.2.1 at date:${date?datetime} Powered by Xiaowei.Jiang
 * 
 * TableName : ${tableName} ${description}
 */
public class ${className} {
<#list fields as field> 
	//${field.remarks}
	private ${field.javaType} ${field.name};
</#list>
    
<#list fields as field> 
	/**
	 * ${field.remarks}
	 */
	public ${field.javaType} get${field.name?cap_first}() {
		return this.${field.name};
	}
	
	public ${className} set${field.name?cap_first}(${field.javaType} ${field.name}) {
		this.${field.name} = ${field.name};
		return this;
	}
	
</#list>
}