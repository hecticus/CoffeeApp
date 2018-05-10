package models;

import controllers.utils.ListPagerCollection;
import io.ebean.text.PathProperties;

import java.util.List;

/**
 * Created by yenny on 8/30/16.
 */
public interface AbstractDao <K, E>{

    E create(E entity);

    E update(E entity);

    boolean delete(K id);

    E findById(K id);

    List<E> findAll();

    List<E> findAll(Integer pageIndex, Integer pageSize);

    ListPagerCollection findAll(Integer pageIndex, Integer pageSize, String sort);

    ListPagerCollection findAll(Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties);
}