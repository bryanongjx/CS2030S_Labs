class LongerThan implements BooleanCondition<String> {
  
  private final int limit;

  public LongerThan(int limit) {
    this.limit = limit;
  }

  public boolean test(String string) {
    return string.length() > this.limit;
  }
}
