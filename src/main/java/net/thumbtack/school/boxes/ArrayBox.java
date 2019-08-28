package net.thumbtack.school.boxes;

public class ArrayBox<T>
{
    private T[] boxArray;

    public ArrayBox(T[] boxArray)
    {
        this.boxArray = boxArray;
    }

    T[] getContent()
    {
        return boxArray;
    }

    public void setContent(T[] boxArray)
    {
        this.boxArray = boxArray;
    }

    public T getElement(int index)
    {
        return boxArray[index];
    }

    public void setElement(T element, int index)
    {
        boxArray[index] = element;
    }

    public boolean isSameSize(ArrayBox box)
    {
        return this.boxArray.length == box.getContent().length;
    }
}
