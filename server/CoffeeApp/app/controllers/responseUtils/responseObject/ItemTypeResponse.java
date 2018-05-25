package controllers.responseUtils.responseObject;

/**
 * Created by sm21 on 10/05/18.
 */
public class ItemTypeResponse extends AbstractEntityResponse
{
    public Long idItemType;
    public String name;
    public Float cost;
    public Integer status;

    public static class Unit
    {

        public Long idUnit;
        public String name;
        public Integer status;
    }

    public Unit unit;
}
