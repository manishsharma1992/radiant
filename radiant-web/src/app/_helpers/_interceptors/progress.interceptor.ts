import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpEventType
} from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { ProgressService } from 'src/app/_shared/_services/progress.service';

@Injectable()
export class ProgressInterceptor implements HttpInterceptor {

  constructor(private progressService: ProgressService) {}

  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (req.reportProgress) {
      // only intercept when the request is configured to report its progress
      return next.handle(req).pipe(
        tap((event: HttpEvent<any>) => {
          if (event.type === HttpEventType.DownloadProgress) {
            // here we get the updated progress values, call your service or what ever here
            this.progressService.progressEventObservable.next(Math.round(event.loaded / event.total! * 100)); // display & update progress bar
          } else if (event.type === HttpEventType.Response) {
            this.progressService.progressEventObservable.next(null); // hide progress bar
          }
        }, error => {
          this.progressService.progressEventObservable.next(null); // hide progress bar
        })
      );
    } else {
      return next.handle(req);
    }
  }
}
