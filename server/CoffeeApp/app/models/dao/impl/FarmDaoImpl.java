package models.dao.impl;

import io.ebean.ExpressionList;
import io.ebean.text.PathProperties;
import io.ebean.FetchPath;
import models.dao.FarmDao;
import models.dao.utils.ListPagerCollection;
import models.domain.Farm;

/**
 * Created by drocha on 31/05/17.
 */
public class FarmDaoImpl extends AbstractDaoImpl<Long, Farm> implements FarmDao {

    public FarmDaoImpl() {
        super(Farm.class);
    }

    public ListPagerCollection findAllSearch(String name, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties) {
        ExpressionList expressionList = find.query().where().eq("status_delete",0);

        if(pathProperties != null)
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.icontains("name", name);

        if(sort != null)
            expressionList.orderBy(AbstractDaoImpl.Sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(), expressionList.query().findCount(), pageIndex, pageSize);
    }
    
}
