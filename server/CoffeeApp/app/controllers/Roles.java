
package controllers;

import controllers.utils.PropertiesCollection;
import play.mvc.Controller;

public class Roles extends Controller{

    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public Roles(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

/*
    public Result findAll(Pager pager, Ordering.String sort, Ordering.String collection) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            PagedList pagedList = Role.findAll(pager.pageIndex, pager.endIndex, sort, pathProperties);

//            return Response.foundEntity(pagedList, pathProperties);
            return Response.foundEntity(pagedList, pathProperties);
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }*/
}

