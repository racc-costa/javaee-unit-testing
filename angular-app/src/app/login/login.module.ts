import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoginRoutingModule } from './login-routing.module';
import { LoginComponent } from './login/login.component';
import { AppMaterialModule } from './../app-material/app-material.module';

@NgModule({
  imports: [
    CommonModule,
    LoginRoutingModule,
    AppMaterialModule
  ],
  declarations: [LoginComponent]
})
export class LoginModule { }
