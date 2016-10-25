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
	Drawing[] itemList =new Drawing[50000]; //绘制图形类
	Drawing[] returnList =null;//存放服务器返回的对象
	int count=0; //returnList中的计数变量
	
	int currentChoice = 1;//设置默认基本图形状态为随笔画
    int index = 0;//当前已经绘制的图形数目
    int R,G,B;//用来存放当前颜色的彩值
    int f1,f2;
    float stroke = 1.0f;//设置画笔的粗细 ，默认的是 1.0
    String string="";//记录对象信息
    String s="";
    
	Socket toServer;
	ObjectInputStream streamFromServer;
	PrintStream streamToServer;
	String IPadr = "localhost";
	public static final int PORT2 = 9090;
	
    public MyPanel() {
		setBackground(Color.white);// 设置绘制区的背景是白色
		this.addMouseListener(this);// 添加鼠标事件
		this.addMouseMotionListener(this);
		createNewitem();
	}
    
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;//定义随笔画
		
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
		i.draw(g2d);//将画笔传到个各类的子类中，用来完成各自的绘图
	}
	
	void sendPaint(){
		try {
			toServer = new Socket(IPadr, PORT2);
			streamFromServer = new ObjectInputStream(
					toServer.getInputStream());
			streamToServer = new PrintStream(toServer.getOutputStream());
			// 发送用户当前新产生的图案对象
			streamToServer.println(string);
			// 读取服务器的回应信息
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
			System.out.println("发送时出现异常" + e);
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
		case " 7": returnList[count] = new fillOval();break;  //实心椭圆
		case " 8": returnList[count] = new fillRect();break;  //实心矩形
		}
		setValue(returnList[count], array);
		count++;
	}			
	//新建一个图形的基本单元对象的程序段
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
			case 7: itemList[index] = new fillOval();break;  //实心椭圆
			case 8: itemList[index] = new fillRect();break;  //实心矩形
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
    
    //清空绘画板
	public void clear() {
		setIndex(0);
		currentChoice=1;//设置默认为随笔画
		setBackground(Color.WHITE);//设置颜色
		setStroke(1.0f);//设置画笔的粗细
		createNewitem();
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//如果当前选择为随笔画或橡皮擦 ，则进行下面的操作
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
    		  createNewitem();//创建新的图形的基本单元对象
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
		
		//如果当前选择为随笔画或橡皮擦 ，则进行下面的操作
		if(currentChoice == 1||currentChoice ==5){
			itemList[index].x1 = itemList[index].x2 = e.getX();
			itemList[index].y1 = itemList[index].y2 = e.getY();
	   		if(currentChoice == 1){
	     		     string=sendString(1, itemList[index].x1, itemList[index].y1, itemList[index].x2, itemList[index].y2,itemList[index].R, itemList[index].G, itemList[index].B, itemList[index].stroke,itemList[index].s1);
	     		  }else if (currentChoice ==5) {
	     			 string=sendString(5, itemList[index].x1, itemList[index].y1, itemList[index].x2, itemList[index].y2,itemList[index].R, itemList[index].G, itemList[index].B, itemList[index].stroke,itemList[index].s1);
				  }
			index++;
			createNewitem();//创建新的图形的基本单元对象
			sendPaint();
		}
		//如果选择图形的文字输入，则进行下面的操作
		if(currentChoice == 6){
			itemList[index].x1 = e.getX();
			itemList[index].y1 = e.getY();
			String input=" ";
			input = JOptionPane.showInputDialog("请输入你要写入的文字！");
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
			createNewitem();//创建新的图形的基本单元对象
			repaint();
			sendPaint();
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//如果当前选择为随笔画或橡皮擦 ，则进行下面的操作
		if(currentChoice == 1||currentChoice ==5){
			itemList[index].x1 = e.getX();
			itemList[index].y1 = e.getY();
		}
		itemList[index].x2 = e.getX();
		itemList[index].y2 = e.getY();
		string=sendString(currentChoice, itemList[index].x1, itemList[index].y1, itemList[index].x2, itemList[index].y2, itemList[index].R, itemList[index].G, itemList[index].B, stroke, itemList[index].s1);
		repaint();
		index++;
		createNewitem();//创建新的图形的基本单元对象
		sendPaint();
	
	}
}


