class Array<T extends Comparable<T>> { // TODO: Change to bounded type parameter
  private T[] array;

  Array(int size) {
    @SuppressWarnings("unchecked")
    T[] arr = (T[]) new Comparable[size];
    this.array = arr;
  }

  public void set(int index, T item) {
    this.array[index] = item;
  }

  public T get(int index) {
    return this.array[index];
  }

  public int length() {
    return array.length;
  }

  public T min() {
    T min = null;
    for (int i = 0; i < array.length; i++) {
      if (min == null) {
        min = array[i];
      }
      if (min.compareTo(array[i]) > 0) {
        min = array[i];
      }
    }
    return min;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("[ ");
    for (int i = 0; i < array.length; i++) {
      s.append(i + ":" + array[i]);
      if (i != array.length - 1) {
        s.append(", ");
      }
    }
    return s.append(" ]").toString();
  }
}
