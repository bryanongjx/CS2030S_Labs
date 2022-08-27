package cs2030s.fp;
 
/**
 * Provides an expression that produces a lazy value that is not evaluated until
 * the value is needed.
 *
 * @author Bryan Ong (10L)
 * @version CS2030s AY 21/22 Sem2
 */

public class Lazy<T> {

  private Producer<? extends T> producer;
  private Maybe<T> value;

  public Lazy(Producer<? extends T> p) {
    this.producer = p;
    this.value = Maybe.none();
  }

  public Lazy(T value) {
    this.value = Maybe.some(value);
  }
  
  public static <T> Lazy<T> of(T value) {
    return new Lazy<T>(value);
  }

  public static <T> Lazy<T> of(Producer<? extends T> p) {
    return new Lazy<T>(p);
  }

  public T get() {
    T content = (this.value).orElseGet(this.producer);
    Maybe<T> result = Maybe.some(content);
    this.value = result;
    return content;
  }

  public boolean evaluated() {
    if (this.value.equals(Maybe.none())) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public String toString() {
    return this.value.map(t -> String.valueOf(t)).orElse("?");
  }

  public <U> Lazy<U> map(Transformer<? super T, ? extends U> transformer) {
    return new Lazy<U>(() -> transformer.transform(this.get()));
  }

  public <U> Lazy<U> flatMap(Transformer<? super T, ? extends Lazy<? extends U>> transformer) {
    return new Lazy<U>(() -> transformer.transform(this.get()).get());
  }

  public Lazy<Boolean> filter(BooleanCondition<? super T> cond) {
    return new Lazy<Boolean>(() -> cond.test(this.get()));
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } 
    if (obj instanceof Lazy<?>) {
      Lazy<?> lazy = (Lazy<?>) obj;
      if (this.get() == lazy.get()) {
        return true;
      } 
      if (this.get() == null || lazy.get() == null) {
        return false;
      }
      return (this.get()).equals(lazy.get());
    }
    return false;
  }

  public <R, S> Lazy<R> combine(Lazy<S> lazy, Combiner<? super T, ? super S, ? extends R> c) {
    return new Lazy<R>(() -> c.combine(this.get(), lazy.get()));
  }



}



