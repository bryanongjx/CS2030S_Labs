class ServiceBegin extends Event {
  private final Customer customer;
  private final Counter counter; 
  private final double serviceTime; 
  private Shop shop;
   
  public ServiceBegin(Customer customer, double time,
         Counter counter, double serviceTime, Shop shop) {
    super(time);
    this.customer = customer;
    this.counter = counter; 
    this.serviceTime = serviceTime; 
    this.shop = shop;
  }

  @Override
  public Event[] simulate() {
    (this.counter).setNotAvailable();
    double endTime = this.getTime() + this.serviceTime;
    return new Event[] {
      new ServiceEnd(this.customer, endTime, this.counter, this.shop)
    };
  }



  @Override
  public String toString() {
    return super.toString() + ": " + this.customer.toString()
           + " service begin (by " + this.counter.toString() +
           " " + counter.counterQueue() + ")"; 
  }

}
