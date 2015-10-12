import java.util.Random;
import java.lang.Thread ;

/**
 * Transaction
 * Manages the specific transaction on a BankAccount object
 * 
 * @author Gianluca Carroccia
 *
 */
public class Transaction implements Runnable {

	private int transID;
	private BankAccount transAccount;
	private String transPassword;
    private double transAmount;
    private int transType;
    private int transRepetition;

	Random randomGenerator = new Random();
 
	/**
	 * Transaction constructor without amount
	 * 
	 * @param account
	 * @param p
	 * @param type
	 * @param repetition	
	 */
    public Transaction(BankAccount account, String p, String type, int repetition) {
    	
    	transID = randomGenerator.nextInt(99);		//Transaction identification number.
    												//Here it is used a random number but a real system
    												//should provide a Transaction Database in which
    												//each Transaction is associated to a unique ID.
    	
    	if (type == "balance" || type == "Balance"){	//should be provide different choices depending the user interface.
    		transAccount = account;
    		transPassword = p;							//the password provided for the transaction could be different
    													//by the password associated to the account
    		transType = 3;								//the "3" value should be replaced by a constant value 
    													//indicating the transaction type
    		transRepetition = repetition;				//how many times the transaction have to be repeated
    	}
    	else 
    		System.out.println("Invalid Transaction [id: "+ transID +"]");
    }
    
	/**
	 * Transaction constructor with amount
	 * 
	 * @param account
	 * @param p
	 * @param type
	 * @param amount
	 * @param repetition
	 */
    public Transaction(BankAccount account, String p, String type, double amount, int repetition) {
    	
    	transID = randomGenerator.nextInt(99);		//Transaction identification number.
													//Here it is used a random number but a real system
													//should provide a Transaction Database in which
													//each Transaction is associated to a unique ID.
    							
    	
    	if (type == "deposit" || type == "Deposit"){	//should be provide different choices depending the user interface.
    		
    		transAccount = account;
    		transPassword = p;	
    	    transAmount = amount;
    		transType = 1;								//the "1" value should be replaced by a constant value 
														//indicating the transaction type
    		transRepetition = repetition;
    	}
    	else if (type == "withdraw" || type == "Withdraw"){	//should be provide different choices depending the user interface.

    		transAccount = account;
    		transPassword = p;
    	    transAmount = amount;
    		transType = 2;								//the "2" value should be replaced by a constant value 
														//indicating the transaction type
    		transRepetition = repetition;
    	}
    	else
        	System.out.println("Invalid Transaction [id: "+ transID +"]");
    			
    }
 
    public void run() {

    	int accountID = transAccount.getId(transPassword);		//obtain the account ID
    	
    	if (accountID == -1)
			System.out.println("[T. " + transID + "] Transaction Access Denied");
    	else{
    		
        	double retValue = 0;
        	int waitTime;									
    		
    		for ( int i = 0; i< transRepetition; i++){		//how many times the Transaction have to be repeated
        		
    			waitTime = randomGenerator.nextInt(100);	//delay time
    			try{
    				Thread.currentThread().sleep((int)waitTime);	//delay
    			}
    			catch (InterruptedException e){
    				e.printStackTrace();
    			} 
    			
    			if(transType == 1){																//deposit
    			    retValue = transAccount.deposit(accountID, transPassword, transAmount);		//bank operation
    	    		if(retValue >= 0)															//results
    	    			System.out.println("[T. " + transID + "] Deposit: Â£" + retValue);
    				else if (retValue == -1)
    		    		System.out.println("[T. " + transID + "] Deposit: Access Denied.");
    				else if (retValue == -3)
    					System.out.println("[T. " + transID + "] Deposit: The amount to deposit must be positive.");
    			} 
    			
    			else if (transType == 2){														//withdraw
    			    retValue = transAccount.withdraw(accountID, transPassword, transAmount);	//bank operation
    	    		if(retValue >= 0)															//results
    	    			System.out.println("[T. " + transID + "] Withdraw: Â£" + retValue);
    				else if (retValue == -1)
    		    		System.out.println("[T. " + transID + "] Withdraw:  Access Denied.");
    				else if (retValue == -2)
    					System.out.println("[T. " + transID + "] Withdraw:  Not enough money in account.");
    			}
    			
    			else if (transType == 3){														//show balance
    			    retValue = transAccount.getBalance(accountID, transPassword);				//bank operation
    	    		if(retValue >= 0)															//results
    	    			System.out.println("[T. " + transID + "] Balance: Â£" + retValue);
    				else if (retValue == -1)
    		    		System.out.println("[T. " + transID + "] Balance: Access Denied.");
    			}	
        	}
    	}
    }
}
	