package models.manager.impl;

import com.fasterxml.jackson.databind.JsonNode;
import models.dao.InvoiceDetailDao;
import models.dao.ItemTypeDao;
import models.dao.ProviderTypeDao;
import models.dao.impl.InvoiceDetailDaoImpl;
import models.dao.impl.ItemTypeDaoImpl;
import models.dao.UnitDao;
import models.dao.impl.ProviderTypeDaoImpl;
import models.dao.impl.UnitDaoImpl;
import models.domain.ItemType;
import models.domain.InvoiceDetail;
import models.manager.ItemTypeManager;
import models.manager.responseUtils.Response;
import models.manager.responseUtils.responseObject.ItemTypeResponse;
import play.libs.Json;
import play.mvc.Result;

import java.util.List;

import static play.mvc.Controller.request;

/**
 * Created by drocha on 25/04/17.
 */
public class ItemTypeManagerImpl implements ItemTypeManager {





    private static ItemTypeDao itemTypeDao = new ItemTypeDaoImpl();
    private static UnitDao unitDao = new UnitDaoImpl();
    private static ProviderTypeDao providerTypeDao = new ProviderTypeDaoImpl();
    private static InvoiceDetailDao invoiceDetailDao = new InvoiceDetailDaoImpl();

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

            int registered = itemTypeDao.getExist(Name.asText().toUpperCase());
            if(registered==0) return  Response.messageExist("name");
            if(registered==1) return  Response.messageExistDeleted("name");

            JsonNode cost = json.get("cost");
            if (cost == null)
                return Response.requiredParameter("cost");

            JsonNode id_unit = json.get("id_unit");
            if (id_unit == null)
                return Response.requiredParameter("id_unit");

            JsonNode status = json.get("status");
            if (status == null)
                return Response.requiredParameter("status");

            JsonNode typeProvider = json.get("id_ProviderType");
            if (typeProvider == null)
                return Response.requiredParameter("id_ProviderType");;

            // mapping object-json
            ItemType itemType = Json.fromJson(json, ItemType.class);

            itemType.setName(Name.asText().toUpperCase());

            itemType.setUnit(unitDao.findById(id_unit.asLong()));
            itemType.setProviderType(providerTypeDao.findById(typeProvider.asLong()));

            itemType = itemTypeDao.create(itemType);
            return Response.createdEntity(Json.toJson(itemType));

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

            ItemType itemType =  Json.fromJson(json, ItemType.class);

            JsonNode Name = json.get("name");
            if (Name != null)
            {
                int registered = itemTypeDao.getExist(Name.asText().toUpperCase());
                if(registered==0) return  Response.messageExist("name");
                if(registered==1) return  Response.messageExistDeleted("name");

                itemType.setName(Name.asText().toUpperCase());
            }

            JsonNode id_unit = json.get("id_unit");
            if (id_unit != null)
                itemType.setUnit(unitDao.findById(id_unit.asLong()));

            JsonNode typeProvider = json.get("id_ProviderType");
            if (typeProvider != null)
                itemType.setProviderType(providerTypeDao.findById(typeProvider.asLong()));

            itemType = itemTypeDao.update(itemType);
            return Response.updatedEntity(Json.toJson(itemType));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @Override
    public Result delete(Long id) {
        try{
            ItemType itemType = itemTypeDao.findById(id);
            List<InvoiceDetail> invoiceDetails = invoiceDetailDao.getOpenByItemTypeId(id);
            if(itemType != null  && invoiceDetails.size()==0) {

                itemType.setStatusDelete(1);
                itemType = itemTypeDao.update(itemType);

                return Response.deletedEntity();
            } else {

                    if(itemType == null)  return  Response.message("Successful no existe el registro a eliminar");
                    else  return  Response.message("Successful el registro tiene facturas aun no cerradas");

            }
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }
    /*public Result delete(Long id) {
        try {

            ItemType ItemType = findById(id);
            //    ItemTypeDao.delete(id);
            return Response.deletedEntity();

        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }*/

    @Override
    public Result findById(Long id) {
        try {
            ItemType itemType = itemTypeDao.findById(id);
            return Response.foundEntity(Response.toJson(itemType, ItemTypeResponse.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @Override
    public Result findAll(Integer index, Integer size) {
        try {
            List<ItemType> itemTypes = itemTypeDao.findAll(index, size);
            return Response.foundEntity(Json.toJson(itemTypes));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

}
