package code.core.parser1;

import java.util.HashMap;
import java.util.Map;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月5日 上午12:14:50
 */
public class XmlEntityGenerator {
	String name;
	Map<String,String> props = new HashMap<String, String>();

	public String getName() {
		return name;
	}
	public Map<String, String> getProps() {
		return props;
	}
}
