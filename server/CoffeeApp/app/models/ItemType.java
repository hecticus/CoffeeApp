package models;

import com.avaje.ebean.validation.Range;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.utils.ListPagerCollection;
import io.ebean.*;
import io.ebean.text.PathProperties;
import org.jetbrains.annotations.NotNull;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sm21 on 10/05/18.
 */
@Entity
@Table(name="item_types")
public class ItemType extends AbstractEntity{

    @Id
    @Column(name = "id_itemType")
    private Long idItemType;

    @Constraints.Required
    @Column(nullable = false, name = "name_itemType", unique = true)
    private String nameItemType;

    @Constraints.Required
    @Constraints.Min(0)
    @Column(nullable = false, name = "cost_itemType")
    private BigDecimal costItemType;

    @Range(min = 0, max = 3)
    @Column(nullable = false, name = "status_itemType")
    private Integer statusItemType;

    @ManyToOne
    @JsonBackReference
    @Constraints.Required
    @JoinColumn(name = "id_providerType", nullable = false)
    private ProviderType providerType;

    @ManyToOne
    @Constraints.Required
    @JoinColumn(name = "id_unit", nullable = false)
    private Unit unit;

    @OneToMany(mappedBy = "itemType", cascade= CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails;



    private static Finder<Long, ItemType> finder = new Finder<>(ItemType.class);

    public ItemType() {
        statusItemType = 1;
        invoiceDetails = new ArrayList<>();
    }

    //GETTER AND SETTER
    public Long getIdItemType() {
        return idItemType;
    }

    public void setIdItemType(Long idItemType) {
        this.idItemType = idItemType;
    }

    public String getNameItemType() {
        return nameItemType;
    }

    public void setNameItemType(String nameItemType) {
        this.nameItemType = nameItemType;
    }

    public BigDecimal getCostItemType() {
        return costItemType;
    }

    public void setCostItemType(BigDecimal costItemType) {
        this.costItemType = costItemType;
    }

    public Integer getStatusItemType() {
        return statusItemType;
    }

    public void setStatusItemType(Integer statusItemType) {
        this.statusItemType = statusItemType;
    }

    public ProviderType getProviderType() {
        return providerType;
    }

    @JsonIgnore
    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @JsonIgnore
    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    //METODOS DEFINE

    public static ItemType findById(Long id){
        return finder.byId(id);
    }

    public static ListPagerCollection findAll(Integer pageIndex, Integer pageSize, PathProperties pathProperties,
                                              String sort, String name, Long id_ProviderType, Integer status,
                                              boolean deleted ){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.startsWith("nameItemType", name);

        if(status != null)
            expressionList.eq("statusItemType", status);

        if(sort != null) {
            if(sort.contains(" ")) {
                String []  aux = sort.split(" ", 2);
                expressionList.orderBy(sort( aux[1], aux[0]));
            }else {
                expressionList.orderBy(sort("idItemType", sort));
            }
        }

        if(id_ProviderType != 0L )
            expressionList.eq("providerType.idProviderType", id_ProviderType);

        if( deleted )
            expressionList.setIncludeSoftDeletes();

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());

        return new ListPagerCollection(
                expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(),
                expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(),
                pageIndex,
                pageSize);
    }


    public static List<ItemType> getByProviderTypeId(Long idProviderType, Integer status){
        String sql="SELECT item.id_itemtype  c0, item.status_delete c1, item.name_itemtype   c2, " +
                " item.cost_itemtype c3, item.status_itemtype c4, item.created_at c5, " +
                " item.updated_at c6, item.id_providertype c7, item.id_unit c8  " +
                " FROM item_types item "+
                " inner join provider_type protype on protype.id_providertype=item.id_providertype " +
                " where item.status_delete=0  and item.id_providertype= :idprovidertype ";

        if(status!=-1) sql+= " and protype.status_delete=:status ";

        SqlQuery query = Ebean.createSqlQuery(sql)
                .setParameter("idprovidertype", idProviderType )
                .setParameter("status",status);

        List<SqlRow>   results = query.findList();

        return toItemTypes(results);
    }

    public static List<ItemType> getByNameItemType(String NameItemType, String order)
    {
        String sql="SELECT item.id_itemtype  c0, item.status_delete c1, item.name_itemtype   c2, " +
                " item.cost_itemtype c3, item.status_itemtype c4, item.created_at c5, " +
                " item.updated_at c6, item.id_providertype c7, item.id_unit c8  " +
                " FROM item_types item "+
                " where item.status_delete=0  and item.name_itemtype like '%"+NameItemType+"%' "+
                " order by item.id_itemtype   "+order;

        SqlQuery query = Ebean.createSqlQuery(sql);

        List<SqlRow>   results = query.findList();

        return toItemTypes(results);
    }

    public static List<ItemType> toItemTypes(List<SqlRow>  sqlRows)
    {
        List<ItemType> itemTypes = new ArrayList<>();

        ItemType itemType;
        for(int i=0; i < sqlRows.size(); ++i)
        {
            itemType = new ItemType();

            itemType.setIdItemType(sqlRows.get(i).getLong("c0"));
            itemType.setNameItemType(sqlRows.get(i).getString("c2"));
            itemType.setCostItemType(sqlRows.get(i).getBigDecimal("c3"));
            itemType.setUnit(Unit.findById(sqlRows.get(i).getLong("c8")));
            itemType.setStatusItemType(sqlRows.get(i).getInteger("c4"));
            itemTypes.add(itemType);
        }

        return itemTypes;
    }

}
