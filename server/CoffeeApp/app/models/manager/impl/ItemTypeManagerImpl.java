package models.manager.impl;

import io.ebean.text.PathProperties;
import com.fasterxml.jackson.databind.JsonNode;
import models.dao.InvoiceDetailDao;
import models.dao.ItemTypeDao;
import models.dao.ProviderTypeDao;
import models.dao.impl.InvoiceDetailDaoImpl;
import models.dao.impl.ItemTypeDaoImpl;
import models.dao.UnitDao;
import models.dao.impl.ProviderTypeDaoImpl;
import models.dao.impl.UnitDaoImpl;
import models.dao.utils.ListPagerCollection;
import models.domain.Invoice;
import models.domain.ItemType;
import models.domain.InvoiceDetail;
import models.domain.Provider;
import models.manager.ItemTypeManager;
import models.manager.responseUtils.ExceptionsUtils;
import models.manager.responseUtils.PropertiesCollection;
import models.manager.responseUtils.Response;
import models.manager.responseUtils.ResponseCollection;
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
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public ItemTypeManagerImpl(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    @Override
    public Result create() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            JsonNode Name = json.get("nameItemType");
            if (Name == null)
                return Response.requiredParameter("nameItemType");

            int registered = itemTypeDao.getExist(Name.asText().toUpperCase());
            if(registered==0) return  Response.messageExist("nameItemType");
            if(registered==1) return  Response.messageExistDeleted("nameItemType");

            JsonNode cost = json.get("costItemType");
            if (cost == null)
                return Response.requiredParameter("costItemType");

            JsonNode id_unit = json.get("id_unit");
            if (id_unit == null)
                return Response.requiredParameter("id_unit");

            JsonNode status = json.get("statusItemType");
            if (status == null)
                return Response.requiredParameter("statusItemType");

            JsonNode typeProvider = json.get("id_ProviderType");
            if (typeProvider == null)
                return Response.requiredParameter("id_ProviderType");;

            // mapping object-json
            ItemType itemType = Json.fromJson(json, ItemType.class);

            itemType.setNameItemType(Name.asText().toUpperCase());

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

            JsonNode id = json.get("idItemType");
            if (id == null)
                return Response.requiredParameter("idItemType");

            ItemType itemType =  Json.fromJson(json, ItemType.class);

            JsonNode Name = json.get("nameItemType");
            if (Name != null)
            {
                int registered = itemTypeDao.getExist(Name.asText().toUpperCase());
                if(registered==0) return  Response.messageExist("nameItemType");
                if(registered==1) return  Response.messageExistDeleted("nameItemType");

                itemType.setNameItemType(Name.asText().toUpperCase());
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
            return Response.foundEntity(Response.toJson(itemType, ItemType.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

   /* @Override
    public Result findAll(Integer index, Integer size) {
        try {
            List<ItemType> itemTypes = itemTypeDao.findAll(index, size);
            return Response.foundEntity(Json.toJson(itemTypes));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }*/

    public Result getByProviderTypeId(Long id_ProviderType, Integer status)
    {
        try {

            if(providerTypeDao.findById(id_ProviderType)==null)
                return Response.message("No existe registro para el campo: [id_ProviderType]");

            List<ItemType> itemTypes = itemTypeDao.getByProviderTypeId(id_ProviderType,status);
                return Response.foundEntity(Json.toJson(itemTypes));

        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    public Result getByNameItemType(String NameItemType, String order)
    {
        String strOrder = "ASC";
        try {

            if (NameItemType.equals("-1")) NameItemType = "";

            if(!order.equals("-1")) strOrder = order;

            if(!strOrder.equals("ASC") && !strOrder.equals("DESC"))
                return Response.requiredParameter("order (ASC o DESC)");

            if(NameItemType.equals(""))
                return Response.message("Falta el atributo [name]");

            List<ItemType> itemTypes = itemTypeDao.getByNameItemType(NameItemType,strOrder);
            return Response.foundEntity(Json.toJson(itemTypes));

        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @Override
    public Result findAll(Integer index, Integer size, String sort, String collection) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = itemTypeDao.findAll(index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

    @Override
    public Result findAllSearch(String name, Integer index, Integer size, String sort, String collection) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = itemTypeDao.findAllSearch(name, index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


    @Override
    public Result preCreate() {


        try {
            ItemType itemtype = new ItemType();

            return Response.foundEntity(
                    Json.toJson(itemtype));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

}
