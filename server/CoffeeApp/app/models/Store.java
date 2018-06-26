package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.utils.ListPagerCollection;
import io.ebean.*;
import io.ebean.text.PathProperties;
import models.status.StatusStore;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 12/05/17.
 */
@Entity
@Table(name="stores")
public class Store extends AbstractEntity{

    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(nullable = false, unique = true, length = 50)
    private String nameStore;

    @ManyToOne
    private StatusStore statusStore;

    @OneToMany(mappedBy = "store", cascade= CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails;

    private static Finder<Long, Store> finder = new Finder<>(Store.class);

    public Store() {
        invoiceDetails = new ArrayList<>();
    }

    //Setter and Getter
    public String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }

    public StatusStore getStatusStore() {
        return statusStore;
    }

    public void setStatusStore(StatusStore statusStore) {
        this.statusStore = statusStore;
    }

    @JsonIgnore
    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    //Metodos Definidos
    public static Store findById(Long id){
        return finder.byId(id);
    }

    public static ListPagerCollection findAll(Integer index, Integer size, PathProperties pathProperties,
                                              String sort, String name, Integer status, boolean delete){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.startsWith("nameStore", name);

        if(delete)
            expressionList.setIncludeSoftDeletes();

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if(status != 0L)
            expressionList.eq("statusStore.id", status );

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.findList());


        return new ListPagerCollection(
                expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }



}
