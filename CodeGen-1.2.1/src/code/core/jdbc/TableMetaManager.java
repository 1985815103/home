package code.core.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *类说明：
 *@author Xiaowei.Jiang
 */
@Component
public class TableMetaManager {
	private Map<String,TableMeta> metas = new HashMap<>();
	
	@Autowired
	private DataSource ds;
	
	public TableMeta getTableMeta(String tableName) {
		if(metas.containsKey(tableName)) return metas.get(tableName);
		Connection conn = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			conn = ds.getConnection();
			String schema = null;
			try {
				schema = conn.getSchema();
			} catch (Throwable e) {
				// TODO: handle exception
			}
			rs = conn.getMetaData().getColumns(null, schema, tableName, null);
			rs2= conn.getMetaData().getTables(null, schema, tableName,new String[] { "TABLE" });
			
			TableMeta table = new TableMeta();
			table.setTableName(tableName);
			
			//表字段
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME").toLowerCase();
				String colDesc = rs.getString("REMARKS");
				String columnType = rs.getString("TYPE_NAME");
				TableMetalColumn column = new TableMetalColumn(colDesc, columnName,columnType);
				table.getColumns().add(column);
			}
			//表描述 mysql:useInformationSchema=true
			if(rs2.next()) {
				table.setTableDesc(rs2.getString("REMARKS"));
			}
			
			metas.put(tableName, table);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				rs2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return metas.get(tableName);
	}
}
