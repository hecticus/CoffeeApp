package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.ListPagerCollection;
import io.ebean.text.PathProperties;
import models.Farm;
import models.InvoiceDetail;
import models.Lot;
import controllers.requestUtils.Request;
import controllers.responseUtils.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sm21 on 10/05/18.
 */
public class Lots extends Controller {

    
    private static Lot lotDao = new Lot();
    private static Farm farmDao = new Farm();
    private static InvoiceDetail invoiceDetailDao = new InvoiceDetail();
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public Lots(){
        propertiesCollection.putPropertiesCollection("s", "(idLot, nameLot)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    //@CoffeAppsecurity
    public Result preCreate() {
        try {
            Farm farm = new Farm();
            Lot lot = new Lot();
            lot.setFarm(farm);

            return Response.foundEntity(
                    Json.toJson(lot));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

    //@CoffeAppsecurity
    public Result create() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();


            JsonNode Name = json.get("name");
            if (Name == null || Name.asText().equals("null") || Name.asText().equals(""))
                return Response.requiredParameter("name","nombre del lote");

            JsonNode farm = json.get("farm");
            if (farm == null || farm.asText().equals("null") || farm.asText().equals(""))
                return Response.requiredParameter("farm", "nombre de la finca");

            farm = Request.removeParameter(json, "farm");

            List<Integer> registered = lotDao.getExist(Name.asText().toUpperCase(), farm.asInt());
            if(registered.get(0)==0) return  Response.messageExist("name");
            //  if(registered==1) return  Response.messageExistDeleted("name");

            JsonNode area = json.get("areaLot");
            if (area == null || area.asText().equals("null") || area.asText().equals(""))
                return Response.requiredParameter("areaLot", "area");

            JsonNode heigh = json.get("heighLot");
            if (heigh == null || heigh.asText().equals("null") || heigh.asText().equals(""))
                return Response.requiredParameter("heighLot", "altura");

            JsonNode price_lot = json.get("price_lot");
            if (price_lot == null || price_lot.asText().equals("null") || price_lot.asText().equals(""))
                return Response.requiredParameter("price_lot", "US precio");

            JsonNode status = json.get("statusLot");
            if (status == null || status.asText().equals("null") || status.asText().equals(""))
                return Response.requiredParameter("statusLot");


            // mapping object-json
            Lot lot = Json.fromJson(json, Lot.class);

            lot.setFarm(farmDao.findById(farm.asLong()));

            lot.setNameLot(Name.asText().toUpperCase());

            if(registered.get(0)==1)
            {   lot.setStatusDelete(0);
                lot.setIdLot(registered.get(1).longValue());
                lot.update();// = lotDao.update(lot);
            }
            else lot.save();// = lotDao.create(lot);

            return Response.createdEntity(Json.toJson(lot));

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

//    @CoffeAppsecurity
    public Result update() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            JsonNode id = json.get("idLot");
            if (id == null)
                return Response.requiredParameter("idLot");

            JsonNode farm = json.get("farm");
            if (farm != null)
                farm = Request.removeParameter(json, "farm");

            if (farm == null || farm.asText().equals("null") || farm.asText().equals(""))
                return Response.requiredParameter("farm", "nombre de la finca");

            JsonNode area = json.get("areaLot");
            if (area == null || area.asText().equals("null") || area.asText().equals(""))
                return Response.requiredParameter("areaLot", "area");

            JsonNode heigh = json.get("heighLot");
            if (heigh == null || heigh.asText().equals("null") || heigh.asText().equals(""))
                return Response.requiredParameter("heighLot", "altura");


            JsonNode price_lot = json.get("price_lot");
            if (price_lot == null || price_lot.asText().equals("null") || price_lot.asText().equals(""))
                return Response.requiredParameter("price_lot", "US precio");

            Lot lot =  Json.fromJson(json, Lot.class);
            Lot lot_up = Lot.findById(id.asLong());

            JsonNode Name = json.get("name");
            if (Name == null || Name.asText().equals("null") || Name.asText().equals(""))
                return Response.requiredParameter("name","nombre del lote");

            JsonNode nameChange = json.get("nameChange");
            if (Name != null && (!nameChange.asText().equals(Name.asText()) || !farm.asText().equals(lot_up.getFarm().getIdFarm().toString())))
            {
                List<Integer> registered = Lot.getExist(Name.asText().toUpperCase(),farm.asInt());
                if(registered.get(0)==0) return  Response.messageExist("name");
                if(registered.get(0)==1) return  Response.messageExistDeleted("name");

                lot.setNameLot(Name.asText().toUpperCase());
            }


            lot.setFarm(Farm.findById(farm.asLong()));

            lot.update();// = lotDao.update(lot);
            return Response.updatedEntity(Json.toJson(lot));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

//    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Lot lot = Lot.findById(id);

            List<InvoiceDetail> invoiceDetails = InvoiceDetail.getOpenByLotId(id);
            if(lot != null  && invoiceDetails.size()==0) {

                lot.setStatusDelete(1);
                lot.update();// = lotDao.update(lot);

                return Response.deletedEntity();
            } else {

                if(lot == null)  return  Response.messageNotDeleted("no existe el registro a eliminar");
                else  return  Response.messageNotDeleted("el registro tiene facturas aun no cerradas");
            }
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }

//    @CoffeAppsecurity
    public Result deletes() {
        boolean aux_delete = true;
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            List<Long> aux = new ArrayList<Long>();
            aux = JsonUtils.toArrayLong(json, "ids");

            for (Long id : aux)
            {
                Lot lot = Lot.findById(id);

                List<InvoiceDetail> invoiceDetails = invoiceDetailDao.getOpenByLotId(id);
                if(lot != null  && invoiceDetails.size()==0) {

                    lot.setStatusDelete(1);
                    lot.update();// = Lot.update(lot);

                    return Response.deletedEntity();
                } else {
                    aux_delete = false;

                }

            }

            if(aux_delete)  return  Response.messageNotDeleted("La eliminacion fue correcta");
            else  return  Response.messageNotDeleted(" algunos registros tiene facturas aun no cerradas");
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

//    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            Lot lot = Lot.findById(id);
            return Response.foundEntity(Response.toJson(lot, Lot.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    //    @CoffeAppsecurity
    public Result findAll(String name, Integer pageindex, Integer pagesize, String sort, String collection, Integer all, Long idFarm, Integer statusLot) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = lotDao.findAll(name, pageindex, pagesize, sort, pathProperties, all, idFarm.intValue());//, statusLot);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

    public Result getByNameLot(String NameLot, String order)
    {
        String strOrder = "ASC";
        try {

            if (NameLot.equals("-1")) NameLot = "";

            if(!order.equals("-1")) strOrder = order;

            if(!strOrder.equals("ASC") && !strOrder.equals("DESC"))
                return Response.requiredParameter("order (ASC o DESC)");

            if(NameLot.equals(""))
                return Response.message("Falta el atributo [name]");

            List<Lot> lots = Lot.getByNameLot(NameLot,strOrder);
            return Response.foundEntity(Json.toJson(lots));

        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    public Result getByStatusLot(String StatusLot, String order)
    {
        String strOrder = "ASC";
        try {

            if(!order.equals("-1")) strOrder = order;

            if(!strOrder.equals("ASC") && !strOrder.equals("DESC"))
                return Response.requiredParameter("order (ASC o DESC)");


            List<Lot> lots = Lot.getByStatusLot(StatusLot,strOrder);
            return Response.foundEntity(Json.toJson(lots));

        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }


//    @CoffeAppsecurity
    public  Result getByIdFarm(Long idFarm, Integer index, Integer size, String sort, String collection)
    {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Lot.getByIdFarm(idFarm, index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

}
