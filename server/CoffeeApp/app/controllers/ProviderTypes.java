package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import controllers.parsers.queryStringBindable.Pager;
import controllers.utils.*;
import io.ebean.Ebean;
import io.ebean.text.PathProperties;
import models.Farm;
import models.ProviderType;
import models.responseUtils.ExceptionsUtils;
import models.responseUtils.ResponseCollection;
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
    private static models.responseUtils.PropertiesCollection propertiesCollection = new models.responseUtils.PropertiesCollection();

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
                return Response.invalidParameter(form.errorsAsJson());//.invalidParameter(form.errorsAsJson());

            ProviderType providerType = form.get(); //Json.fromJson(json, ProviderType.class);
//            providerType.insert();
            providerType.save();

            return Response.createdEntity(Json.toJson(providerType));

        }catch(Exception e){
            return NsExceptionsUtils.create(e);// Response.responseExceptionCreated(e);
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
                return Response.invalidParameter(form.errorsAsJson());//.invalidParameter(form.errorsAsJson());

            ProviderType providerType = Json.fromJson(json, ProviderType.class);
            providerType.setIdProviderType(id);
            providerType.update();

            return Response.updatedEntity(Json.toJson(providerType));

        }catch(Exception e){
            return NsExceptionsUtils.update(e);// Response.responseExceptionCreated(e);
        }
    }


    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Ebean.delete(ProviderType.class, id);
            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @CoffeAppsecurity
    public Result deletes() {
        try {
            JsonNode json = request().body().asJson();
            if (json == null)
                return Response.requiredJson();

            Ebean.delete(ProviderType.class, JsonUtils.toArrayLong(json, "ids"));

            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    //@CoffeAppsecurity
    public Result findById(Long id) {
        try {
            ProviderType providerType = ProviderType.findById(id);
            System.out.println(providerType.getNameProviderType());
            return Response.foundEntity(Json.toJson(providerType));
        }catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }

    @CoffeAppsecurity
    public Result findAll(String name, Pager pager, String sort, String collection){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Farm.findAll(name, pager, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size) {
        Pager pager = new Pager();
        pager.size = size;
        pager.index = index;
        return findAll(null, pager, null, null);
    }

    @CoffeAppsecurity
    public Result  getProviderTypesByName( String name, String order){
        String strOrder = "ASC";
        try {

            if (name.equals("-1")) name = "";

            if(!order.equals("-1")) strOrder = order;

            if(!strOrder.equals("ASC") && !strOrder.equals("DESC"))
                return Response.requiredParameter("order (ASC o DESC)");

            ProviderType providerTypes = ProviderType.findByName(name);

            return Response.foundEntity(Json.toJson(providerTypes));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }
}
