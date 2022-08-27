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
    (this.counter).setAvailable(); 
    Customer nextCustomerInCounterQueue = (this.counter).nextInCounterQueue();

    if (nextCustomerInCounterQueue == null) {
      Customer nextCustomerInShopQueue = (this.shop).nextInQueue();
      if (nextCustomerInShopQueue == null) {
        return new Event[] {
          new Departure(this.customer, this.getTime())
        };
      } else {
        return new Event[] {
          new Departure(this.customer, this.getTime()),
          new ServiceBegin(nextCustomerInShopQueue, this.getTime(),
          counter, nextCustomerInShopQueue.getServiceTime(), this.shop)
        };
      }
    } else {
      Customer nextCustomerInShopQueue = (this.shop).nextInQueue();
      if (nextCustomerInShopQueue == null) {
        return new Event[] {
          new Departure(this.customer, this.getTime()),
          new ServiceBegin(nextCustomerInCounterQueue,
          this.getTime(), counter, nextCustomerInCounterQueue.getServiceTime(), this.shop)
        };
      } else {
        return new Event[] {
          new Departure(this.customer, this.getTime()),
          new ServiceBegin(nextCustomerInCounterQueue, this.getTime(),
              counter, nextCustomerInCounterQueue.getServiceTime(), this.shop),
          new CounterQueueEvent(this.counter, nextCustomerInShopQueue, this.getTime())
        };
      }
    }
  }


  @Override
  public String toString() {
    return super.toString() + ": " + this.customer.toString()
           + " service done (by " + this.counter.toString()
           + " " + counter.counterQueue() + ")";
  }

}

