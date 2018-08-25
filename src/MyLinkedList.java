public class MyLinkedList<T> {
    private Node first;
    private Node last;
    private int size;

    public MyLinkedList() {
    }

    public boolean add(T element) {
        final Node newEntry = new Node(element, null, last);

        if (last == null)
            first = newEntry;
        else
            last.next = newEntry;
        last = newEntry;
        size++;
        return true;
    }

    public boolean add(int index, T element) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Index ot of bounds. Index = " + index + " size = " + size);
        else if (index == size)
            add(element);
        else
            addBefore(element, node(index));
        return true;
    }

    public T remove(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Index ot of bounds. Index = " + index + " size = " + size);
        return unlink(node(index));
    }

    public boolean remove(T element) {
        if (element == null)
            for (Node x = first; x != null; x = x.next) {
                if (x.element == null) {
                    unlink(x);
                    return true;
                }
            }
        else {
            for (Node x = first; x != null; x = x.next) {
                if (element.equals(x.element)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    public T get(int index) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Index ot of bounds. Index = " + index + " size = " + size);
        return node(index).element;
    }

    public T set(int index, T element) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Index ot of bounds. Index = " + index + " size = " + size);
        Node x = node(index);
        T oldVal = x.element;
        x.element = element;
        return oldVal;
    }

    private T unlink(Node node) {
        final T element = node.element;
        final Node prev = node.prev;
        final Node next = node.next;

        if (prev == null)
            first = next;
        else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null)
            last = prev;
        else {
            next.prev = prev;
            node.next = null;
        }
        node.element = null;
        size--;

        return element;
    }

    private void addBefore(T element, Node node) {
        final Node newNode = new Node(element, node, node.prev);
        final Node prev = node.prev;
        node.prev = newNode;

        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private Node node(int index) {
        if (index < (size >> 1)) {
            Node x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    private class Node {
        T element;
        Node next;
        Node prev;

        Node(T element, Node next, Node prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public String toString() {
        String str = "MyLinkedList{";
        Node x = first;

        for (; x != last; x = x.next) {
            str = str.concat(x.element + " ");
        }
        str = str.concat(x.element + "}");

        return str;
    }
}