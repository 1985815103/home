package code.core.gen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import code.core.BeanUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年8月17日 上午12:36:46
 */
@Component
public class EnumGenerator extends AbstractJavaGenerator {
	private Logger logger = Logger.getLogger(EnumGenerator.class);
	
	@Autowired
	private DataSource ds;
	
	private EnumGroup group;
	
	public String getGeneratorSimpleClassName() {
		return BeanUtils.getSimpleClassName(group.getGroupEn());
	}
	
	@Override
	public Map<String, Object> getContents() {
		Map<String, Object> contents = new HashMap<>();
		contents.put("date", new Date());
		contents.put("packageName", getPackageName());
		contents.put("enumName", getGeneratorSimpleClassName());
		contents.put("enumParas", group.getParas());
		contents.put("paraNameType", codeGenerator.getExtProperty("paraNameType"));
		contents.put("paraValueType", codeGenerator.getExtProperty("paraValueType"));
		contents.put("description",  group.getGroupDesc());

		return contents;
	}
	
	@Override
	public void process() {
		Collection<EnumGroup> groups = get();
		Configuration config = context.getFreemakerConfig(); 
		try {
			Template template = config.getTemplate(context.getCodeGenerator().getTemplate());
			Iterator<EnumGroup> iter = groups.iterator();
			
			while(iter.hasNext()) {
				this.group = iter.next();
				String outpathFilePath = getOutpathFilePath();
				File newFile = new File(outpathFilePath);
				String option = "Create";
				if(!newFile.getParentFile().exists()) {
					newFile.getParentFile().mkdirs();
				}
				if(newFile.exists()) {
					option = "Update";
					if(!"true".equals(codeGenerator.getProperty("overrideWhenExist"))) continue;
				}
				
				OutputStream fos = new FileOutputStream(new File(outpathFilePath));
				Writer out = new OutputStreamWriter(fos,context.getxRoot().getOutputDefaultEncoding());
				template.process(getContents(), out);
				fos.close();
				out.close();
				
				String info = String.format("%s%s(%s)", getGeneratorClassName(),fileType, option);
				System.out.println(info);
			}
		} catch (Throwable e) {
			logger.error(e);
		}
	}
	
	private Collection<EnumGroup> get() {
		String sql = codeGenerator.getExtProperty("sql");
		Map<String,EnumGroup> groups = new HashMap<String,EnumGroup>();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String groupEn = rs.getString("group_en");
				EnumPara para = new EnumPara();
				para.setParaEn(rs.getString("para_en"));
				para.setParaName(rs.getString("para_name"));
				para.setParaVal(rs.getString("para_val"));
				
				try {
					Integer.parseInt(para.getParaEn());
					para.setParaEn(groupEn + "_" + para.getParaEn());
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				EnumGroup group = groups.get(groupEn);
				if(group == null) {
					group = new EnumGroup();
					group.setGroupDesc(rs.getString("group_desc"));
					group.setGroupEn(groupEn);
					groups.put(groupEn, group);
				}
				group.getParas().add(para);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return groups.values();
	}
	
	public static class EnumGroup {
		List<EnumPara> paras = new ArrayList<EnumPara>();
		String groupEn;
		String groupDesc;
		
		public List<EnumPara> getParas() {
			return paras;
		}
		public void setParas(List<EnumPara> paras) {
			this.paras = paras;
		}
		public String getGroupEn() {
			return groupEn;
		}
		public void setGroupEn(String groupEn) {
			this.groupEn = groupEn;
		}
		public String getGroupDesc() {
			return groupDesc;
		}
		public void setGroupDesc(String groupDesc) {
			this.groupDesc = groupDesc;
		}
	}

	public static class EnumPara {
		String paraEn;
		Object paraName;
		Object paraVal;
		
		public String getParaEn() {
			return paraEn;
		}
		public void setParaEn(String paraEn) {
			this.paraEn = paraEn;
		}
		public Object getParaName() {
			return paraName;
		}
		public void setParaName(Object paraName) {
			this.paraName = paraName;
		}
		public Object getParaVal() {
			return paraVal;
		}
		public void setParaVal(Object paraVal) {
			this.paraVal = paraVal;
		}
	}
}

