package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.responseUtils.ResponseCollection;
import controllers.utils.ListPagerCollection;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.Response;
import io.ebean.Ebean;
import io.ebean.text.PathProperties;
import models.Invoice;
import models.Multimedia;
import models.Provider;
import models.ProviderType;

import models.status.StatusProvider;
import multimedia.MultimediaUtils;
import multimedia.models.MultimediaCDN;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;

import static sun.security.krb5.Confounder.longValue;

/**
 * Created by sm21 on 10/05/18.
 */
public class Providers extends Controller {

    @Inject
    private FormFactory formFactory;

    @CoffeAppsecurity
    public Result create() {
        try {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            Form<Provider> form = formFactory.form(Provider.class).bindFromRequest();
            if (form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

//            Provider provider = Json.fromJson(json, Provider.class);
            Provider provider= form.get();
            provider.setStatusProvider(StatusProvider.findById(new Long(41)));
            Provider aux = Provider.findByProvider(provider);
            if (aux != null ) {
                if (aux.isDeleted()){
                    provider.setId(aux.getId());
                    aux = provider;
                    aux.setDeleted(false);
                    aux.update();
                    provider = aux;
                } else if (Provider.findByName(provider.getNameProvider()) != null & provider.getProviderType().getId().intValue() == 1 ) {
                    return controllers.utils.Response.invalidParameter("There is  a provider active with name: " + provider.getNameProvider());
                }
            }

            provider.save();
            return  Response.createdEntity(Json.toJson(provider));
        }catch(Exception e){
            return NsExceptionsUtils.create(e);
        }
    }

    @CoffeAppsecurity
    public Result update(Long id) {
        try {
            JsonNode json = request().body().asJson();
            if(json== null)
                return badRequest("Expecting Json data");

            Form<Provider> form = formFactory.form(Provider.class).bind(json);
            if (form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            Provider provider = Json.fromJson(json, Provider.class);
            provider.setId(id);
            System.out.println(provider.getNameProvider());

            if(provider.getMultimediaProfile() != null){
                Multimedia multimedia =  Multimedia.findId(provider.getMultimediaProfile().getId());
                MultimediaCDN multimediaCDN = MultimediaCDN.findId(multimedia.getMultimediaCDN().getId());
                multimediaCDN.setDeleted(false);
                multimediaCDN.update();
                multimedia.setDeleted(false);
                multimedia.update();

            }

            provider.update();
            return  Response.updatedEntity(Json.toJson(provider));

        }catch(Exception e){
            return NsExceptionsUtils.update(e);
        }
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            if (!Invoice.invoicesByProviderId(id).isEmpty()){
                return controllers.utils.Response.constraintViolation("El Proveedor tiene facturas");
            }

            Provider provider = Provider.findById(id);
            Multimedia multimedia = provider.getMultimediaProfile();
            if ( multimedia != null){
                Ebean.delete(multimedia);
            }
            Ebean.delete(Provider.class, id);
            return Response.deletedEntity();
        } catch (Exception e) {
            return  NsExceptionsUtils.delete(e);
        }
    }

    @CoffeAppsecurity
    public Result deletes() {
        try {
            JsonNode json = request().body().asJson();
            if (json == null)
                return controllers.utils.Response.requiredJson();

            Ebean.deleteAll(Provider.class, controllers.utils.JsonUtils.toArrayLong(json, "ids"));
            return controllers.utils.Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
//    public Result findById(Long id, String collection) {
        try {
            return Response.foundEntity(Json.toJson(Provider.findById(id)));
//            return Response.foundEntity(Provider.findId(id, pathProperties), pathProperties);
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }

    @CoffeAppsecurity
    public Result findAll( Integer index, Integer size, String collection,
                           String sort, String name,  Long idProviderType,
                           String identificationDocProvider, String addressProvider,
                           String phoneNumberProvider, String emailProvider,
                           String contactNameProvider, Long status, boolean deleted){
        try {
            PathProperties pathProperties = null;
            if (collection != null)
                pathProperties = PathProperties.parse(collection);

            ListPagerCollection listPager = Provider.findAll( index, size,  pathProperties, sort, name,
                                                            idProviderType, identificationDocProvider, addressProvider,
                                                            phoneNumberProvider, emailProvider,  contactNameProvider,
                                                            status, deleted);

            return ResponseCollection.foundEntity(listPager, pathProperties);
//            return Response.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return  NsExceptionsUtils.find(e);
        }
    }

}