package controllers.statusController;

import controllers.utils.*;
import io.ebean.PagedList;
import io.ebean.text.PathProperties;
import models.status.StatusInvoice;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

public class StatusInvoices extends Controller {
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();


    @CoffeAppsecurity
    public Result findAll( Integer index, Integer size, String sort, String collection){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            PagedList pagedList = StatusInvoice.findAll(index, size, sort, pathProperties);

            return Response.foundEntity(pagedList, pathProperties);
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }
}

