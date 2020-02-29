import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { User } from '@/models/user';
import { ApiResponse } from '@/models/api-response';
import { map } from 'rxjs/operators';
import { UserService } from './user.service';
import { DomSanitizer } from '@angular/platform-browser';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject: BehaviorSubject<User>;
  public isUserLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  constructor(private http: HttpClient, private userService: UserService, private sanitizer: DomSanitizer) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  public get getCurrentUserSubject() {
    return this.currentUserSubject;
  }

  login(loginPayload): Observable<ApiResponse> {
    return this.http.post<ApiResponse>('http://localhost:8080/login', loginPayload)
      .pipe(
        map(data => {
        if (data.result != null) {
          let user: User = data.result;
          user.avatar = this.sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + user.avatar);
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
          this.isUserLoggedIn.next(true);
        }
        return data;
      }));
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.isUserLoggedIn.next(false);
  }

  isAdmin(): boolean {
    let user = JSON.parse(localStorage.getItem('currentUser'));
    if (!user) {
      return false;
    }
    return user.role == "admin";
  }

  getUserId() {
    let user = JSON.parse(localStorage.getItem('currentUser'));
    if (user) {
      return user.id;
    } else {
      return 0;
    }
  }

  getUserAvatar() {
    let user = JSON.parse(localStorage.getItem('currentUser'));
    let objectURL = 'data:image/png;base64,' + user.avatar;
    if (user.avatar) {
      return objectURL;
    } else {
      return null;
    }
  }

}
