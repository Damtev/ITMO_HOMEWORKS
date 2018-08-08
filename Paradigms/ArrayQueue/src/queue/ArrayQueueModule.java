package queue;

public class ArrayQueueModule {
    private static int head = 0;
    private static int tail = 0;
    private static int size = 0;
    private static int capacity = 8;
    private static Object[] elements = new Object[capacity];

    // Pre: element != null
    // Post: elements[tail] = element && tail = (tail + 1) % elements.length
    public static void enqueue(Object element) {
        assert element != null;

        ensureCapacity();
        elements[tail] = element;
        tail = (tail + 1) % capacity;
        ++size;
    }

    // Pre: everything
    // Post: if size == capacity return new Queue(2 * capacity)
    private static void ensureCapacity() {
        if (size < capacity) {
            return;
        }
        Object[] newElements = copy(capacity << 1);
        head = 0;
        tail = size;
        capacity *= 2;
        elements = newElements;
    }

    // Pre: !ArrayQueueModule.isEmpty() && immutable
    // Post: return elements[head]
    public static Object element() {
        assert head != tail;

        return elements[head];
    }

    // Pre: !ArrayQueueModule.isEmpty()
    // Post: return elements[head] && head = (head + 1) % elements.length
    public static Object dequeue() {
        assert head != tail;

        Object element = elements[head];
        elements[head] = null;
        head = (head + 1) % capacity;
        --size;
        return element;
    }

    // Pre:  immutable
    // Post: return amount of elements
    public static int size() {
        return size;
    }

    // Pre: immutable
    // Post: return (amount of elements > 0)
    public static boolean isEmpty() {
        return head == tail;
    }

    // Pre: everything
    // Post: elements = new Object[1000000] && head = 0 && tail = 0;
    public static void clear() {
        head = 0;
        tail = 0;
        size = 0;
        elements = new Object[5];
        capacity = 5;
    }

    // Pre: newCapacity >= 0
    // Post: return new Object[newCapacity] && all elements from head to tail are for int i = 0..size-1
    public static Object[] copy(int newCapacity) {
        Object[] newElements = new Object[newCapacity];
        if (head < tail) {
            System.arraycopy(elements, head, newElements, 0, size);
        } else {
            System.arraycopy(elements, head, newElements, 0, capacity - head);
            System.arraycopy(elements, 0, newElements, capacity - head, tail);
        }
        return newElements;
    }

    // Pre: everything
    // Post: return new Object[size] contains all elements from head to tail
    public static Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        }
        return copy(size);
    }

}
