import java.lang.Thread;

/**
 * Main
 * Tests the functionality, the reliability and the correctness of the system 
 * 
 * @author Gianluca Carroccia
 *
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        
        Thread t[] = new Thread[6];													//6 Threads are tested
        
        System.out.println("*** Bank Account Test***");
        System.out.println("");
    	System.out.println("Creation of the Gianluca Carroccia's account");
        System.out.println("");

    	BankAccount accounts[] = new BankAccount[1]; 								//accounts' array	
    	accounts[0] = new BankAccount(1, "pwd", 1000, "Gianluca", "Carroccia");		//my account

		System.out.println("Thread 1: deposit £10, 100 times with the right password");
		t[0] = new Thread(new Transaction(accounts[0], "pwd", "deposit", 10, 100));			//creation of a new Thread
																							//performing a specific transaction
        
		System.out.println("Thread 2: withdrow £15, 100 times with the right password");
		t[1] = new Thread(new Transaction(accounts[0], "pwd", "withdraw", 15, 100));
        
		System.out.println("Thread 3: show the balance, 100 times with the right password");
		t[2] = new Thread(new Transaction(accounts[0], "pwd", "balance", 100));
        
		System.out.println("Thread 4: deposit £10, 50 times with the wrong password");
		t[3] = new Thread(new Transaction(accounts[0], "wrong pwd", "deposit", 10, 50));
        
		System.out.println("Thread 5: deposit £10, 50 times with the wrong password");
		t[4] = new Thread(new Transaction(accounts[0], "wrong pwd", "withdraw", 10, 50));
        
		System.out.println("Thread 6: show the balance, 50 times with the wrong password");
		t[5] = new Thread(new Transaction(accounts[0], "wrong pwd", "balance", 50));

        
        System.out.println("");
        
        
        for (int i = 0; i < 6; i++){			//starts the Threads
        	t[i].start();
        }
        
        for (int i = 0; i < 6; i++){			//wait for the Threads end
        	t[i].join();
        }
        
        System.out.println("");
        System.out.println("");
        Thread tBalance = new Thread(new Transaction(accounts[0], "pwd", "balance", 1));		//show the final balance
        tBalance.start();
    }
     
}
