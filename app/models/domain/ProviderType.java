package models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 12/05/17.
 */
@Entity
@Table(name="provider_Type")
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
}
