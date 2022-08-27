class Arrival extends Event {
  private final Shop shop; 
  private final Customer customer;
  
   
  public Arrival(Shop shop, Customer customer, double time) {
    super(time);
    this.shop = shop;
    this.customer = customer; 
  }


  @Override 
  public Event[] simulate() {
    Counter counter = customer.goToCounter(this.shop);

    if (counter != null) { 
      if (counter.getAvailable()) {
        counter.setAvailable();
        return new Event[] {
          new ServiceBegin(this.customer, this.getTime(), 
          counter, customer.getServiceTime(), this.shop) 
        };
      } 

      if (!counter.checkQueueFull()) {
        return new Event[] {
          new CounterQueueEvent(counter, this.customer, this.getTime())
        };
      }
    }  

    if (shop.checkQueue()) { 
      return new Event[] {
          new Departure(this.customer, this.getTime())
      };
    } else {
      return new Event[] { 
          new QueueEvent(this.shop, this.customer, this.getTime())
      };
    }    
  }

  @Override
  public String toString() {
    return super.toString() + ": " + this.customer.toString() + " arrived " + shop.shopQueue();
  }
}
