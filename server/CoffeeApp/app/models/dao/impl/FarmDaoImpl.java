package models.dao.impl;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.text.PathProperties;
import models.dao.FarmDao;
import models.dao.utils.ListPagerCollection;
import models.domain.Farm;

/**
 * Created by drocha on 31/05/17.
 */
public class FarmDaoImpl  extends AbstractDaoImpl<Long, Farm> implements FarmDao {

    public FarmDaoImpl() {
        super(Farm.class);
    }

    @Override
    public ListPagerCollection findAllSearch(String name, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties) {
        ExpressionList expressionList = find.where().eq("status_delete",0);

        if(pathProperties != null)
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.icontains("name", name);

        if(sort != null)
            expressionList.orderBy(AbstractDaoImpl.Sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.findPagedList(pageIndex, pageSize).getList(), expressionList.findRowCount(), pageIndex, pageSize);
    }
    
}
