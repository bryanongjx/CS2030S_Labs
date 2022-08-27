class Arrival extends Event {

	private Customer customer;
	private double serviceTime;
	private boolean[] available;

	public Arrival(Customer customer, double time, double serviceTime, boolean[] available) {
		super(time);
		this.customer = customer;
		this.serviceTime = serviceTime;
		this.available = available;
	}


	@Override
	public Event[] simulate() {

		int counter = -1;

		for (int i = 0; i < this.available.length; i++) {
		       if (this.available[i]) {
		       counter = i;
		       break;
		       }
		}

		if (counter == -1) {
			return new Event[] {
				new Departure(this.getTime(), this.customer) 
 			};
		}

		else {
			return new Event[] {
				new ServiceBegin(this.customer, new Counter(counter), this.getTime(), this.serviceTime, this.available)   
			};
		}
	}


	 @Override
 	 public String toString() {
    		String str = "";
    		str = String.format(": Customer %d arrives", this.customer.getId());
    		return super.toString() + str;
 	}		

		
}

