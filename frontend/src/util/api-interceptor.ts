import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environment/environment';

@Injectable()
export class APIInterceptor implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (req.url !== 'https://www.reddit.com/api/v1/access_token') {
      const apiReq = req.clone({
        url: `${environment.apiBase}/${req.url}`,
      });
      return next.handle(apiReq);
    } else {
      return next.handle(req);
    }
  }
}
