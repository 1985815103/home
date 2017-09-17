import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * ��˵����
 * @author Xiaowe.Jiang
 * @version ����ʱ�䣺2017��9��13�� ����8:10:46
 */
public class MPSEventSource {
	private Set<MPSEventListener> listeners = new HashSet<MPSEventListener>();
	private Object object = new Object();
	private boolean sync = false;
	
	private MPSEventSource() {};
	
	public static MPSEventSource me() {
		return new MPSEventSource();
	}
	
	/**
	 * ���ӶϿ����߳�������ʵ��ͬ���ȴ�
	 */
	public void blockUntilConnectOk() {
		sync = true;
		asynRebuildConnect();
	}
	
	public void addEventListener(MPSEventListener e) {
		listeners.add(e);
	}
	
	public void asynRebuildConnect() {
		if(sync) {
			synchronized (object) {
				try {
					MPSHeartbeat.reconnect(this);
					object.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	void connectSucc() {
		trigger(MPSEvent.MPSEventType.CONNECT);
		if(sync) {
			synchronized (object) {
				object.notify();
			}
		}
	}
	
	private void trigger(MPSEvent.MPSEventType eventType) {
		Iterator<MPSEventListener> iter = listeners.iterator();
		while(iter.hasNext()) {
			MPSEventListener e = iter.next();
			e.process(new MPSEvent(eventType, this));
		}
	}
}
