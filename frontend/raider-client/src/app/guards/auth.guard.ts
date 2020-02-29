import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private authService: AuthService) { 
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      const currentUser = this.authService.currentUserValue;
      
      if(currentUser){
        console.log(next.data.roles);
        if(next.data.roles && next.data.roles === currentUser.userRole){
          this.router.navigate(['/']);
          return false;
        }
        return true;  
      }
      this.router.navigate(['/'], { queryParams: { returnUrl: state.url }});
      return false;
  }
  
}
