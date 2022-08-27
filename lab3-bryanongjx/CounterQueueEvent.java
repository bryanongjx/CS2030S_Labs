class CounterQueueEvent extends Event {
  private Counter counter;
  private Customer customer;

  public CounterQueueEvent(Counter counter, Customer customer, double time) {
    super(time);
    this.counter = counter;
    this.customer = customer;
  }

  @Override
  public Event[] simulate() {
    counter.addToCounterQueue(this.customer);
    return new Event[] {};
  }

  @Override
  public String toString() {
    return super.toString() + ": " + customer.toString()
           + " joined counter queue (at " + counter.toString()
           + " " + counter.counterQueue() + ")";
  }

}
