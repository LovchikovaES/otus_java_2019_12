package ru.catn.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private final String ACTION_PUT = "PUT";
    private WeakHashMap<K, V> cache = new WeakHashMap<>();
    private List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        remove(key);
        cache.put(key, value);
        onPut(key, value);
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void onPut(K key, V value) {
        for (var listener : listeners) {
            listener.notify(key, value, ACTION_PUT);
        }
    }
}
