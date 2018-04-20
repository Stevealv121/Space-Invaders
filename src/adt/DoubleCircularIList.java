package adt;

public class DoubleCircularIList<T> implements IList<T> {
    private DoubleCircularNode<T> head;
    private String type;
    private int size;

    public DoubleCircularIList(){
        this.head = null;
        this.size = 0;
    }

    public void add(T data){
        DoubleCircularNode<T> newNode = new DoubleCircularNode<>(data);

        if (this.head != null){
            if (size == 1){
                this.head.setNext(newNode);
                this.head.setPrev(newNode);
                newNode.setPrev(this.head);
                newNode.setNext(this.head);
            } else {
                DoubleCircularNode<T> temp = head;

                while (temp.getNext() != head){
                    temp = temp.getNext();
                }
                temp.setNext(newNode);
                newNode.setNext(this.head);
                this.head.setPrev(newNode);
                newNode.setPrev(temp);
            }

        } else {
            this.head = newNode;
            this.head.setNext(this.head);
            this.head.setPrev(this.head);
        }
        size++;
    }

    public void removeAtPos(int pos){
        if (head != null){
            DoubleCircularNode<T> temp = head;
            int i = 0;

            if (pos == 0){
                this.head = this.head.getNext();

                while(temp.getNext() != head){
                    temp = temp.getNext();
                }
                temp.setNext(temp.getNext().getNext());
                temp.getNext().getNext().setPrev(temp);
                size--;

            } else {
                i++;
                while (temp.getNext() != head){
                    if (i == pos){
                        temp.setNext(temp.getNext().getNext());
                        temp.getNext().getNext().setPrev(temp);
                        size--;
                        return;
                    }
                    temp = temp.getNext();
                    i++;
                }
            }

        }
    }

    public T getAtPos(int pos){
        int i = 0;

        if (this.head != null){
            DoubleCircularNode<T> temp = head;

            while(i <= size){
                if (i == pos)
                    return temp.getData();
                temp = temp.getNext();
                i++;
            }
        }
        return null;
    }

    public int size(){
        return size;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public DoubleCircularNode<T> getNode(int pos) {
        int i = 0;

        if (head != null){
            DoubleCircularNode<T> temp = head;

            while (i <= size){
                if (i == pos){
                    return temp;
                }
                temp = temp.getNext();
                i++;
            }
        }
        return null;
    }
}
