package org.example;

import java.util.Comparator;

/**
 * Интерфейс CustomList содержит методы для взаимодействия с динамически расширяемым списком.
 *
 * @param <T> тип элементов в списке
 */
public interface CustomList<T> {
    int DEFAULT_BASE_CAPACITY = 10;

    void add(T t);

    void add(T t, int index);

    T get(int index);

    void remove(T t);

    void remove(int index);

    void removeAll();

    void trimCapacityToSize();

    void sort(Comparator<? super T> comp);

    boolean hasElement(T t);

    int size();
}
