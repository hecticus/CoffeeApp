import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { HttpModule, Http, RequestOptions  } from '@angular/http';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { provideAuth, AuthHttp, AuthConfig  } from 'angular2-jwt';
import { AuthGuard } from './common/auth.guard';
import { Home } from './home';
import { Login } from './login';
import { Signup } from './signup';
import { App } from './app';
import { routes } from './app.routes';
import { ChangePasswordComponent } from './user/change-password/change-password.component';
import { EqualValidator } from './common/directives/equal-validator.directive';
import { MenuComponent } from './common/menu/menu.component';
import { MenuItemComponent } from './common/menu/menu-item/menu-item.component';
import { LogoutComponent } from './user/logout/logout.component';
import { NotificationComponent } from './common/notification/notification.component';
import { SimpleNotificationsModule  , NotificationsService} from 'angular2-notifications';
import { NotificationService } from './common/notification/notification.service';
import { BrowserAnimationsModule,NoopAnimationsModule } from '@angular/platform-browser/animations';



export function authHttpServiceFactory(http: Http, options: RequestOptions) {
  return new AuthHttp( new AuthConfig({}), http, options);
}

@NgModule({
  bootstrap: [App],
  declarations: [
    Home,
    Login,
    Signup,
    App,
    ChangePasswordComponent,
    EqualValidator,
    MenuComponent,
    MenuItemComponent,
    LogoutComponent,
    NotificationComponent
  ],
  imports: [
    HttpModule, BrowserModule, FormsModule,
    RouterModule.forRoot(routes, {
      useHash: true
    }),
    SimpleNotificationsModule.forRoot(),
    NoopAnimationsModule
   // BrowserAnimationsModule 
  ],
  providers: [
    NotificationService,
    AuthGuard,
    {
      provide: AuthHttp,
      useFactory: authHttpServiceFactory,
      deps: [ Http, RequestOptions ]
    }
  ]
})


export class AppModule {

}





