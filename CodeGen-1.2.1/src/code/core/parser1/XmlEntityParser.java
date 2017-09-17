package code.core.parser1;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月2日 下午10:08:28
 */
@Component
public class XmlEntityParser {
	
	@SuppressWarnings("unchecked")
	public XmlEntityRoot parse() {
		try {
			//InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("odeGen.xml");
			InputStream is = new FileInputStream(new File("codeGen.xml"));
			SAXReader reader = new SAXReader();
			Document document = null;
			document = reader.read(is);
			Element rootEl = document.getRootElement();
			
			XmlEntityRoot em = new XmlEntityRoot();
			List<Element> entityEls = rootEl.elements("table");
			List<Element> codeGenEls = rootEl.element("generators").elements();
			
			//系统属性
			em.outputHomeDir = rootEl.selectSingleNode("property[@name='targetProject']").getText();
			em.outputDefaultEncoding = rootEl.selectSingleNode("property[@name='outputDefaultEncoding']").getText();
			
			//<entity>
			for (Element element : entityEls) {
				if(!checkXmlEntityTable(element)) continue;
				boolean override = !"false".equals(element.attributeValue("overrideWhenExist"));
				XmlEntityTable ce = new XmlEntityTable();
				ce.props.put("module", element.attributeValue("module"));
				ce.props.put("override", String.valueOf(override));
				ce.props.put("primaryKey", element.attributeValue("primaryKey"));
				ce.props.put("crud", element.attributeValue("crud"));
				ce.tableName = element.attributeValue("tableName").toLowerCase();
				em.entities.put(ce.tableName, ce);
			}
			
			//<codeGen>
			for (Element element : codeGenEls) {
				XmlEntityGenerator gen = new XmlEntityGenerator();
				List<Element> paraElts = element.elements("property");
				gen.name = element.getName();
				for (Element paraElt : paraElts) {
					gen.props.put(paraElt.attributeValue("name"), paraElt.getTextTrim());
				}
				em.codeGens.put(gen.name, gen);
			}
			
			return em;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean checkXmlEntityTable(Element element) {
		boolean result = true;
		String tableName = element.attributeValue("tableName");
		String primaryKey = element.attributeValue("primaryKey");
		if(StringUtils.isEmpty(tableName)) {
			result = false;
			System.err.println("please check <entity> configuration, tableName can't be empty:" + element);
		}
		if(result && StringUtils.isEmpty(primaryKey)) {
			result = false;
			System.err.println("please check <entity> configuration, primaryKey can't be empty:" + element);
		}
		
		return result;
	}
}
