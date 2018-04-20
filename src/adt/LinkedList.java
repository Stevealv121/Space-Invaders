package adt;

public class LinkedList<T> implements IList<T> {
    private Node<T> head;
    private int size;
    private String type;

    public LinkedList(){
        head = null;
    }

    public void add(T data){
        if (head == null)
            head = new Node<>(data, null);
        else{
            Node<T> temp = head;
            while(temp.getNext() != null)
                temp = temp.getNext();
            temp.setNext(new Node<>(data, null));
        }
        size++;
    }

    public void removeAtPos(int pos) {
            Node<T> temp = head;
            int i = 0;

            if (head == null){
                return;
            }

            if (pos == 0) {
                head = head.getNext();
                size--;
                return;
            }

            i++;

            while (temp.getNext() != null) {
                if (i == pos) {
                    temp.setNext(temp.getNext().getNext());
                    size--;
                    return;
                }
                else{
                    temp = temp.getNext();
                    i++;
                }
            }
    }

    public int size(){
        return size;
    }

    public T getAtPos(int pos){
        Node<T> temp = head;
        int i = 0;

        while (temp != null){
            if (i == pos) {
                return temp.getData();
            }

            i++;
            temp = temp.getNext();
        }
        return null;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }

    @Override
    public CircularNode<T> getNode(int pos) {
        return null;
    }
}
