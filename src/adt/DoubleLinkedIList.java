package adt;

public class DoubleLinkedIList<T> implements IList<T> {
    private DoubleNode<T> head;
    private String type;
    private int size;

    public DoubleLinkedIList(){
        this.head = null;
        this.size = 0;
    }

    public void add(T data){
        if (this.head == null)
            head = new DoubleNode<>(data, null, null);

        else{
            DoubleNode<T> temp = head;
            DoubleNode<T> newNode = new DoubleNode<>(data);

            while(temp.getNext() != null){
                temp = temp.getNext();
            }

            temp.setNext(newNode);
            newNode.setPrev(temp);
        }
        size++;
    }

    public void removeAtPos(int pos){
        int i = 0;

        if (head != null){
            if (pos == 0){
                head.getNext().setPrev(null);
                head = head.getNext();
                head.setPrev(null);
                size--;
            } else{
                DoubleNode<T> temp = head;
                i++;

                while (temp.getNext() != null){
                    if (i == pos){
                        temp.setNext(temp.getNext().getNext());

                        if (temp.getNext() != null)
                            temp.getNext().setPrev(temp);

                        size--;
                        return;
                    } else{
                        i++;
                        temp = temp.getNext();
                    }
                }
            }
        }
    }

    public T getAtPos(int pos){
        if (head != null){
            DoubleNode<T> temp = head;
            int i = 0;

            while(temp != null){
                if (i == pos)
                    return temp.getData();

                i++;
                temp = temp.getNext();
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
    public CircularNode<T> getNode(int pos) {
        return null;
    }
}

