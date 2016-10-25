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
							JOptionPane.showMessageDialog(myPanel,"没有找到源文件！","没有找到源文件",JOptionPane.ERROR_MESSAGE);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(myPanel,"读文件是发生错误！","读取错误",JOptionPane.ERROR_MESSAGE);
						} catch (ClassNotFoundException e) {
							JOptionPane.showMessageDialog(myPanel,"不能创建对象！","已到文件末尾",JOptionPane.ERROR_MESSAGE);
						}
					
			    }
		//保存图像文件
		public void saveFile() {
			// TODO 保存图像
		    	try {
//  				    Socket toServer = new Socket(IPadr, Server.PORT);
//  				    PrintStream streamToServer = new PrintStream(toServer.getOutputStream());
					file.delete();
					file.createNewFile();
					FileOutputStream fos = new FileOutputStream(fileName);//文件输出流以字节的方式输出
					//对象输出流
					ObjectOutputStream output = new ObjectOutputStream(fos);
					//Drawing record;
					
					output.writeInt(myPanel.getIndex());
					
					for(int i = 0;i<myPanel.getIndex()+1 ;i++)
					{
						Drawing p = myPanel.itemList[i];
						output.writeObject(p);
						output.flush();//刷新该流的缓冲。此操作将写入所有已缓冲的输出字节，并将它们刷新到底层流中。
						               //将所有的图形信息强制的转换成父类线性化存储到文件中    
					}
					output.close();
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		    
		}
		
	}
