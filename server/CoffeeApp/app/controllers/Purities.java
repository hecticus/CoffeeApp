package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.ListPagerCollection;
import io.ebean.text.PathProperties;
import models.InvoiceDetail;
import models.Purity;
import models.responseUtils.ExceptionsUtils;
import models.responseUtils.PropertiesCollection;
import models.responseUtils.Response;
import models.responseUtils.ResponseCollection;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import java.util.List;


/**
 * Created by sm21 on 10/05/18.
 */
public class Purities extends Controller{

    private static Purity purityDao = new Purity();
    private static InvoiceDetail invoiceDetailDao = new InvoiceDetail();
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public Purities(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

//    @CoffeAppsecurity
    public Result create() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();


            JsonNode Name = json.get("namePurity");
            if (Name == null)
                return Response.requiredParameter("namePurity");

            int registered = purityDao.getExist(Name.asText().toUpperCase());
            if(registered==0) return  Response.messageExist("namePurity");
            if(registered==1) return  Response.messageExistDeleted("namePurity");


            JsonNode DiscountRate = json.get("discountRatePurity");
            if (DiscountRate == null)
                return Response.requiredParameter("discountRatePurity");

            JsonNode status = json.get("statusPurity");
            if (status == null)
                return Response.requiredParameter("statusPurity");




            // mapping object-json
            Purity purity = Json.fromJson(json, Purity.class);


            purity.save();
            return Response.createdEntity(Json.toJson(purity));

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

            JsonNode id = json.get("idPurity");
            if (id == null)
                return Response.requiredParameter("idPurity");

            Purity purity =  Json.fromJson(json, Purity.class);

            JsonNode Name = json.get("namePurity");
            if (Name != null)
            {
                int registered = purityDao.getExist(Name.asText().toUpperCase());
                if(registered==0) return  Response.messageExist("namePurity");
                if(registered==1) return  Response.messageExistDeleted("namePurity");

                purity.setNamePurity(Name.asText().toUpperCase());
            }


            purity.update();
            return Response.updatedEntity(Json.toJson(purity));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

//    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Purity purity = Purity.findById(id);
            List<InvoiceDetail> invoiceDetails = InvoiceDetail.getOpenByLotId(id);
            System.out.println(purity.getNamePurity());
            if(purity != null  && invoiceDetails.size()==0) {
                purity.setStatusDelete(1);
                purity.update();
                return Response.deletedEntity();
            } else {
                if(purity == null)  return  Response.message("Successful no existe el registro a eliminar");
                else  return  Response.message("Successful el registro tiene facturas aun no cerradas");
            }
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }

//    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            Purity purity = purityDao.findById(id);
            return Response.foundEntity(Response.toJson(purity, Purity.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    public Result getByNamePurity(String NamePurity, String order)
    {
        String strOrder = "ASC";
        try {

            if (NamePurity.equals("-1")) NamePurity = "";

            if(!order.equals("-1")) strOrder = order;

            if(!strOrder.equals("ASC") && !strOrder.equals("DESC"))
                return Response.requiredParameter("order (ASC o DESC)");

            if(NamePurity.equals(""))
                return Response.message("Falta el atributo [name]");

            List<Purity> purities = purityDao.getByNamePurity(NamePurity,strOrder);
            return Response.foundEntity(Json.toJson(purities));

        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    public Result getByStatusPurity(String StatusPurity, String order)
    {
        String strOrder = "ASC";
        try {

            if(!order.equals("-1")) strOrder = order;

            if(!strOrder.equals("ASC") && !strOrder.equals("DESC"))
                return Response.requiredParameter("order (ASC o DESC)");


            List<Purity> purities = purityDao. getByStatusPurity(StatusPurity,strOrder);
            return Response.foundEntity(Json.toJson(purities));

        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    //@CoffeAppsecurity
    public Result findAll(String name, Integer index, Integer size, String sort, String collection,  Integer status){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Purity.findAll(name, index, size, sort, pathProperties, status);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

    @CoffeAppsecurity
    public Result findAllSearch(String name, Integer index, Integer size, String sort, String collection) {
        try {

            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = purityDao.findAllSearch(name, index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


//    @CoffeAppsecurity
    public Result preCreate() {


        try {
            Purity purity = new Purity();
            return Response.foundEntity(
                    Json.toJson(purity));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

}