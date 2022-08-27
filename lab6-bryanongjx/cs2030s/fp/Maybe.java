package cs2030s.fp;

import java.util.NoSuchElementException;

/**
 * CS2030S Lab 5.
 * AY21/22 Semester 2.
 *
 * @author Ong Jin Xiang, Bryan (Group 10L). 
 */

public abstract class Maybe<T> {
  
  // Fields
  private static final Maybe<?> NONE = new None();
  
  // Abstract Methods
  public abstract T orElse(T input);

  public abstract T orElseGet(Producer<? extends T> producer);

  protected abstract T get();

  // None<T> class
  private static class None extends Maybe<Object> {
    
    @Override
    public String toString() {
      return "[]";
    }
    
    @Override
    protected Object get() throws NoSuchElementException {
      throw new NoSuchElementException();
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof None) {
        return true;
      } else {
        return false;
      }
    }

    @Override
    public Object orElse(Object obj) {
      return obj;
    }

    @Override
    public Object orElseGet(Producer<? extends Object> producer) {
      return producer.produce();
    }
  }
  
  
  // Some<T> class
  private static final class Some<T> extends Maybe<T> {
    private final T content;

    private Some(T content) {
      this.content = content;
    }

    @Override
    public String toString() {
      return "[" + this.get() + "]";
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      } 
      if (obj instanceof Some<?>) {
        Some<?> some = (Some<?>) obj;
        if (this.get() == some.get()) {
          return true;
        }
        if (this.content == null || some.content == null) {
          return false;
        }
        return this.get().equals(some.get());
      }
      return false;
    }

    @Override
    protected T get() {
      return this.content;
    }

    @Override
    public T orElse(T t) {
      return this.get();
    }

    @Override 
    public T orElseGet(Producer<? extends T> producer) {
      return this.get();
    }
  }      
  
  // Concrete Methods
  public static <T> Maybe<T> none() {
    @SuppressWarnings("unchecked")
    Maybe<T> obj = (Maybe<T>) NONE;
    return obj;
  }

  public static <T> Maybe<T> some(T input) {
    return new Some<T>(input);
  }

  public static <T> Maybe<T> of(T input) {
    if (input == null) {
      return none();
    } else {
      return some(input);
    }
  }

  public Maybe<T> filter(BooleanCondition<? super T> cond) {
    if (this instanceof None) {
      return Maybe.<T>none();
    } else {
      if (this.get() == null) {
        return this;
      } else if (cond.test(this.get())) {
        return this;
      } else {
        return Maybe.<T>none();
      }
    }
  }    

  public <U> Maybe<U> map(Transformer<? super T, ? extends U> transformer)
      throws NullPointerException {
    try {
      if (this instanceof None) {
        return Maybe.<U>none();
      } else {
        U output = transformer.transform(this.get());
        return Maybe.<U>some(output);
      }
    } catch (NullPointerException e) {
      throw e;
    }
  }

  public <U> Maybe<U> flatMap(Transformer<? super T, ? extends Maybe<? extends U>> transformer)
      throws NullPointerException {
    try {
      if (this instanceof None) {
        return Maybe.<U>none();
      } else {
        @SuppressWarnings("unchecked")
        Maybe<U> output = (Maybe<U>) transformer.transform(this.get());
        return output;
      }
    } catch (NullPointerException e) {
      throw e;
    }
  }

}
