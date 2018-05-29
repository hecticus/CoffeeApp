package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.parsers.queryStringBindable.Pager;
import controllers.utils.ListPagerCollection;
import io.ebean.text.PathProperties;
import models.InvoiceDetail;
import models.ItemType;
import models.ProviderType;
import models.Unit;
import controllers.responseUtils.ExceptionsUtils;
import controllers.responseUtils.PropertiesCollection;
import controllers.responseUtils.Response;
import controllers.responseUtils.ResponseCollection;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import java.util.List;

/**
 * Created by sm21 on 10/05/18.
 */
public class ItemTypes extends Controller {

    private static ItemType itemTypeDao = new ItemType();
    private static Unit unitDao = new Unit();
    private static ProviderType providerTypeDao = new ProviderType();
    private static InvoiceDetail invoiceDetailDao = new InvoiceDetail();
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public ItemTypes(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }


//    @CoffeAppsecurity
    public Result preCreate() {
        try {
            ItemType itemtype = new ItemType();

            return Response.foundEntity(
                    Json.toJson(itemtype));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

//    @CoffeAppsecurity
    public Result create() {

        JsonNode json = request().body().asJson();
        if(json == null)
            return Response.requiredJson();


        try{
            JsonNode Name = json.get("nameItemType");
            if (Name == null || !ItemType.existName(Name.asText().toUpperCase()) )
                return badRequest("Required o there is ItemType with name");

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

            itemType.setUnit(Unit.findById(id_unit.asLong()));
            itemType.setProviderType(ProviderType.findById(typeProvider.asLong()));

            itemType.save();// = itemTypeDao.create(itemType);
            return Response.createdEntity(Json.toJson(itemType));

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }


//    @CoffeAppsecurity
    public Result update() {
        JsonNode json = request().body().asJson();
        if(json== null)
            return badRequest("Expecting Json data");

        JsonNode id = json.get("idItemType");
        if (id == null )
            return badRequest("Missing parameter idItemType");

        if(!ItemType.existId(id.asLong()))
            return badRequest("There is no ItemType with id");

        try {
            ItemType itemType =  ItemType.findById(id.asLong());

            JsonNode name = json.findValue("nameItemType");
            if (name != null & ItemType.existName(name.asText().toUpperCase())){
                itemType.setNameItemType(name.asText().toUpperCase());
            }

            JsonNode id_unit = json.get("id_unit");
            if (id_unit != null)
                itemType.setUnit(Unit.findById(id_unit.asLong()));

            JsonNode typeProvider = json.get("id_ProviderType");
            if (typeProvider != null)
                itemType.setProviderType(ProviderType.findById(typeProvider.asLong()));

            itemType.update();
            return Response.updatedEntity(Json.toJson(itemType));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

//    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            ItemType itemType = ItemType.findById(id);
            List<InvoiceDetail> invoiceDetails = InvoiceDetail.getOpenByItemTypeId(id);
            if(itemType != null  && invoiceDetails.size()==0) {

                itemType.setStatusDelete(1);
                itemType.update();// = itemTypeDao.update(itemType);

                return Response.deletedEntity();
            } else {

                if(itemType == null)  return  Response.message("Successful no existe el registro a eliminar");
                else  return  Response.message("Successful el registro tiene facturas aun no cerradas");

            }
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }


    //@CoffeAppsecurity
    public  Result findById(Long id) {
        try {
            ItemType itemType = ItemType.findById(id);
            return Response.foundEntity(Json.toJson((itemType)));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    //    @CoffeAppsecurity
    public Result findAll(String name, Integer index, Integer size, String sort, String collection, Long id_ProviderType, Integer status) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = itemTypeDao.findAll(name, index, size, sort, pathProperties, id_ProviderType, status);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

    public Result getByProviderTypeId(Long id_ProviderType, Integer status){
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


}
