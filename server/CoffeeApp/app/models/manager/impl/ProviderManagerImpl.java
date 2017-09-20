package models.manager.impl;

/**
 * Created by drocha on 25/04/17.
 */



import com.avaje.ebean.text.PathProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.dao.InvoiceDao;
import models.dao.impl.InvoiceDaoImpl;
import models.dao.utils.ListPagerCollection;
import models.manager.responseUtils.*;
import play.libs.Json;
import play.mvc.Result;
import models.manager.ProviderManager;
import models.dao.ProviderDao;
import models.dao.impl.ProviderDaoImpl;
import models.dao.ProviderTypeDao;
import models.dao.impl.ProviderTypeDaoImpl;
import models.domain.Provider;
import models.domain.Invoice;
import static play.mvc.Controller.request;
import models.manager.responseUtils.responseObject.ProviderResponse;
import models.manager.responseUtils.responseObject.providerExtendResponse;

import java.util.ArrayList;
import java.util.List;
import models.domain.ProviderType;

public class ProviderManagerImpl implements ProviderManager
{


    private static ProviderDao providerDao = new ProviderDaoImpl();
    private static ProviderTypeDao providerTypeDao = new ProviderTypeDaoImpl();
    private static InvoiceDao invoiceDao = new InvoiceDaoImpl();
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public ProviderManagerImpl(){

        propertiesCollection.putPropertiesCollection("s", "(idProvider, fullName_Provider)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    @Override
    public Result create() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            JsonNode identificationDoc = json.get("identificationDocProvider");
            if (identificationDoc == null || identificationDoc.asText().equals("null") || identificationDoc.asText().equals(""))
                return Response.requiredParameter("identificationDocProvider","numero de identificacion");

            List<Integer> registered =  providerDao.getExist(identificationDoc.asText().toUpperCase());
            if(registered.get(0)==0)  return  Response.messageExist("identificationDocProvider");
          //  if(registered==1) return  Response.messageExistDeleted("identificationDocProvider");

            JsonNode fullName = json.get("fullNameProvider");
            if (fullName == null || fullName.asText().equals("null") || fullName.asText().equals(""))
                return Response.requiredParameter("fullNameProvider", "nombre de proveedor");

            JsonNode typeProvider = json.get("id_ProviderType");
            if (typeProvider == null || typeProvider.asText().equals("null") || typeProvider.asText().equals(""))
            return Response.requiredParameter("id_ProviderType", "tipo de proveedor");

            JsonNode phoneNumber = json.get("phoneNumberProvider");
            if (phoneNumber == null || phoneNumber.asText().equals("null") || phoneNumber.asText().equals(""))
            return Response.requiredParameter("phoneNumberProvider","numero de telefono");

            JsonNode address = json.get("addressProvider");
            if (address == null || address.asText().equals("null") || address.asText().equals(""))
                return Response.requiredParameter("addressProvider","direccion");

            JsonNode contactName = json.get("contactNameProvider");
            if (contactName == null || contactName.asText().equals("null") || contactName.asText().equals(""))
                return Response.requiredParameter("contactNameProvider","contacto");


            // mapping object-json
            Provider provider = Json.fromJson(json, Provider.class);

            provider.setProviderType(providerTypeDao.findById(typeProvider.asLong()));

            if(registered.get(0)==1)
            {   provider.setStatusDelete(0);
                provider.setIdProvider(registered.get(1).longValue());
                provider = providerDao.update(provider);
            }
            else provider = providerDao.create(provider);
            return Response.createdEntity(Json.toJson(provider));

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

            JsonNode id = json.get("idProvider");
            if (id == null)
                return Response.requiredParameter("idProvider");

            Provider provider =  Json.fromJson(json, Provider.class);

            JsonNode identificationDoc = json.get("identificationDocProvider");
            JsonNode identificationDocChange = json.get("identificationDocProviderChange");

            if (identificationDoc == null || identificationDoc.asText().equals("null") || identificationDoc.asText().equals(""))
                return Response.requiredParameter("identificationDocProvider","numero de identificacion");

            if (!identificationDoc.asText().equals(identificationDocChange.asText()))
            {
                List<Integer> registered =  providerDao.getExist(identificationDoc.asText().toUpperCase());
                if(registered.get(0)==0)  return  Response.messageExist("identificationDocProvider");
             //   if(registered==1) return  Response.messageExistDeleted("identificationDocProvider");

                provider.setIdentificationDocProvider(identificationDoc.asText().toUpperCase());
            }

            JsonNode fullName = json.get("fullNameProvider");
            if (fullName == null || fullName.asText().equals("null") || fullName.asText().equals(""))
                return Response.requiredParameter("fullNameProvider", "nombre de proveedor");

            JsonNode typeProvider = json.get("id_ProviderType");
            if (typeProvider == null || typeProvider.asText().equals("null") || typeProvider.asText().equals(""))
                return Response.requiredParameter("id_ProviderType", "tipo de proveedor");

            if (typeProvider != null)
                provider.setProviderType(providerTypeDao.findById(typeProvider.asLong()));

            JsonNode phoneNumber = json.get("phoneNumberProvider");
            if (phoneNumber == null || phoneNumber.asText().equals("null") || phoneNumber.asText().equals(""))
                return Response.requiredParameter("phoneNumberProvider","numero de telefono");

            JsonNode address = json.get("addressProvider");
            if (address == null || address.asText().equals("null") || address.asText().equals(""))
                return Response.requiredParameter("addressProvider","direccion");

            JsonNode contactName = json.get("contactNameProvider");
            if (contactName == null || contactName.asText().equals("null") || contactName.asText().equals(""))
                return Response.requiredParameter("contactNameProvider","contacto");

            provider = providerDao.update(provider);
            return Response.updatedEntity(Json.toJson(provider));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @Override
    public Result delete(Long id) {
        try{
            Provider provider = providerDao.findById(id);
            List<Invoice> invoices = invoiceDao.getOpenByProviderId(id);
            if(provider != null && invoices.size()==0) {

                provider.setStatusDelete(1);

                provider = providerDao.update(provider);

                return Response.deletedEntity();
            } else {
                if(provider == null)  return  Response.message("Successful no existe el registro a eliminar");
                else  return  Response.message("Successful el proveedor tiene facturas aun no cerradas");
            }
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }
    /*public Result delete(Long id) {
        try {

            Provider provider = findById(id);
            //    providerDao.delete(id);
            return Response.deletedEntity();

        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }*/

    @Override
    public Result findById(Long id) {
        try {
            Provider provider = providerDao.findById(id);
            return Response.foundEntity(Json.toJson(provider));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

  /*  @Override
    public Result findAll(Integer index, Integer size) {
        try {
            List<Provider> providers = providerDao.findAll(index, size);
            return Response.foundEntity(Json.toJson(providers));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }
*/
    @Override
    public Result  getByIdentificationDoc(String IdentificationDoc)
    {
        try {
            Provider provider = providerDao.getByIdentificationDoc(IdentificationDoc);
            return Response.foundEntity(Json.toJson(provider));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    public Result  getProvidersByName(String name, String order)
    {


        String strOrder = "ASC";
        try {

            if (name.equals("-1")) name = "";

            if(!order.equals("-1")) strOrder = order;

            if(!strOrder.equals("ASC") && !strOrder.equals("DESC"))
                return Response.requiredParameter("order (ASC o DESC)");
            List<Provider> providers = providerDao.getProvidersByName(name,strOrder);
            return Response.foundEntity(Json.toJson(providers));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    public Result  getByTypeProvider(Long id_providertype, String order)
    {
              String strOrder = "ASC";
            try {

                  if(!order.equals("-1")) strOrder = order;

                if(!strOrder.equals("ASC") && !strOrder.equals("DESC"))
                    return Response.requiredParameter("order (ASC o DESC)");


            List<Provider> providers = providerDao.getByTypeProvider(id_providertype,strOrder);
            return Response.foundEntity(Json.toJson(providers));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    public Result getByNameDocByTypeProvider(String nameDoc, Long id_providertype, String order)
    {
        List<Provider> providers;
        String strOrder = "ASC";
        try
        {


            if (providerTypeDao.findById(id_providertype)==null) return Response.notFoundEntity("providerType");

            if(!order.equals("-1")) strOrder = order;

            if(!strOrder.equals("ASC") && !strOrder.equals("DESC")) return Response.requiredParameter("order (ASC o DESC)");


            if(!nameDoc.equals("-1"))  providers = providerDao.getByNameDocByTypeProvider(nameDoc,id_providertype,strOrder);
            else   providers = providerDao.getByTypeProvider(id_providertype,strOrder);


                return Response.foundEntity(Json.toJson(providers));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @Override
    public Result findAll(Integer index, Integer size, String sort, String collection) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = providerDao.findAll(index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

    @Override
    public Result findAllSearch(String name, Integer index, Integer size, String sort, String collection) {
        try {

            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = providerDao.findAllSearch(name, index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


    @Override
    public Result preCreate() {


        try {
            ProviderType providerType = new ProviderType();
            Provider provider = new Provider();
             provider.setProviderType(providerType);
            return Response.foundEntity(
                    Json.toJson(provider));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

    @Override
    public Result deletes() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            List<Long> aux = new ArrayList<Long>();
            aux = JsonUtils.toArrayLong(json, "ids");

            for (Long id : aux)
            {
                this.delete(id);

            }

            return Response.message("Successful deletes");
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

    @Override
    public Result  uploadPhotoProvider()
    {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            JsonNode id = json.get("idProvider");
            if (id == null)
                return Response.requiredParameter("idProvider");

            JsonNode base64Photo_json = json.get("photoProvider");
            if (base64Photo_json == null)
                return Response.requiredParameter("photoProvider");

            String base64Photo = base64Photo_json.asText();

            String url;
            if(base64Photo.contains("data:image/jpeg;base64,"))
            {
                base64Photo = base64Photo.replace("data:image/jpeg;base64,", "");
                url = providerDao.uploadPhoto(base64Photo,"jpg");
            }
            else {
                base64Photo = base64Photo.replace("data:image/png;base64,", "");
                url = providerDao.uploadPhoto(base64Photo,"png");
            }

            Provider provider = providerDao.findById(id.asLong());

            provider.setPhotoProvider(url);

            provider = providerDao.update(provider);

            ObjectNode response = Json.newObject();
            response.put("urlPhoto", url);
            return Response.updatedEntity(response);

        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

}
