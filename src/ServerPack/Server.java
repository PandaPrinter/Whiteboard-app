package ServerPack;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable{  //聊天端口
	ServerSocket server;
	Socket fromClient;
	Thread serverThread;
	public static final int PORT1 = 8080;

	public Server() {
		try {
			server = new ServerSocket(PORT1);//创建一个ServerSocket在端口PORT监听客户请求(聊天)
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
				Connect con = new Connect(fromClient);
			}
		} catch (Exception e) {
			System.out.println("无法监听客户请求" + e);
		}
	}

	public static void main(String args[]) throws IOException, InterruptedException {
		new Server();
		new DrawServer();
		System.out.println("服务器已经启动");
	}
}
         

class Connect {
	ObjectOutputStream streamToClient;
	BufferedReader streamFromClient;

	static Vector<String> vector;// 白板信息
	String message = " ";
	static String string="欢迎来到网络白板(*^__^*)";

	static {
		vector = new Vector<String>(1, 1);//初始容量为1，每次扩充1
		vector.addElement((String) string);
	}
	public Connect(Socket inFromClient) {

		String msg = "";
		try {
			
			streamFromClient = new BufferedReader(new InputStreamReader(
					inFromClient.getInputStream()));
			streamToClient = new ObjectOutputStream(
				 	inFromClient.getOutputStream());
			msg = streamFromClient.readLine();

			if ((msg.equals("renew"))) // 更新信息
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


