import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 类说明：
 * 
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年9月13日 下午8:59:09
 */
public class TestServer {

	static void startSocket(final Socket socket) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//3、获取输入流，并读取客户端信息
					InputStream is = socket.getInputStream();
					InputStreamReader isr =new InputStreamReader(is);
					BufferedReader br =new BufferedReader(isr);
					String info =null;
					while((info=br.readLine())!=null){
						System.out.println("我是服务器，客户端说："+info);
					}
					socket.shutdownInput();//关闭输入流
					//4、获取输出流，响应客户端的请求
					OutputStream os = socket.getOutputStream();
					PrintWriter pw = new PrintWriter(os);
					pw.write("欢迎您！");
					pw.flush();

					//5、关闭资源
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
		ServerSocket serverSocket =new ServerSocket(10086);//1024-65535的某个端口
		while(true) {
			Socket socket = serverSocket.accept();
			startSocket(socket);
		}
	}
}
