import { Routes } from '@angular/router';
import { FailureListComponent } from './pages/failure-list/failure-list.component';

export const routes: Routes = [
  {path: 'incidents', component: FailureListComponent},
  {path: '', redirectTo: 'incidents', pathMatch: 'full'}
];
