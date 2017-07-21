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
import { Panel } from './panel';
import { Farm } from './farm';
import { FarmServicesComponent } from './farm/farm-services/farm-services.component';




export function authHttpServiceFactory(http: Http, options: RequestOptions) {
  return new AuthHttp( new AuthConfig({}), http, options);
}

@NgModule({
  bootstrap: [App],
  declarations: [
    Home,
    Panel,
    Login,
    Signup,
    App,
    Farm,
    FarmServicesComponent
  ],
  imports: [
    HttpModule, BrowserModule, FormsModule,
    RouterModule.forRoot(routes, {
      useHash: true
    }),
  ],
  providers: [
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





