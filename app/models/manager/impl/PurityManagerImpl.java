package models.manager.impl;

import com.fasterxml.jackson.databind.JsonNode;
import models.dao.PurityDao;
import models.dao.impl.PurityDaoImpl;
import models.domain.Purity;
import models.manager.PurityManager;
import models.manager.responseUtils.Response;
import models.manager.responseUtils.responseObject.PurityResponse;
import play.libs.Json;
import play.mvc.Result;

import java.util.List;

import static play.mvc.Controller.request;

/**
 * Created by drocha on 26/04/17.
 */
public class PurityManagerImpl    implements PurityManager {




    private static PurityDao purityDao = new PurityDaoImpl();

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


            JsonNode DiscountRate = json.get("discountRate");
            if (DiscountRate == null)
                return Response.requiredParameter("discountRate");

            JsonNode status = json.get("status");
            if (status == null)
                return Response.requiredParameter("status");




            // mapping object-json
            Purity purity = Json.fromJson(json, Purity.class);


            purity = purityDao.create(purity);
            return Response.createdEntity(Json.toJson(purity));

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

            Purity purity =  Json.fromJson(json, Purity.class);


            purity = purityDao.update(purity);
            return Response.updatedEntity(Json.toJson(purity));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @Override
    public Result delete(Long id) {
        try{
            Purity purity = purityDao.findById(id);
            if(purity != null) {

                purity.setStatusDelete(1);
                purity = purityDao.update(purity);

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

            Purity purity = findById(id);
            //    purityDao.delete(id);
            return Response.deletedEntity();

        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }*/

    @Override
    public Result findById(Long id) {
        try {
            Purity purity = purityDao.findById(id);
            return Response.foundEntity(Response.toJson(purity, PurityResponse.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @Override
    public Result findAll(Integer index, Integer size) {
        try {
            List<Purity> puritys = purityDao.findAll(index, size);
            return Response.foundEntity(Json.toJson(puritys));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

}

