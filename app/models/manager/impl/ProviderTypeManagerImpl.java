package models.manager.impl;

import com.fasterxml.jackson.databind.JsonNode;
import models.dao.ProviderTypeDao;
import models.dao.impl.ProviderTypeDaoImpl;
import models.domain.ProviderType;
import models.manager.ProviderTypeManager;
import models.manager.responseUtils.Response;
import models.manager.responseUtils.responseObject.ProviderTypeResponse;
import play.libs.Json;
import play.mvc.Result;

import java.util.List;

import static play.mvc.Controller.request;

/**
 * Created by drocha on 12/05/17.
 */
public class ProviderTypeManagerImpl    implements ProviderTypeManager {




    private static ProviderTypeDao providerTypeDao = new ProviderTypeDaoImpl();

    @Override
    public Result create() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();


            JsonNode Name = json.get("name");
            if (Name == null)
                return Response.requiredParameter("name");


            // mapping object-json
            ProviderType providerType = Json.fromJson(json, ProviderType.class);


            providerType = providerTypeDao.create(providerType);
            return Response.createdEntity(Json.toJson(providerType));

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

    @Override
    public Result update() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            JsonNode id = json.get("id");
            if (id == null)
                return Response.requiredParameter("id");

            ProviderType providerType =  Json.fromJson(json, ProviderType.class);


            providerType = providerTypeDao.update(providerType);
            return Response.updatedEntity(Json.toJson(providerType));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @Override
    public Result delete(Long id) {
        try{
            ProviderType providerType = providerTypeDao.findById(id);
            if(providerType != null) {

                providerType.setStatusDelete(1);
                providerType = providerTypeDao.update(providerType);

                return Response.deletedEntity();
            } else {
                return  Response.message("Successful no existe el registro a eliminar");
            }
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }
    /*public Result delete(Long id) {
        try {

            ProviderType providerType = findById(id);
            //    providerTypeDao.delete(id);
            return Response.deletedEntity();

        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }*/

    @Override
    public Result findById(Long id) {
        try {
            ProviderType providerType = providerTypeDao.findById(id);
            return Response.foundEntity(Response.toJson(providerType, ProviderTypeResponse.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @Override
    public Result findAll(Integer index, Integer size) {
        try {
            List<ProviderType> providerTypes = providerTypeDao.findAll(index, size);
            return Response.foundEntity(Json.toJson(providerTypes));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    public Result  getProviderTypesByName( String name, String order)
    {
        String strOrder = "ASC";
        try {

            if (name.equals("-1")) name = "";

            if(!order.equals("-1")) strOrder = order;

            if(!strOrder.equals("ASC") && !strOrder.equals("DESC"))
                return Response.requiredParameter("order (ASC o DESC)");


            List<ProviderType> providerTypes = providerTypeDao.getProviderTypesByName(name,strOrder);
            return Response.foundEntity(Json.toJson(providerTypes));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }
}
