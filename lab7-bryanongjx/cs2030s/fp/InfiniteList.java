package cs2030s.fp;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * CS2030S Lab 7.
 * AY21/22 Semester 2.
 *
 * @author CS2030S Ong Jin Xiang, Bryan Lab10L
 *
 */

public class InfiniteList<T> {

  private final Lazy<Maybe<T>> head;
  private final Lazy<InfiniteList<T>> tail;
  private static final InfiniteList<?> SENTINEL = new Sentinel();
  

  /**
   * Private constructor to initialise InfiniteList.
   *
   */
  private InfiniteList() { 
    this.head = null; 
    this.tail = null;
  }

  /**
   * Private constructor to initialise InfiniteList.
   *
   * @param head (type T)
   * @param tail (type Producer)
   *
   */
  private InfiniteList(T head, Producer<InfiniteList<T>> tail) {
    this.head = Lazy.of(Maybe.some(head));
    this.tail = Lazy.of(() -> tail.produce());
  }
  
  /**
   * Private constructor to initialise InfiniteList.
   *
   * @param head (type Lazy)
   * @param tail (type Lazy)
   *
   */
  private InfiniteList(Lazy<Maybe<T>> head, Lazy<InfiniteList<T>> tail) {
    this.head = head;
    this.tail = tail;
  }

  
  public boolean isSentinel() {
    return false;
  }
  
  /**
   * Method to return head of InfiniteList.
   *
   * @return head(type T)
   *
   */
  public T head() {
    Maybe<T> tempHead = this.head.get();
    return tempHead.orElseGet(() -> this.tail.get().head());
  }
  
  /**
   * Method to return tail of InfiniteList.
   *
   * @return tail(type InfiniteList)
   *
   */
  public InfiniteList<T> tail() {
    Maybe<T> tempHead = this.head.get();
    if (tempHead.equals(Maybe.none())) {
      return this.tail.get().isSentinel() ? sentinel() : this.tail.get().tail();
    }
    return this.tail.get();
  }


  /**
   * Static method to create an InfiniteList.
   *
   * @param producer Producer
   * @param <T> type of elements in InfiniteList
   * @return InfiniteList
   *
   */
  public static <T> InfiniteList<T> generate(Producer<T> producer) {
    return new InfiniteList<>(
      Lazy.of(() -> Maybe.some(producer.produce())),
      Lazy.of(() -> InfiniteList.generate(producer))
      );
  }

  /**
   * Static method to create an InfiniteList.
   *
   * @param seed T
   * @param next Transformer
   * @param <T> type of elements in InfiniteList
   * @return InfiniteList
   *
   */
  public static <T> InfiniteList<T> iterate(T seed, Transformer<T, T> next) {
    return new InfiniteList<>(
      seed,
        () -> iterate(next.transform(seed), next)
      );
  }

  /**
   * Method that lazily applies the given transformation to each element in the list
   * and returns the resulting 'InfiniteList'.
   *
   * @param mapper Transformer
   * @param <R> type of elements in InfiniteList
   * @return InfiniteList
   *
   */
  public <R> InfiniteList<R> map(Transformer<? super T, ? extends R> mapper) {
    return new InfiniteList<>(
      Lazy.of(() -> Maybe.some(mapper.transform(this.head()))),
      Lazy.of(() -> this.tail().map(mapper))
      );
  }
  
  /**
   * Method that filters out elements in the list that fails a given BooleanCondition
   * and marks any filtered element as Maybe.none() returning a lazily filtered InfiniteList.
   *
   * @param predicate BooleanCondition
   * @return InfiniteList
   *
   */
  public InfiniteList<T> filter(BooleanCondition<? super T> predicate) {
    Lazy<Maybe<T>> tempHead = Lazy.of(() -> {
      if (predicate.test(this.head())) {
        return Maybe.some(this.head());
      } else {
        return Maybe.none();
      }
    }
    );
    return new InfiniteList<>(tempHead, Lazy.of(() -> this.tail().filter(predicate)));
  }
  
  /**
   * Static method that returns a sentinel (empty list).
   *
   * @param <T> type of elements in InfiniteList
   * @return InfiniteList
   *
   */
  public static <T> InfiniteList<T> sentinel() {
    @SuppressWarnings("unchecked")
    InfiniteList<T> result = (InfiniteList<T>) SENTINEL;
    return result;
  }
  
  /**
   * Method that takes in a value 'n' and truncates the InfiniteList to a finite
   * list of at most n elements.
   *
   * @param n long
   * @return InfiniteList
   *
   */
  public InfiniteList<T> limit(long n) {
    if (n <= 0) {
      return InfiniteList.sentinel();
    } else {
      return new InfiniteList<>(this.head, Lazy.of(() -> this.head.get() == Maybe.none()
        ? this.tail.get().limit(n) : this.tail.get().limit(n - 1)));
    }
  }

  /**
   * Method that collects all elements in the InfiniteList into a java.util.List.
   *
   * @return List
   *
   */
  public List<T> toList() {
    InfiniteList<T> temp = this;
    List<T> result = new ArrayList<T>();
    while (!(temp.isSentinel())) {
      result.add(temp.head());
      temp = temp.tail();
    }
    return result;
  }


  /**
   * Method that takes in a BooleanCondition and truncates the list as soon
   * as it finds an element that evaluates the conditioin to false.
   *
   * @param predicate BooleanCondition
   * @return InfiniteList
   *
   */
  
  public InfiniteList<T> takeWhile(BooleanCondition<? super T> predicate) {
    Lazy<Boolean> condition = Lazy.of(() -> this.head()).filter(predicate);
    return new InfiniteList<T>(
        Lazy.of(() -> condition.get()
          ? Maybe.some(this.head())
          : Maybe.none()),
        Lazy.of(() -> condition.get() && predicate.test(this.tail().head())
          ? this.tail().helper(predicate)
          : sentinel())
        );
  }
  
  /**
   * helper method for takeWhile.
   *
   * @param predicate BooleanCondition
   * @return InfiniteList
   */
  public InfiniteList<T> helper(BooleanCondition<? super T> predicate) {
    return new InfiniteList<T>(
        this.head(), () -> predicate.test(this.tail().head())
        ? this.tail().helper(predicate)
        : sentinel());
  } 
  
  /**
   * Method that takes in a combiner and initial value and returns result
   * of applying the combiner to every element in the InfiniteList.
   *
   * @param identity U
   * @param accumulator Combiner
   * @param <U> type of output of combiner
   *
   * @return U
   *
   */
  public <U> U reduce(U identity, Combiner<U, ? super T, U> accumulator) {
    return accumulator.combine(this.tail().reduce(identity, accumulator), this.head());
  }
  
  /**
   * Method that returns length of the list.
   *
   * @return long
   *
   */
  public long count() {
    long value = this.head.get().equals(Maybe.none()) ? 0L : 1L;
    return this.tail.get().count() + value;  
  }

  /**
   * Method that returns a string representation of the InfiniteList.
   *
   * @return String
   *
   */
  @Override
  public String toString() {
    return "[" + this.head + " " + this.tail + "]";
  }
  
  
  private static class Sentinel extends InfiniteList<Object> {
    
    private Sentinel() {
      super();
    }

    @Override
    public Object head() throws NoSuchElementException {
      throw new java.util.NoSuchElementException();
    }

    @Override
    public InfiniteList<Object> tail() throws NoSuchElementException {
      throw new java.util.NoSuchElementException();
    }

    @Override
    public <R> InfiniteList<R> map(Transformer<? super Object, ? extends R> mapper) {
      return sentinel();
    }

    @Override
    public InfiniteList<Object> filter(BooleanCondition<? super Object> predicate) {
      return sentinel();
    }

    @Override
    public InfiniteList<Object> limit(long n) {
      return sentinel();
    }

    @Override
    public InfiniteList<Object> takeWhile(BooleanCondition<? super Object> predicate) {
      return sentinel();
    }

    @Override
    public <U> U reduce(U identity, Combiner<U, ? super Object, U> accumulator) {
      return identity;
    }

    @Override
    public long count() {
      return 0L;
    }

    @Override
    public boolean isSentinel() {
      return true;
    }

    @Override
    public String toString() {
      return "-";
    }
  }
}
