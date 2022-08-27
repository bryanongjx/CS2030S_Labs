package cs2030s.fp;
 
/**
 * Provides an expression that produces a lazy value that is not evaluated until
 * the value is needed.
 *
 * @author Bryan Ong (10L)
 * @version CS2030s AY 21/22 Sem2
 */

public class Lazy<T> {
  /**
   * Producer stores the evaluation of the value until needed
   * and value is stored inside Maybe.
   */
  private Producer<? extends T> producer;
  private Maybe<T> value;
  
  /**
   * Private constructor for Lazy to create an instance of Lazy with a value.
   *
   * @param value the input value
   */
  public Lazy(T value) {
    Maybe<T> x = Maybe.some(value);
    this.value = x;
  }

  /**
   * Private constructor to create an instance of Lazy with a producer waiting
   * to be evaluated.
   *
   * @param producer the input producer
   */
  public Lazy(Producer<? extends T> producer) {
    this.producer = producer;
    this.value = Maybe.none();
  }

  /**
   * Factory method for intialising a Lazy instance object with the given
   * value.
   *
   * @param <T> the type of value
   * @param value the input value
   * @return the instance of Lazy that contains input value as value
   */
  public static <T> Lazy<T> of(T value) {
    return new Lazy<T>(value);
  }

  /**
   * Factory method that takes in a producer that produces the value when needed.
   *
   * @param <T> the type of vale that producer gives when evaluated
   * @param producer the producer that when evaluated, gives the value of the Lazy instance
   * @return the instance of Lazy with a null value and contains Producer producer
   */
  public static <T> Lazy<T> of(Producer<? extends T> producer) {
    return new Lazy<T>(producer);
  }
  
  /**
   * method that is called when value is needed. if value is already available
   * return that value, else compute the value and return it.
   * Computation should only be done once for the same value.
   *
   * @return the value stored
   */
  public T get() {
    T content = (this.value).orElseGet(this.producer);
    this.value = Maybe.some(content);
    return content;
  }

  /**
   * Maps content of Lazy with transformer, not evaluated until get() is called.
   * and should only be evaluated once. once evaluated, result should be cached
   * (also called memoized), so that function must not be called again.
   *
   * @param <U> the type of transformed Lazy
   * @param transformer trasnformer used for mapping
   * @return a transformed Lazy Type
   */
  public <U> Lazy<U> map(Transformer<? super T, ? extends U> transformer) {
    return new Lazy<U>(() -> transformer.transform(this.get()));
  }

  /**
   * Maps content of Lazy with transformer, not evaluated until get() is called.
   * and should only be evaluated once. once evaluated, result should be cached
   * (also called memoized), so that function must not be called again.
   *
   * @param <U> the type of transformed Lazy
   * @param transformer transformer
   * @return a transformed Lazy type
   */  
  public <U> Lazy<U> flatMap(Transformer<? super T, ? extends Lazy<? extends U>> transformer) {
    return new Lazy<U>(() -> transformer.transform(this.get()).get());
  }

  /**
   * takes in a BooleanCondition and lazily tests if the value passes the test or not, 
   * Returns a LazyBoolean object and is executed at most once.
   *
   * @param cond BooleanCondition
   * @return Lazy
   */
  public Lazy<Boolean> filter(BooleanCondition<? super T> cond) {
    return new Lazy<Boolean>(() -> cond.test(this.get()));
  }

  /** 
   * method to combine two values, of type S and T respectively,
   * into a result of type R.
   *
   * @param <S> type of input Lazy
   * @param <R> type of returned Lazy
   * @param lazy input Lazy
   * @param c Combiner
   * @return Lazy
   */
  public <R, S> Lazy<R> combine(Lazy<S> lazy, Combiner<? super T, ? super S, ? extends R> c) {
    return new Lazy<R>(() -> c.combine(this.get(), lazy.get()));
  }
 
  /**
   * overrides equals method in object class, is an eager operation that 
   * causes values to be evaluated(if not already cached), should return true
   * only if both objects being compared are Lazy and value contains within
   * are equals (according to their equals() methods).
   *
   * @param obj input object to be compared against
   * @return boolean 
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof Lazy<?>) {
      Lazy<?> lazy = (Lazy<?>) obj;
      if (this.get() == lazy.get()) {
        return true;
      }
      if (this.value == null || lazy.value == null) {
        return false;
      }
      return this.get().equals(lazy.get());
    }
    return false;
  }

  /**
   * returns "?" if value is not yet available; returns the string
   * representation of the value otherwise.
   *
   * @return string
   */
  @Override
  public String toString() {
    return this.value.map(t -> String.valueOf(t)).orElse("?");
  }  
}
    


