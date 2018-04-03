package models.dao.impl;

import models.dao.TokenDao;
import models.domain.Token;

import java.util.List;

/**
 * Created by darwin on 31/10/17.
 */
public class TokenDaoImpl  extends AbstractDaoImpl<Long, Token> implements TokenDao {

    public TokenDaoImpl() {
        super(Token.class);
    }

    @Override
    public Token findByToken(String token){
        List<Token> tokens = find.query()
                .where()
                .eq("token", token)
                .orderBy("created_at desc")
                .findList(); //por que no findunique?
        if(!tokens.isEmpty())
            return tokens.get(0);
        return null;
    }


    @Override
    public  void deletedByToken(String token)
    {
         this.delete(this.findByToken(token).getIdToken());
    }
}
