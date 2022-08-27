
class Counter { 
  private int id; 
  private boolean available; 

  public Counter(int id) {
    this.id = id; 
    this.available = true; 
  }

  public boolean getAvailable() { 
    return this.available; 
  }
  
  public int getId() {
    return this.id;
  }

  public void setAvailable(boolean available) { 
    this.available = available; 
  }

  @Override
  public String toString() {
    return "S" + this.id;
  }
}

