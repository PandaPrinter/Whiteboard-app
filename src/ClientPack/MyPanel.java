package ClientPack;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class MyPanel extends JPanel implements MouseListener,MouseMotionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1205565416775552198L;
	Drawing[] itemList =new Drawing[50000]; //����ͼ����
	Drawing[] returnList =null;//��ŷ��������صĶ���
	int count=0; //returnList�еļ�������
	
	int currentChoice = 1;//����Ĭ�ϻ���ͼ��״̬Ϊ��ʻ�
    int index = 0;//��ǰ�Ѿ����Ƶ�ͼ����Ŀ
    int R,G,B;//������ŵ�ǰ��ɫ�Ĳ�ֵ
    int f1,f2;
    float stroke = 1.0f;//���û��ʵĴ�ϸ ��Ĭ�ϵ��� 1.0
    String string="";//��¼������Ϣ
    String s="";
    
	Socket toServer;
	ObjectInputStream streamFromServer;
	PrintStream streamToServer;
	String IPadr = "localhost";
	public static final int PORT2 = 9090;
	
    public MyPanel() {
		setBackground(Color.white);// ���û������ı����ǰ�ɫ
		this.addMouseListener(this);// �������¼�
		this.addMouseMotionListener(this);
		createNewitem();
	}
    
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;//������ʻ�
		
		for(int j = 0;j<=index;j++)
		{
			draw(g2d,itemList[j]);
		
	    }
		for(int i=0;i<count;i++){
			draw(g2d, returnList[i]);
		}
	}
	
	void draw(Graphics2D g2d , Drawing i)
	{
		i.draw(g2d);//�����ʴ���������������У�������ɸ��ԵĻ�ͼ
	}
	
	void sendPaint(){
		try {
			toServer = new Socket(IPadr, PORT2);
			streamFromServer = new ObjectInputStream(
					toServer.getInputStream());
			streamToServer = new PrintStream(toServer.getOutputStream());
			// �����û���ǰ�²�����ͼ������
			streamToServer.println(string);
			// ��ȡ�������Ļ�Ӧ��Ϣ
			Vector vector = (Vector) streamFromServer.readObject();
			count=0;
			returnList =new Drawing[50000];
			for(int i =0;i<vector.capacity();i++)
			{
			  s=(String) vector.elementAt(i);
	          analyseAndDraw(s);
			}
			
		}
		catch (Exception e) {
			System.out.println("����ʱ�����쳣" + e);
		}
	}
	
	public String sendString(int type,int x,int y,int width,int high,int R,int G,int B,float stroke,String s){
		String string=type+":"+x+":"+y+":"+width+":"+high+":"+R+":"+G+":"+B+":"+stroke+":"+s;
		return string;
	}
	public void setValue(Drawing draw,String []array){
		draw.x1=Integer.parseInt(array[1]);draw.y1=Integer.parseInt(array[2]);
        draw.x2=Integer.parseInt(array[3]);draw.y2=Integer.parseInt(array[4]);
        draw.R=Integer.parseInt(array[5]);draw.G=Integer.parseInt(array[6]);
        draw.B=Integer.parseInt(array[7]);draw.stroke=Float.parseFloat(array[8]);
        draw.s1=array[9];
	}
	public void analyseAndDraw(String s){
		String []array=s.split(":");
		switch (array[0]) {
		case " 1": returnList[count] = new Pencil();break;
		case " 2": returnList[count] = new Line();break;
		case " 3": returnList[count] = new Circle();break;
		case " 4": returnList[count] = new Rect();break;
		case " 5": returnList[count] = new Rubber();break;
		case " 6": returnList[count] = new Word();break;
		case " 7": returnList[count] = new fillOval();break;  //ʵ����Բ
		case " 8": returnList[count] = new fillRect();break;  //ʵ�ľ���
		}
		setValue(returnList[count], array);
		count++;
	}			
	//�½�һ��ͼ�εĻ�����Ԫ����ĳ����
	void createNewitem(){
		if(currentChoice == 6)
			setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		else  	setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			switch(currentChoice){
			case 1: itemList[index] = new Pencil();break;
			case 2: itemList[index] = new Line();break;
			case 3: itemList[index] = new Circle();break;
			case 4: itemList[index] = new Rect();break;
			case 5: itemList[index] = new Rubber();break;
			case 6: itemList[index] = new Word();break;
			case 7: itemList[index] = new fillOval();break;  //ʵ����Բ
			case 8: itemList[index] = new fillRect();break;  //ʵ�ľ���
		}
	  itemList[index].R = R;
	  itemList[index].G = G;
	  itemList[index].B = B;
	  itemList[index].stroke = stroke ;
	 
	}
	
    public void setIndex(int x){
    	index = x;
    }
    public int getIndex(){
    	return index ;
    }
    public void setStroke(float f)
    {
    	stroke = f;
    }
    
    //��ջ滭��
	public void clear() {
		setIndex(0);
		currentChoice=1;//����Ĭ��Ϊ��ʻ�
		setBackground(Color.WHITE);//������ɫ
		setStroke(1.0f);//���û��ʵĴ�ϸ
		createNewitem();
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//�����ǰѡ��Ϊ��ʻ�����Ƥ�� �����������Ĳ���
		  if(currentChoice == 1||currentChoice ==5){
    		  itemList[index-1].x1 = itemList[index].x2 = itemList[index].x1 =e.getX();
    		  itemList[index-1].y1 = itemList[index].y2 = itemList[index].y1 = e.getY();

     		  if(currentChoice == 1){
     			 string=sendString(1, itemList[index].x1, itemList[index].y1, itemList[index-1].x2, itemList[index-1].y2,itemList[index-1].R, itemList[index-1].G, itemList[index-1].B, itemList[index-1].stroke,itemList[index-1].s1);
      		  }else if (currentChoice ==5) {
      			 string=sendString(5, itemList[index].x1, itemList[index].y1, itemList[index-1].x2, itemList[index-1].y2,itemList[index-1].R, itemList[index-1].G, itemList[index-1].B, itemList[index-1].stroke,itemList[index-1].s1);
 			  }
     		  repaint();
    		  sendPaint();
    		  index++;
    		  createNewitem();//�����µ�ͼ�εĻ�����Ԫ����
    	  }
    	  else 
    	  {
    		  itemList[index].x2 = e.getX();
    		  itemList[index].y2 = e.getY();
    	  }
    	  repaint();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
		// TODO Auto-generated method stub
		itemList[index].x1 = itemList[index].x2 = e.getX();
		itemList[index].y1 = itemList[index].y2 = e.getY();
		
		//�����ǰѡ��Ϊ��ʻ�����Ƥ�� �����������Ĳ���
		if(currentChoice == 1||currentChoice ==5){
			itemList[index].x1 = itemList[index].x2 = e.getX();
			itemList[index].y1 = itemList[index].y2 = e.getY();
	   		if(currentChoice == 1){
	     		     string=sendString(1, itemList[index].x1, itemList[index].y1, itemList[index].x2, itemList[index].y2,itemList[index].R, itemList[index].G, itemList[index].B, itemList[index].stroke,itemList[index].s1);
	     		  }else if (currentChoice ==5) {
	     			 string=sendString(5, itemList[index].x1, itemList[index].y1, itemList[index].x2, itemList[index].y2,itemList[index].R, itemList[index].G, itemList[index].B, itemList[index].stroke,itemList[index].s1);
				  }
			index++;
			createNewitem();//�����µ�ͼ�εĻ�����Ԫ����
			sendPaint();
		}
		//���ѡ��ͼ�ε��������룬���������Ĳ���
		if(currentChoice == 6){
			itemList[index].x1 = e.getX();
			itemList[index].y1 = e.getY();
			String input=" ";
			input = JOptionPane.showInputDialog("��������Ҫд������֣�");
			itemList[index].s1 = input;
			if(input==null){
				itemList[index].s1 ="  ";
			}else if(input.equals("")){
				itemList[index].s1 ="  ";
			}
			itemList[index].x2 = f1;
			itemList[index].y2 = f2;
			string=sendString(6, itemList[index].x1, itemList[index].y1, itemList[index].x2, itemList[index].y2, itemList[index].R, itemList[index].G, itemList[index].B, stroke, itemList[index].s1);
			index++;
			createNewitem();//�����µ�ͼ�εĻ�����Ԫ����
			repaint();
			sendPaint();
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//�����ǰѡ��Ϊ��ʻ�����Ƥ�� �����������Ĳ���
		if(currentChoice == 1||currentChoice ==5){
			itemList[index].x1 = e.getX();
			itemList[index].y1 = e.getY();
		}
		itemList[index].x2 = e.getX();
		itemList[index].y2 = e.getY();
		string=sendString(currentChoice, itemList[index].x1, itemList[index].y1, itemList[index].x2, itemList[index].y2, itemList[index].R, itemList[index].G, itemList[index].B, stroke, itemList[index].s1);
		repaint();
		index++;
		createNewitem();//�����µ�ͼ�εĻ�����Ԫ����
		sendPaint();
	
	}
}


