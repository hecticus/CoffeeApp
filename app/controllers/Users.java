package controllers;

import models.Security.HSecurity;
import models.domain.User;
import models.manager.UserManager;
import models.manager.impl.UserManagerImpl;
import play.Configuration;
import play.api.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

/**
 * Created by yenny on 10/3/16.
 */
public class Users extends Controller {

    @Inject
    MailerClient mailerClient;

    @Inject
    private Configuration configuration;

    private UserManager userManager = new UserManagerImpl();

    //@With(HecticusSecurity.class)
  //  @HSecurity("/user/findByEmail/@potato,nigga,chuleta,basic")
    public Result findByEmail(String email) {
        User CurrentUser = (User) ctx().args.get("CurrentUser");
        return userManager.findByEmail(email);
    }

    //public Result uploadPhoto() {return userManager.uploadPhoto();}

    public Result login(){  return userManager.login(this.configuration);  }

    public Result authorize(String path){ return userManager.authorize(this.configuration, path);  }

    public Result verify(){  return userManager.verify(this.configuration);  }

    public Result startResetPassword(String email){  return userManager.startResetPassword(email,this.mailerClient, this.configuration);  }

    public Result handleStartResetPassword(){  return userManager.handleStartResetPassword();  }
}
