import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * This class implements a SushiBar, with a clock, door, waitresses, and customers coming and going.
 */
public class SushiBar {
    // SushiBar settings.
    private static int waitingAreaCapacity = 20; //20
    private static int waitressCount = 7; //7
    private static int duration = 5; //5
    public static int maxOrder = 15; //15
    public static int waitressWait = 60; // Used to calculate the time the waitress spends before taking the order 60
    public static int customerWait = 2500; // Used to calculate the time the customer spends eating 2500
    public static int doorWait = 120; // Used to calculate the interval at which the door tries to create a customer 120
    public static boolean isOpen = true;
    
    // Creating log file.
    private static File log;
    private static String path = "./";
    
    // Variables related to statistics.
    public static SynchronizedInteger servedOrders;
    public static SynchronizedInteger takeawayOrders;
    public static SynchronizedInteger totalOrders;
    public static SynchronizedInteger customerNo;


    // Components of the Sushi Bar.
    public Clock clock;
    public WaitingArea wa; 

    /**
     *  Creates a new SushiBar. Starts the clock, and assigns waitingArea capacity.
     */
    public SushiBar() {
        this.clock = new Clock(duration);
        this.wa = new WaitingArea(SushiBar.waitingAreaCapacity);
    }

    /**
     *  Initializes all threads needed to run the SushiBar.
     */
    public void runBar() {
        // Creating door.
        Thread doorThread = new Thread(new Door(this.wa), "Door");
        // Making waitressThreads, and putting them in a collection "threadPool".
        List<Thread> threadPool = new LinkedList<>();
        for (int i = 0; i < SushiBar.waitressCount; i++) {
            Runnable waitress = new Waitress(this.wa);
            Thread waitressThread = new Thread(waitress, "Waitress #" + i);
            threadPool.add(waitressThread);
        }
        // Threads are started.
        doorThread.start();
        for (Thread thread : threadPool) {
            thread.start();
        }
        // Making sure the methods wait for the waitressThreads to terminate befor printing statistics.
        for (Thread thread : threadPool) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // After waitressThreads has terminated, print total statistics.
        SushiBar.write("***** NO MORE CUSTOMERS - THE SHOP IS CLOSED NOW. *****");
        SushiBar.write("Number of orders: " + SushiBar.totalOrders.get() + " Eaten: " + SushiBar.servedOrders.get() + " Takeaway: " + SushiBar.takeawayOrders.get());
    }



    public static void main(String[] args) {
        log = new File(path + "log.txt");

        // Initializing shared variables for counting number of orders.
        servedOrders = new SynchronizedInteger(0);
        takeawayOrders = new SynchronizedInteger(0);
        totalOrders = new SynchronizedInteger(0);
        // Initializing shared variable for creating unique customer IDs.
        customerNo = new SynchronizedInteger(0);

        // Initializing bar and different Threads.
        SushiBar bar = new SushiBar();
        bar.runBar();
    }

    // Writes actions in the log file and console.
    public static void write(String str) {
        try {
            FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Clock.getTime() + ", " + str + "\n");
            bw.close();
            System.out.println(Clock.getTime() + ", " + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
