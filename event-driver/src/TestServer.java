import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ��˵����
 * 
 * @author Xiaowe.Jiang
 * @version ����ʱ�䣺2017��9��13�� ����8:59:09
 */
public class TestServer {

	static void startSocket(final Socket socket) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//3����ȡ������������ȡ�ͻ�����Ϣ
					InputStream is = socket.getInputStream();
					InputStreamReader isr =new InputStreamReader(is);
					BufferedReader br =new BufferedReader(isr);
					String info =null;
					while((info=br.readLine())!=null){
						System.out.println("���Ƿ��������ͻ���˵��"+info);
					}
					socket.shutdownInput();//�ر�������
					//4����ȡ���������Ӧ�ͻ��˵�����
					OutputStream os = socket.getOutputStream();
					PrintWriter pw = new PrintWriter(os);
					pw.write("��ӭ����");
					pw.flush();

					//5���ر���Դ
					pw.close();
					os.close();
					br.close();
					isr.close();
					is.close();
					socket.close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		}).start();
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		@SuppressWarnings("resource")
		ServerSocket serverSocket =new ServerSocket(10086);//1024-65535��ĳ���˿�
		while(true) {
			Socket socket = serverSocket.accept();
			startSocket(socket);
		}
	}
}
