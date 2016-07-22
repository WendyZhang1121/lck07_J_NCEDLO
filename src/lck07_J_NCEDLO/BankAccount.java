package lck07_J_NCEDLO;

final class BankAccount {
	private double balanceAmount; // Total amount in bank account
	BankAccount(double balance) { 
		this.balanceAmount = balance;
	}
	// Deposits the amount from this object instance
	// to BankAccount instance argument ba

	private void depositAmount(BankAccount ba, double amount) {
		synchronized (this) { 
			synchronized (ba) {
				if (amount > balanceAmount) {
					throw new IllegalArgumentException( "Transfer cannot be completed"
			); }
			ba.balanceAmount += amount;
			this.balanceAmount -= amount; 
			}
		} 
	}
	
	public static void initiateTransfer(final BankAccount first, final BankAccount second, final double amount) {

		Thread transfer = new Thread(new Runnable() {
			public void run() {
				first.depositAmount(second, amount);
				}
			});
			   transfer.start();
	}
	
	public static void main(String[] args) { 
		BankAccount a = new BankAccount(5000); 
		BankAccount b = new BankAccount(6000); 
		initiateTransfer(a, b, 1000); // starts thread 1 
		initiateTransfer(b, a, 1000); // starts thread 2
	} 
}