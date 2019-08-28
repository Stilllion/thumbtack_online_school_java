package net.thumbtack.school.boxes;

import net.thumbtack.school.area.HasArea;
import net.thumbtack.school.figures.v3.Figure;

public class PairBox<T extends Figure, V extends Figure> implements HasArea
{
    private V objV;
    private T objT;

    public PairBox(T objT, V objV)
    {
        this.objT = objT;
        this.objV = objV;
    }

    public T getContentFirst()
    {
        return objT;
    }

    public void setContentFirst(T objT)
    {
        this.objT = objT;
    }

    public V getContentSecond()
    {
        return objV;
    }

    public void  setContentSecond(V objV)
    {
        this.objV = objV;
    }

    @Override
    public double getArea()
    {
        return objT.getArea() + objV.getArea();
    }

    public boolean isAreaEqual(PairBox pairBox)
    {
        return this.getArea() == pairBox.getArea();
    }

   public static boolean isAreaEqual(PairBox pairBox1, PairBox pairBox2)
    {
        return pairBox1.getArea() == pairBox2.getArea();
    }
}
