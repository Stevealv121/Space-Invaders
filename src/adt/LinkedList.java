package adt;

public class LinkedList<T> extends List<T>{

    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(T value) {
        return add(new Node<T>(value));
    }

    /**
     * Add node to list.
     *
     * @param node
     *            to add to list.
     */
    private boolean add(Node<T> node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            Node<T> prev = tail;
            prev.next = node;
            tail = node;
        }
        size++;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(T value) {
        // Find the node
        Node<T> prev = null;
        Node<T> node = head;
        while (node != null && (!node.value.equals(value))) {
            prev = node;
            node = node.next;
        }

        if (node == null)
            return false;

        // Update the tail, if needed
        if (node.equals(tail)) {
            tail = prev;
            if (prev != null)
                prev.next = null;
        }

        Node<T> next = node.next;
        if (prev != null && next != null) {
            prev.next = next;
        } else if (prev != null && next == null) {
            prev.next = null;
        } else if (prev == null && next != null) {
            // Node is the head
            head = next;
        } else {
            // prev==null && next==null
            head = null;
        }

        size--;
        return true;
    }

    public boolean removeAtPos(int pos){
        Node temp = head;
        int i = 0;

        if(head==null){
            return false;
        }

        if(pos==0){
            head = head.next;
            size--;
            return false;
        }
        i++;

        while (temp.next != null){
            if (i == pos){
                temp.next = temp.next.next;
                size--;
            }else{
                temp = temp.next;
                i++;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T value) {
        Node<T> node = head;
        while (node != null) {
            if (node.value.equals(value))
                return true;
            node = node.next;
        }
        return false;
    }

    public T getAtPos(int pos){
        Node<T> temp = head;
        int i = 0;
        while(temp!=null){
            if(i==pos){
                return temp.value;
            }
            i++;
            temp = temp.next;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        java.util.Set<T> keys = new java.util.HashSet<T>();
        Node<T> node = head;
        if (node != null) {
            keys.add(node.value);

            Node<T> child = node.next;
            while (child != null) {
                if (!validate(child,keys))
                    return false;
                child = child.next;
            }
        }
        return (keys.size()==size);
    }

    private boolean validate(Node<T> node, java.util.Set<T> keys) {
        if (node.value==null)
            return false;

        keys.add(node.value);

        Node<T> child = node.next;
        if (child==null) {
            if (!node.equals(tail))
                return false;
        }
        return true;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node<T> node = head;
        while (node != null) {
            builder.append(node.value).append(", ");
            node = node.next;
        }
        return builder.toString();
    }

    private static class Node<T> {

        private T value = null;
        private Node<T> next = null;

        private Node() { }

        private Node(T value) {
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "value=" + value + " next=" + ((next != null) ? next.value : "NULL");
        }
    }
}
