package models.dao;

import play.mvc.Result;

import java.util.List;

import static play.mvc.Results.ok;

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
}