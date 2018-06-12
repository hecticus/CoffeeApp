package models;

import com.avaje.ebean.validation.Range;
import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.utils.ListPagerCollection;
import io.ebean.*;
import io.ebean.text.PathProperties;
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

    @Id
    @Column(name = "id_store")
    private Long idStore;

    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(nullable = false, name = "name_store", unique = true, length = 50)
    private String NameStore;

    @Range(min = 0, max = 1)
    @Column(nullable = false, name = "status_store", columnDefinition = "integer default 1")
    private Integer statusStore;

    @OneToMany(mappedBy = "store", cascade= CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails;

    private static Finder<Long, Store> finder = new Finder<>(Store.class);

    public Store() {
        statusStore = 1;
        invoiceDetails = new ArrayList<>();
    }

    public Long getIdStore() {
        return idStore;
    }

    public void setIdStore(Long idStore) {
        this.idStore = idStore;
    }

    public String getNameStore() {
        return NameStore;
    }

    public void setNameStore(String nameStore) {
        NameStore = nameStore;
    }

    public Integer getStatusStore() {
        return statusStore;
    }

    public void setStatusStore(Integer statusStore) {
        this.statusStore = statusStore;
    }

    @JsonIgnore
    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public static Store findById(Long id){
        return finder.byId(id);
    }

    public static ListPagerCollection findAll(Integer index, Integer size, PathProperties pathProperties,
                                              String sort, String name, Integer status, boolean deleted){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.icontains("NameStore", name);

        if(deleted)
            expressionList.setIncludeSoftDeletes();

        if(sort != null) {
            if(sort.contains(" ")) {
                String []  aux = sort.split(" ", 2);
                expressionList.orderBy(sort( aux[0], aux[1]));
            }else {
                expressionList.orderBy(sort("idStore", sort));
            }
        }

        if(status != null)
            expressionList.eq("statusStore", status );

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.findList());


        return new ListPagerCollection(
                expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }


    public static List<Store> getByStatusStore(String StatusStore, String order){
        String sql="select t0.id_store c0, t0.status_delete c1, t0.name_store c2, t0.status_store c3, " +
                " t0.created_at c5, t0.updated_at c6 " +
                " from stores t0 "+
                " where t0.status_delete=0 ";

        if(!StatusStore.equals("-1"))  sql+=" and t0.status_store= "+StatusStore;

        sql+=" order by t0.name_Store "+order;

        SqlQuery query = Ebean.createSqlQuery(sql);

        List<SqlRow>   results = query.findList();

        return toStores(results);
    }

    public static List<Store> toStores(List<SqlRow>  sqlRows) {
        List<Store> stores = new ArrayList<>();

        Store Store;
        for(int i=0; i < sqlRows.size(); ++i)
        {
            Store = new Store();

            Store.setIdStore(sqlRows.get(i).getLong("c0"));
//            Store.setStatusDelete(sqlRows.get(i).getInteger("c1"));
            Store.setNameStore(sqlRows.get(i).getString("c2"));
            Store.setStatusStore(sqlRows.get(i).getInteger("c3"));

            stores.add(Store);
        }

        return stores;
    }


}
