package controllers.statusController;

import controllers.utils.ListPagerCollection;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.PropertiesCollection;
import controllers.utils.Response;
import io.ebean.PagedList;
import io.ebean.text.PathProperties;
import models.status.StatusFarm;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

public class StatusFarms extends Controller {
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public StatusFarms(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size, String sort, String collection) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection pagedList = StatusFarm.findAll(index, size, sort, pathProperties);

            return Response.foundEntity(pagedList, pathProperties);
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }
}