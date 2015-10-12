/**
 * BankAccount - Safe version
 * Manages the basic functionalities of a bank account
 * 
 * @author Gianluca Carroccia
 * 
 */
public class BankAccount {
	
	private int id; 			//account identificator
	String password;			//user password
	private double balance;		//user balance
	private String name;		//user name
	private String surname;		//user surname

	/**
	 * BankAccount full constructor
	 * 
	 * @param id 
	 * @param p
	 * @param b
	 * @param n
	 * @param s
	 */
    public BankAccount(int id, String p, double b, String n, String s) {
    	this.id  = id;
        password = p;
        balance = b;
    	name = n;
    	surname = s;
    }
    
    /**
     * BankAccount basic constructor with balance
     * 
     * @param id
     * @param b
     */
    public BankAccount(int id, double b) {
    	this.id = id;
        password = "";
        balance = b;
    	name = "";
    	surname = "";
    }

    /**
     * BankAccount basic constructor
     * 
     * @param id
     */
    public BankAccount(int id) {
    	this.id = id;
        password = "";
        balance = 0;
    	name = "";
    	surname = "";
    }
    

    public String getUser(int id, String p){
    	if (this.id == id && p == password)
    		return (surname + " " + name);
    	else
    		return null;
    }
    
    synchronized public boolean setUser(int id, String p, String name, String surname) {
   	if (this.id == id && p == password){
       	this.name = name;
       	this.surname = surname;
       	return true;
       }
       else
   		return false;
    }
    
    public int getId(String name, String surname, String p){
    	if (this.name == name && this.surname == surname && p == password)
    		return id;
    	else
    		return -1;
    }
    
    public int getId(String p){
    	if (p == password)
    		return id;
    	else
    		return -1;
    }
    
    public String getPassword(int id, String p){
    	if (this.id == id && p == password)
    		return password;
    	else
    		return null;
    }
    
    synchronized public boolean setPassword(int id, String p) {
   	if (this.id == id && this.password == ""){	
   		this.password = p;
   		return true;
       }
       else
   		return false;
    }
    
    public double getBalance(int id, String p) {
    	if (this.id == id && p == password)
    		return balance;
    	else
    		return -1;
    }
    
     synchronized public double setBalance(int id, String p, double amount) {
    	if (this.id == id && p == password){
        	balance = amount;
        	return balance;
        }
        else
    		return -1;
     }
     
     synchronized public double resetBalance(int id, String p) {
    		return setBalance(id, p, 0);
    }
       
     /**
      * Withdraw money from the account.
      * Possible errors: "Access Denied" and "Not Enough Money"
      * 
      * @param id
      * @param p
      * @param amount
      * @return
      */
     synchronized public double withdraw(int id, String p, double amount) {
    	if (this.id == id && p == password){
        	if(amount <= balance){
        		balance -= amount;
        		return amount;
	        }
	        else
	            return -2;		//not enough money
        }
        else
    		return -1;
    }
    
     /**
      * Deposit money on the account
      * Possible errors: "Access Denied" and "Invalid argument"
      * 
      * @param id
      * @param p
      * @param amount
      * @return
      */
     synchronized public double deposit(int id, String p, double amount) {
    	if (this.id == id && p == password){
			if( amount > 0){
		        balance += amount;
				return amount;
		    }
	    	else
	            return -3;		//invalid argument
        }
        else
    		return -1;

     }
}

