package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.*;
import io.ebean.Ebean;
import io.ebean.text.PathProperties;
import models.ProviderType;
import controllers.responseUtils.ExceptionsUtils;
import controllers.responseUtils.ResponseCollection;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;

import static play.mvc.Controller.request;

/**
 * Created by drocha on 12/05/17.
 */
public class ProviderTypes {

    @Inject
    private FormFactory formFactory;
    private static controllers.responseUtils.PropertiesCollection propertiesCollection = new controllers.responseUtils.PropertiesCollection();

    public ProviderTypes(){
        propertiesCollection.putPropertiesCollection("s", "(idProviderType, nameProviderType)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    @CoffeAppsecurity
    public Result create() {
        try{
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            Form<ProviderType> form = formFactory.form(ProviderType.class).bind(json);
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());

            ProviderType providerType = form.get();
            ProviderType aux = ProviderType.findByName(providerType.getNameProviderType());
            if (aux != null ) {
                if (aux.isDeleted()) {
                    providerType.setId(aux.getId());
                    aux = providerType;
                    aux.setDeleted(false);
                    aux.update();
                    providerType = aux;
                }else {
                    return controllers.utils.Response.invalidParameter(providerType.getNameProviderType());
                    }
            }else {
                providerType.save();
            }
            return Response.createdEntity(Json.toJson(providerType));
        }catch(Exception e){
            return NsExceptionsUtils.create(e);
        }
    }


    @CoffeAppsecurity
    public Result update(Long id) {
        try{
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            Form<ProviderType> form = formFactory.form(ProviderType.class).bind(json);
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());

            ProviderType providerType = Json.fromJson(json, ProviderType.class);
            providerType.setId(id);
            providerType.update();
            return Response.updatedEntity(Json.toJson(providerType));
        }catch(Exception e){
            return NsExceptionsUtils.update(e);
        }
    }

//
    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Ebean.delete(ProviderType.findById(id));
            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @CoffeAppsecurity
    public Result deletes() {
        try {
            Ebean.delete(ProviderType.finder.query().findList());
            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            ProviderType providerType = ProviderType.findById(id);
            return Response.foundEntity(Json.toJson(providerType));
        }catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }

    @CoffeAppsecurity
    public Result findAll( Integer index, Integer size, String collection,
                           String sort, String name, boolean deleted){
        try {

            PathProperties pathProperties = null;
            if (collection != null)
                pathProperties = PathProperties.parse(collection);

            ListPagerCollection listPager = ProviderType.findAll(index, size, pathProperties, sort, name, deleted);
            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

}
