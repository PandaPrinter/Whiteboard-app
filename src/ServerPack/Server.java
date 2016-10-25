package ServerPack;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable{  //����˿�
	ServerSocket server;
	Socket fromClient;
	Thread serverThread;
	public static final int PORT1 = 8080;

	public Server() {
		try {
			server = new ServerSocket(PORT1);//����һ��ServerSocket�ڶ˿�PORT�����ͻ�����(����)
			serverThread = new Thread(this);
			serverThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (true) {
				// �����ͻ��˵�����
				fromClient = server.accept();
				//ʹ��accept()�����ȴ��ͻ�����
				Connect con = new Connect(fromClient);
			}
		} catch (Exception e) {
			System.out.println("�޷������ͻ�����" + e);
		}
	}

	public static void main(String args[]) throws IOException, InterruptedException {
		new Server();
		new DrawServer();
		System.out.println("�������Ѿ�����");
	}
}
         

class Connect {
	ObjectOutputStream streamToClient;
	BufferedReader streamFromClient;

	static Vector<String> vector;// �װ���Ϣ
	String message = " ";
	static String string="��ӭ��������װ�(*^__^*)";

	static {
		vector = new Vector<String>(1, 1);//��ʼ����Ϊ1��ÿ������1
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

			if ((msg.equals("renew"))) // ������Ϣ
			{
				streamToClient.writeObject(vector);
			}else {
				message = message + msg;
				vector.addElement((String) message);
				streamToClient.writeObject(vector);
			}
		}
		catch (Exception e) {
			System.out.println("��ǰû���κ��û����� ��������" );
		} finally 
		{
			try {
				inFromClient.close();
			} catch (IOException e) {
			}
		}
	}
}


