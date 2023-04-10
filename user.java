package Bank_system;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class user {
	private String firstName;
	private String lastName;
	private String uuid;
	private byte pinhash[];
	private ArrayList<Account> accounts;
	
	public user(String firstName, String lastName, String pin, Bank theBank) {
		this.firstName = firstName;
		this.lastName = lastName;
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinhash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		} 
		
		this.uuid = theBank.getNewUserUUID();
		
		this.accounts = new ArrayList<Account>();
		
		System.out.printf("New User %s, %s with ID %s created. \n", firstName, lastName, this.uuid);
	}
	
	public void addAccount(Account onAcc) {
		this.accounts.add(onAcc);
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public boolean validatePin(String apin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(apin.getBytes()), 
					this.pinhash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}

	 
	public String getFirstName() {
		return this.firstName;
	}
	
	public void printAccountSummary() {
		System.out.printf("\n\n%s's account's summary\n", this.firstName);
		
		for(int a=0; a<this.accounts.size(); a++) {
			System.out.printf("%d) %s\n", a+1, 
					this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
	}
	
	//Get The Number of account of the user
	//return the number of accounts
	public int numAccounts() {
		 return this.accounts.size();
	}
	
	
	//Print Transaction history for a particular account
	//acctIdx the index of the  account to use
	public void printAcctTransHistory(int acctIdx) {
		this.accounts.get(acctIdx).printTransHistroy();
	}
	
	
	//get the balance of particular account
	//acctIdx   the index of the account to use
	//          the balance of the account
	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();
	}
	
	//Get the uuid of the particular account
	//          the index of the account to use
	//          the uuid of the account
	public String getAcctUUID(int acctIdx) {
		return this.accounts.get(acctIdx).getUUID();
	}
	
	//Add A transaction to a particular account
	// param    acctIdx     the index of the account
	// param    amount      the amount of the transaction
	// param    memo        the memo of the transaction
	
	public void addAcctTransaction(int acctIdx, double amount, String memo){
		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}

	 
}
