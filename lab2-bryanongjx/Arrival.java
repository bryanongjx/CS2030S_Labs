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
    Counter availableCounter = shop.getAvailableCounter();
    boolean queueFull = shop.checkQueue();

   
    if (availableCounter == null) {

      if (queueFull) {
        return new Event[] {
          new Departure(this.customer, this.getTime())
        };
      } else {
        return new Event[] {
          new QueueEvent(this.shop, this.customer, this.getTime())
        };
      }
    } else {
      return new Event[] {
        new ServiceBegin(this.customer, this.getTime(), availableCounter,
        (this.customer).getServiceTime(), this.shop)
      };
    }
  }

  @Override
  public String toString() {
    return super.toString() + ": " + this.customer.toString() + " arrived " + shop.ShopQueue();
  }


}
