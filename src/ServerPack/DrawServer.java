package ServerPack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class DrawServer implements Runnable{
	ServerSocket server;
	Socket fromClient;
	Thread serverThread;
	public static final int PORT2 = 9090;

	public DrawServer() {
		try {
			server = new ServerSocket(PORT2);//创建一个ServerSocket在端口PORT监听客户请求(绘图)
			serverThread = new Thread(this);
			serverThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (true) {
				// 监听客户端的请求
				fromClient = server.accept();
				//使用accept()阻塞等待客户请求
				Connect2 con = new Connect2(fromClient);
			}
		} catch (Exception e) {
			System.out.println("无法监听客户请求" + e);
		}
	}
}
//绘图端口
class Connect2 {
	ObjectOutputStream streamToClient;
	BufferedReader streamFromClient;

	static Vector<String> vector;// 白板信息
	String message = " ";
	static String string=" 2:510:50:510:50:0:0:0:1.0:o";
	static {
		vector = new Vector<String>(1, 1);//初始容量为1，每次扩充1
		vector.addElement((String) string);
	}
	public Connect2(Socket inFromClient) {

		String msg = "";
		try {
			
			streamFromClient = new BufferedReader(new InputStreamReader(
					inFromClient.getInputStream()));
			streamToClient = new ObjectOutputStream(
				 	inFromClient.getOutputStream());
			msg = streamFromClient.readLine();
			if ((msg.equals("renew"))) // 更新画板信息
			{
				streamToClient.writeObject(vector);
			}else {
				message = message + msg;
				vector.addElement((String) message);
				streamToClient.writeObject(vector);
			}
		}
		catch (Exception e) {
			System.out.println("当前没有任何用户在线 。。。。" );
		} finally 
		{
			try {
				inFromClient.close();
			} catch (IOException e) {
			}
		}
	}
}

