import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MdInputModule, MdButtonModule, MdCardModule, MdToolbarModule, MdIconModule, MdMenuModule
} from '@angular/material';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    MdInputModule,
    MdButtonModule,
    MdCardModule,
    MdToolbarModule,
    MdIconModule,
    MdMenuModule
  ],
  declarations: []
})
export class AppMaterialModule { }
