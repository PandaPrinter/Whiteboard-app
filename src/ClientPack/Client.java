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
	String usr_name;//用户名
	String IPadr = "localhost";//本地
	public static final int PORT1 = 8080;//消息端口
	public static final int PORT2 = 9090;//绘图端口
 
	final JTextPane txt_WhiteBoard = new JTextPane();// 聊天面板消息窗口
	final JTextPane txt_UserNews = new JTextPane();// 用户发送消息窗口

	JButton Send;// 发送消息按钮
	JButton Logout;// 退出按钮
	JButton pencil;//随笔画
	JButton line;//直线
	JButton circle;//椭圆或圆
	JButton fillcircle;//实心椭圆
	JButton rect;//矩形
	JButton fillrect;//实心矩形
	JButton rubber;//橡皮擦
	JButton word;//写字
	JButton colorButton;//改变颜色
	JButton withe;  //画笔粗细
	JButton save;  //保存当前图片
	JButton load;  //读取用户的图片
	
	JLabel ChatRoom;// 共享白板
	JScrollPane userNews;// 用户写消息框滚动条
	JScrollPane whiteBoard;// 聊天室消息滚动条
	
	String message; //用户想要发送的聊天消息
	JPanel panel = new JPanel();
	MyPanel paint =new MyPanel();//专门绘图用的Panel
	FileOperate fileClass=null;
	Timer t = new Timer(0, new GetNews());//自动更新消息线程
	Timer t2 = new Timer(0, new GetNewPaint());//自动更新绘图板线程
	public Client(String nm) {
		usr_name = nm;
		this.setTitle("用户:" + usr_name);
		panel.setLayout(null);
		paint.setBounds(510, 50, 480, 400);
		paint.setBackground(Color.WHITE);
		panel.add(paint);//绘图Panel覆盖在第一个Panel的指定位置上
		panel.setBackground(Color.YELLOW);
		try {
			fileClass=new FileOperate(this, paint,usr_name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		String name = "欢迎来到网络白板聊天室~~今天是" + c.get(Calendar.YEAR)
				+ "年" + c.get(Calendar.MONTH) + 1 + "月" + c.get(Calendar.DATE)
				+ "日";
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

		Send = new JButton("发送");
		panel.add(Send);
		Send.setBounds(150, 480, 70, 50);
		Send.addActionListener(this);
		
		Logout = new JButton("退出");
		panel.add(Logout);
		Logout.setBounds(50, 480, 70, 50);
		Logout.addActionListener(this);

		pencil = new JButton("随笔画");
		panel.add(pencil);
		pencil.setBounds(400, 380, 90, 50);
		pencil.addActionListener(this);
		
		line = new JButton("直线");
		panel.add(line);
		line.setBounds(400, 455, 70, 50);
		line.addActionListener(this);
		
		circle = new JButton("椭圆");
		panel.add(circle);
		circle.setBounds(500, 455, 70, 50);
		circle.addActionListener(this);
		
		fillcircle = new JButton("实心椭圆");
		panel.add(fillcircle);
		fillcircle.setBounds(500, 510, 110, 50);
		fillcircle.addActionListener(this);
		
		rect = new JButton("矩形");
		panel.add(rect);
		rect.setBounds(600, 455, 70, 50);
		rect.addActionListener(this);
		
		fillrect = new JButton("实心矩形");
		panel.add(fillrect);
		fillrect.setBounds(640, 510, 110, 50);
		fillrect.addActionListener(this);
		
		rubber = new JButton("橡皮擦");
		panel.add(rubber);
		rubber.setBounds(700, 455, 90, 50);
		rubber.addActionListener(this);
		
		word = new JButton("写字");
		panel.add(word);
		word.setBounds(820, 455, 70, 50);
		word.addActionListener(this);
		
		colorButton = new JButton("颜色");
		panel.add(colorButton);
		colorButton.setBounds(920, 455, 70, 50);
		colorButton.addActionListener(this);
		
		withe = new JButton("线粗");
		panel.add(withe);
		withe.setBounds(800, 510, 70, 50);
		withe.addActionListener(this);
		
		save = new JButton("保存");
		panel.add(save);
		save.setBounds(310, 380, 70, 50);
		save.addActionListener(this);
		
		load = new JButton("读取");
		panel.add(load);
		load.setBounds(310, 455, 70, 50);
		load.addActionListener(this);
		
		
		// 向窗体添加事件侦听器
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e1) {
				try {
					Socket toServer;
					PrintStream streamToServer;
					toServer = new Socket(IPadr, PORT1);
					streamToServer = new PrintStream(toServer.getOutputStream());
					streamToServer.println(Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE
							+ ":" + Calendar.SECOND+ " " + usr_name
							+" "+"退出了共享白板。");

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
	//消息处理
	int messageCount = 0; // 消息数量
	PrintStream streamToServer;
	ObjectInputStream streamFromServer;
	Socket toServer;
	public void actionPerformed(ActionEvent e1) {
		JButton button = (JButton) e1.getSource();
		if (button.equals(Logout)) {
			try {
				toServer = new Socket(IPadr, PORT1);
				streamToServer = new PrintStream(toServer.getOutputStream());
				//向服务器发送退出消息
				streamToServer.println(Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE
						+ ":" + Calendar.SECOND + " " + usr_name + "   退出了共享白板。");
				System.exit(-1);

			} catch (Exception e) {
				System.out.println("发生异常:" + e);
			}
			this.dispose();
		}else if (button.equals(Send)) {
			try {
				toServer = new Socket(IPadr, PORT1);
				streamFromServer = new ObjectInputStream(
						toServer.getInputStream());
				streamToServer = new PrintStream(toServer.getOutputStream());
				message = txt_UserNews.getText();
				String msg = message;// 发送用户名和用户输入的聊天信息
				streamToServer.println(Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE
						+ ":" + Calendar.SECOND + " " + usr_name + ":" + msg);
				txt_UserNews.setText("");
				// 读取服务器的回应信息
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
				System.out.println("发生异常: " + e);
			}
		}else if (button.equals(colorButton)) {
			Color color=Color.BLACK;
			color=JColorChooser.showDialog(paint, "请选择颜色", color);
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
			input = JOptionPane.showInputDialog("请输入画笔的粗细( >0 )");
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
	  //自动更新聊天信息
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
					// 向服务器发送更新消息请求
					streamToServer.println("renew");
					// 从服务器端接收Vector向量
					Vector vector = (Vector) streamFromServer.readObject();
					// 显示该消息
					int i = messageCount;
					for (; i < vector.capacity(); i++) {
						String s = txt_WhiteBoard.getText(); // 显示消息
						txt_WhiteBoard.setText(s + (String) vector.elementAt(i) + "\n");
					}
					messageCount = i;
				} catch (Exception e) {
					System.out.println("更新聊天消息出错"+e);	
				}
			}
		}
		//自动更新画板信息
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
					// 向服务器发送更新绘图板请求
					streamToServer.println("renew");
					// 从服务器端接收Vector向量
					Vector vector = (Vector) streamFromServer.readObject();
					// 显示该消息
					paint.count=0;
					paint.returnList =new Drawing[50000];
					for(int i =0;i<vector.capacity();i++)
					{
					  s=(String) vector.elementAt(i);
					  paint.analyseAndDraw(s);
					  paint.repaint();
					}
				} catch (Exception e) {
					System.out.println("更新绘图板消息出错"+e);
				}
			}
		}
	
	
	public static void main(String args[]) throws UnknownHostException, IOException {
		Login L = new Login();  //用户登录

	}

}




