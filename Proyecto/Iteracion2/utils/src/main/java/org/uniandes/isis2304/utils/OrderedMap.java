package org.uniandes.isis2304.utils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OrderedMap<K, V> extends ArrayList<Map.Entry<K, V>> {

    public V put(K key, V value) {
        int index = getIndex(key);
        if (index == -1) this.add(new AbstractMap.SimpleEntry<>(key, value));
        else this.set(index, new AbstractMap.SimpleEntry<>(key, value));
        return value;
    }

    public void forEach(BiConsumer<? super K, ? super V> action) {
        Objects.requireNonNull(action);
        for (Map.Entry<K, V> entry : this) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch (IllegalStateException ise) {
                throw new ConcurrentModificationException(ise);
            }
            action.accept(k, v);
        }
    }

    public V get(K key) {
        return this.stream()
                   .filter(entry -> Objects.equals(entry.getKey(), key))
                   .findFirst()
                   .map(Map.Entry::getValue)
                   .orElse(null);
    }

    public int getIndex(K key) {
        return IntStream.range(0, this.size())
                        .filter(i -> Objects.equals(this.get(i).getKey(), key))
                        .findFirst()
                        .orElse(-1);
    }

    public Collection<K> getKeys2() {
        return this.stream().map(Map.Entry::getKey).collect(Collectors.toCollection(LinkedList::new));
    }

    public Collection<V> getValues2() {
        return this.stream().map(Map.Entry::getValue).collect(Collectors.toCollection(LinkedList::new));
    }

    public Object[] getKeys() {
        return getKeys2().toArray(new Object[0]);
    }

    public Object[] getValues() {
        return getValues2().toArray(new Object[0]);
    }
}