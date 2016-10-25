package ClientPack;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Drawing implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 7447193984263228752L;
int x1,x2,y1,y2;   	    //定义坐标属性
int  R,G,B;				//定义色彩属性
float stroke ;			//定义线条粗细的属性
String s1;				//要写的字
//定义绘图函数
void draw(Graphics2D g2d ){
	
 }
}

class Line extends Drawing//直线类
{
	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));

		g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));
		g2d.drawLine(x1, y1, x2, y2);
		
	}
}
class Rect extends Drawing{//矩形类
	void draw(Graphics2D g2d ){
		g2d.setPaint(new Color(R,G,B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
	}
}
class fillRect extends Drawing{//实心矩形类
	   void draw(Graphics2D g2d ){
			g2d.setPaint(new Color(R,G,B));
			g2d.setStroke(new BasicStroke(stroke));
			g2d.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
		}
	}

class Circle extends Drawing{//椭圆类
	void draw(Graphics2D g2d ){
		g2d.setPaint(new Color(R,G,B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
	}
}
class fillOval extends Drawing{//实心椭圆类
    void draw(Graphics2D g2d ){
		g2d.setPaint(new Color(R,G,B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
	}
}
class Pencil extends Drawing{//随笔画类
	void draw(Graphics2D g2d ){
		
		g2d.setPaint(new Color(R,G,B));
		g2d.setStroke(new BasicStroke(stroke,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
	    g2d.drawLine(x1, y1,x2, y2);
	}
}

class Rubber extends Drawing{//橡皮擦类
	void draw(Graphics2D g2d ){
		g2d.setPaint(new Color(255,255,255));//白色
		g2d.setStroke(new BasicStroke(stroke+4,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
	    g2d.drawLine(x1, y1,x2, y2);
	}
}

class Word extends Drawing{//输入文字类
	void draw(Graphics2D g2d ){
		g2d.setPaint(new Color(R,G,B));
		g2d.setFont(new Font("BOLD",x2+y2,((int)stroke)*18));//设置字体
	    if(s1 != null)
		    g2d.drawString( s1, x1,y1);
	    
	}
}

