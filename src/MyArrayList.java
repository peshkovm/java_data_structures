import java.util.Arrays;

public class MyArrayList<T> {
    protected Object[] elementData;
    protected int size;
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    public MyArrayList(int initialCapacity) {
        if (initialCapacity >= 0)
            elementData = new Object[initialCapacity];
        else
            throw new IllegalArgumentException("Size of the list must be greater then 0. Received size = " + initialCapacity);
    }

    public MyArrayList() {
        this(10);
    }

    public boolean add(T element) {
        ensureCapacity(size + 1);
        elementData[size++] = element;
        return true;
    }

    public boolean add(int index, T element) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Index out of bounds. index = " + index + " size = " + size);
        else {
            ensureCapacity(index + 1);
            System.arraycopy(elementData, index, elementData, index + 1, size - index);
            elementData[index] = element;
            size++;
            return true;
        }
    }

    public boolean remove(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Index out of bounds. index = " + index + " size = " + size);
        else {
            int numMoved = size - index - 1;
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
            elementData[--size] = null;
            return true;
        }
    }

    public boolean remove(T element) {
        boolean found = false;

        for (int i = 0; i < size; i++)
            if (elementData[i] != null && elementData[i].equals(element)) {
                found = remove(i);
                break;
            }

        return found;
    }

    T get(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Index out of bounds. index = " + index + " size = " + size);
        else
            return (T) elementData[index];
    }

    T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index out of bounds. index = " + index + " size = " + size);
        } else {
            T oldValue = (T) elementData[index];
            elementData[index] = element;
            return oldValue;
        }
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elementData.length) {
            elementData = grow(minCapacity);
        }
    }

    private Object[] grow(int minCapacity) {
        int newCapacity = (minCapacity * 3) / 2 + 1;
        return elementData = Arrays.copyOf(elementData, newCapacity);
    }

    @Override
    public String toString() {
        String str = "MyArrayList{";
        for (int i = 0; i < size; i++) {
            if (elementData[i] != null)
                str = str.concat(elementData[i].toString());
            else
                str = str.concat("null");
            if (i < size - 1)
                str = str.concat(", ");
        }
        str = str.concat("}");

        return str;
    }

    public <E extends Number> boolean someMethod() {
        E[] mas;
        mas = (E[]) new Object[10];
        return true;
    }
}