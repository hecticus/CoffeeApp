package models.manager.responseUtils.responseObject;

/**
 * Created by drocha on 25/04/17.
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
