package controllers.authControllers;

import io.ebean.PagedList;
import io.ebean.text.PathProperties;
import security.authorization.HSecurity;
import security.models.AuthRole;
import controllers.parsers.queryStringBindable.Pager;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.Response;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by sm21 on 10/3/16.
 */
public class AuthRoles extends Controller {

    @HSecurity
    public Result findAll(Pager pager, String sort, String collection) {
        try {
            if(collection != null && !collection.isEmpty()) {
                PathProperties pathProperties = PathProperties.parse(collection);
                PagedList pagedList = AuthRole.findAll(pager.index, pager.size, sort, pathProperties);
                return Response.foundEntity(pagedList, pathProperties);
            }
            PagedList pagedList = AuthRole.findAll(pager.index, pager.size, sort, null);
            return Response.foundEntity(pagedList);
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }
}
