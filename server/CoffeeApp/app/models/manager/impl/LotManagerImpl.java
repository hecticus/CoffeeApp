package models.manager.impl;

import com.avaje.ebean.text.PathProperties;
import com.fasterxml.jackson.databind.JsonNode;
import models.dao.FarmDao;
import models.dao.InvoiceDetailDao;
import models.dao.LotDao;
import models.dao.impl.FarmDaoImpl;
import models.dao.impl.InvoiceDetailDaoImpl;
import models.dao.impl.LotDaoImpl;
import models.dao.utils.ListPager;
import models.dao.utils.ListPagerCollection;
import models.domain.InvoiceDetail;
import models.domain.Lot;
import models.domain.Farm;
import models.manager.LotManager;
import models.manager.responseUtils.ExceptionsUtils;
import models.manager.responseUtils.ResponseCollection;
import models.manager.responseUtils.Response;
import models.manager.requestUtils.Request;
import models.manager.responseUtils.PropertiesCollection;
import play.libs.Json;
import play.mvc.Result;

import java.util.List;

import static play.mvc.Controller.request;

/**
 * Created by drocha on 26/04/17.
 */
public class LotManagerImpl implements LotManager {




    private static LotDao lotDao = new LotDaoImpl();
    private static FarmDao farmDao = new FarmDaoImpl();
    private static InvoiceDetailDao invoiceDetailDao = new InvoiceDetailDaoImpl();
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public LotManagerImpl(){
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


            JsonNode area = json.get("areaLot");
            if (area == null)
                return Response.requiredParameter("areaLot");

            JsonNode Name = json.get("name");
            if (Name == null)
                return Response.requiredParameter("name");

            int registered = lotDao.getExist(Name.asText().toUpperCase());
            if(registered==0) return  Response.messageExist("name");
            if(registered==1) return  Response.messageExistDeleted("name");

            JsonNode farm = json.get("farm");
            if (farm == null)
                return Response.requiredParameter("farm");

            farm = Request.removeParameter(json, "farm");


            JsonNode price_lot = json.get("price_lot");
            if (price_lot == null)
                return Response.requiredParameter("price_lot");

            JsonNode status = json.get("status");
            if (status == null)
                return Response.requiredParameter("status");


            // mapping object-json
            Lot lot = Json.fromJson(json, Lot.class);

            lot.setFarm(farmDao.findById(farm.asLong()));

            lot.setNameLot(Name.asText().toUpperCase());
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

            JsonNode id = json.get("idLot");
            if (id == null)
                return Response.requiredParameter("idLot");

            JsonNode farm = json.get("farm");
            if (farm != null)
                     farm = Request.removeParameter(json, "farm");

            JsonNode price_lot = json.get("price_lot");
            if (price_lot == null)
                return Response.requiredParameter("price_lot");

            Lot lot =  Json.fromJson(json, Lot.class);

            JsonNode Name = json.get("name");
            JsonNode nameChange = json.get("nameChange");
            if (Name != null && !nameChange.asText().equals(Name.asText()))
            {
                int registered = lotDao.getExist(Name.asText().toUpperCase());
                if(registered==0) return  Response.messageExist("name");
                if(registered==1) return  Response.messageExistDeleted("name");

                lot.setNameLot(Name.asText().toUpperCase());
            }

            if(farm != null) lot.setFarm(farmDao.findById(farm.asLong()));

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

            List<InvoiceDetail> invoiceDetails = invoiceDetailDao.getOpenByLotId(id);
            if(lot != null  && invoiceDetails.size()==0) {

                lot.setStatusDelete(1);
                lot = lotDao.update(lot);

                return Response.deletedEntity();
            } else {

                if(lot == null)  return  Response.message("Successful no existe el registro a eliminar");
                else  return  Response.message("Successful el registro tiene facturas aun no cerradas");
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
            return Response.foundEntity(Response.toJson(lot, Lot.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

   /* @Override
    public Result findAll(Integer index, Integer size) {
        try {
            List<Lot> lots = lotDao.findAll(index, size);
            return Response.foundEntity(Json.toJson(lots));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }*/

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

            List<Lot> lots = lotDao.getByNameLot(NameLot,strOrder);
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


            List<Lot> lots = lotDao.getByStatusLot(StatusLot,strOrder);
            return Response.foundEntity(Json.toJson(lots));

        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @Override
    public Result findAll(Integer index, Integer size, String sort, String collection) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = lotDao.findAll(index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

    @Override
    public Result findAllSearch(String name, Integer index, Integer size, String sort, String collection) {
        try {
                        PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = lotDao.findAllSearch(name, index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


    @Override
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

}