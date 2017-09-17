
/**
 * ��˵����
 * @author Xiaowe.Jiang
 * @version ����ʱ�䣺2017��9��13�� ����8:10:27
 */
public class MPSEvent {
	private MPSEventType eventType;
	private MPSEventSource eventSource;
	
	public static enum MPSEventType {
		DISCONNECT,
		CONNECT,
		DEFAULT;
	}
	
	public MPSEvent(MPSEventSource eventSource) {
		this(MPSEventType.DEFAULT, eventSource);
	}
	
	public MPSEvent(MPSEventType eventType, MPSEventSource eventSource) {
		this.eventType = eventType;
		this.eventSource = eventSource;
	}

	public MPSEventType getEventType() {
		return eventType;
	}

	public void setEventType(MPSEventType eventType) {
		this.eventType = eventType;
	}

	public MPSEventSource getEventSource() {
		return eventSource;
	}

	public void setEventSource(MPSEventSource eventSource) {
		this.eventSource = eventSource;
	}
}
