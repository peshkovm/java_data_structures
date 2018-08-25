public class MyHashMap<K, V> {
    private Node<K, V>[] table;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private final float loadFactor;
    private int threshold;
    private int size;
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
    static final int MAXIMUM_CAPACITY = 1 << 30;

    public MyHashMap() {
        loadFactor = DEFAULT_LOAD_FACTOR;
        threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
        table = (Node<K, V>[]) new Node[DEFAULT_INITIAL_CAPACITY];
    }

    public void put(K key, V value) {
        if (key == null)
            putForNullKey(value);
        else {
            int hash = hash(key);
            int index = indexFor(hash, table.length);
            if (!checkIdenticals(index, key, value)) {
                addEntry(index, hash, key, value);
            }
        }
    }

    public V remove(K key) {
        int hash = (key == null) ? 0 : hash(key);
        int index = indexFor(hash, table.length);
        Node<K, V> prev = table[index];
        Node<K, V> e = prev;

        while (e != null) {
            Node<K, V> next = e.next;
            Object k;
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k)))) {
                size--;
                if (prev == e)
                    table[index] = next;
                else
                    prev.next = next;
                return e.value;
            }
            prev = e;
            e = next;
        }

        return null;
    }

    private void putForNullKey(V value) {
        if (!checkNullIdenticals(value)) {
            addEntry(0, 0, null, value);
        }
    }

    private void addEntry(int index, int hash, K key, V value) {
        Node<K, V> x = table[index];
        table[index] = new Node<>(hash, key, value, x);
        if (size++ >= threshold)
            resize(2 * table.length);
    }


    private boolean checkIdenticals(int index, K key, V value) {
        Node<K, V> x = table[index];

        for (; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.value = value;
                return true;
            }
        }
        return false;
    }

    private boolean checkNullIdenticals(V value) {
        Node<K, V> x = table[0];

        for (; x != null; x = x.next) {
            if (x.key == null) {
                x.value = value;
                return true;
            }
        }
        return false;
    }

    private int indexFor(int hash, int tableLength) {
        return hash & (tableLength - 1);
    }

    private int hash(K key) {
        int h;
        return (h = key.hashCode()) ^ (h >>> 16);
    }

    void resize(int newCapacity) {
        if (table.length == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }

        Node<K, V>[] oldTable = table;
        table = new Node[newCapacity];
        transfer(oldTable);
    }

    void transfer(Node<K, V>[] table) {
        for (int i = 0; i < table.length; i++) {
            Node<K, V> x = table[i];
            for (; x != null; x = x.next) {
                put(x.key, x.value);
            }
        }
    }

    private static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        public Node(int hash, K key, V value, Node next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}