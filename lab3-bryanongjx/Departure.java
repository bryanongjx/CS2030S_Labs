class Departure extends Event {
  private final Customer customer;
  
  public Departure(Customer customer, double time) {
    super(time);
    this.customer = customer; 
  }

  @Override
  public Event[] simulate() {
    return new Event[0];
  }

 
  @Override
  public String toString() {
    return super.toString() + ": " + this.customer.toString() + " departed";
  }

}
