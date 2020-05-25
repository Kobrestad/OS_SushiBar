
/**
 * This class implements the consumer part of the producer/consumer problem.
 * One waitress instance corresponds to one consumer.
 */
public class Waitress implements Runnable {
    public WaitingArea waitingArea; 

    /**
     * Creates a new waitress.
     *
     * @param waitingArea The waiting area for customers
     */
    Waitress(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    /**
     * This is the code that will run when a new thread is
     * created for this instance
     */
    @Override
    public void run() {
        //SushiBar.write(Thread.currentThread().getName() + " is now running!");
        try {
            // While the bar still has waiting customers do:
            while(!this.waitingArea.isReadyToClose()) {
                // Fetch next customer
                Customer customer = this.waitingArea.next();
                SushiBar.write(Thread.currentThread().getName() + ": Customer #" + customer.getCustomerID() + " is now fetched.");
                // After waitressWait-delay, take order.
                Thread.sleep(SushiBar.waitressWait);
                customer.order();
                // Wait for customer to finish eating.
                Thread.sleep(SushiBar.customerWait);
                SushiBar.write(Thread.currentThread().getName() + ": Customer #" + customer.getCustomerID() + " is now leaving.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //SushiBar.write(Thread.currentThread().getName() + " is shutting down!");
        return;
    }


}

