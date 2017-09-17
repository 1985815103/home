package code.mybatis.gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import code.core.BeanUtils;
import code.core.gen.AbstractJavaGenerator;
import code.core.gen.Field;
import code.core.jdbc.TableMetalColumn;
import code.core.parse0.Generator;
import code.core.parse0.ResourceContainer;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月4日 下午11:50:30
 */
@Component
@Scope("prototype")
public class XmlMapperGenerator extends AbstractJavaGenerator{
	public static final int insert = 128;
	public static final int get = 64;
	public static final int list = 32;
	public static final int listMap = 16;
	public static final int delete = 8;
	public static final int deleteByMap = 4;
	public static final int update = 2;
	public static final int updateByMap = 1;
	
	private String genInsertSqlTemplate(int stepLen, int mode) {
		List<TableMetalColumn> cMetas = context.getTableMeta().getColumns();
		int i = 0;
		Map<Integer,Integer> maxLens = new HashMap<Integer, Integer>();
		List<String> fields = new ArrayList<String>();
		int maxLen;
		
		for (TableMetalColumn cMeta : cMetas) {
			String field0 = cMeta.getColumnName();
			String filed1 = ("#{" + BeanUtils.fomartProperty(cMeta.getColumnName()) + "}");
			String field = (mode == 0)? field0 : filed1; 
			maxLen = field0.length() > filed1.length() ? field0.length() : filed1.length();
			
			fields.add(field);
			if(maxLens.get(i%stepLen) == null) {
				maxLens.put(i%stepLen, maxLen);
			}
			else if(maxLens.get(i%stepLen) < maxLen) {
				maxLens.put(i%stepLen, maxLen); 
			}
			i++;
		}
		
		return fomartFieldLens(maxLens, fields, stepLen,"\t\t\t\t");
	}
	
	private String genUpdateSqlTemplate(int stepLen) {
		List<TableMetalColumn> cMetas = context.getTableMeta().getColumns();
		int i = 0;
		Map<Integer,Integer> maxLens = new HashMap<Integer, Integer>();
		Map<Integer,Integer> maxLens0 = new HashMap<Integer, Integer>();
		Map<Integer,Integer> maxLens1 = new HashMap<Integer, Integer>();
		List<String> fields = new ArrayList<String>();
		List<String> fields0 = new ArrayList<String>();
		List<String> fields1 = new ArrayList<String>();
		String primaryKey = context.getxTable().getProperty("primaryKey");

		for (TableMetalColumn cMeta : cMetas) {
			if(cMeta.getColumnName().equals(primaryKey)) continue;
			String field0 = ("#{" + BeanUtils.fomartProperty(cMeta.getColumnName()) + "}");
			if(maxLens0.get(i%stepLen) == null) {
				maxLens0.put(i%stepLen, field0.length());
			}
			else if(maxLens0.get(i%stepLen) < field0.length()) {
				maxLens0.put(i%stepLen, field0.length()); 
			}
			i++;
			fields0.add(field0);
		}
		
		i=0;
		for (TableMetalColumn cMeta : cMetas) {
			if(cMeta.getColumnName().equals(primaryKey)) continue;
			String field0 = cMeta.getColumnName();
			if(maxLens1.get(i%stepLen) == null) {
				maxLens1.put(i%stepLen, field0.length());
			}
			else if(maxLens1.get(i%stepLen) < field0.length()) {
				maxLens1.put(i%stepLen, field0.length()); 
			}
			i++;
			fields1.add(field0);
		}
		
		for (i=0; i<fields0.size(); i++) {
			String field0 = fields0.get(i);
			while(field0.length() < maxLens0.get(i%stepLen)) {
				field0 += " ";
			}
			String field1 = fields1.get(i);
			while(field1.length() < maxLens1.get(i%stepLen)) {
				field1 += " ";
			}
			String field = field1 + " = " + field0;
			maxLens.put(i%stepLen, field.length());
			fields.add(field);
		}
		
		return fomartFieldLens(maxLens, fields, stepLen,"\t\t\t\t");
	}
	
	private String fomartFieldLens(Map<Integer,Integer> maxLens, List<String> fields, int stepLen, String tab) {
		String field = null;
		StringBuffer sql = new StringBuffer();
		
		for (int i=0; i<fields.size(); i++) { 
			field = fields.get(i);
			if(i>0) {
				sql.append(",");
			}
			if(i>0 && i%stepLen == 0) {
				sql.append("\r\n").append(tab);
			}
			while(field.length() < maxLens.get(i%stepLen)) {
				field += " ";
			}
			sql.append(field);
		}
		
		return sql.toString();
	}
	
	@Override
	public Map<String, Object> getContents() {
		List<Field> fields = new ArrayList<>();
		for(TableMetalColumn col : context.getTableMeta().getColumns()) {
			String remarks = col.getColDesc();
			String javaType = ResourceContainer.getFieldJavaType(col.getColumnType().toLowerCase());
			String property = BeanUtils.fomartProperty(col.getColumnName());
			
			fields.add(new Field(remarks, javaType, property, col.getColumnName()));
		}
		String insertStepLength = codeGenerator.getProperty("insertStepLength");
		String updateStepLength = codeGenerator.getProperty("updateStepLength");
		int insertStepLen = insertStepLength == null ? 5 : Integer.parseInt(insertStepLength);
		int updateStepLen = updateStepLength == null ? 3 : Integer.parseInt(updateStepLength);
		
		AbstractJavaGenerator beanClass = context.getGenerator(Generator.BeanGenerator.toString());
		
		Map<String, Object> contents = new HashMap<>();
		contents.put("namespace", getGeneratorClassName());
		contents.put("fullBeanName", beanClass.getGeneratorClassName());
		contents.put("fields", fields);
		contents.put("updateSqlTemplate", genUpdateSqlTemplate(updateStepLen));
		contents.put("insertSqlTemplate0", genInsertSqlTemplate(insertStepLen,0));
		contents.put("insertSqlTemplate1", genInsertSqlTemplate(insertStepLen,1));
		contents.put("tableName", context.getxTable().getTableName());
		
		String primaryKey = context.getxTable().getProperty("primaryKey");
		if(primaryKey != null) {
			contents.put("primaryKey", primaryKey);
			contents.put("primaryProperty", BeanUtils.fomartProperty(primaryKey));
		}
		
		//模块生成控制
		String curdExpr = context.getxTable().getProperty("crud");
		boolean insert_enable = true;
		boolean get_enable = true;
		boolean list_enable = true;
		boolean listMap_enable = true;
		boolean delete_enable = true;
		boolean deleteByMap_enable = true;
		boolean update_enable = true;
		boolean updateByMap_enable = true;
		
		if(curdExpr != null) {
			int crud = Integer.parseInt(curdExpr, 2);
			insert_enable = (insert & crud) == insert;
			get_enable = (get & crud) == get;
			list_enable = (list & crud) == list;
			listMap_enable = (listMap & crud) == listMap;
			delete_enable = (delete & crud) == delete;
			deleteByMap_enable = (deleteByMap & crud) == deleteByMap;
			update_enable = (update & crud) == update;
			updateByMap_enable = (updateByMap & crud) == updateByMap;
		}
		contents.put("insert_enable",insert_enable);
		contents.put("get_enable",get_enable);
		contents.put("list_enable",list_enable);
		contents.put("listMap_enable",listMap_enable);
		contents.put("delete_enable",delete_enable);
		contents.put("deleteByMap_enable",deleteByMap_enable);
		contents.put("update_enable",update_enable);
		contents.put("updateByMap_enable",updateByMap_enable);
		
		if(get_enable || list_enable || listMap_enable) {
			int i = 0;
			int total = fields.size();
			int step = 3*updateStepLen;
			if(total > step && total - step <= 2) {
				step = step + 2;
			}
			StringBuffer temp = new StringBuffer();
			for(Field field : fields) {
				if(i > 0) temp.append(",");
				if(++i % step == 0) {
					temp.append("\r\n\t\t");
				}
				temp.append(field.getColumn());
			}
			contents.put("Base_Column_List", temp.toString());
		}
		
		return contents;
	}
}
