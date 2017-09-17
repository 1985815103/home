import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年9月13日 下午8:10:46
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
	 * 连接断开后，线程阻塞；实现同步等待
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
