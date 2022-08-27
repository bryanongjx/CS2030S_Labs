import java.util.Scanner;

/**
 * This class implements a shop simulation.
 *
 * @author Wei Tsang
 * @version CS2030S AY20/21 Semester 2
 */ 
class ShopSimulation extends Simulation {
  
  private final Shop shop; 

  /** 
   * The list of customer arrival events to populate
   * the simulation with.
   */
  private final Event[] initEvents;

  /** 
   * Constructor for a shop simulation. 
   *
   * @param sc A scanner to read the parameters from.  The first
   *           integer scanned is the number of customers; followed
   *           by the number of service counters.  Next is a 
   *           sequence of (arrival time, service time) pair, each
   *           pair represents a customer.
   */
  public ShopSimulation(Scanner sc) {
    /** Initialize an array of length n,
      * where n is the number of customers
      */
    initEvents = new Event[sc.nextInt()];

    /** Initialize a Shop object with
      * k number of counters
      */
    shop = new Shop(sc.nextInt(), sc.nextInt());
    int id = 0;

    while (sc.hasNextDouble()) {
      double arrivalTime = sc.nextDouble();
      double serviceTime = sc.nextDouble();
      Customer customer = new Customer(arrivalTime, serviceTime, id);
      initEvents[id] = new Arrival(this.shop, customer, arrivalTime);
      id += 1;
    }
  }

  /**
   * Retrieve an array of events to populate the 
   * simulator with.
   *
   * @return An array of events for the simulator.
   */
  @Override
  public Event[] getInitialEvents() {
    return initEvents;
  }
}
