package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.utils.ListPagerCollection;
import io.ebean.Ebean;
import io.ebean.text.PathProperties;
import models.Invoice;
import models.Provider;
import models.ProviderType;
import controllers.responseUtils.*;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sm21 on 10/05/18.
 */
public class Providers extends Controller {

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public Providers(){
        propertiesCollection.putPropertiesCollection("s", "(idProvider, fullNameProvider)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    ////@CoffeAppsecurity
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

    ////@CoffeAppsecurity
    public Result create() {
        try {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            //providerType
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(json.toString());
            ObjectNode providerTypeNode = Json.newObject();
            providerTypeNode.set("idProviderType", json.findValue("id_ProviderType"));
            node.set("providerType", providerTypeNode);


            Form<Provider> form = formFactory.form(Provider.class).bind(node);
            if (form.hasErrors()){
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());
            }
            Provider provider = Json.fromJson(node, Provider.class);
            provider.save();
            return  Response.createdEntity(Json.toJson(provider));
        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

////@CoffeAppsecurity
    public Result update() {
        try {
            JsonNode json = request().body().asJson();
            if(json== null)
                return badRequest("Expecting Json data");

            JsonNode id = json.get("idProvider");
            if (id == null )
                return badRequest("Missing parameter idProvider");

            //providerType
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(json.toString());
            ObjectNode providerTypeNode = Json.newObject();
            providerTypeNode.set("idProviderType", json.findValue("id_ProviderType"));
            node.set("providerType", providerTypeNode);


            Provider provider = Json.fromJson(node, Provider.class);
            provider.update();
            return  Response.updatedEntity(Json.toJson(provider));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    ////@CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Ebean.delete(Provider.findById(id));
            return Response.deletedEntity();
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }


    ////@CoffeAppsecurity
    public Result deletes(){
        try {
            Ebean.deleteAll(Provider.finder.query().findList());
            return Response.deletedEntity();
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }


    ////@CoffeAppsecurity
    public Result  uploadPhotoProvider(){
        try  {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            JsonNode idprovider = json.get("idProvider");
            Long idProvider;
            if (idprovider == null) {
                JsonNode identificationDocProvider = json.get("identificationDocProvider");
                if(identificationDocProvider == null){
                    return Response.requiredParameter("identificationDocProvider or idProvider");
                }else{
                    Provider testp = Provider.getByIdentificationDoc(identificationDocProvider.asText());
                    if(testp != null){
                        idProvider = testp.getIdProvider();
                    }else{
                        return Response.requiredParameter("identificationDocProvider invalid");
                    }
                }
            }else{
                idProvider = idprovider.asLong();
            }

            JsonNode base64Photo_json = json.get("photoProvider");
            if (base64Photo_json == null)
                return Response.requiredParameter("photoProvider");

            String base64Photo = base64Photo_json.asText();

            String url;
            if(base64Photo.contains("data:image/jpeg;base64,"))
            {
                base64Photo = base64Photo.replace("data:image/jpeg;base64,", "");
                url = Provider.uploadPhoto(base64Photo,"jpg");
            }
            else {
                base64Photo = base64Photo.replace("data:image/png;base64,", "");
                url = Provider.uploadPhoto(base64Photo,"png");
            }

            Provider provider = Provider.findById(idProvider);

            provider.setPhotoProvider(url);

            provider.update();

            ObjectNode response = Json.newObject();
            response.put("urlPhoto", url);
            return Response.updatedEntity(response);

        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }


    ////@CoffeAppsecurity
    public Result findById(Long id) {
        try {
            Provider provider = Provider.findById(id);
            return Response.foundEntity(Json.toJson(provider));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    ////@CoffeAppsecurity
    public Result findAll( Integer index, Integer size, String collection,
                           String sort, String name,  Long idProviderType,
                           String identificationDocProvider, String addressProvider,
                           String phoneNumberProvider, String emailProvider,
                           String contactNameProvider, Integer status, boolean deleted){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Provider.findAll( index, size,  pathProperties, sort, name,
                                                            idProviderType, identificationDocProvider, addressProvider,
                                                            phoneNumberProvider, emailProvider,  contactNameProvider,
                                                            status, deleted);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

}