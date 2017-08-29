package models.dao.impl;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.avaje.ebean.text.PathProperties;
import models.dao.AbstractDao;
import models.dao.utils.ListPager;
import models.domain.AbstractEntity;

import models.dao.utils.ListPagerCollection;

import java.util.List;

/**
 * Created by yenny on 8/31/16.
 */
public abstract class AbstractDaoImpl<K, E> implements AbstractDao<K, E> {

    public Model.Finder<K, E> find;

    public AbstractDaoImpl(Class<E> type){
        find = new Model.Finder<K, E>(type);
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
          List<E> entities = find.where().eq("status_delete",0).findList();
        return entities;
    }

    @Override
    public List<E> findAll(Integer pageIndex, Integer pageSize){
        List<E> entities;
        if(pageIndex != -1 && pageSize != -1)
        //    entities = find.findPagedList(pageIndex, pageSize).getList();
            entities = find.where().eq("status_delete",0).findPagedList(pageIndex, pageSize).getList();
        else
            // entities =  find.all();
            entities = find.where().eq("status_delete",0).findList();
        return entities;
    }


    @Override
    public ListPagerCollection findAll(Integer pageIndex, Integer pageSize, String sort){
        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(find.where().eq("status_delete",0).orderBy(Sort(sort)).findList());
        return new ListPagerCollection(find.where().eq("status_delete",0).orderBy(Sort(sort)).findPagedList(pageIndex, pageSize).getList(), find.findRowCount(), pageIndex, pageSize);
    }

    @Override
    public ListPagerCollection findAll(Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties){
        ExpressionList expressionList = find.where();

        if(pathProperties != null && !pathProperties.isEmpty())
            expressionList.apply(pathProperties);

        if(sort != null)
            expressionList.orderBy(AbstractDaoImpl.Sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.eq("status_delete",0).findList());
        return new ListPagerCollection(expressionList.eq("status_delete",0).findPagedList(pageIndex, pageSize).getList(), expressionList.findRowCount(), pageIndex, pageSize);
    }


    static String Sort(String sort){
        if(sort == null)
            return "";
        if(sort.startsWith("-"))
            return sort.substring(1) + " desc";
        return sort + " asc";
    }


}
