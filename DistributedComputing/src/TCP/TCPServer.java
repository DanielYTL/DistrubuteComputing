package TCP;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TCPServer {
	
	//Globes 
	public static ArrayList<String> CurrentUsers = new ArrayList<String>();
	//public static ArrayList<Socket> connectionArray = new ArrayList<Socket>();
	public static String QuestionsAnswers[][]= {{"1+2=?"+"\n"+"A:1, B:2, C:3"+"\n","1.C"},{"2+3=?"+"\n"+"A:5, B:7, C:9"+"\n","2.A"},{"0+0=?"+"\n"+"A:-1, B:0, C:100"+"\n","3.B"}};
		
	public static void main(String[] args) throws IOException {
		TCPServer server = new TCPServer();
		server.go();
	}

	public void go() throws IOException {
		ServerSocket ss = new ServerSocket(1235);
		//System.out.println("Waiting for Client...");
		
		while(true) {
			
			System.out.println("Socket waiting on accept()");
			Socket s = ss.accept();
			
			System.out.println("Accepted new connection - creating handler.");
						
			ConnectionHandler ch = new ConnectionHandler(s);
			ch.start();
		}
	}
}
	
//---------------------------------------------------------------------------------------------------------------------
		class ConnectionHandler implements Runnable {

			protected Socket s;
			protected Thread t;
			protected DataInputStream in;
			protected DataOutputStream out;
		
			public ConnectionHandler(Socket s) {
				this.s = s;
				try {
					in = new DataInputStream(s.getInputStream());
				
					out = new DataOutputStream(s.getOutputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			public void start() {
					t = new Thread(this);
					t.start();
			}

			// must implement Run as we 'implement Runnable'
			public void run()
			{

				String name =null;			     
			    	try 
			    	{
			    		name = in.readUTF();
			    		TCPServer.CurrentUsers.add(name);
			    		System.out.println(name + "  connceting");
			    	} 
			    	catch (IOException e) 
			    	{
			    		// TODO Auto-generated catch block
			    		e.printStackTrace();
			    	}
			    	
			    	int[] correct = new int[3];
				
			    	String question=null;
			    	String[] clientanswers = new String[3];
			     
			    	try
			    	{
			    		Scanner sc = new Scanner(System.in);
			    		String start = sc.nextLine();
			    		out.writeUTF(start);
			    		out.flush();
			    	 
			    		HandleQuestions hq = new HandleQuestions(out, TCPServer.QuestionsAnswers);
			    	 
			    		HandleAnswers2 ha = new HandleAnswers2(in);
			    	 
			    		Runnable[] threads = {hq, ha};
			    	 
			    		ExecutorService es = Executors.newCachedThreadPool();
			    		for(int i=0;i<2;i++)
			    			es.execute(threads[i]);
			    		es.shutdown();
			    		boolean finshed = es.awaitTermination(1, TimeUnit.MINUTES);
			    		// all tasks have finished or the time has been reached.
			    	 
			    		clientanswers = ha.getAnswers();

			    		for(String answer: clientanswers)
			    		{
			    			boolean flag = false;
			    			int i = 0;
			    		
			    			if(answer==null) break;  
			    	    
			    			while(i<3 && flag == false){
			    				if(answer.equals(TCPServer.QuestionsAnswers[i][1])){
			    					correct[i] = 1;
			    					flag = true;
			    				}
			    				else i++;
			    			}
			    		
			    			if(flag==false)
			    			{
			    				int t = answer.charAt(0)-48;
			    				correct[t-1] = 0;
			    			}
			    		
			    		}
			    	
			    	
			    		for(int i=0;i<3;i++)
			    		{
			    			if(correct[i]==1)
			    			{
			    				out.writeUTF((i+1)+" Right");
			    				out.flush();
			    			}else{
			    				out.writeUTF((i+1)+" Wrong");
			    				out.flush();
			    			}
			    		}
			    	 
			    		int correctcount = 0;
			    		for(int c:correct)
			    		{
			    			correctcount = correctcount + c;
			    		}
			    	
			    		System.out.println(correctcount + " correct");
			    	 
			    		out.writeUTF("right: "+correctcount*100.0/3+"%");
			    		out.flush();
			   
			    		if(!this.s.isConnected())
			    		{
			    			System.out.println("Client disconnected!");
			    		}
			    	 
			    	}catch (IOException e){
			    		System.out.println("Sorry!");
			    	}catch (InterruptedException e) {
			    		System.out.println("Oh dear, interrupted.");
			    	}
				}

			}	
		
		//-----------------------------------------------------------------------------------
		
		class HandleQuestions implements Runnable
		{
			private DataOutputStream outH;
			private String[][] qs;
			
			public HandleQuestions (DataOutputStream out, String[][] questions)
			{
				outH = out;
				qs = questions;
			}
			
			public void run()
			{
				try{
					for(int i=0;i< qs.length;i++){
						outH.writeUTF(qs[i][0]);
						outH.flush();
						Thread.sleep(5*1000);
					}
				}catch (IOException e){
					System.out.println("Cannot get questions!");
				}catch (InterruptedException e) {
					System.out.println("Oh dear, interrupted.");
				}
				
			}
		}
	
		//-----------------------------------------------------------------------------------
		
		class HandleAnswers2 implements Runnable
		{
			private DataInputStream inH;
			private String[] x = new String[3];
			
			public HandleAnswers2 (DataInputStream in)
			{
				inH = in;
			}
			
			public String[] getAnswers()
			{
				return x;
			}
			
			public void run()
			{	
				try{
					
					for(int i =0; i< 3; i++)
					{
						while(x[i]==null)
						{
							x[i] = inH.readUTF();
							System.out.println(x[i]);
						}
					}
				}catch (IOException e){
					System.out.println("Cannot get input!");
				}
				
			}
		}