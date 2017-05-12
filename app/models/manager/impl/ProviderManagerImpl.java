package models.manager.impl;

/**
 * Created by drocha on 25/04/17.
 */



import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Result;
import models.manager.ProviderManager;
import models.manager.responseUtils.Response;
import models.dao.ProviderDao;
import models.dao.impl.ProviderDaoImpl;
import models.dao.ProviderTypeDao;
import models.dao.impl.ProviderTypeDaoImpl;
import models.domain.Provider;
import static play.mvc.Controller.request;
import models.manager.responseUtils.responseObject.ProviderResponse;
import models.manager.responseUtils.responseObject.providerExtendResponse;
import java.util.List;

public class ProviderManagerImpl implements ProviderManager
{


    private static ProviderDao providerDao = new ProviderDaoImpl();
    private static ProviderTypeDao providerTypeDao = new ProviderTypeDaoImpl();

    @Override
    public Result create() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            JsonNode identificationDoc = json.get("identificationDoc");
            if (identificationDoc == null)
                return Response.requiredParameter("identificationDoc");

            JsonNode fullName = json.get("fullName");
            if (fullName == null)
                return Response.requiredParameter("fullName");

            JsonNode address = json.get("address");
            if (address == null)
                return Response.requiredParameter("address");

            JsonNode phoneNumber = json.get("phoneNumber");
            if (phoneNumber == null)
                return Response.requiredParameter("phoneNumber");

            JsonNode typeProvider = json.get("id_ProviderType");
            if (typeProvider == null)
                return Response.requiredParameter("id_ProviderType");

            JsonNode contactName = json.get("contactName");
            if (contactName == null)
                return Response.requiredParameter("contactName");


            // mapping object-json
            Provider provider = Json.fromJson(json, Provider.class);

            provider.setProviderType(providerTypeDao.findById(typeProvider.asLong()));

            provider = providerDao.create(provider);
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

            JsonNode id = json.get("id");
            if (id == null)
                return Response.requiredParameter("id");

            Provider provider =  Json.fromJson(json, Provider.class);

            JsonNode typeProvider = json.get("id_ProviderType");
            if (typeProvider != null)
                provider.setProviderType(providerTypeDao.findById(typeProvider.asLong()));

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
            if(provider != null) {

                provider.setStatusDelete(1);
                provider = providerDao.update(provider);

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

    @Override
    public Result findAll(Integer index, Integer size) {
        try {
            List<Provider> providers = providerDao.findAll(index, size);
            return Response.foundEntity(Json.toJson(providers));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }



}
