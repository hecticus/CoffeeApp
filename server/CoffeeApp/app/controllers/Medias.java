package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.JsonUtils;
import io.ebean.Ebean;
import models.Provider;
import oldmultimedia.models.Media;
import oldmultimedia.RackspaceCloudFiles;
import play.data.Form;
import play.data.FormFactory;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.Response;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nisa on 9/27/16.
 */
public class Medias extends Controller {

    public static final String DTYPE_PROVIDER_PROFILE = "providerProfile";
    public static final String DTYPE_USER_PROFILE = "userProfile";

    public static final String DTYPE_RESOLUTION_CLASSIC = "classic";
    public static final String DTYPE_RESOLUTION_SIGNATURE = "signature";
    public static final String DTYPE_RESOLUTION_CONTRACT = "contract";
    public static final String DTYPE_RESOLUTION_PROFILE = "profile";

    @Inject
    public RackspaceCloudFiles rackspaceCloudFiles;
    @Inject
    private FormFactory formFactory;

    @CoffeAppsecurity
    public Result createProviderProfile(Long id) {
        Ebean.beginTransaction();
        try {
            JsonNode request = request().body().asJson();
            if(request == null)
                return Response.requiredJson();
            if(!request.hasNonNull("media"))
                return Response.requiredParameter("media");

            Form<Media> form = formFactory.form(Media.class).bindFromRequest();
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());

            Provider provider = Provider.findById(id);
            if (provider == null)
                return Response.notFoundEntity("id[" + id + "]");

            Media media = form.get();
            media.setDtype(DTYPE_PROVIDER_PROFILE);
            media.setDtypeResolution(DTYPE_RESOLUTION_PROFILE);
            media.insert();
            provider.setMediaProfile(media);
            provider.update();   // TODO revisar que se borre el anterior
            Ebean.commitTransaction();

            return Response.createdEntity(Json.toJson(media));
        }catch(Exception e){
            return NsExceptionsUtils.create(e);
        }
    }


    @CoffeAppsecurity
    public Result delete(Long id) { // TODO terimar delete por cada relaci[on y eliminar este métooo
        Ebean.beginTransaction();
        try {
            Media entity = Media.findById(id);
            if(entity != null) {

                Provider provider = Provider.findByMediaProfileId(id);
                if (provider != null) {
                    provider.setMediaProfile(null);
                    provider.update();
                }

                Media.delete(id);
                Ebean.commitTransaction();
            }
            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }finally {
            Ebean.endTransaction();
        }
    }

    @CoffeAppsecurity
    public Result deletes() {
        try {
            JsonNode request = request().body().asJson();
            if(request == null)
                return Response.requiredJson();

            Ebean.deleteAll(Media.class, Media.findByIds(JsonUtils.toArrayLong(request, "ids")));

            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            Media media = Media.findById(id);

            return Response.foundEntity(Json.toJson(media));
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }

    @CoffeAppsecurity
    public Result findAll() {
        try {
            List<Media> medias = Media.findAll();

            return Response.foundEntity(Json.toJson(medias));
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }

    @CoffeAppsecurity
    public Result deleteUselessObjectsToContainer(){ // TODO revisar método
        try {
            List<Media> medias = Media.findAll();
            List<String> objectsNames = new ArrayList();

            for (Media media: medias) {
                if(media.getNameCdn() != null){
                    objectsNames.add(media.getNameCdn());
                }
                if(!media.getResolutions().isEmpty()) {
                    objectsNames.addAll(media.getNameCdnResolutions());
                }
                if(media.getNameCdnOptional() != null) {
                    objectsNames.add(media.getNameCdnOptional());
                }
            }
            System.out.println(medias.size());
            System.out.println(objectsNames.size());

            //rackspaceCloudFiles.deleteObjectsUselessToContainer(objectsNames);
            return Response.deletedEntity();
        }catch(Exception e){
            return NsExceptionsUtils.delete(e);
        }
    }

    @CoffeAppsecurity
    public Result deleteObjectsTest(){ // TODO revisar método
        try {
            rackspaceCloudFiles.deleteObjectsTest();
            return Response.deletedEntity();
        }catch(Exception e){
            return NsExceptionsUtils.delete(e);
        }
    }
}
