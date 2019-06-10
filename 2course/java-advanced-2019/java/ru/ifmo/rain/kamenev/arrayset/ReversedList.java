package ru.ifmo.rain.kamenev.arrayset;

import java.util.AbstractList;
import java.util.List;

public class ReversedList<T> extends AbstractList<T> {

    private List<T> data;
    private boolean isReversed;

    public ReversedList(List<T> data) {
        this.data = data;
    }

    public void reverse() {
        isReversed = !isReversed;
    }

    @Override
    public T get(int i) {
        return (isReversed) ? data.get(data.size() - 1 - i) : data.get(i);
    }

    public int size() {
        return data.size();
    }
}
