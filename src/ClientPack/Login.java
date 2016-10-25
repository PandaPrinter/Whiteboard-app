package ClientPack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Login extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 321652564502782398L;
	JFrame f = new JFrame("�û���¼");
	JLabel l = new JLabel("�������ǳ�,�������õ�һ�����ǳƵ�ͼ�ı����ļ�");
	JTextField user = new JTextField();
	JButton button1 = new JButton("��¼");
	JButton button2 = new JButton("�˳�");
	String IPadr = "localhost";
	public static final int PORT1 = 8080;
	Socket toServer;
	PrintStream streamToServer;
	public Login() {
		// ���ò���
		f.setSize(350, 200);
		f.setLayout(null);
		f.add(l);
		user.setText(null);
		l.setBounds(0, 5, 350, 20);
		f.add(user);
		user.setBounds(50, 50, 200, 30);
		f.add(button1);
		f.add(button2);
		button1.setBounds(65, 100, 60, 40);
		button2.setBounds(200, 100, 60, 40);
		f.setLocation(400, 200);
		f.setVisible(true);
		button1.addActionListener(this);
		button2.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		try {
			toServer = new Socket(IPadr, PORT1);
			streamToServer = new PrintStream(toServer.getOutputStream());
		JButton button = (JButton) e.getSource();
		if (button.equals(button1)) {
			Client cn = new Client(user.getText());
			streamToServer.println(Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE
					+ ":" + Calendar.SECOND + "  "+"�û�-->"+user.getText()+"<---����������װ�(*^__^*) ");
			f.setVisible(false);

		} else if(button.equals(button2)) {
			System.exit(-1);
		}else {
			f.setVisible(true);
		}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}

