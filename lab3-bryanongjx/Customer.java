class Customer {
   
  private int id; 
  private double arrivalTime; 
  private double serviceTime; 

  
  public Customer(double arrivalTime, double serviceTime, int id) {
    this.id = id; 
    this.arrivalTime = arrivalTime; 
    this.serviceTime = serviceTime; 
  }
  
  public double getServiceTime() {
    return this.serviceTime;
  }

  public Counter goToCounter(Shop s) { 
    return s.getCounter(); 
  }

  @Override
  public String toString() {
    return "C" + this.id;
  }
  

}
