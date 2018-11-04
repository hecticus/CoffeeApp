package controllers.authControllers;

import controllers.parsers.queryStringBindable.Pager;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.Response;
import io.ebean.PagedList;
import io.ebean.text.PathProperties;
import play.mvc.Result;
import security.authorization.HSecurity;
import security.models.AuthGroup;

public class AuthGroups {
    @HSecurity
    public Result findAll(Pager pager, String sort, String collection) {
        try {
            if(collection != null && !collection.isEmpty()) {
                PathProperties pathProperties = PathProperties.parse(collection);
                PagedList pagedList = AuthGroup.findAll(pager.index, pager.size, sort, pathProperties);
                return Response.foundEntity(pagedList, pathProperties);
            }
            PagedList pagedList = AuthGroup.findAll(pager.index, pager.size, sort, null);
            return Response.foundEntity(pagedList);
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }
}
