class ServiceBegin extends Event{

	private double serviceTime;	
	private Customer customer;
	private Counter counter;
	private boolean[] available;


	public ServiceBegin(Customer customer, Counter counter, double time, double serviceTime, boolean[] available) {
		super(time);
		this.customer = customer;
		this.counter = counter;
		this.serviceTime = serviceTime;
		this.available = available;
	}	


	

	@Override
	public Event[] simulate() {
		// The current event is a service-begin event.
	        // Mark the counter is unavailable, then schedule
     	        // a service-end event at the current time + service time.
     	        this.available[this.counter.getcounterId()] = false;
     	        double endTime = this.getTime() + this.serviceTime;
     	        return new Event[] {
       	        new ServiceEnd(endTime, this.customer,
            	this.counter, this.available)
		};
	}

	@Override
  	public String toString() {
    		String str = "";
    		str = String.format(": Customer %d service begin (by Counter %d)",
          	this.customer.getId(), this.counter.getcounterId());
    		return super.toString() + str;
  	}	
}
