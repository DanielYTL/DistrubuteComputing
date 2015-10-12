package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class TCPClient {
	
	protected String host;
	protected int port;
	protected Socket s;
	
	public final JTextField clientSend = new JTextField();
	public final JTextArea clientReply = new JTextArea(8, 20);
	
	public TCPClient(String host, int port) {
		this.host = host;
		this.port = port;
		 try {
			s = new Socket(host, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void go() {
		try{

			 DataInputStream in = new DataInputStream(s.getInputStream());
			 DataOutputStream out= new DataOutputStream(s.getOutputStream());
			
			 JFrame cGUI = new JFrame("TCP Client"); // Set the window title
		     cGUI.setSize(300, 300); // Set the window size
		     cGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    
		     cGUI.getContentPane().add(clientSend, "South");
		     clientSend.addActionListener(new ActionListener(){
		    	 public void actionPerformed(ActionEvent event) {
		    		String SC = clientSend.getText();
		    		clientReply.append(SC+"\n");
		    		clientSend.selectAll();
		    		try {
							out.writeUTF(SC);
							out.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	  }
		    });

		    // Set echo replay text area
		    JScrollPane scrollPane = new JScrollPane(clientReply);
		    cGUI.getContentPane().add(scrollPane, "Center");
		    
		    clientReply.setEditable(false);
		    
			cGUI.addWindowListener(new WindowAdapter() {
			      public void windowClosing(WindowEvent e) {
			        try {
			        	in.close();
			        	out.close();
			          s.close();
			          
			        } catch (Exception exception) {}
			        System.exit(0);
			      }
			    });
		    
		    cGUI.setVisible(true);
			
		    clientReply.append("Welcome" + "\n");
		    clientReply.append("PS: You answer should be like '1.A' form "+ "\n");
		    clientReply.append("Please Create ID:"+ "\n");
		    
		    in.readUTF();
		    
			for(int i=0;i< 3;i++){
				String s = in.readUTF();
				clientReply.append(s + "\n");
			}

			for(int i =0;i<3;i++)
			{
				String xx= in.readUTF();
				
				clientReply.append(xx + "\n");
			}
			
			String per = in.readUTF();
			clientReply.append(per + "\n");
			
			System.out.println("hso3");

			
		} catch (UnknownHostException e) {
			System.out.println("Can't find " + host);
		} catch (IOException e) {
			System.out.println("Connection closed.");
		//} catch (InterruptedException e) {
			//System.out.println("Oh dear, interrupted.");
		} finally {
			try{ s.close();}catch(Exception ex){}
		}		
	}
	
	public static void main(String[] args) {
		TCPClient c = new TCPClient("localhost", 1235);
		c.go();
	}
}

//-----------------------------------------------------------------------------------

class HandleAnswers extends Thread
{
	private DataOutputStream outH;
	private boolean flag = false;
	
	public HandleAnswers (DataOutputStream out)
	{
		outH = out;
	}
	
	public void run()
	{
		Scanner SC = new Scanner(System.in);
		
		try{
			for(int i=0;i< 3;i++){
				String answer = SC.nextLine();
			    outH.writeUTF(answer);
			    outH.flush();
			}
			System.out.println("check");
		}catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}