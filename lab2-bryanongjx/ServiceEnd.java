class ServiceEnd extends Event {
  private final Customer customer;
  private final Counter counter; 
  private Shop shop;

  // Constructor 
  public ServiceEnd(Customer customer, double time, Counter counter, Shop shop) {
    super(time);
    this.customer = customer;
    this.counter = counter;
    this.shop = shop; 
  }

  @Override 
  public Event[] simulate() {
    (this.counter).setAvailable(true); 
    Customer nextCustomer = (this.shop).nextInQueue();

    if (nextCustomer != null) {  
      return new Event[] {
          new Departure(this.customer, this.getTime()), 
          new ServiceBegin(nextCustomer, this.getTime(), counter, 
              nextCustomer.getServiceTime(), this.shop)
      };
    } else { 
      return new Event[] { 
          new Departure(this.customer, this.getTime())
      };
    }
  }

  @Override
  public String toString() {
    return super.toString() + ": " + this.customer.toString()
           + " service done (by " + this.counter.toString() + ")";
  }

}

