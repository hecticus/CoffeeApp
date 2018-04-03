package models.manager.impl;

import io.ebean.text.PathProperties;
import com.fasterxml.jackson.databind.JsonNode;
import models.dao.FarmDao;
import models.dao.impl.FarmDaoImpl;
import models.dao.utils.ListPagerCollection;
import models.domain.Farm;
import models.manager.requestUtils.Request;
import models.manager.responseUtils.ExceptionsUtils;
import models.manager.responseUtils.PropertiesCollection;
import models.manager.responseUtils.Response;
import models.manager.responseUtils.ResponseCollection;
import play.libs.Json;
import models.manager.FarmManager;
import play.mvc.Result;

import java.util.List;

import static play.mvc.Controller.request;

/**
 * Created by darwin on 30/08/17.
 */
public class FarmManagerImpl implements FarmManager {




    private static FarmDao farmDao = new FarmDaoImpl();

    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public FarmManagerImpl(){
        propertiesCollection.putPropertiesCollection("s", "(idFarm, NameFarm)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    @Override
    public Result create() {
        try
        {
            return null;

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

    @Override
    public Result update() {
        try
        {
            return null;
            
        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @Override
    public Result delete(Long id) {
        try{
            return null;
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }
    
    @Override
    public Result findById(Long id) {
        try {
            Farm farm = farmDao.findById(id);
            return Response.foundEntity(Response.toJson(farm, Farm.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }


    @Override
    public Result findAll(Integer index, Integer size, String sort, String collection) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = farmDao.findAll(index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

    @Override
    public Result findAllSearch(String name, Integer index, Integer size, String sort, String collection) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = farmDao.findAllSearch(name, index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


    @Override
    public Result preCreate() {
        try {

            return  null;
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

}
