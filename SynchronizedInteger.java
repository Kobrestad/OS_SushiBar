public class SynchronizedInteger {
    private int c ;

    public SynchronizedInteger(int initial){
    	c=initial;
    }
    public synchronized void add(int value){
    	c+=value;
    }

    public synchronized void subtract(int value){
    	c-=value;
    }
    public synchronized void increment() {
        c++;
    }

    public synchronized void decrement() {
        c--;
    }

    public synchronized int get() {
        return c;
    }

    /**
     *  Helper function for easier customerID creation.
     *  @return Returns the int c.
     */
    public synchronized int getAndIncrement() {
        int returnvalue = this.get();
        this.increment();
        return returnvalue;
    }

}
