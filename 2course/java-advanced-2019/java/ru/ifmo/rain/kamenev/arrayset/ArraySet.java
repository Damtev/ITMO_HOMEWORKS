package ru.ifmo.rain.kamenev.arrayset;

import java.util.*;

public class ArraySet<T> extends AbstractSet<T> implements NavigableSet<T> {

    private final List<T> data;
    private final Comparator<? super T> comparator;

    public ArraySet() {
        this(Collections.emptyList(), null);
    }

    public ArraySet(Collection<? extends T> other, Comparator<? super T> comparator) {
        this.comparator = comparator;
        Set<T> tempSet = new TreeSet<>(comparator);
        tempSet.addAll(other);
        data = new ArrayList<>(tempSet);
    }

    public ArraySet(Collection<? extends T> other) {
        this(other, null);
    }

    private ArraySet(List<T> other, Comparator<? super T> comparator) {
        this.comparator = comparator;
        data = other;
        if (data instanceof ReversedList) {
            ((ReversedList<T>) data).reverse();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return Collections.unmodifiableList(data).iterator();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public T lower(T t) {
        return get(lowerIndex(t));
    }

    @Override
    public T floor(T t) {
        return get(floorIndex(t));
    }

    @Override
    public T ceiling(T t) {
        return get(ceilingIndex(t));
    }

    @Override
    public T higher(T t) {
        return get(higherIndex(t));
    }

    @Override
    public T pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T pollLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        return Collections.binarySearch(data, (T) Objects.requireNonNull(o), comparator) >= 0;
    }

    @Override
    public NavigableSet<T> descendingSet() {
        return new ArraySet<>(new ReversedList<>(data), Collections.reverseOrder(comparator));
    }

    @Override
    public Iterator<T> descendingIterator() {
        return descendingSet().iterator();
    }

    private int compare(T a, T b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        } else {
            return ((Comparable) a).compareTo(b);
        }
    }

    private int findIndexFromHead(T element, boolean inclusive) {
        int index = Collections.binarySearch(data, element, comparator);
        return index < 0 ? ~index : (inclusive ? index : index + 1);
    }

    private int findIndexFromTail(T element, boolean inclusive) {
        int index = Collections.binarySearch(data, element, comparator);
        return index < 0 ? ~index - 1 : (inclusive ? index : index - 1);
    }

    @Override
    public NavigableSet<T> subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
        if (compare(fromElement, toElement) > 0) {
            throw new IllegalArgumentException();
        }
        int l = fromInclusive ? find(fromElement, 0, 0) :
                find(fromElement, 1, 0);
        int r = (toInclusive ? floorIndex(toElement) : lowerIndex(toElement));
        if (r + 2 == l) {
            r = l - 1;
        }
        return new ArraySet<>(data.subList(l, r + 1), comparator);
    }

    @Override
    public NavigableSet<T> headSet(T toElement, boolean inclusive) {
        int index = findIndexFromTail(toElement, inclusive) + 1;
        return new ArraySet<>(data.subList(0, index), comparator);
    }

    @Override
    public NavigableSet<T> tailSet(T fromElement, boolean inclusive) {
        int index = findIndexFromHead(fromElement, inclusive);
        return new ArraySet<>(data.subList(index, data.size()), comparator);
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return headSet(toElement, false);
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return tailSet(fromElement, true);
    }

    @Override
    public Comparator<? super T> comparator() {
        return comparator;
    }

    @Override
    public T first() {
        if (data.isEmpty()) {
            throw new NoSuchElementException();
        }
        return data.get(0);
    }

    @Override
    public T last() {
        if (data.isEmpty()) {
            throw new NoSuchElementException();
        }
        return data.get(data.size() - 1);
    }

    private T get(int index) {
        return (index < 0 || index >= data.size()) ? null : data.get(index);
    }

    private int lowerIndex(T key) {
        return find(key, -1, -1);
    }

    private int higherIndex(T key) {
        return find(key, 1, 0);
    }

    private int floorIndex(T key) {
        return find(key, 0, -1);
    }

    private int ceilingIndex(T key) {
        return find(key, 0, 0);
    }

    private int find(T key, int strictComp, int laxComp) {
        int res = Collections.binarySearch(data, Objects.requireNonNull(key), comparator);
        return res < 0 ? ~res + laxComp : res + strictComp;
    }
}