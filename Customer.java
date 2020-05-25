import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements a customer, which is used for holding data
 * and update the statistics
 */
public class Customer {
    protected final int customerID;

    /**
     *  Creates a new Customer.  Each customer should be given a
     *  unique ID
     * @param customerID is used to create CustomerID
     */
    public Customer(int customerID) {
        this.customerID = customerID;
        SushiBar.write(Thread.currentThread().getName() + "       : Customer #" + customerID + " is now created.");
    }


    /**
     * Adds the customers desired order numbers to the shared variables.
     * Order numbers are randomly generated below the SushiBar.maxOrder cap.
     */
    public void order() {
        int totalOrders = ThreadLocalRandom.current().nextInt(1, SushiBar.maxOrder + 1);
        int eatenOrders = ThreadLocalRandom.current().nextInt(0, totalOrders + 1);
        int takeawayOrders = totalOrders - eatenOrders;
        SushiBar.totalOrders.add(totalOrders);
        SushiBar.servedOrders.add(eatenOrders);
        SushiBar.takeawayOrders.add(takeawayOrders);
        SushiBar.write(Thread.currentThread().getName() + ": Customer #" + this.getCustomerID() + " is now eating.");
    }

    /**
     *
     * @return Returns the customerID
     */
    public int getCustomerID() {
        return this.customerID;
    }
}
