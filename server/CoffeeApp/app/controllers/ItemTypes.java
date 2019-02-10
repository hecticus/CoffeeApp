package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.*;
import io.ebean.Ebean;
import io.ebean.PagedList;
import models.ItemType;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;

/**
 * Created by sm21 on 10/05/18.
 */
public class ItemTypes extends Controller {

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    @CoffeAppsecurity
    public Result create() {
        try{
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            Form<ItemType> form = formFactory.form(ItemType.class).bind(json);
            if (form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            ItemType itemType = Json.fromJson(json, ItemType.class);
            itemType.save();
            return  Response.createdEntity(Json.toJson(itemType));

        }catch(Exception e){
            return NsExceptionsUtils.create(e);
        }
    }

    @CoffeAppsecurity
    public Result update(Long id) {
        try {
            JsonNode json = request().body().asJson();
            if(json== null)
                return Response.requiredJson();

            Form<ItemType> form = formFactory.form(ItemType.class).bind(json);
            if (form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            ItemType itemType = Json.fromJson(json, ItemType.class);
            itemType.setId(id);
            itemType.update();
            return  Response.createdEntity(Json.toJson(itemType));
        }catch(Exception e){
            return NsExceptionsUtils.update(e);
        }
    }


    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Ebean.delete(ItemType.findById(id));
            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.update(e);
        }
    }

    @CoffeAppsecurity
    public Result deletes( ) {
        try {
            JsonNode json = request().body().asJson();
            if (json == null)
                return Response.requiredJson();

            Ebean.deleteAll(ItemType.class, JsonUtils.toArrayLong(json, "ids"));

            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @CoffeAppsecurity
    public  Result findById(Long id) {
        try {
            return Response.foundEntity(Json.toJson(ItemType.findById(id)));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @CoffeAppsecurity
    public Result findAll(Integer pageIndex, Integer pageSize, String collection,
                          String sort, String name, Long idProviderType, Long unit, boolean deleted ) {
        try {
            PagedList pagedList = ItemType.findAll( pageIndex, pageSize, propertiesCollection.getPathProperties(collection),
                    sort, name, idProviderType, unit, deleted);

            return Response.foundEntity(pagedList, propertiesCollection.getPathProperties(collection));
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }
}
