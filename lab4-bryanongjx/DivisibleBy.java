class DivisibleBy implements BooleanCondition<Integer> {
  
  private Integer integer;

  public DivisibleBy(Integer integer) {
    this.integer = integer;
  }
       	
  public boolean test(Integer arg) {
    return arg % (this.integer) == 0;
  }
} 

