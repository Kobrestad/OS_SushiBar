import java.util.LinkedList;
import java.util.Queue;

/**
 * This class implements a waiting area used as the bounded buffer, in the producer/consumer problem.
 */
public class WaitingArea {
    public int size;
    public Queue<Customer> customers;

    /**
     * Creates a new waiting area.
     *
     * @param size The maximum number of Customers that can be waiting.
     */
    public WaitingArea(int size) {
        this.size = size;
        this.customers = new LinkedList<>();
    }

    /**
     * This method puts the customer into the waitingArea
     *
     * @param customer A customer created by Door, trying to enter the waiting area
     */
    public synchronized void enter(Customer customer) throws InterruptedException {
        while(this.customers.size() >= this.size) {
            // Handle full waiting area.
            //SushiBar.write("Sorry, waiting area is full!");
            wait();
        }
        this.customers.add(customer);
        SushiBar.write(Thread.currentThread().getName() + "       : Customer #" + customer.getCustomerID() + " is now waiting.");
        // Since waitresses are interchangable threads, only need to wake one of them. Therefore notify() is used here to avoid excessive overhead.
        notify();
    }

    /**
     * @return The customer that is first in line.
     */
    public synchronized Customer next() throws InterruptedException {
        // Wait current thread if waitingroom contains no customers.
        //SushiBar.write("Getting next customer");
        while (this.customers.isEmpty()){
            wait();
        }
        // else get a customer and notify all threads.
        Customer customer = this.customers.poll();
        // To allow for maximum throughput it is critical to wake the door thread every time a customer is consumed. Therefore notifyAll() is used here.
        notifyAll();
        return customer;
    }

    /**
     * Used to keep track of when the bar is closed, and there are no more customers waiting for service.
     * @return True if the bar is closed and there are no remaining customers.
     */
    public synchronized boolean isReadyToClose() {
        return (this.customers.isEmpty() && !SushiBar.isOpen);
    }
}
