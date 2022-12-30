import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProgressService {
  progressEventObservable: Subject<any> = new Subject();
  progressEvent$ = this.progressEventObservable.asObservable();
}
