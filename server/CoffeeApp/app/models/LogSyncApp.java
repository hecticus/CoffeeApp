package models;

import controllers.utils.ListPagerCollection;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import play.data.validation.Constraints;

import javax.persistence.*;

@Entity
public class LogSyncApp extends  AbstractEntity {

    @Constraints.Required
    @ManyToOne
    User user;

    @Constraints.Required
    @Lob
    private String data;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private static Finder<Long, LogSyncApp> finder = new Finder<>(LogSyncApp.class);

    public static LogSyncApp findById(Long id){
        return finder.byId(id);
    }

    public static ListPagerCollection findAll(){

        ExpressionList expressionList = finder.query().where();

        return new ListPagerCollection(expressionList.findList());
    }

}




