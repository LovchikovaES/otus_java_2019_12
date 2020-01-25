package ru.catn;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class DIYArrayList<T> implements List<T> {

    private static final int ADDITIONAL_CAPACITY = 10;

    private Object[] elementData;
    private int size;

    public DIYArrayList() {
    }

    public DIYArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
            this.size = initialCapacity;
        } else if (initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity = " + initialCapacity);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    @NotNull
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {

        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        @Override
        public boolean hasNext() {
            return cursor != size();
        }

        @Override
        public T next() {
            int i = cursor;
            if (i >= size())
                throw new NoSuchElementException();
            lastRet = i;
            cursor++;
            return get(i);
        }
    }

    @Override
    @NotNull
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t) {
        add(size, t);
        return true;
    }

    @Override
    public void add(int index, T element) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Incorrect index = " + index);

        if (size == 0) {
            elementData = new Object[ADDITIONAL_CAPACITY];
        } else if (this.size + 1 > elementData.length) {
            int newLength = this.size + ADDITIONAL_CAPACITY;
            this.elementData = Arrays.copyOf(this.elementData, newLength);
        }

        if (index < size) {
            System.arraycopy(this.elementData, index,
                    this.elementData, index + 1,
                    this.size - index);
        }
        elementData[index] = element;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        checkIndex(index);
        return (T) this.elementData[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T set(int index, T element) {
        checkIndex(index);
        T oldValue = (T) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    @NotNull
    public ListIterator<T> listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListItr(index);
    }

    private class ListItr extends Itr implements ListIterator<T> {
        ListItr(int index) {
            checkIndex(index);
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public T previous() {
            if (cursor == 0)
                throw new NoSuchElementException("No previous element");
            cursor--;
            lastRet = cursor;
            return get(cursor);
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            if (cursor == 0)
                throw new NoSuchElementException("No previous index");
            return cursor - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(T t) {
            if (lastRet < 0)
                throw new NoSuchElementException();
            DIYArrayList.this.set(lastRet, t);
        }

        @Override
        public void add(T t) {
            DIYArrayList.this.add(cursor, t);
            cursor++;
        }
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        if (this.size == 0)
            return "[]";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (int i = 0; i < this.size; i++) {
            stringBuilder.append(elementData[i]);
            if (i + 1 < this.size)
                stringBuilder.append(", ");
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Incorrect index = " + index);
    }
}