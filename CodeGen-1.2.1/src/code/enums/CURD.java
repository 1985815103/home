package code.enums;
public enum CURD {
	Insert(128),
	Get(64),
	List(32),
	ListByMap(16),
	Delete(8),
	DeleteByMap(4),
	Update(2),
	UpdateByMap(1);
	
	int value;
	
	private CURD(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}