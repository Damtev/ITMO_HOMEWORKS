package queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Stack;

public class MyArrayQueueTest {
    public static void fill(ArrayQueue queue) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
    }

    public static void dump(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(
                    queue.size() + " " +
                            queue.element() + " " +
                            queue.dequeue()
            );
        }
    }

    public static void main(String[] args) {
//        ArrayQueue queue = new ArrayQueue();
//        fill(queue);
//        dump(queue);
        Object[] objects = new Object[0];
        System.out.println(objects);
    }
}

