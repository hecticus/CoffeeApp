package security.authentication.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Claim {
    public Long id;
    public String email;
    public String client_id;
    public String token_type;

    public Claim(Long id, String email, String client_id){
        this.id = id;
        this.email = email;
        this.client_id = client_id;
    }

    public Map toMap(){
        return (new ObjectMapper()).convertValue(this, Map.class);
    }
}
