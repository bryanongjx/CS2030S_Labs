class Shop {
  private Array<Counter> counters; 
  private Queue<Customer> queue;

  public Shop(int numOfCounters, int counterQueueSize, int shopQueueSize) {
    this.counters = new Array<Counter>(numOfCounters); 
    this.queue = new Queue<Customer>(shopQueueSize);

    for (int i = 0; i < numOfCounters; i++) {
      counters.set(i, new Counter(i, counterQueueSize)); 
    }
  }

  public Counter getAvailableCounter() {
    Counter availableCounter = null; 

    for (int i = 0; i < counters.length(); i++) {
      if ((counters.get(i)).getAvailable()) {
        availableCounter =  counters.get(i);
        break;
      }
    }
    return availableCounter;
  }

  public Counter getAvailableCounterQueue() {
    Counter availableCounterQueue = counters.min();
    return availableCounterQueue;
  }

  public boolean isThereAvailableCounterQueue() {
    boolean flag = false;
    for (int i = 0; i < counters.length(); i++) {
      if ((counters.get(i)).checkQueueFull() != true) {
        flag = true;
        break;
      }
    }
    return flag;
  }

  public Counter getCounter() {
    Counter counter = counters.min();

    if (counter.getAvailable()) {
      return counter;
    } else if (!counter.checkQueueFull()) {
      return counter;
    } else {
      return null;
    }
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
