package controllers.authControllers;

import io.ebean.PagedList;
import io.ebean.text.PathProperties;
import controllers.parsers.queryStringBindable.Pager;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.Response;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.HSecurity;
import security.models.AuthPermission;

public class AuthPermissions extends Controller {
    @HSecurity
    public Result findAll(Long authUserId, Pager pager, String sort, String collection) {
        try {
            if(collection != null && !collection.isEmpty()) {
                PathProperties pathProperties = PathProperties.parse(collection);
                PagedList pagedList = AuthPermission.findAll(authUserId, pager.index, pager.size, sort, pathProperties);
                return Response.foundEntity(pagedList, pathProperties);
            }
            PagedList pagedList = AuthPermission.findAll(authUserId, pager.index, pager.size, sort, null);
            return Response.foundEntity(pagedList);
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }
}
