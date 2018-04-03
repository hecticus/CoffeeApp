package models.dao.impl;

import io.ebean.ExpressionList;
import io.ebean.FetchPath;
import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.text.PathProperties;
import models.dao.AbstractDao;
import models.dao.utils.ListPager;
import models.domain.AbstractEntity;

import models.dao.utils.ListPagerCollection;

import java.util.List;

/**
 * Created by yenny on 8/31/16.
 */
public abstract class AbstractDaoImpl<K, E> implements AbstractDao<K, E> {

    public Finder<K, E> find;

    public AbstractDaoImpl(Class<E> type){
        find = new Finder<K, E>(type);
    }

    @Override
    public E create(E entity){
 //       ((AbstractEntity) entity).setId(null);
        ((Model) entity).save();
        return entity;
    }

    @Override
    public E update(E entity){
        //warm: si no exite el <id> pero tampoco se le manda actualizar campos, no genera una excepción
        ((Model) entity).update();
        return entity;
    }

    @Override
    public boolean delete(K id) {
        E entity = find.byId(id);
        ((Model) entity).delete();
        return true;
    }

    @Override
    public E findById(K id){
        E entity = find.byId(id);
        return entity;
    }

    @Override
    public List<E> findAll(){
    //    List<E> entities =  find.all();
          List<E> entities = find.query().where().eq("status_delete",0).findList();
        return entities;
    }

    @Override
    public List<E> findAll(Integer pageIndex, Integer pageSize){
        List<E> entities;
        if(pageIndex != -1 && pageSize != -1)
        //    entities = find.setFirstRow(pageIndex).setMaxRows(pageSize).findList();
            entities = find.query().where().eq("status_delete",0).setFirstRow(pageIndex).setMaxRows(pageSize).findList();
        else
            // entities =  find.all();
            entities = find.query().where().eq("status_delete",0).findList();
        return entities;
    }


    @Override
    public ListPagerCollection findAll(Integer pageIndex, Integer pageSize, String sort){
        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(find.query().where().eq("status_delete",0).orderBy(Sort(sort)).findList());
        return new ListPagerCollection(
                find.query().where().eq("status_delete",0).orderBy(Sort(sort)).setFirstRow(pageIndex).setMaxRows(pageSize).findList(),
                find.query().findCount(),
                pageIndex,
                pageSize);
    }

    public ListPagerCollection findAll(Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties){
        ExpressionList expressionList = find.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(sort != null)
            expressionList.orderBy(AbstractDaoImpl.Sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.eq("status_delete",0).findList());
        return new ListPagerCollection(
                expressionList.eq("status_delete",0).setFirstRow(pageIndex).setMaxRows(pageSize).findList(),
                expressionList.eq("status_delete",0).setFirstRow(pageIndex).setMaxRows(pageSize).findCount(),
                pageIndex,
                pageSize);
    }


    static String Sort(String sort){
        if(sort == null)
            return "";
        if(sort.startsWith("-"))
            return sort.substring(1) + " desc";
        return sort + " asc";
    }


}
