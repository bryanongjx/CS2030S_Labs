class BoxIt<T> implements Transformer<T, Box<T>> {
  
  @Override
  public Box<T> transform(T obj) {
    return Box.of(obj);
  }
}
