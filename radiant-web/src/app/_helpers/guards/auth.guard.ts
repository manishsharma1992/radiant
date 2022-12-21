import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenService } from 'src/app/_shared/_services/token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
      private tokenService: TokenService, 
      private router: Router
  ) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      const user = this.tokenService.getUser();
      return this.checkIfUserLoggedIn(user);;
  }

  checkIfUserLoggedIn(user: any): boolean {
    let isloggedIn = false;
    if(user && user.username) {
      isloggedIn = true;
    }
    else {
      isloggedIn = false;
      this.router.navigateByUrl('/auth/login')
    }
    return isloggedIn;
  }
  
}
