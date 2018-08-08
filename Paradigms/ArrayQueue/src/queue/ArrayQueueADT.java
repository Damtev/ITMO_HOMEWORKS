package queue;

public class ArrayQueueADT {
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private int capacity = 8;
    private Object[] elements = new Object[capacity];

    // Pre: element != null && queueADT != null
    // Post: queueADT.elements[queueADT.tail] = element &&
    // && queueADT.tail = (queueADT.tail + 1) % queueADT.elements.length
    public static void enqueue(ArrayQueueADT queueADT, Object element) {
        assert element != null;
        assert queueADT != null;

        ensureCapacity(queueADT);
        queueADT.elements[queueADT.tail] = element;
        queueADT.tail = (queueADT.tail + 1) % queueADT.capacity;
        ++queueADT.size;
    }

    // Pre: everything
    // Post: if size == capacity return new Queue(2 * capacity)
    private static void ensureCapacity(ArrayQueueADT queueADT) {
        if (queueADT.size < queueADT.capacity) {
            return;
        }
        Object[] newElements = copy(queueADT, queueADT.capacity << 1);
        queueADT.head = 0;
        queueADT.tail = queueADT.size;
        queueADT.capacity *= 2;
        queueADT.elements = newElements;
    }

    // Pre: queueADT != null && !ArrayQueueADT.isEmpty(queueADT) && immutable
    // Post: return queueADT.elements[queueADT.head]
    public static Object element(ArrayQueueADT queueADT) {
        assert queueADT != null;
        assert queueADT.head != queueADT.tail;

        return queueADT.elements[queueADT.head];
    }

    // Pre: queueADT != null && !ArrayQueueADT.isEmpty(queueADT)
    // Post: return queueADT.elements[queueADT.head] &&
    // && queueADT.head = (queueADT.head + 1) % queueADT.elements.length;
    public static Object dequeue(ArrayQueueADT queueADT) {
        assert queueADT != null;
        assert queueADT.head != queueADT.tail;

        Object element = queueADT.elements[queueADT.head];
        queueADT.elements[queueADT.head] = null;
        queueADT.head = (queueADT.head + 1) % queueADT.capacity;
        --queueADT.size;
        return element;
    }

    // Pre: queueADT != null && immutable
    // Post: return amount of queueADT.elements
    public static int size(ArrayQueueADT queueADT) {
        assert queueADT != null;

        return queueADT.size;
    }

    // Pre: queueADT != null && immutable
    // Post: return (amount of queueADT.elements > 0)
    public static boolean isEmpty(ArrayQueueADT queueADT) {
        assert queueADT != null;

        return queueADT.head == queueADT.tail;
    }

    // Pre: queueADT != null
    // Post: queueADT.elements = new Object[1000000] && queueADT.head = 0 && queueADT.tail = 0
    public static void clear(ArrayQueueADT queueADT) {
        assert queueADT != null;

        queueADT.head = 0;
        queueADT.tail = 0;
        queueADT.size = 0;
    }

    // Pre: newCapacity >= 0
    // Post: return new Object[newCapacity] && all elements from head to tail are for int i = 0..size-1
    public static Object[] copy(ArrayQueueADT queueADT, int newCapacity) {
        Object[] newElements = new Object[newCapacity];
        //System.arraycopy(elements, 0, newElements, 0, tail);
        //System.arraycopy(elements, tail, newElements, (tail + capacity), capacity - tail);
        if (queueADT.head < queueADT.tail) {
            System.arraycopy(queueADT.elements, queueADT.head, newElements, 0, queueADT.size);
        } else {
            System.arraycopy(queueADT.elements, queueADT.head, newElements, 0, queueADT.capacity - queueADT.head);
            System.arraycopy(queueADT.elements, 0, newElements, queueADT.capacity - queueADT.head, queueADT.tail);
        }
        return newElements;
    }

    // Pre: everything
    // Post: return new Object[size] contains all elements from head to tail
    public static Object[] toArray(ArrayQueueADT queueADT) {
        if (queueADT.size == 0) {
            return new Object[0];
        }
        return copy(queueADT, queueADT.size);
    }

}
