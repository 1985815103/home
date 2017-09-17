package code.core.gen;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import code.core.BeanUtils;
import code.core.jdbc.TableMetalColumn;
import code.core.parse0.ResourceContainer;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月4日 下午11:50:30
 */
@Component
@Scope("prototype")
public class JavaBeanGenerator extends AbstractJavaGenerator{
	private static Logger logger = Logger.getLogger(JavaBeanGenerator.class);

	@Override
	public Map<String, Object> getContents() {
		Map<String, Object> contents = new HashMap<>();
		List<Field> fields = new ArrayList<>();
		
		for(TableMetalColumn col : context.getTableMeta().getColumns()) {
			String remarks = col.getColDesc();
			String javaType = ResourceContainer.getFieldJavaType(col.getColumnType().toLowerCase());
			String property = BeanUtils.fomartProperty(col.getColumnName());
			
			if(javaType == null) {
				logger.error("fieldType: " + col.getColumnType() + "->" + javaType + " can't build mapping!!!");
			}
			fields.add(new Field(remarks, javaType, property, null));
		}

		contents.put("date", new Date());
		contents.put("packageName", getPackageName());
		contents.put("tableName", context.getxTable().getTableName());
		contents.put("description",  context.getTableMeta().getTableDesc());
		contents.put("className", getGeneratorSimpleClassName());
		contents.put("fields", fields);
		
		return contents;
	}
}
