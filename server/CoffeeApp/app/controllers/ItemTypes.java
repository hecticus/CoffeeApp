package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.utils.ListPagerCollection;
import io.ebean.Ebean;
import io.ebean.text.PathProperties;
import models.ItemType;
import controllers.responseUtils.ExceptionsUtils;
import controllers.responseUtils.PropertiesCollection;
import controllers.responseUtils.Response;
import controllers.responseUtils.ResponseCollection;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;

/**
 * Created by sm21 on 10/05/18.
 */
public class ItemTypes extends Controller {

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public ItemTypes(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }


////@CoffeAppsecurity
    public Result preCreate() {
        try {
            ItemType itemtype = new ItemType();

            return Response.foundEntity(Json.toJson(itemtype));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

////@CoffeAppsecurity
    public Result create() {
        try{
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            //unit
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(json.toString());
            ObjectNode unitNode = Json.newObject();
            unitNode.set("idUnit", json.findValue("id_unit"));
            node.set("unit", unitNode);

            //providerType
            ObjectNode providerTypeNode = Json.newObject();
            providerTypeNode.set("idProviderType", json.findValue("id_ProviderType"));
            node.set("providerType", providerTypeNode);


            Form<ItemType> form = formFactory.form(ItemType.class).bind(node);
            if (form.hasErrors()){
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());
            }

            ItemType itemType = Json.fromJson(node, ItemType.class);
            itemType.save();
            return  Response.createdEntity(Json.toJson(itemType));

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }


////@CoffeAppsecurity
    public Result update() {
        try {
            JsonNode json = request().body().asJson();
            if(json== null)
                return Response.requiredJson();

            JsonNode id = json.get("idItemType");
            if (id == null )
                return Response.invalidParameter("Missing parameter idItemType");

            //unit
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(json.toString());
            ObjectNode unitNode = Json.newObject();
            unitNode.set("idUnit", json.findValue("id_unit"));
            node.set("unit", unitNode);

            //providerType
            ObjectNode providerTypeNode = Json.newObject();
            providerTypeNode.set("idProviderType", json.findValue("id_ProviderType"));
            node.set("providerType", providerTypeNode);


            Form<ItemType> form = formFactory.form(ItemType.class).bind(node);
            if (form.hasErrors()){
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());
            }

            ItemType itemType = Json.fromJson(node, ItemType.class);
            itemType.update();
            return  Response.createdEntity(Json.toJson(itemType));
        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }


////@CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Ebean.delete(ItemType.findById(id));
            return Response.deletedEntity();
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }


    //@CoffeAppsecurity
    public  Result findById(Long id) {
        try {
            ItemType itemType = ItemType.findById(id);
            return Response.foundEntity(Json.toJson((itemType)));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    ////@CoffeAppsecurity
    public Result findAll(Integer pageIndex, Integer pageSize, String collection,
                          String sort, String name, Long idProviderType, Integer status, boolean deleted ) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = ItemType.findAll( pageIndex, pageSize, pathProperties,
                    sort, name, idProviderType, status, deleted);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }
}
