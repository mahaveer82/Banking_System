package Bank_system;

import java.util.ArrayList;
import java.util.Random;
public class Bank {
	private String name;
	private ArrayList<user> users;
	private ArrayList<Account> accounts;
	
	public Bank(String name) {
		this.name = name;
		this.users = new ArrayList<user>();
		this.accounts = new ArrayList<Account>();
	}
	
	public String getNewUserUUID() {
		String uuid;
		Random rng = new Random();
		int len = 6;
		boolean nonUnique;
		do {
			uuid="";
			for(int c=0; c<len; c++) {
				uuid+=((Integer)rng.nextInt(10)).toString();
			}
			nonUnique = false;
			for(user u : this.users) {
				if(uuid.compareTo(u.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
		}while(nonUnique);
		
		return uuid;
		
		
	}

	public String getNewAccountUUID() {
		String uuid;
		Random rng = new Random();
		int len = 10;
		boolean nonUnique;
		do {
			uuid="";
			for(int c=0; c<len; c++) {
				uuid+=((Integer)rng.nextInt(10)).toString();
			}
			nonUnique = false;
			for(Account a : this.accounts) {
				if(uuid.compareTo(a.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
		}while(nonUnique);
		
		return uuid;
	}
	
	public void addAccount(Account onAcc) {
		this.accounts.add(onAcc);
	}
	
	public user addUser(String firstName, String lastName, String pin) {
		user newUser = new user(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);
		
		return newUser;
	}
	
	public user userLogin(String userID, String pin) {
		for(user u : this.users) {
			if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
				return u;
			}
		}
		return null;
	}
	
	public String getName() {
		return this.name;
	}

}
