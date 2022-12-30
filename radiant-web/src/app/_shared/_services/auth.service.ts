import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpRequest } from '@angular/common/http';
import { observable, Observable } from 'rxjs';
import { ProgressService } from './progress.service';

const AUTH_API = 'http://localhost:8080/rest/auth/';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  reportProgress: true
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient, private progressService: ProgressService) { }

  authenticateLogin(request: any): Observable<any> {
    return this.httpClient.post(AUTH_API + 'authenticate', request, httpOptions);
  }
}
