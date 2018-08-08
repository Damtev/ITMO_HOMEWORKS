package queue;

public class ArrayQueue /*implements Queue*/ {
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private int capacity = 8;
    private Object[] elements = new Object[capacity];

    // Pre: element != null
    // Post: elements[tail] = element && tail = (tail + 1) % elements.length
    public void enqueue(Object element) {
        assert element != null;

        ensureCapacity();
        elements[tail] = element;
        tail = (tail + 1) % capacity;
        ++size;
    }

    // Pre: everything
    // Post: if size == capacity return new Queue(2 * capacity)
    private void ensureCapacity() {
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
    public Object element() {
        assert head != tail;

        return elements[head];
    }

    // Pre: !ArrayQueueModule.isEmpty()
    // Post: return elements[head] && head = (head + 1) % elements.length
    public Object dequeue() {
        assert head != tail;

        Object element = elements[head];
        elements[head] = null;
        head = (head + 1) % capacity;
        --size;
        return element;
    }

    // Pre:  immutable
    // Post: return amount of elements
    public int size() {
        return size;
    }

    // Pre: immutable
    // Post: return (amount of elements > 0)
    public boolean isEmpty() {
        return head == tail;
    }

    // Pre: everything
    // Post: elements = new Object[1000000] && head = 0 && tail = 0;
    public void clear() {
        head = 0;
        tail = 0;
        size = 0;
    }

    // Pre: newCapacity >= 0
    // Post: return new Object[newCapacity] && all elements from head to tail are for int i = 0..size-1
    public Object[] copy(int newCapacity) {
        Object[] newElements = new Object[newCapacity];
        //System.arraycopy(elements, 0, newElements, 0, tail);
        //System.arraycopy(elements, tail, newElements, (tail + capacity), capacity - tail);
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
    public Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        }
        return copy(size);
    }

}
