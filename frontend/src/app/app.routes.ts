import { Routes } from '@angular/router';
import { AuthenticationComponent } from './components/authentication/authentication.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ViewComponent } from './components/view/view.component';
import { UploadComponent } from './components/upload/upload.component';
import { ParseComponent } from './components/parse/parse.component';
import { AuthGuard } from './guards/auth.guard';
import { QueryComponent } from './components/query/query.component';

export const routes: Routes = [
    { path: '', component:AuthenticationComponent },
    { path:'dashboard', component:DashboardComponent, canActivate: [AuthGuard] },
    { path: 'view', component:ViewComponent, canActivate: [AuthGuard] },
    { path: 'upload', component:UploadComponent, canActivate: [AuthGuard] },
    { path: 'parse', component:ParseComponent, canActivate: [AuthGuard] },
    { path: 'view/query', component:QueryComponent, canActivate: [AuthGuard] },
];
