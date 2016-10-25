package ClientPack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;


public class FileOperate {
	Client client;
	MyPanel myPanel = null;
	String IPadr = "localhost";
	String fileName="";
	File file=null;
	FileOperate(Client cl,MyPanel mp,String username) throws IOException {
			client = cl;
			myPanel = mp;
			this.fileName=username;
			file=new File(fileName);
			file.createNewFile();
		}

	public void readFile() {

		              try {
							FileInputStream ifs = new FileInputStream(fileName);
							ObjectInputStream input = new ObjectInputStream(ifs);
							
							int countNumber = 0;
							Drawing inputRecord;
							countNumber = input.readInt();
							for(int i =0;i<countNumber+1;i++)
							{
								myPanel.setIndex(i);
								inputRecord = (Drawing)input.readObject();
								myPanel.itemList[i] = inputRecord;
							}
							myPanel.createNewitem();
							input.close();
							myPanel.repaint();
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(myPanel,"û���ҵ�Դ�ļ���","û���ҵ�Դ�ļ�",JOptionPane.ERROR_MESSAGE);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(myPanel,"���ļ��Ƿ�������","��ȡ����",JOptionPane.ERROR_MESSAGE);
						} catch (ClassNotFoundException e) {
							JOptionPane.showMessageDialog(myPanel,"���ܴ�������","�ѵ��ļ�ĩβ",JOptionPane.ERROR_MESSAGE);
						}
					
			    }
		//����ͼ���ļ�
		public void saveFile() {
			// TODO ����ͼ��
		    	try {
//  				    Socket toServer = new Socket(IPadr, Server.PORT);
//  				    PrintStream streamToServer = new PrintStream(toServer.getOutputStream());
					file.delete();
					file.createNewFile();
					FileOutputStream fos = new FileOutputStream(fileName);//�ļ���������ֽڵķ�ʽ���
					//���������
					ObjectOutputStream output = new ObjectOutputStream(fos);
					//Drawing record;
					
					output.writeInt(myPanel.getIndex());
					
					for(int i = 0;i<myPanel.getIndex()+1 ;i++)
					{
						Drawing p = myPanel.itemList[i];
						output.writeObject(p);
						output.flush();//ˢ�¸����Ļ��塣�˲�����д�������ѻ��������ֽڣ���������ˢ�µ��ײ����С�
						               //�����е�ͼ����Ϣǿ�Ƶ�ת���ɸ������Ի��洢���ļ���    
					}
					output.close();
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		    
		}
		
	}
