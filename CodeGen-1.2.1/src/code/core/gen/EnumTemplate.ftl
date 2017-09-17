package ${packageName};

/**
 * Created by CodeGen v1.2.1 at date:${date?datetime} Powered by Xiaowei.Jiang
 * ${description}
 */
public enum ${enumName} {  
<#list enumParas as para> 
<#if paraNameType == 'number'>
<#elseif paraNameType == 'String' && paraValueType == 'String'>
	${para.paraEn}("${para.paraVal}","${para.paraName}")<#if para_has_next>,</#if><#if !para_has_next>;</#if>
</#if>
</#list>
    
    private ${paraNameType} name;  
    private ${paraValueType} value;  
    
    private ${enumName}(${paraValueType} value,${paraNameType} name) {  
        this.value = value;  
        this.name = name;  
    }  
    
    public static ${enumName} getByName(${paraNameType} name) {  
        for (${enumName} c : ${enumName}.values()) {  
            if (c.getName().equals(name)) {  
                return c;  
            }  
        }  
        return null;  
    } 
    
    public static ${enumName} getByValue(${paraValueType} value) {  
        for (${enumName} c : ${enumName}.values()) {  
            if (c.getValue().equals(value)) {  
                return c;  
            }  
        }  
        return null;  
    } 
    
    public ${paraNameType} getName() {  
        return this.name;  
    }  
    
    public ${paraValueType} getValue() {
    	return this.value;
    }
}  