package ClientPack;

import java.io.*;
import java.net.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.util.*;

import javax.swing.Timer;


public class Client extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -849973533023624608L;
	String usr_name;//�û���
	String IPadr = "localhost";//����
	public static final int PORT1 = 8080;//��Ϣ�˿�
	public static final int PORT2 = 9090;//��ͼ�˿�
 
	final JTextPane txt_WhiteBoard = new JTextPane();// ���������Ϣ����
	final JTextPane txt_UserNews = new JTextPane();// �û�������Ϣ����

	JButton Send;// ������Ϣ��ť
	JButton Logout;// �˳���ť
	JButton pencil;//��ʻ�
	JButton line;//ֱ��
	JButton circle;//��Բ��Բ
	JButton fillcircle;//ʵ����Բ
	JButton rect;//����
	JButton fillrect;//ʵ�ľ���
	JButton rubber;//��Ƥ��
	JButton word;//д��
	JButton colorButton;//�ı���ɫ
	JButton withe;  //���ʴ�ϸ
	JButton save;  //���浱ǰͼƬ
	JButton load;  //��ȡ�û���ͼƬ
	
	JLabel ChatRoom;// ����װ�
	JScrollPane userNews;// �û�д��Ϣ�������
	JScrollPane whiteBoard;// ��������Ϣ������
	
	String message; //�û���Ҫ���͵�������Ϣ
	JPanel panel = new JPanel();
	MyPanel paint =new MyPanel();//ר�Ż�ͼ�õ�Panel
	FileOperate fileClass=null;
	Timer t = new Timer(0, new GetNews());//�Զ�������Ϣ�߳�
	Timer t2 = new Timer(0, new GetNewPaint());//�Զ����»�ͼ���߳�
	public Client(String nm) {
		usr_name = nm;
		this.setTitle("�û�:" + usr_name);
		panel.setLayout(null);
		paint.setBounds(510, 50, 480, 400);
		paint.setBackground(Color.WHITE);
		panel.add(paint);//��ͼPanel�����ڵ�һ��Panel��ָ��λ����
		panel.setBackground(Color.YELLOW);
		try {
			fileClass=new FileOperate(this, paint,usr_name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		String name = "��ӭ��������װ�������~~������" + c.get(Calendar.YEAR)
				+ "��" + c.get(Calendar.MONTH) + 1 + "��" + c.get(Calendar.DATE)
				+ "��";
		ChatRoom = new JLabel(name, SwingConstants.LEFT);
		panel.add(ChatRoom);
		ChatRoom.setBounds(100, 0, 500, 50);

		txt_WhiteBoard.setEditable(false);
		whiteBoard = new JScrollPane(txt_WhiteBoard,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.add(whiteBoard);
		whiteBoard.setBounds(50, 50, 450, 300);

		userNews = new JScrollPane(txt_UserNews,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.add(userNews);
		userNews.setBounds(50, 375, 250, 100);

		Send = new JButton("����");
		panel.add(Send);
		Send.setBounds(150, 480, 70, 50);
		Send.addActionListener(this);
		
		Logout = new JButton("�˳�");
		panel.add(Logout);
		Logout.setBounds(50, 480, 70, 50);
		Logout.addActionListener(this);

		pencil = new JButton("��ʻ�");
		panel.add(pencil);
		pencil.setBounds(400, 380, 90, 50);
		pencil.addActionListener(this);
		
		line = new JButton("ֱ��");
		panel.add(line);
		line.setBounds(400, 455, 70, 50);
		line.addActionListener(this);
		
		circle = new JButton("��Բ");
		panel.add(circle);
		circle.setBounds(500, 455, 70, 50);
		circle.addActionListener(this);
		
		fillcircle = new JButton("ʵ����Բ");
		panel.add(fillcircle);
		fillcircle.setBounds(500, 510, 110, 50);
		fillcircle.addActionListener(this);
		
		rect = new JButton("����");
		panel.add(rect);
		rect.setBounds(600, 455, 70, 50);
		rect.addActionListener(this);
		
		fillrect = new JButton("ʵ�ľ���");
		panel.add(fillrect);
		fillrect.setBounds(640, 510, 110, 50);
		fillrect.addActionListener(this);
		
		rubber = new JButton("��Ƥ��");
		panel.add(rubber);
		rubber.setBounds(700, 455, 90, 50);
		rubber.addActionListener(this);
		
		word = new JButton("д��");
		panel.add(word);
		word.setBounds(820, 455, 70, 50);
		word.addActionListener(this);
		
		colorButton = new JButton("��ɫ");
		panel.add(colorButton);
		colorButton.setBounds(920, 455, 70, 50);
		colorButton.addActionListener(this);
		
		withe = new JButton("�ߴ�");
		panel.add(withe);
		withe.setBounds(800, 510, 70, 50);
		withe.addActionListener(this);
		
		save = new JButton("����");
		panel.add(save);
		save.setBounds(310, 380, 70, 50);
		save.addActionListener(this);
		
		load = new JButton("��ȡ");
		panel.add(load);
		load.setBounds(310, 455, 70, 50);
		load.addActionListener(this);
		
		
		// ��������¼�������
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e1) {
				try {
					Socket toServer;
					PrintStream streamToServer;
					toServer = new Socket(IPadr, PORT1);
					streamToServer = new PrintStream(toServer.getOutputStream());
					streamToServer.println(Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE
							+ ":" + Calendar.SECOND+ " " + usr_name
							+" "+"�˳��˹���װ塣");

				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		getContentPane().add(panel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000, 600);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - 1000) / 2, (screen.height - 580) / 2);
		setVisible(true);
		setResizable(false);
		t.start();
		t2.start();
	}
	//��Ϣ����
	int messageCount = 0; // ��Ϣ����
	PrintStream streamToServer;
	ObjectInputStream streamFromServer;
	Socket toServer;
	public void actionPerformed(ActionEvent e1) {
		JButton button = (JButton) e1.getSource();
		if (button.equals(Logout)) {
			try {
				toServer = new Socket(IPadr, PORT1);
				streamToServer = new PrintStream(toServer.getOutputStream());
				//������������˳���Ϣ
				streamToServer.println(Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE
						+ ":" + Calendar.SECOND + " " + usr_name + "   �˳��˹���װ塣");
				System.exit(-1);

			} catch (Exception e) {
				System.out.println("�����쳣:" + e);
			}
			this.dispose();
		}else if (button.equals(Send)) {
			try {
				toServer = new Socket(IPadr, PORT1);
				streamFromServer = new ObjectInputStream(
						toServer.getInputStream());
				streamToServer = new PrintStream(toServer.getOutputStream());
				message = txt_UserNews.getText();
				String msg = message;// �����û������û������������Ϣ
				streamToServer.println(Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE
						+ ":" + Calendar.SECOND + " " + usr_name + ":" + msg);
				txt_UserNews.setText("");
				// ��ȡ�������Ļ�Ӧ��Ϣ
				Vector vector = (Vector) streamFromServer.readObject();
				int i = messageCount;
				for (; i < vector.capacity(); i++) {
					String s = txt_WhiteBoard.getText();
					String str = "";
					for (int k = 0; k < s.length(); k++) {
						if ((int) s.charAt(k) == 32
								&& (int) s.charAt(k + 1) == 32)
							continue;
						str += s.charAt(k);
					}
					txt_WhiteBoard.setText(str + (String) vector.elementAt(i)
							+ "\n");

				}
				
				messageCount = i;
			}
			catch (Exception e) {
				System.out.println("�����쳣: " + e);
			}
		}else if (button.equals(colorButton)) {
			Color color=Color.BLACK;
			color=JColorChooser.showDialog(paint, "��ѡ����ɫ", color);
			try {
				paint.R = color.getRed();
				paint.G = color.getGreen();
				paint.B = color.getBlue();
			} catch (Exception e) {
				paint.R = 0;
				paint.G = 0;
				paint.B = 0;
			}
			paint.createNewitem();
			paint.repaint();
		}else if (button.equals(pencil)) {
			paint.currentChoice=1;
			paint.createNewitem();
			paint.repaint();
		}else if (button.equals(line)) {
			paint.currentChoice=2;
			paint.createNewitem();
			paint.repaint();
		}else if (button.equals(circle)) {
			paint.currentChoice=3;
			paint.createNewitem();
			paint.repaint();
		}else if (button.equals(fillcircle)) {
			paint.currentChoice=7;
			paint.createNewitem();
			paint.repaint();
		}else if (button.equals(rect)) {
			paint.currentChoice=4;
			paint.createNewitem();
			paint.repaint();
		}else if (button.equals(fillrect)) {
			paint.currentChoice=8;
			paint.createNewitem();
			paint.repaint();
		}else if (button.equals(rubber)) {
			paint.currentChoice=5;
			paint.createNewitem();
			paint.repaint();
		}else if (button.equals(word)) {
			paint.currentChoice=6;
			paint.createNewitem();
			paint.repaint();
		}else if (button.equals(withe)) {
			String input ;
			input = JOptionPane.showInputDialog("�����뻭�ʵĴ�ϸ( >0 )");
			try {
				paint.stroke = Float.parseFloat(input);
				
			} catch (Exception e) {
				paint.stroke = 1.0f;
				
			}paint.itemList[paint.index].stroke = paint.stroke;
		}else if (button.equals(save)) {
			fileClass.saveFile();
		}else if(button.equals(load)){
			fileClass.readFile();
		}
	}
	  //�Զ�����������Ϣ
		class GetNews implements ActionListener {
			Socket toServer;
			ObjectInputStream streamFromServer;
			PrintStream streamToServer;

			public void actionPerformed(ActionEvent e2) {
				try {  
					toServer = new Socket(IPadr, PORT1);
					streamFromServer = new ObjectInputStream(
							toServer.getInputStream());
					streamToServer = new PrintStream(toServer.getOutputStream());
					// ����������͸�����Ϣ����
					streamToServer.println("renew");
					// �ӷ������˽���Vector����
					Vector vector = (Vector) streamFromServer.readObject();
					// ��ʾ����Ϣ
					int i = messageCount;
					for (; i < vector.capacity(); i++) {
						String s = txt_WhiteBoard.getText(); // ��ʾ��Ϣ
						txt_WhiteBoard.setText(s + (String) vector.elementAt(i) + "\n");
					}
					messageCount = i;
				} catch (Exception e) {
					System.out.println("����������Ϣ����"+e);	
				}
			}
		}
		//�Զ����»�����Ϣ
		 class GetNewPaint implements ActionListener {
			Socket toServer;
			ObjectInputStream streamFromServer;
			PrintStream streamToServer;
			String s="";

			public void actionPerformed(ActionEvent e2) {
				try {  
					toServer = new Socket(IPadr, PORT2);
					streamFromServer = new ObjectInputStream(
							toServer.getInputStream());
					streamToServer = new PrintStream(toServer.getOutputStream());
					// ����������͸��»�ͼ������
					streamToServer.println("renew");
					// �ӷ������˽���Vector����
					Vector vector = (Vector) streamFromServer.readObject();
					// ��ʾ����Ϣ
					paint.count=0;
					paint.returnList =new Drawing[50000];
					for(int i =0;i<vector.capacity();i++)
					{
					  s=(String) vector.elementAt(i);
					  paint.analyseAndDraw(s);
					  paint.repaint();
					}
				} catch (Exception e) {
					System.out.println("���»�ͼ����Ϣ����"+e);
				}
			}
		}
	
	
	public static void main(String args[]) throws UnknownHostException, IOException {
		Login L = new Login();  //�û���¼

	}

}




