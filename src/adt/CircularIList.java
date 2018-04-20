package adt;

public class CircularIList<T> implements IList<T> {
    private CircularNode<T> head;
    private String type;
    private int size;


    public CircularIList(){
        this.head = null;
        this.size = 0;
    }

    @Override
    public void add(T data){
        CircularNode<T> newNode = new CircularNode<>(data);

        if (this.head != null){
            if (size == 1){
                this.head.setNext(newNode);
                newNode.setNext(this.head);
            } else{
                CircularNode<T> temp = head;

                while (temp.getNext() != head){
                    temp = temp.getNext();
                }
                temp.setNext(newNode);
                newNode.setNext(this.head);
            }
        } else {
            this.head = newNode;
            this.head.setNext(this.head);
        }
        size++;
    }

    @Override
    public void removeAtPos(int pos){
        if (head != null){
            CircularNode<T> temp = head;
            int i = 0;

            if (pos == 0){
                this.head = this.head.getNext();

                while (temp.getNext() != head){
                    temp = temp.getNext();
                }

                temp.setNext(this.head.getNext());
                size--;
                if (size == -1)
                    size = 0;

            } else {
                i++;

                while (temp.getNext() != head){
                    if (i == pos){
                        temp.setNext(temp.getNext().getNext());
                        size--;
                        if (size == -1)
                            size = 0;
                        return;
                    }

                    i++;
                    temp = temp.getNext();
                }
            }
        }
    }

    @Override
    public T getAtPos(int pos){
        int i = 0;

        if (this.head != null){
            CircularNode<T> temp = head;

            while (i <= size){
                if (i == pos){
                    return temp.getData();
                }
                temp = temp.getNext();
                i++;
            }
        }
        return null;
    }

    @SuppressWarnings("Duplicates")
    public CircularNode<T> getNode(int pos){
        int i = 0;

        if (this.head != null){
            CircularNode<T> temp = head;

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
}