import java.net.*;
import java.io.*;
import java.lang.*;

public class Fib1 {

	public static void main(String argv[]) {
	
		try {
			ServerSocket sSoc = new ServerSocket(2001);
			
			while(true) {
				Socket inSoc = sSoc.accept();
				
				FibThread FibT = new FibThread(inSoc);
				
				FibT.start();}
		}
		catch (Exception e) {
			System.out.println("Oh Dear! " + e.toString());
		}
	}
}

class FibThread extends Thread {

	Socket threadSoc;

	int F1 = 1;
	int F2 = 1;

	FibThread(Socket inSoc) {
		threadSoc = inSoc;
	}
	
	public void run() {
		try {
			PrintStream FibOut = new PrintStream(threadSoc.getOutputStream());
						
			while (true) {
                                int temp;

                                temp = F1;

				FibOut.println(F1);
				Thread.sleep(500);

                                F1 = F2;
				F2 = temp + F2;
			}
		}
		catch (Exception e) {
			System.out.println("Whoops! " + e.toString());
		}
		
		try {
			threadSoc.close();
		}
		catch (Exception e) {
			System.out.println("Oh no! " + e.toString());
		}
	}
}