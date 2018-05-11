package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.Ebean;
import io.ebean.Finder;
import io.ebean.SqlRow;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 12/05/17.
 */
@Entity
@Table(name="provider_type")
public class ProviderType  extends AbstractEntity {
    @Id
    @Column(name = "id_ProviderType")
    private Long idProviderType;

    @Constraints.Required
    @Column(nullable = false, name = "name_ProviderType")
    private String nameProviderType;


    @Constraints.Required
    @Column(nullable = false, name = "status_ProviderType")
    private Integer statusProviderType = 1;

    @OneToMany(mappedBy = "providerType", cascade = CascadeType.ALL)
    private List<Provider> providers = new ArrayList<>();

    @OneToMany(mappedBy = "providerType", cascade = CascadeType.ALL)
    private List<ItemType> itemTypes = new ArrayList<>();

    private static Finder<Long, ProviderType> finder = new Finder<>(ProviderType.class);


    public Long getIdProviderType() {
        return idProviderType;
    }

    public void setIdProviderType(Long idProviderType) {
        this.idProviderType = idProviderType;
    }

    public String getNameProviderType() {
        return nameProviderType;
    }

    public void setNameProviderType(String nameProviderType) {
        this.nameProviderType = nameProviderType;
    }

    public Integer getStatusProviderType() {
        return statusProviderType;
    }

    public void setStatusProviderType(Integer statusProviderType) {
        this.statusProviderType = statusProviderType;
    }

    @JsonIgnore
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

    public static ProviderType findById(Long id){
        return finder.byId(id);
    }

    public List<ProviderType> getProviderTypesByName(String name_providertype, String order)
    {
        String sql = " select t0.id_providertype, t0.status_delete, " +
                " t0.name_providertype, t0.created_at, t0.updated_at  " +
                " from provider_types t0 " +
                " where status_delete = 0 "+
                " ";

        if(!name_providertype.equals(""))     sql += "  and name_providertype like '%"+name_providertype+"%' ";

        sql += "  order by name_providertype "+order+"";


        List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql)
                .findList();



        return toProviderTypes(sqlRows);


    }

    public List<ProviderType> toProviderTypes(List<SqlRow>  sqlRows)
    {
        List<ProviderType> providerTypes = new ArrayList<>();
        ProviderType providerType;

        for(int i=0; i < sqlRows.size(); ++i)
        {
            providerType = new ProviderType();

            providerType.setIdProviderType(sqlRows.get(i).getLong("id_providertype"));
            providerType.setNameProviderType(sqlRows.get(i).getString("name_providertype"));


            providerTypes.add(providerType);
        }

        return providerTypes;
    }

    public int getExist(String name_providertype)
    {
        if(finder.query().where().eq("name_providertype",name_providertype).eq("status_delete",0).findUnique()!=null) return 0;
        else
        {
            if(finder.query().where().eq("name_providertype",name_providertype).eq("status_delete",1).findUnique()!=null)  return 1;
            else return 2;

        }

    }

    public List<ProviderType> findAll(Integer pageIndex, Integer pageSize){
        List<ProviderType> entities;
        if(pageIndex != -1 && pageSize != -1)
            //    entities = find.setFirstRow(pageIndex).setMaxRows(pageSize).findList();
            entities = finder.query().where().eq("status_delete",0).setFirstRow(pageIndex).setMaxRows(pageSize).findList();
        else
            // entities =  find.all();
            entities = finder.query().where().eq("status_delete",0).findList();
        return entities;
    }

}
