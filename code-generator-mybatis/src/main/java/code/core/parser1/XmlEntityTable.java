package code.core.parser1;

import java.util.HashMap;
import java.util.Map;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月3日 下午8:15:19
 */
public class XmlEntityTable {
	String tableName;
	Map<String,String> props = new HashMap<String, String>();
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setProperty(String name, String value) {
		props.put(name, value);
	}
	
	public String getProperty(String name) {
		return props.get(name);
	}
}
