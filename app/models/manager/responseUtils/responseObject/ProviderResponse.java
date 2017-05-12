package models.manager.responseUtils.responseObject;

import play.data.validation.Constraints;

import javax.persistence.Column;

/**
 * Created by drocha on 25/04/17.
 */
public class ProviderResponse extends AbstractEntityResponse
{

    public Long idProvider;
    public Long identificationDocProvider;
    public String fullNameProvider;
    public String addressProvider;
    public String phoneNumberProvider;
    public String emailProvider;
    public String photoProvider;
    public String contactNameProvider;
    
}
