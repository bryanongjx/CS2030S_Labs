class Box<T> {

  private final T content;
  private static final Box<?> EMPTY_BOX = new Box<>(null);
  
  public Box(T content) {
    this.content = content;
  }

  public static <T> Box<T> empty() {
    @SuppressWarnings("unchecked")
    Box<T> empty = (Box<T>) Box.EMPTY_BOX;
    return empty;
  }

  public boolean isPresent() {
    return this.content != null;
  }

  public static <T> Box<T> ofNullable(T obj) {
    if (obj == null) {
      return empty();
    } else {
      return Box.of(obj);
    }
  }  
  
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof Box<?>) {
      Box<?> b = (Box<?>) obj;
      if (this.content == b.content) {
        return true;
      }
      if (this.content == null || b.content == null) {
	return false;
      }
      return this.content.equals(b.content);
    }
    return false;
  }    

  @Override
  public String toString() {
    if (this.content == null) {
      return "[]";
    } else {
      return "[" + this.content + "]";
    }
  }

  public static <T> Box<T> of(T obj) {
    if (obj == null) {
      return null;
    }
    return new Box<T>(obj);
  }

  public Box<T> filter(BooleanCondition<? super T> condition) {
    if (this.content == null) {
      return Box.empty();
    } else {
      if (condition.test(this.content)) {
        return this;
      } else {
        return Box.empty();
      }
    }
  }

  public <U> Box<U> map(Transformer<? super T, ? extends U> transformer) {
    if (this.content == null) {
      return Box.empty();
    } else {
      @SuppressWarnings("unchecked")
      U a = (U) transformer.transform(this.content);
      return new Box<U>(a);
    }
  }
}
