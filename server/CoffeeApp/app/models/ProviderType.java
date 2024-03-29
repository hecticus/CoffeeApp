package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import controllers.utils.ListPagerCollection;
import io.ebean.*;
import io.ebean.text.PathProperties;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * modify sm21 06/2018
 */
@Entity
@Table(name="provider_type")
public class ProviderType  extends AbstractEntity {

    @Constraints.Required
    @Constraints.MaxLength(60)
    @Column(nullable = false, unique = true, length = 60)
    private String nameProviderType;

    @JsonIgnore
    @OneToMany(mappedBy = "providerType", cascade= CascadeType.ALL)
    private List<Provider> providers;

    @OneToMany(mappedBy = "providerType", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    private List<ItemType> itemTypes;

    public static Finder<Long, ProviderType> finder = new Finder<>(ProviderType.class);

    public ProviderType() {
        itemTypes = new ArrayList<>();
        providers = new ArrayList<>();
    }

    //Setter and Getter

    public String getNameProviderType() {
        return nameProviderType;
    }

    public void setNameProviderType(String nameProviderType) {
        this.nameProviderType = nameProviderType;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    public List<ItemType> getItemTypes() {
        return itemTypes;
    }

    public void setItemTypes(List<ItemType> itemTypes) {
        this.itemTypes = itemTypes;
    }

    //Metodos Definidos
    public static ProviderType findById(Long id){
        return finder.byId(id);
    }

    public static ProviderType findByName(String name){
        return finder.query().where()
                .eq("nameProviderType", name)
                .setIncludeSoftDeletes()
                .findUnique();
    }

    public static ListPagerCollection findAll( Integer index, Integer size, PathProperties pathProperties,
                                               String sort, String name, boolean delete){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null )
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.startsWith("nameProviderType", name);

        if(sort != null)
            expressionList.orderBy(sort( sort));

        if( delete )
            expressionList.setIncludeSoftDeletes();

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(
                expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }

    public static PagedList findAll2( Integer index, Integer size, PathProperties pathProperties,
                                               String sort, String name, boolean delete){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null )
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.startsWith("nameProviderType", name);

        if(sort != null)
            expressionList.orderBy(sort( sort));

        if( delete )
            expressionList.setIncludeSoftDeletes();

        if(index == null || size == null)
            return expressionList.findPagedList();
//            return expressionList.setFirstRow(0).setMaxRows(expressionList.findCount()).findPagedList();
        return expressionList.setFirstRow(0).setMaxRows(expressionList.findCount()).findPagedList();
    }
}
