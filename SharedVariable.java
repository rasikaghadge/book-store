public class SharedVariable {
    private static int sharedValue = 0;

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            performIncrement();
        });

        Thread thread2 = new Thread(() -> {
            performIncrement();
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final shared value: " + sharedValue);
    }

    // Synchronized method ensures that only one thread can execute this method at a time
    private synchronized static void performIncrement() {
        for (int i = 0; i < 5; i++) {
            // Critical section: Accessing the shared resource
            int temp = sharedValue;
            System.out.println(Thread.currentThread().getName() + " read: " + temp);
            sharedValue = temp + 1;
            System.out.println(Thread.currentThread().getName() + " wrote: " + sharedValue);
        }
    }
}
