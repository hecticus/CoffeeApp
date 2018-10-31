package models;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.ListPagerCollection;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.annotation.DbJson;
import play.data.validation.Constraints;

import javax.persistence.*;

@Entity
public class LogSyncApp extends  AbstractEntity {

    @Constraints.Required
    @JoinColumn( nullable = false)
    @ManyToOne
    User user;

    @DbJson
    JsonNode content;

    @Lob
    String description;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JsonNode getContent() {
        return content;
    }

    public void setContent(JsonNode content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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




