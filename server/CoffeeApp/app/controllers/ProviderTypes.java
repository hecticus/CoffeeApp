package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.ItemType;
import models.ProviderType;
import models.manager.ProviderTypeManager;
import models.manager.impl.ProviderTypeManagerImpl;
import models.responseUtils.Response;
import play.libs.Json;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import java.util.List;

import static play.mvc.Controller.request;

/**
 * Created by drocha on 12/05/17.
 */
public class ProviderTypes {

    private static ProviderType providerTypeDao = new ProviderType();
    private static ItemType itemTypeDao = new ItemType();

    @CoffeAppsecurity
    public Result create() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();


            JsonNode Name = json.get("name");
            if (Name == null)
                return Response.requiredParameter("name");

            int registered = providerTypeDao.getExist(Name.asText().toUpperCase());
            if(registered==0) return  Response.messageExist("name");
            if(registered==1) return  Response.messageExistDeleted("name");


            // mapping object-json
            ProviderType providerType = Json.fromJson(json, ProviderType.class);


            providerType = providerTypeDao.create(providerType);
            return Response.createdEntity(Json.toJson(providerType));

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

    @CoffeAppsecurity
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

            JsonNode Name = json.get("name");
            if (Name != null)
            {
                int registered = providerTypeDao.getExist(Name.asText().toUpperCase());
                if(registered==0) return  Response.messageExist("name");
                if(registered==1) return  Response.messageExistDeleted("name");

                providerType.setNameProviderType(Name.asText().toUpperCase());
            }


            providerType = providerTypeDao.update(providerType);
            return Response.updatedEntity(Json.toJson(providerType));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            ProviderType providerType = providerTypeDao.findById(id);
            List<ItemType> itemTypes = itemTypeDao.getOpenByProviderTypeId(id);
            if(providerType != null  && itemTypes.size()==0) {

                providerType.setStatusDelete(1);
                providerType = providerTypeDao.update(providerType);

                return Response.deletedEntity();
            } else {

                if(providerType == null)  return  Response.message("Successful no existe el registro a eliminar");
                else  return  Response.message("Successful el registro tiene facturas aun no cerradas");
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

    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            ProviderType providerType = providerTypeDao.findById(id);
            return Response.foundEntity(Response.toJson(providerType, ProviderType.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @CoffeAppsecurity
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
