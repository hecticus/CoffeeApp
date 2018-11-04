package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.utils.JsonUtils;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.Response;
import io.ebean.Ebean;
import models.Multimedia;
import models.Provider;
import multimedia.RackspaceCloudFiles;
import multimedia.models.MultimediaCDN;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.HSecurity;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class Multimedias extends Controller {

    public static final String DTYPE_PROVIDER_PROFILE = "providerProfile";
    public static final String DTYPE_USER_PROFILE = "userProfile";

    @Inject
    public RackspaceCloudFiles rackspaceCloudFiles;
    @Inject
    private FormFactory formFactory;

    @HSecurity
    public Result createProviderProfile() {
        try {
            JsonNode request = request().body().asJson();
            if(request == null)
                return Response.requiredJson();

            JsonNode multimediaCDN = request.findValue("multimediaCDN");
            Form<MultimediaCDN> formCDN = formFactory.form(MultimediaCDN.class).bind(multimediaCDN);
            if (formCDN.hasErrors())
                return Response.invalidParameter(formCDN.errorsAsJson());

            MultimediaCDN multimediaCDN1 = formCDN.get();

            JsonNode name = request.findValue("name");
            multimediaCDN1.setPath(DTYPE_PROVIDER_PROFILE.concat("/").concat(name.asText()));

            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(request.toString());
            node.set("multimediaCDN",  Json.toJson(multimediaCDN1));

            Form<Multimedia> form = formFactory.form(Multimedia.class).bindFromRequest();
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());

            Multimedia multimedia = form.get();
            multimedia.setDtype(DTYPE_PROVIDER_PROFILE);
            multimedia.save();

            return Response.createdEntity(Json.toJson(multimedia));
        }catch(Exception e){
            return NsExceptionsUtils.create(e);
        }
    }

    @HSecurity
    public Result createProviderProfile(Long id) {
        try {

            Provider provider = Provider.findById(id);

            JsonNode request = request().body().asJson();
            if(request == null)
                return Response.requiredJson();

            JsonNode multimediaCDN = request.findValue("multimediaCDN");
            Form<MultimediaCDN> formCDN = formFactory.form(MultimediaCDN.class).bind(multimediaCDN);
            if (formCDN.hasErrors())
                return Response.invalidParameter(formCDN.errorsAsJson());

            MultimediaCDN multimediaCDN1 = formCDN.get();

            JsonNode name = request.findValue("name");
            multimediaCDN1.setPath(DTYPE_PROVIDER_PROFILE.concat("/").concat(name.asText()));

            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(request.toString());
            node.set("multimediaCDN",  Json.toJson(multimediaCDN1));

            Form<Multimedia> form = formFactory.form(Multimedia.class).bindFromRequest();
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());

            Multimedia multimedia = form.get();
            multimedia.setDtype(DTYPE_PROVIDER_PROFILE);
            multimedia.save();

            provider.setMultimediaProfile(multimedia);
            provider.update();

            return Response.createdEntity(Json.toJson(multimedia));
        }catch(Exception e){
            return NsExceptionsUtils.create(e);
        }
    }


    @HSecurity
    public Result update(Long id) {
        try {
            Multimedia multimedia = Multimedia.findById(id);
            JsonNode request = request().body().asJson();
            if(request == null)
                return Response.requiredJson();

            JsonNode multimediaCDN = request.findValue("multimediaCDN");
            Form<MultimediaCDN> formCDN = formFactory.form(MultimediaCDN.class).bind(multimediaCDN);
            if (formCDN.hasErrors())
                return Response.invalidParameter(formCDN.errorsAsJson());

            MultimediaCDN multimediaCDN1 = formCDN.get();
            multimediaCDN1.setId(multimedia.getMultimediaCDN().getId());
            multimediaCDN1.update();

            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(request.toString());
            node.set("multimediaCDN",  Json.toJson(multimediaCDN1));
//            node.putPOJO("provider", provider);

            Form<Multimedia> form = formFactory.form(Multimedia.class).bindFromRequest();
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());
//
            Multimedia multimediaUp = form.get();
            multimediaUp.setId(id);
            multimediaUp.update();

            return Response.createdEntity(Json.toJson(multimedia));
        }catch(Exception e){
            return NsExceptionsUtils.create(e);
        }
    }


    @HSecurity
    public Result delete(Long id) {
        Ebean.beginTransaction();
        try {
            Ebean.delete(Multimedia.class, id);

            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }finally {
            Ebean.endTransaction();
        }
    }


    @HSecurity
    public Result deletes() {
        try {
            JsonNode request = request().body().asJson();
            if(request == null)
                return Response.requiredJson();

            Ebean.deleteAll(Multimedia.class,Multimedia.findByIds(JsonUtils.toArrayLong(request, "ids")));

            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }


    @HSecurity
    public Result findById(Long id) {
        try {
            Multimedia  multimedia = Multimedia.findById(id);

            return Response.foundEntity(Json.toJson(multimedia));
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }

    @HSecurity
    public Result findAll() {
        try {
            List<Multimedia> multimedias = Multimedia.findAll();

            return Response.foundEntity(Json.toJson(multimedias));
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }

    @HSecurity
    public Result deleteUselessObjectsToContainer(){
        try {
            List<String> objectsNamesCDN = rackspaceCloudFiles.getObjectNames();
            List<MultimediaCDN> MultimediaCDNs = MultimediaCDN.findAll();
            List<String> objectsNames = new ArrayList<>();

            for (MultimediaCDN multimediaCDN : MultimediaCDNs) {
                if(multimediaCDN.getNameCdn() != null) {
                    objectsNames.add(multimediaCDN.getNameCdn());
                }
            }

            //System.out.println(objectsNames.size());
            //System.out.println(objectsNamesCDN.size());

            objectsNamesCDN.removeIf(x -> objectsNames.contains(x));

            rackspaceCloudFiles.deleteObjectsToContainer(objectsNamesCDN);

            return Response.deletedEntity();
        }catch(Exception e){
            return NsExceptionsUtils.delete(e);
        }
    }
}


