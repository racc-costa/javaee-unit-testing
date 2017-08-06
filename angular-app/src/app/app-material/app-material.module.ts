import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MdInputModule, MdButtonModule, MdCardModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    MdInputModule,
    MdButtonModule,
    MdCardModule
  ],
  declarations: []
})
export class AppMaterialModule { }
