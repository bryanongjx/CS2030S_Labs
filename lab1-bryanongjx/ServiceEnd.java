class ServiceEnd extends Event{
	
	private Customer customer;
	private Counter counter;
	private boolean[] available;

	public ServiceEnd(double time, Customer customer, Counter counter, boolean[] available) {
		super(time);
		this.customer = customer;
		this.counter = counter;
		this.available = available;
	}


	@Override
	public Event[] simulate() {
		// The current event is a service-end event.
      		// Mark the counter is available, then schedule
      		// a departure event at the current time.
      		this.available[this.counter.getcounterId()] = true;
      		return new Event[] {
        		new Departure(this.getTime(), this.customer)
		};
	}

	@Override
  	public String toString() {
    		String str = "";
    		str = String.format(": Customer %d service done (by Counter %d)",
        	this.customer.getId(), this.counter.getcounterId());
    		return super.toString() + str;
  	}
}
