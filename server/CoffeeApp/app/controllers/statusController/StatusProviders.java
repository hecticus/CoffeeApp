package controllers.statusController;

import controllers.utils.ListPagerCollection;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.PropertiesCollection;
import controllers.utils.Response;
import io.ebean.PagedList;
import io.ebean.text.PathProperties;
import models.status.StatusProvider;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

public class StatusProviders extends Controller {
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public StatusProviders(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size, String sort, String collection) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection pagedList = StatusProvider.findAll(index, size, sort, pathProperties);

            return Response.foundEntity(pagedList, pathProperties);
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }
}