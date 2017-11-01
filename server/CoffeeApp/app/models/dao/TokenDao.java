package models.dao;
import models.domain.Token;

/**
 * Created by darwin on 31/10/17.
 */
public interface TokenDao extends AbstractDao<Long, Token> {

    public Token findByToken(String token);
    public void deletedByToken(String token);
}
