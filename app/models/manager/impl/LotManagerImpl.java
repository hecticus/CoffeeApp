package models.manager.impl;

import com.fasterxml.jackson.databind.JsonNode;
import models.dao.LotDao;
import models.dao.impl.LotDaoImpl;
import models.domain.Lot;
import models.manager.LotManager;
import models.manager.responseUtils.Response;
import models.manager.responseUtils.responseObject.LotResponse;
import play.libs.Json;
import play.mvc.Result;

import java.util.List;

import static play.mvc.Controller.request;

/**
 * Created by drocha on 26/04/17.
 */
public class LotManagerImpl implements LotManager {




    private static LotDao lotDao = new LotDaoImpl();

    @Override
    public Result create() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();


            JsonNode area = json.get("area");
            if (area == null)
                return Response.requiredParameter("area");

            JsonNode Name = json.get("name");
            if (Name == null)
                return Response.requiredParameter("name");

            JsonNode farm = json.get("farm");
            if (farm == null)
                return Response.requiredParameter("farm");

            JsonNode status = json.get("status");
            if (status == null)
                return Response.requiredParameter("status");




            // mapping object-json
            Lot lot = Json.fromJson(json, Lot.class);


            lot = lotDao.create(lot);
            return Response.createdEntity(Json.toJson(lot));

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

            Lot lot =  Json.fromJson(json, Lot.class);


            lot = lotDao.update(lot);
            return Response.updatedEntity(Json.toJson(lot));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @Override
    public Result delete(Long id) {
        try{
            Lot lot = lotDao.findById(id);
            if(lot != null) {

                lot.setStatusDelete(1);
                lot = lotDao.update(lot);

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

            Lot lot = findById(id);
            //    lotDao.delete(id);
            return Response.deletedEntity();

        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }*/

    @Override
    public Result findById(Long id) {
        try {
            Lot lot = lotDao.findById(id);
            return Response.foundEntity(Response.toJson(lot, LotResponse.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @Override
    public Result findAll(Integer index, Integer size) {
        try {
            List<Lot> lots = lotDao.findAll(index, size);
            return Response.foundEntity(Json.toJson(lots));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

}