
class Counter implements Comparable<Counter> { 
  private int id; 
  private boolean available;
  private Queue<Customer> queue;  
  

  public Counter(int id, int counterQueueSize) {
    this.id = id; 
    this.available = true; 
    this.queue = new Queue<Customer>(counterQueueSize);
  }

  public boolean getAvailable() { 
    return this.available; 
  }
  
  public int getId() {
    return this.id;
  }

  public void setAvailable() { 
    this.available = true; 
  }

  public void setNotAvailable() {
    this.available = false;
  }
  
  public boolean addToCounterQueue(Customer customer) {
    return queue.enq(customer);
  }

  public Customer nextInCounterQueue() {
    return (Customer) queue.deq();
  }

  public boolean checkQueueFull() {
    return queue.isFull();
  }
  
  public String counterQueue() {
    return queue.toString();
  }

  @Override
  public int compareTo(Counter counterToCompare) {
    if (this.queue.length() == counterToCompare.queue.length()) {
      if (this.available && counterToCompare.available) {
        return this.id - counterToCompare.id;
      }
      
      if (this.available && !counterToCompare.available) {
        return -1;
      }

      if (!this.available && counterToCompare.available) {
        return 1; 
      }

      if (!this.available && !counterToCompare.available) {
        return this.id - counterToCompare.id;  
      }
    } 
    
    return this.queue.length() - counterToCompare.queue.length(); 
  }

  @Override
  public String toString() {
    return "S" + this.id;
  }
}

