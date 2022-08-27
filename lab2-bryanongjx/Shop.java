class Shop {
  private Counter[] counters; 
  private Queue queue;

  public Shop(int numOfCounters, int queueSize) {
    counters = new Counter[numOfCounters]; 

    for (int i = 0; i < numOfCounters; i++) {
      counters[i] = new Counter(i); 
    }

    this.queue = new Queue(queueSize);
  }

  public Counter getAvailableCounter() {
    Counter availableCounter = null; 

    for (int i = 0; i < counters.length; i++) {
      if (counters[i].getAvailable()) {
        availableCounter =  counters[i];
        break;
      }
    }

    return availableCounter;
  }

  public boolean addToQueue(Customer customer) {
    return queue.enq(customer);
  }

  public Customer nextInQueue() {
    return (Customer) queue.deq();
  }

  public boolean checkQueue() {
    return queue.isFull();
  }

  public String shopQueue() {
    return queue.toString();
  }
}
