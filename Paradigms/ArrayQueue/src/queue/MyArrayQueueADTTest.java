package queue;

public class MyArrayQueueADTTest {
    public static void fill(ArrayQueueADT queueADT) {
        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.enqueue(queueADT, i);
        }
    }

    public static void dump(ArrayQueueADT queueADT) {
        while (!ArrayQueueADT.isEmpty(queueADT)) {
            System.out.println(
                    ArrayQueueADT.size(queueADT) + " " +
                            ArrayQueueADT.element(queueADT) + " " +
                            ArrayQueueADT.dequeue(queueADT)
            );
        }
    }

    public static void main(String[] args) {
        ArrayQueueADT queueADT = new ArrayQueueADT();
        fill(queueADT);
        dump(queueADT);
    }
}

