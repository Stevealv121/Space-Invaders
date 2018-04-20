package adt;

public class Node<T> implements INode<T>{
    public T data;
    public Node<T> next;

    Node(T data, Node<T> next){
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public String toString(){
        return data + "";
    }
}