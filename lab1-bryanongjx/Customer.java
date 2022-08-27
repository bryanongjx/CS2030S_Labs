class Customer {
	
	private final int customerId;
	private double arrivalTime;
	private double serviceTime;

	public Customer(int customerId, double arrivalTime, double serviceTime) {
		this.customerId = customerId;
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
	}

	public int getId() {
		return this.customerId;
	}

}	
