package net.thumbtack.school.colors;

public interface Colored
{
    void setColor(Color color) throws ColorException;
    void setColor(String colorString) throws ColorException;
    Color getColor();
}
