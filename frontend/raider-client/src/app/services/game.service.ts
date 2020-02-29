import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { ApiResponse } from '../models/api-response';
import { Observable } from 'rxjs';
import { GameReview } from '@/models/game-review';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private baseUrl: string = `${environment.apiUrl}/store`;
  constructor(private http: HttpClient) { }

  getGames(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl);
  }

  getGenres(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/genres');
  }

  getCompanies(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/companies');
  }

  getLanguages(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/languages');
  }

  getGameById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/' + id);
  }

  getGenreById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/genres/' + id);
  }

  getCompanyById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/companies/' + id);
  }

  getLanguageById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/languages/' + id);
  }

  getGameReviewsByGameId(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/' + id + '/reviews');
  }

  createGameReview(gameReview: GameReview): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + '/submitReview', gameReview);
  }
}
