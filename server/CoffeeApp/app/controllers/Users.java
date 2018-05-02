package controllers;

import com.typesafe.config.Config;
import play.api.libs.mailer.MailerClient;
import play.mvc.Controller;

import javax.inject.Inject;

/**
 * Created by yenny on 10/3/16.
 */
public class Users extends Controller {

    @Inject
    MailerClient mailerClient;

    @Inject
    private Config config;
    //private Configuration configuration;

    //private UserManager userManager = new UserManagerImpl();

    //@With(HecticusSecurity.class)
  //  @HSecurity("/user/findByEmail/@potato,nigga,chuleta,basic")
//    public Result findByEmail(String email) {
//        User CurrentUser = (User) ctx().args.get("CurrentUser");
//        return userManager.findByEmail(email);
//    }

    //public Result uploadPhoto() {return userManager.uploadPhoto();}

//    public Result login(){  return userManager.login(this.config);  }
//
//    public Result authorize(String path){ return userManager.authorize(this.config, path);  }
//
//    public Result verify(){  return userManager.verify(this.config);  }
//
//    public Result startResetPassword(String email){  return userManager.startResetPassword(email,this.mailerClient, this.config);  }
//
//    public Result handleStartResetPassword(){  return userManager.handleStartResetPassword();  }
//
//    public Result  logout(){  return userManager.logout(); }
}
