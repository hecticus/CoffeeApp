package models.dao.impl;

import models.dao.SecurityTagDao;
import models.domain.SecurityTag;

import java.util.Calendar;
import java.util.List;

/**
 * Created by yenny on 9/30/16.
 */
public class SecurityTagDaoImpl extends AbstractDaoImpl<Long, SecurityTag> implements SecurityTagDao {


    public SecurityTagDaoImpl() {
        super(SecurityTag.class);
    }

    ///Fucnion Ultra sensual que permitira fillear dese lleno
    @Override
    public SecurityTag GetTagByName(String name)
    {
             List<SecurityTag> sroutes = find
                .where()
                .eq("name", name)
                .findList();

        if(!sroutes.isEmpty())
            return sroutes.get(0);
        return null;
    }

    @Override
    public SecurityTag CheckAndInsert(String resourceName)
    {
        List<SecurityTag> sroutes = find
                .where()
                .eq("name", resourceName)
                .findList();

        if(!sroutes.isEmpty())
            return sroutes.get(0);

        SecurityTag obj =  new SecurityTag();
        obj.setName(resourceName);
        obj.setDescription("AUTOMATIC ADD");
        obj.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
        obj.setUpdatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
        obj.save();
        return obj;
    }


}
