package models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by palenge.
 */
@Entity
@Table(name="route")
public class SecurityRoute extends AbstractEntity {

    @Id
    private Long idSecurityRoute;

    @Constraints.MaxLength(50)
    @Column(length = 50, nullable = false)
    protected String name;

    @Constraints.MaxLength(255)
    @Column(length = 255, nullable = false)
    protected String description;

    protected int routeType;

    @ManyToMany
    @JsonIgnore
    private List<SecurityTag> securityTag = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SecurityTag> getSecurityTag() {
        return securityTag;
    }

    public void setSecurityTag(List<SecurityTag> securityTag) {
        this.securityTag = securityTag;
    }


    public int getType() {
        return routeType;
    }

    public void setType(int type) {
        this.routeType = type;
    }

    public Long getIdSecurityRoute() {
        return idSecurityRoute;
    }

    public void setIdSecurityRoute(Long idSecurityRoute) {
        this.idSecurityRoute = idSecurityRoute;
    }
}
