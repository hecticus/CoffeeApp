package models.manager.impl;

import com.avaje.ebean.text.PathProperties;
import com.fasterxml.jackson.databind.JsonNode;
import models.dao.InvoiceDetailDao;
import models.dao.PurityDao;
import models.dao.impl.InvoiceDetailDaoImpl;
import models.dao.impl.PurityDaoImpl;
import models.dao.utils.ListPagerCollection;
import models.domain.InvoiceDetail;
import models.domain.Provider;
import models.domain.ProviderType;
import models.domain.Purity;
import models.manager.PurityManager;
import models.manager.responseUtils.ExceptionsUtils;
import models.manager.responseUtils.PropertiesCollection;
import models.manager.responseUtils.Response;
import models.manager.responseUtils.ResponseCollection;
import play.libs.Json;
import play.mvc.Result;

import java.util.List;

import static play.mvc.Controller.request;

/**
 * Created by drocha on 26/04/17.
 */
public class PurityManagerImpl    implements PurityManager {




    private static PurityDao purityDao = new PurityDaoImpl();
    private static InvoiceDetailDao invoiceDetailDao = new InvoiceDetailDaoImpl();
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public PurityManagerImpl(){

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
            List<InvoiceDetail> invoiceDetails = invoiceDetailDao.getOpenByLotId(id);
            if(purity != null  && invoiceDetails.size()==0) {

                purity.setStatusDelete(1);
                purity = purityDao.update(purity);

                return Response.deletedEntity();
            } else {
                if(purity == null)  return  Response.message("Successful no existe el registro a eliminar");
                else  return  Response.message("Successful el registro tiene facturas aun no cerradas");
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
            return Response.foundEntity(Response.toJson(purity, Purity.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

 /*   @Override
    public Result findAll(Integer index, Integer size) {
        try {
            List<Purity> puritys = purityDao.findAll(index, size);
            return Response.foundEntity(Json.toJson(puritys));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }*/

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

    @Override
    public Result findAll(Integer index, Integer size, String sort, String collection) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = purityDao.findAll(index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

    @Override
    public Result findAllSearch(String name, Integer index, Integer size, String sort, String collection) {
        try {

            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = purityDao.findAllSearch(name, index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


    @Override
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
