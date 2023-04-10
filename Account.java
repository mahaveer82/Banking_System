package Bank_system;

import java.util.ArrayList;
public class Account {
	private String name;
	private String uuid;
	private user holder;
	private ArrayList<Transaction> transaction;
	
	public Account(String name, user holder, Bank theBank) {
		this.name = name;
		this.holder = holder;
		this.uuid = theBank.getNewAccountUUID();
		this.transaction = new ArrayList<Transaction>();
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public String getSummaryLine() {
		double balance = this.getBalance();
		
		if(balance >= 0) {
			return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
		}else {
			return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
			
		}
	}
	
	public double getBalance() {
		double balance = 0;
		for(Transaction t : this.transaction) {
			balance += t.getAmount();
		}
		return balance;
	}
	
	public void printTransHistroy() {
		System.out.printf("\nTransaction History for account %s\n", this.uuid);
		for(int t = this.transaction.size()-1; t>=0; t--) {
			System.out.println(this.transaction.get(t).getSummaryLine());
		}
		System.out.println();
	}
	
	public void addTransaction(double amount, String memo) {
		// Create   new transaction object  and add it to our list
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transaction.add(newTrans); 
	}
}
