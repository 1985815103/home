import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年9月13日 下午9:03:52
 */
public class TestClient {
	public static void send() throws UnknownHostException, IOException, InterruptedException {
		//客户端
		//1、创建客户端Socket，指定服务器地址和端口
		Socket socket =new Socket("localhost",10086);
		//2、获取输出流，向服务器端发送信息
		OutputStream os = socket.getOutputStream();//字节输出流
		DataOutputStream pw =new DataOutputStream(os);//将输出流包装成打印流
		pw.write(new byte[]{0x11,0x13});
		socket.close();
	}
	
	
	public static void connect() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
					while(true) {
						try {
							send();
							Thread.sleep(2*1000);
							System.out.println(Thread.currentThread().getId() + ":---------------run");
						} catch (IOException e) {
							MPSEventSource.me().blockUntilConnectOk();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				
			}
		}).start();;
	}
	
	public static void main(String[] args) {
		MPSHeartbeat.startMoniter("localhost", 10086);
		for(int i=0; i<5; i++) {
			connect();
		}
	}
}
