package code.core.jdbc;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 */
public class TableMeta {
	private String tableName;
	private List<TableMetalColumn> columns = new ArrayList<TableMetalColumn>();
	private String tableDesc;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<TableMetalColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<TableMetalColumn> columns) {
		this.columns = columns;
	}
	
	public TableMetalColumn getMetalColumn(String columnName) {
		for (TableMetalColumn tableMetalColumn : columns) {
			if(tableMetalColumn.getColumnName().equals(columnName)) {
				return tableMetalColumn;
			}
		}
		
		return null;
	}
	
	public String getTableDesc() {
		return tableDesc;
	}
	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}
}
