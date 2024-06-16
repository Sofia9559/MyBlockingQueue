import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class MyBlockingQueue {
    ArrayList<Integer> tasks = new ArrayList<>(8);

    public MyBlockingQueue(ArrayList<Integer> tasks) {
    }

    public synchronized void enqueue(int i) {
        tasks.add(i);
        notify();
    }

    public synchronized int dequeue() {
        while (tasks.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int i = tasks.get(0);
        tasks.remove(i);
        return i;
    }

    public int size(ArrayList<Integer> tasks) {
        return tasks.size();
    }

    public static void main(String[] args) {
        MyBlockingQueue tasks = new MyBlockingQueue(new ArrayList<>(8));

        new Thread(() -> {
            int i = 0;
            while (i < 10){
                tasks.enqueue(++i);
                System.out.println("pr "+ i);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                    Integer j = tasks.dequeue();
                    System.out.println("cons " + j);
            }
        }).start();
    }
}