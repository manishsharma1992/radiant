import { NgModule } from '@angular/core';
import { NavbarComponent } from './_components/navbar/navbar.component';
import { FooterComponent } from './_components/footer/footer.component';
import { RouterModule } from '@angular/router';
import { ProgressbarComponent } from './_components/progressbar/progressbar.component';




@NgModule({
  declarations: [
    NavbarComponent,
    FooterComponent,
    ProgressbarComponent
  ],
  imports: [ 
    RouterModule
  ],
  exports: [
    NavbarComponent,
    FooterComponent,
    ProgressbarComponent
  ]
})
export class SharedModule { }
