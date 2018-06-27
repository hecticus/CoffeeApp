package models;

import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Contact {

    private Double latitude = null;

    private Double longitude = null;

    @Constraints.MaxLength(200)
    @Column(length = 200)
    private String address = null;

    @Constraints.MaxLength(20)
    @Column(length = 20)
    private String phone = null;

    @Constraints.MaxLength(20)
    @Column(length = 20)
    private String phone2 = null;

    @Constraints.MaxLength(50)
    @Constraints.Email
    @Column(length = 50)
    private String email2 = null;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
