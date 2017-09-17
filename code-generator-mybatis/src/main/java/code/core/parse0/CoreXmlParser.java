package code.core.parse0;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月30日 上午4:13:40
 */
public class CoreXmlParser {
	public static void main(String[] args) {
		parse();
	}
	
	public static void parse() {
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("code/core/code-core.xml");
			SAXReader reader = new SAXReader();
			Document document = null;
			document = reader.read(is);
			Element rootEl = document.getRootElement();
			Element fieldTypesElt = rootEl.element("fieldTypes");
			Element supportElt = rootEl.element("supports");
			@SuppressWarnings("unchecked")
			List<Element> gensElts = rootEl.elements("gens");
			
			parseFieldTypes(fieldTypesElt);
			parseSupport(supportElt);
			parseGens(gensElts);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	private static void parseFieldTypes(Element fieldTypesElt) {
		@SuppressWarnings("unchecked")
		List<Element> fieldTypeEls = fieldTypesElt.elements("fieldType");
		for (Element element : fieldTypeEls) {
			ResourceContainer.fieldTypes.put(element.attributeValue("jdbcColumnType"), element.attributeValue("javaType"));
		}
	}
	
	private static void parseSupport(Element supportElt) {
		@SuppressWarnings("unchecked")
		List<Element> supportElts = supportElt.elements("support");
		for (Element element : supportElts) {
			ResourceContainer.supports.add(element.getTextTrim());
		}
	}
	
	private static void parseGens(List<Element> gensElts) {
		for (Element gensElt : gensElts) {
			@SuppressWarnings("unchecked")
			List<Element> genElts = gensElt.elements("gen");
			String support = gensElt.attributeValue("support");
			if(support != null && !ResourceContainer.isSupportGenerator(support)) {
				continue;
			}
			for (Element e : genElts) {
				CodeGeneratorConfig gen = new CodeGeneratorConfig();
				gen.name = e.attributeValue("name");
				gen.clazz = e.attributeValue("class");
				gen.template = e.attributeValue("template");
				gen.support = support;
				//props
				@SuppressWarnings("unchecked")
				List<Element> genPropElts = e.elements("property");
				for (Element genPropElt : genPropElts) {
					gen.props.put(genPropElt.attributeValue("name"), genPropElt.getTextTrim());
				}
				
				ResourceContainer.gens.put(gen.name, gen);
			}
		}
	}
}
