package Bank_system;

import java.util.Scanner;

public class ATM {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Bank theBank = new Bank("Bank Of America");
		
		user aUser = theBank.addUser("Lucky", "Jain", "1234");
		
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		user curUser;
		
		while(true) {
			curUser = ATM.mainMenuPrompt(theBank, sc);
			
			ATM.printUserMenu(curUser, sc);
		}
	}
	public static user mainMenuPrompt(Bank theBank, Scanner sc) {
		String userID;
		String Pin;
		user authUser;
		do {
			System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
			System.out.print("Enter User ID:");
			userID = sc.nextLine();
			System.out.print("Enter Pin:");
			Pin = sc.nextLine();
			
			authUser = theBank.userLogin(userID, Pin);
			if(authUser == null) {
				System.out.print("\nInCorrect UserID/Pin Combination\n" + "Please Try Again");
			}
			
		}while(authUser == null);
		
		return authUser;
	}
	
	public static void printUserMenu(user theUser, Scanner sc) {
		theUser.printAccountSummary();
		
		int choice;
		
		do {
			System.out.printf("Welcome %s, What would you like to do?\n",theUser.getFirstName());
			System.out.println("(1)Show Account Transaction History");
			System.out.println("(2)Withdraw");
			System.out.println("(3)Deposit");
			System.out.println("(4)Transfer");
			System.out.println("(5)Quit");
			System.out.println();
			System.out.print("Enter Choice: ");
			choice = sc.nextInt();
			
			if(choice < 1 || choice > 5) {
				System.out.println("Invalid Choice, Please Enter 1-5");
			}
		}while(choice < 1 || choice > 5);
		
		switch (choice) {
			case 1:
				 ATM.showTransHistory(theUser, sc);
				 break;
			case 2:
				 ATM.withdrawFunds(theUser, sc);
				 break;
			case 3:
				 ATM.depositFunds(theUser, sc);
				 break;
			case 4:
				 ATM.transferFunds(theUser, sc);
				 break;
			case 5:
				//gobble up rest of previous input
				sc.nextLine();
				break;
				 
		}
		if(choice != 5) {
			ATM.printUserMenu(theUser, sc);
		}
	}
	
	public static void showTransHistory(user theUser, Scanner sc) {
		int inAcct;
		
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + 
					"whose transaction you want to see: ", 
					theUser.numAccounts());
			inAcct = sc.nextInt()-1;
			if(inAcct < 0 || inAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please Try Again.");
			}
		}while(inAcct < 0 || inAcct >= theUser.numAccounts());
		theUser.printAcctTransHistory(inAcct);
	}
	
	public static void transferFunds(user theUser, Scanner sc) {
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		do {
			System.out.printf("Enter the number (1-%d) of the account\n"+ 
		"to transfer from: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please Try Again.");
			}
			
		}while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		//get the account to transfer to
		do {
			System.out.printf("Enter the number (1-%d) of the account\n"+
		"to transfer from: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please Try Again.");
			}
			
		}while(toAcct < 0 || toAcct >= theUser.numAccounts());
		
		//get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
			amount  = sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than zero");
			}else {
				System.out.printf("Amount must not be greater than\n" + "balance of $%.02f.\n", acctBal);
			}
		}while(amount < 0 || amount > acctBal);
		
		//finally do the transfer
		theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
				"Transfer to account %s", theUser.getAcctUUID(toAcct)));
	
		theUser.addAcctTransaction(toAcct, amount, String.format(
				"Transfer to account %s", theUser.getAcctUUID(fromAcct)));

	
	}
	public static void withdrawFunds(user theUser, Scanner sc) {
		int fromAcct;
		String memo;
		double amount;
		double acctBal;
		
		do {
			System.out.printf("Enter the number (1-%d) of the account\n"+ 
		"to withdraw from: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please Try Again.");
			}
			
		}while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		//get the amount to transfer
		do {
			System.out.printf("Enter the amount to Withdraw (max $%.02f): $", acctBal);
			amount  = sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than zero");
			}else {
				System.out.printf("Amount must not be greater than\n" + "balance of $%.02f.\n", acctBal);
			}
		}while(amount < 0 || amount > acctBal);
					
		//gobble up rest of previous input
		sc.nextLine();
		
		System.out.print("Enter a Memo: ");
		memo = sc.nextLine();
		
		theUser.addAcctTransaction(fromAcct, -1*amount, memo);
				
				
				
	}
 	
	public static void depositFunds(user theUser, Scanner sc) {
		int toAcct;
		String memo;
		double amount;
		double acctBal;
		
		do {
			System.out.printf("Enter the number (1-%d) of the account\n"+ 
		"to deposit in: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please Try Again.");
			}
			
		}while(toAcct < 0 || toAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(toAcct);
		
		//get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
			amount  = sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than zero");
			} 
		}while(amount < 0 );
					
		//gobble up rest of previous input
		sc.nextLine();
		
		System.out.print("Enter a Memo: ");
		memo = sc.nextLine();
		
		theUser.addAcctTransaction(toAcct, amount, memo);
	}

}
