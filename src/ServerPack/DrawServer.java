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
			server = new ServerSocket(PORT2);//����һ��ServerSocket�ڶ˿�PORT�����ͻ�����(��ͼ)
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
				Connect2 con = new Connect2(fromClient);
			}
		} catch (Exception e) {
			System.out.println("�޷������ͻ�����" + e);
		}
	}
}
//��ͼ�˿�
class Connect2 {
	ObjectOutputStream streamToClient;
	BufferedReader streamFromClient;

	static Vector<String> vector;// �װ���Ϣ
	String message = " ";
	static String string=" 2:510:50:510:50:0:0:0:1.0:o";
	static {
		vector = new Vector<String>(1, 1);//��ʼ����Ϊ1��ÿ������1
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
			if ((msg.equals("renew"))) // ���»�����Ϣ
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

