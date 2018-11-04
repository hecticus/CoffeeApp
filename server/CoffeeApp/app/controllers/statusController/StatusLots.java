package controllers.statusController;

import controllers.responseUtils.ExceptionsUtils;
import controllers.responseUtils.ResponseCollection;
import controllers.utils.ListPagerCollection;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.PropertiesCollection;
import controllers.utils.Response;
import io.ebean.PagedList;
import io.ebean.text.PathProperties;
import models.status.StatusLot;
import play.mvc.Result;
import security.authorization.HSecurity;

public class StatusLots {
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public StatusLots(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    @HSecurity
    public Result findAll( Integer index, Integer size, String sort, String collection){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = StatusLot.findAll(index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }
}