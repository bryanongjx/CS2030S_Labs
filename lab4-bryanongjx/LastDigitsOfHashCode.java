class LastDigitsOfHashCode implements Transformer<Object,Integer>{
  private int k;

  public LastDigitsOfHashCode(int k) {
    this.k = k;
  }
 
  @Override
  public Integer transform(Object obj) {
    
    return Math.abs(obj.hashCode()) % (int) Math.pow(10, k);
  }
} 

