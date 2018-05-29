package controllers.requestUtils.requestObject;


import controllers.responseUtils.responseObject.AbstractEntityResponse;
import models.Farm;
import play.data.validation.Constraints;

/**
 * Created by sm21 on 10/05/18.
 */
public class LotRequest  extends AbstractEntityResponse {

    public Long idLot;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String areaLot;

    @Constraints.Required
    public Double heighLot;

    public Integer statusLot=1;

    @Constraints.Required
    public Long farm;

    @Constraints.Required
    public Float price_lot;

}
