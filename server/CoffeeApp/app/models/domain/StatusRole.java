package models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gmantilla on 5/16/17.
 */
@Entity
@DiscriminatorValue("role")
public class StatusRole extends Status {

    @OneToMany(mappedBy = "statusRole", cascade = CascadeType.ALL)
    public List<Role> roles = new ArrayList<>();

    @JsonIgnore
    public List<Role> getRoles() {  return roles;  }

    public void setRoles(List<Role> roles) {  this.roles = roles; }
}
