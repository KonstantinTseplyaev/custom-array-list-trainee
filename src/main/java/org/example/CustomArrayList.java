package org.example;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Класс CustomArrayList представляет собой список с динамически расширяемой емкостью.
 * Реализует интерфейс CustomList.
 *
 * @param <T> тип элементов в списке
 */
public class CustomArrayList<T> implements CustomList<T> {
    private int capacity;
    private int size;
    private T[] data;

    /**
     * Конструктор без параметров, создает экземпляр CustomArrayList с емкостью по умолчанию (10).
     */
    public CustomArrayList() {
        this.capacity = DEFAULT_BASE_CAPACITY;
        this.data = (T[]) new Object[this.capacity];
        this.size = 0;
    }

    /**
     * Конструктор с параметром, создает экземпляр CustomArrayList с указанной начальной емкостью.
     *
     * @param capacity начальная емкость списка
     * @throws IllegalArgumentException если начальная емкость меньше нуля
     */
    public CustomArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be less 0");
        }
        this.capacity = capacity;
        this.data = (T[]) new Object[this.capacity];
        this.size = 0;
    }

    /**
     * Добавляет элемент в конец списка.
     * Если на момент добавления нового знаечения список заполнен, генерирует новый массив с увеличенной емкостью.
     *
     * @param element элемент, который нужно добавить
     */
    @Override
    public void add(T element) {
        ensureCapacity();
        this.data[this.size++] = element;
    }

    /**
     * Добавляет элемент в список по указанному индексу.
     * Если на момент добавления нового знаечения список заполнен, генерирует новый массив с увеличенной емкостью.
     *
     * @param element элемент, который нужно добавить
     * @param index   индекс массива, по которому в него будет добавлен элемент
     */
    @Override
    public void add(T element, int index) {
        checkIndex(index);
        ensureCapacity();
        addByIndex(element, index);
    }

    /**
     * Извлекает элемент из списка по указанному индексу.
     *
     * @param index индекс элемента, который нужно получить
     * @return элемент, находящийся по указанному индексу массива
     */
    @Override
    public T get(int index) {
        checkIndex(index);
        return this.data[index];
    }

    /**
     * Удаляет первое вхождение элемента из списка (в классе элемента должен быть корректно реализован equals)
     *
     * @param element элемент, который нужно удалить
     */
    @Override
    public void remove(T element) {
        for (int i = 0; i < this.size; i++) {
            if (this.data[i].equals(element)) {// NPE если элемент пустой
                removeByIndex(i);
                return;
            }
        }
    }

    /**
     * Удаляет элемент из списка по его индексу
     *
     * @param index индекс, по которому находится элемент для удаления
     */
    @Override
    public void remove(int index) {
        checkIndex(index);
        removeByIndex(index);
    }

    /**
     * Удаляет все элементы из списка (сохраняет текущую вместимость)
     */
    @Override
    public void removeAll() {
        if (this.size == 0) {
            return;
        }
        Arrays.fill(this.data, null);
        this.size = 0;
    }

    /**
     * Сортирует все элементы списка согласно условию в компараторе.
     * Используется рекурсивный алгоритм быстрой сортировки O(n * log n).
     *
     * @param comp компаратор, на основе которого происходит сортировка
     */
    @Override
    public void sort(Comparator<? super T> comp) {
        quickSort(comp, this.data, 0, this.size - 1);
    }

    /**
     * Проверяет, находится ли объект в списке (в классе элемента должен быть корректно реализован equals)
     *
     * @param element искомый элемент
     * @return true, если искомый элемент найден в списке, иначе false
     */
    @Override
    public boolean hasElement(T element) {
        for (int i = 0; i < this.size; i++) {
            if (this.data[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Урезает вместимость списка до текущего количества элементов в списке.
     * (иными словами, удаляет все null ячейки для оптмизации используемой памяти)
     */
    @Override
    public void trimCapacityToSize() { // молодец что сделал доп методы
        if (this.size == 0) {
            if (this.capacity > DEFAULT_BASE_CAPACITY) {
                this.capacity = DEFAULT_BASE_CAPACITY;
            }
            this.data = getDataWithNewCapacity();
        } else {
            this.capacity = this.size;
            this.data = Arrays.copyOf(this.data, this.capacity);
        }
    }

    /**
     * Возвращает количество элементов в списке.
     *
     * @return количество элементов в списке
     */
    @Override
    public int size() {
        return this.size;
    }

    private void ensureCapacity() {
        if (this.size == this.capacity) {
            this.capacity = getNewCapacity();
            T[] newData = getDataWithNewCapacity();
            System.arraycopy(this.data, 0, newData, 0, this.size);
            this.data = newData;
        }
    }

    private int getNewCapacity() {
        if (this.capacity < DEFAULT_BASE_CAPACITY) {
            return DEFAULT_BASE_CAPACITY;
        } else {
            return (this.capacity * 3) / 2 + 1;
        }
    }

    @SuppressWarnings("unchecked")
    private T[] getDataWithNewCapacity() {
        return (T[]) new Object[this.capacity];
    }

    private void addByIndex(T element, int index) {
        System.arraycopy(this.data, index, this.data, index + 1, this.size - index);
        this.data[index] = element;
        this.size++;
    }

    private void removeByIndex(int index) {
        if (index == this.size - 1) {
            this.data[index] = null;
            this.size--;
            return;
        }

        System.arraycopy(this.data, index + 1, this.data, index, this.size - index + 1);
        this.size--;
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }

        if (index >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        }
    }

    private void quickSort(Comparator<? super T> comp, T[] array, int low, int high) {
        if (array.length == 0 || low >= high) {
            return;
        }

        int baseIndex = low + (high - low) / 2;
        T baseValue = array[baseIndex];

        int l = low;
        int h = high;

        while (l <= h) {
            while (comp.compare(baseValue, array[l]) > 0) {
                l++;
            }
            while (comp.compare(baseValue, array[h]) < 0) {
                h--;
            }
            if (l <= h) {
                T swap = array[l];
                array[l] = array[h];
                array[h] = swap;
                l++;
                h--;
            }
        }

        if (low < h) {
            quickSort(comp, array, low, h);
        }

        if (high > l) {
            quickSort(comp, array, l, high);
        }
    }
}
