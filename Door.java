import java.util.concurrent.ThreadLocalRandom;
/**
 * This class implements the Door component of the sushi bar
 * assignment The Door corresponds to the Producer in the
 * producer/consumer problem
 */
public class Door implements Runnable {
    public WaitingArea waitingArea;

    /**
     * Creates a new Door. Make sure to save the
     * @param waitingArea   The customer queue waiting for a seat
     */
    public Door(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    /**
     * This method will run when the door thread is created (and started).
     * The method should create customers at random intervals and try to put them in the waiting area.
     */
    @Override
    public void run() {
        while (SushiBar.isOpen) {
            try {
                Customer customer = new Customer(SushiBar.customerNo.getAndIncrement());
                this.waitingArea.enter(customer);
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, SushiBar.doorWait + 1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        SushiBar.write("***** DOOR CLOSED *****");
        return;
    }

    // Add more methods as you see fit
}
