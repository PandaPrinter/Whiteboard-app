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
int x1,x2,y1,y2;   	    //������������
int  R,G,B;				//����ɫ������
float stroke ;			//����������ϸ������
String s1;				//Ҫд����
//�����ͼ����
void draw(Graphics2D g2d ){
	
 }
}

class Line extends Drawing//ֱ����
{
	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));

		g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));
		g2d.drawLine(x1, y1, x2, y2);
		
	}
}
class Rect extends Drawing{//������
	void draw(Graphics2D g2d ){
		g2d.setPaint(new Color(R,G,B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
	}
}
class fillRect extends Drawing{//ʵ�ľ�����
	   void draw(Graphics2D g2d ){
			g2d.setPaint(new Color(R,G,B));
			g2d.setStroke(new BasicStroke(stroke));
			g2d.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
		}
	}

class Circle extends Drawing{//��Բ��
	void draw(Graphics2D g2d ){
		g2d.setPaint(new Color(R,G,B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
	}
}
class fillOval extends Drawing{//ʵ����Բ��
    void draw(Graphics2D g2d ){
		g2d.setPaint(new Color(R,G,B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
	}
}
class Pencil extends Drawing{//��ʻ���
	void draw(Graphics2D g2d ){
		
		g2d.setPaint(new Color(R,G,B));
		g2d.setStroke(new BasicStroke(stroke,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
	    g2d.drawLine(x1, y1,x2, y2);
	}
}

class Rubber extends Drawing{//��Ƥ����
	void draw(Graphics2D g2d ){
		g2d.setPaint(new Color(255,255,255));//��ɫ
		g2d.setStroke(new BasicStroke(stroke+4,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
	    g2d.drawLine(x1, y1,x2, y2);
	}
}

class Word extends Drawing{//����������
	void draw(Graphics2D g2d ){
		g2d.setPaint(new Color(R,G,B));
		g2d.setFont(new Font("BOLD",x2+y2,((int)stroke)*18));//��������
	    if(s1 != null)
		    g2d.drawString( s1, x1,y1);
	    
	}
}

