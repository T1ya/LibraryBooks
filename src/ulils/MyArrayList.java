package ulils;

import java.util.Iterator;

public class MyArrayList<T> implements MyList<T> {
    private T[] array;
    private int cursor; // по умолчанию = 0

    //для итератора
    private class MyIterator implements Iterator<T> {
        int currentIndex = 0;

        //есть ли след. элемент?
        @Override
        public boolean hasNext() {
            return currentIndex < cursor;
        }
        //вернуть след элемент
        @Override
        public T next() {
            return array[currentIndex++];
        }
    }

    @SuppressWarnings("unchecked")
    public MyArrayList() {
        // Стирание типов - невозможно создать объект типа Т!
        // Поэтому в конструкторе используют такой вот "костыль"
        this.array = (T[]) new Object[10];
    }

    @SuppressWarnings("unchecked")
    public MyArrayList(T[] array) {
        if (array == null || array.length == 0) {
            // проверка переданного массива. В случае если передан пустой массив, создадим массив по умолчанию
            this.array = (T[]) new Object[10];
            this.cursor = 0;
        } else {
            // Создаем новый массив с помощью метода add.
            // А чтобы избежать операции расширения массива, сделаем его сразу вдвое больше
            this.array = (T[]) new Object[array.length * 2];
            // Копируем элементы
            this.addAll(array);
            // актуализируем курсор.
            this.cursor = array.length;
        }
    }

    public void add(T value) {
        if (cursor == array.length) {
            expandArray();
        }
        array[cursor] = value;
        cursor++;
    }

    @SuppressWarnings("unchecked")
    private void expandArray() {
        System.out.println("Расширяем внутренний массив! Курсор = " + cursor);
        T[] newArray = (T[]) new Object[array.length * 2];
        for (int i = 0; i < cursor; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    public void addAll(T... values) {
        if (values == null) {
            System.out.println("Error - parameter is null!");
            return;
        }
        for (int i = 0; i < values.length; i++) {
            add(values[i]);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if (cursor == 0) return "[]";
        for (int i = 0; i < cursor; i++) {
            sb.append(array[i]);
            sb.append(i < cursor - 1 ? ", " : "]");
        }
        return sb.toString();
    }

    public int size() {
        return cursor;
    }

    public T get(int index) {
        // Проконтролировать входящий индекс!
        if (index >= 0 && index < cursor) {
            return array[index];
        }

        return null;
    }

    @Override
    public void set(int index, T value) {
        if (index >= 0 && index < cursor) {
            array[index] = value;
            return;
        }
        System.out.println("Error. Invalid index " + index);
    }

    public T remove(int index) {
        if (index >= 0 && index < cursor) {
            // Логика удаления
            T value = array[index]; // запомнить старое значение
            for (int i = index; i < cursor - 1; i++) { // граница перебора индексов
                array[i] = array[i + 1];
            }
            array[cursor - 1] = null;
            cursor--;
            return value; // вернуть старое значение
        } else {
            // Индекс не валидный
            return null;
        }
    }

    @Override
    public boolean isEmpty() {
        return cursor == 0;
    }

    public int indexOf(T value) {
        // Поиск по значению
        for (int i = 0; i < cursor; i++) {
            if (array[i] != null && array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(T value) {
        // Индекс последнего вхождения
        for (int i = cursor - 1; i >= 0; i--) {
            if (array[i] != null && array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    public boolean remove(T value) {
        int index = indexOf(value);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] result = (T[]) new Object[cursor];
        for (int i = 0; i < cursor; i++) {
            result[i] = array[i];
        }
        return result;
    }
    // должны вернуть класс типа Т, который имплементирует итератор
    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }
}

