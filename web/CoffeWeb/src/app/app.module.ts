import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { HttpModule, Http, RequestOptions  } from '@angular/http';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { provideAuth, AuthHttp, AuthConfig  } from 'angular2-jwt';
import { AuthGuard } from './common/auth.guard';

import { HomeModule } from './home/home.module';
import { UserModule } from './user/user.module';


import { Signup } from './signup';
import { App } from './app';
import { routes } from './app.routes';
import { ChangePasswordComponent } from './user/change-password/change-password.component';
import { EqualValidator } from './common/directives/equal-validator.directive';
import { NotificationComponent } from './common/notification/notification.component';
import { SimpleNotificationsModule  , NotificationsService} from 'angular2-notifications';
import { NotificationService } from './common/notification/notification.service';
import { BrowserAnimationsModule,NoopAnimationsModule } from '@angular/platform-browser/animations';
import { homeRoutes } from './home/home.routes';

export function authHttpServiceFactory(http: Http, options: RequestOptions) {
  return new AuthHttp( new AuthConfig({}), http, options);
}

@NgModule({
  bootstrap: [App],
  declarations: [
    App,
    ChangePasswordComponent,
    EqualValidator,
    NotificationComponent,
  ],
  imports: [
    UserModule,
    HomeModule,
    HttpModule,
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(routes, {
      useHash: true
    }),
    SimpleNotificationsModule.forRoot(),
    NoopAnimationsModule,
    BrowserAnimationsModule 
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





