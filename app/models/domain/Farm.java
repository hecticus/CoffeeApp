package models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 31/05/17.
 */
@Entity
@Table(name="farms")
public class Farm extends AbstractEntity
{
    @Id
    @Column(name = "id_farm")
    private Long idFarm;

    @Constraints.Required
    @Column(nullable = false, name = "name_farm")
    private String NameFarm;

    @Constraints.Required
    @Column(nullable = false, name = "status_farm")
    private Integer statusFarm=1;

    @OneToMany(mappedBy = "farm", cascade= CascadeType.ALL)
    private List<Lot> lots = new ArrayList<>();

    public Long getIdFarm() {
        return idFarm;
    }

    public void setIdFarm(Long idFarm) {
        this.idFarm = idFarm;
    }

    @JsonIgnore
    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    public Integer getStatusFarm() {
        return statusFarm;
    }

    public void setStatusFarm(Integer statusFarm) {
        this.statusFarm = statusFarm;
    }

    public String getNameFarm() {
        return NameFarm;
    }

    public void setNameFarm(String nameFarm) {
        NameFarm = nameFarm;
    }
}
