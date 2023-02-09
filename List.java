
public interface List<T> {

    public boolean empty();

    public void findFirst();

    public void findNext();

    public boolean last();

    public T retrieve();

    public void update(T e);

    public void insert(T e);

}
