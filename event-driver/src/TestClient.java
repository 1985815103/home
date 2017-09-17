import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * ��˵����
 * @author Xiaowe.Jiang
 * @version ����ʱ�䣺2017��9��13�� ����9:03:52
 */
public class TestClient {
	public static void send() throws UnknownHostException, IOException, InterruptedException {
		//�ͻ���
		//1�������ͻ���Socket��ָ����������ַ�Ͷ˿�
		Socket socket =new Socket("localhost",10086);
		//2����ȡ���������������˷�����Ϣ
		OutputStream os = socket.getOutputStream();//�ֽ������
		DataOutputStream pw =new DataOutputStream(os);//���������װ�ɴ�ӡ��
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
