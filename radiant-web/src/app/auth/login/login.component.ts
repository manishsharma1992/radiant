import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { faApple, faFacebook, faGoogle, faTwitter } from '@fortawesome/free-brands-svg-icons';
import { catchError, throwError } from 'rxjs';
import { AuthService } from 'src/app/_shared/_services/auth.service';
import { ProgressService } from 'src/app/_shared/_services/progress.service';
import { TokenService } from 'src/app/_shared/_services/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  faGoogle = faGoogle;
  faFacebook = faFacebook;
  faApple = faApple;
  faTwitter = faTwitter;
  loginErrorMsg!: string;
  isSaveRequested: boolean = false;
  requestProgress!: string;
  
  constructor(
    public fb: FormBuilder,
    public authService: AuthService,
    public tokenService: TokenService,
    public progressService: ProgressService,
    public router: Router
  ) {}

  ngOnInit(): void {
    this.initLoginForm();
  }

  initLoginForm(): void {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.pattern('^[A-Za-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$')]],
      password: ['', Validators.required]
    });
  }

  authenticateUser(): void {
    this.authService.authenticateLogin(this.loginForm.value)
    .pipe(catchError(ex => {
      this.isSaveRequested = false;
      this.loginErrorMsg = ex.error.message
      return throwError(() => new Error(ex));
    }))
    .subscribe(
      (response) => {
        this.isSaveRequested = true;
        this.progressService.progressEventObservable.subscribe(res => this.requestProgress = res);
        this.tokenService.saveToken(response.token);
        this.tokenService.saveUser(response);
        this.router.navigateByUrl('/')
      })
  }

  authenticateSocialUser(socialMediaType: string): void {
    switch(socialMediaType) {
      case 'GOOGLE': 
        this.loginViaGoogle();
        break;
    }
  }

  loginViaGoogle() {
    throw new Error('Method not implemented.')
  }

}
