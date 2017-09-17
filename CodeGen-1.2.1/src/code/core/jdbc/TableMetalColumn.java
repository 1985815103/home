package code.core.jdbc;

import code.core.BeanUtils;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 */
public class TableMetalColumn {
	private String colDesc;
	
	private String columnName;
	
	private String columnType;

	private String property;
	
	public TableMetalColumn(String colDesc, String columnName, String columnType) {
		super();
		this.colDesc = colDesc;
		this.columnName = columnName;
		this.columnType = columnType.toLowerCase();
		this.property = BeanUtils.fomartProperty(columnName);
	}

	public String getColDesc() {
		return colDesc;
	}

	public void setColDesc(String colDesc) {
		this.colDesc = colDesc;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType.toLowerCase();
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
}
