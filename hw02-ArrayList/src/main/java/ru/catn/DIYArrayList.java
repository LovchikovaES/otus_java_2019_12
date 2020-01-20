package ru.catn;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class DIYArrayList<T> implements List<T> {

    private Object[] elementData;
    private int size;

    DIYArrayList() {}

    DIYArrayList(int initialCapacity) {
        this.elementData = new Object[initialCapacity];
        this.size = initialCapacity;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
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
        add(size(), t);
        return true;
    }

    @Override
    public void add(int index, T element) {
        Object[] arrayListNew = new Object[this.size() + 1];
        if (index > 0) {
            System.arraycopy(this.elementData, 0,
                            arrayListNew, 0,
                            index);
        }
        arrayListNew[index] = element;
        if (index + 1 <= this.size()) {
            System.arraycopy(this.elementData, index,
                            arrayListNew, index + 1,
                            this.size() - index);
        }
        this.elementData = arrayListNew;
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
        //return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        return (T) this.elementData[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T set(int index, T element) {
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
            cursor = index;
        }

        @Override
        public boolean hasPrevious() { return cursor != 0; }

        @Override
        public T previous() {
            cursor--;
            lastRet = cursor;
            return get(cursor);
        }

        @Override
        public int nextIndex() { return cursor; }

        @Override
        public int previousIndex() { return cursor - 1; }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(T t) {
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

    public void print() {
        Iterator<T> iterator = this.iterator();

        System.out.print("[");
        while (iterator.hasNext()) {
            System.out.print(iterator.next());
            if (iterator.hasNext()){
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}