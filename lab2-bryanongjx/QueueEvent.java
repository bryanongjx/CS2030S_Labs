class QueueEvent extends Event {
  private Shop shop;
  private Customer customer;

  public QueueEvent(Shop shop, Customer customer, double time) {
    super(time);
    this.shop = shop;
    this.customer = customer;
  }

  @Override
  public Event[] simulate() {
    shop.addToQueue(this.customer);
    return new Event[] {};
  }

  @Override
  public String toString() {
    return super.toString() + ": " + customer.toString() + " joined queue " + shop.ShopQueue();
  }
}
