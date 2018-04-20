package adt;

public interface IList<T> {

    void add(T data);
    void removeAtPos(int pos);
    T getAtPos(int pos);
    /**
     * Size of the list.
     *
     * @return size of the list.
     */
    int size();
    void setType(String type);
    String getType();
    INode<T> getNode(int pos);
}
