package controllers;

import com.avaje.ebean.text.PathProperties;
import controllers.utils.NsExceptionsUtils;
import security.models.Role;
import controllers.utils.Pager;
import controllers.utils.PropertiesCollection;
import controllers.utils.Response;
import io.ebean.PagedList;
import play.mvc.Controller;
import play.mvc.Result;

public class Role extends Controller{

    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public Role(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }


    public Result findAll(Pager pager, String sort, String collection) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            PagedList pagedList = Role.findAll(pager.pageIndex, pager.endIndex, sort, pathProperties);

            return Response.foundEntity(pagedList, pathProperties);
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }
}
