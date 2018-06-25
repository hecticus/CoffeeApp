package models;

import com.avaje.ebean.validation.Range;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import controllers.utils.ListPagerCollection;
import io.ebean.*;
import io.ebean.text.PathProperties;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 12/05/17.
 * modify sm21 06/2018
 */
@Entity
@Table(name="provider_type")
public class ProviderType  extends AbstractEntity {

    @Constraints.Required
    @Constraints.MaxLength(60)
    @Column(nullable = false, unique = true, length = 60)
    private String nameProviderType;

    @OneToMany(mappedBy = "providerType", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Provider> providers;

    @OneToMany(mappedBy = "providerType", cascade = CascadeType.ALL)
    @JsonManagedReference
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

    public static ListPagerCollection findAll( Integer index, Integer size, PathProperties pathProperties,
                                               String sort, String name, Integer status, boolean deleted){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.startsWith("nameProviderType", name);

        if(sort != null) {
            if(sort.contains(" ")) {
                String []  aux = sort.split(" ", 2);
                expressionList.orderBy(sort( aux[0], aux[1]));
            }else {
                expressionList.orderBy(sort("idProviderType", sort));
            }
        }

        if(status != null)
            expressionList.eq("statusProvider", status);

        if( deleted )
            expressionList.setIncludeSoftDeletes();

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(
                expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }


}
