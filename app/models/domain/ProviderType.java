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
@Table(name="provider_types")
public class ProviderType  extends AbstractEntity
{
    @Id
    private Long idProviderType;

    @Constraints.Required
    @Column(nullable = false)
    private String nameProviderType;

    @OneToMany(mappedBy = "providerType", cascade= CascadeType.ALL)
    private List<Provider> providers = new ArrayList<>();

    @OneToMany(mappedBy = "providerType", cascade= CascadeType.ALL)
    private List<ItemType> itemTypes = new ArrayList<>();

    public Long getId() {
        return idProviderType;
    }

    public void setId(Long idProviderType) {
        this.idProviderType = idProviderType;
    }

    public String getName() {
        return nameProviderType;
    }

    public void setName(String name) {
        this.nameProviderType = name;
    }
    @JsonIgnore
    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }
    @JsonIgnore
    public List<ItemType> getItemTypes() {
        return itemTypes;
    }

    public void setItemTypes(List<ItemType> itemTypes) {
        this.itemTypes = itemTypes;
    }
}
