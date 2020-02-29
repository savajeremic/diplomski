import { Injectable } from '@angular/core';
import { Observable, Subject, BehaviorSubject } from 'rxjs';
import { User } from '../models/user';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { ApiResponse } from '../models/api-response';
import { DomSanitizer } from '@angular/platform-browser';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl: string = `${environment.apiUrl}/user`;
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient, private sanitizer: DomSanitizer) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }
  getUsers(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl);
  }

  getUserById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/' + id);
  }

  createUser(registerPayload): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + '/register', registerPayload);
  }

  updateUser(user): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl + '/' + user.id, user);
  }

  deleteUser(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + '/' + id);
  }

  uploadAvatar(avatar, id): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl + '/uploadAvatar/' + id, avatar);
  }

  updatePassword(passwordDto): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + '/updatePassword', passwordDto);
  }

  getAvatar(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/getAvatar');
  }

  sanitize(avatar) {
    let src = 'data:image/png;base64,' + avatar;
    if (avatar != null) {
      return this.sanitizer.bypassSecurityTrustUrl(src);
    }
    else {
      return null;
    }
  }
}
