import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年9月13日 下午8:35:23
 */
public class MPSHeartbeat {
	private static List<MPSEventSource> eventSources = new ArrayList<MPSEventSource>();
	private static Boolean connecting = false;
	private final static Lock lock = new ReentrantLock();
	private final static Condition lockCond = lock.newCondition();
	private static Thread reconnectThread;
	
	public static synchronized void startMoniter(final String host, final int port) {
		if(reconnectThread != null) return;
		
		reconnectThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						lock.lock();
						lockCond.await();
						System.out.println("Start reconnect...");
						reconnect(host, port);
						System.out.println("Reconnect success...");
					} catch (InterruptedException e) {
					} finally {
						lock.unlock();
					}
				}
			}
		});
		reconnectThread.start();
	}

	private static void reconnect(String host, int port) {
		try {
			Thread.sleep(3*1000);
			Socket socket = new Socket(host, port);
			socket.close();
			
			for(MPSEventSource eventResource : eventSources) {
				eventResource.connectSucc();
			}
			connecting = false;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			reconnect(host, port);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	static synchronized void reconnect(MPSEventSource mpsEventSource) throws InterruptedException {
		if(reconnectThread == null) {
			throw new RuntimeException("Init error, please init with MPSSocketMoniter.startMoniter(host, port)!");
		}

		eventSources.add(mpsEventSource);
		
		if(!connecting && lock.tryLock()) {
			connecting = true;
			lockCond.signal();
			lock.unlock();
		}
	}
}
