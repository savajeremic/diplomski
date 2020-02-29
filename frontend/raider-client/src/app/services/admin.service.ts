import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '@/models/api-response';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private baseUrl: string = `${environment.apiUrl}/admin`;
  private gameUrl: string = this.baseUrl.concat('/game');

  constructor(private http: HttpClient) { }

  createGame(game: any): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.gameUrl + '/saveGame', game);
  }
  createGenre(genre: any): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.gameUrl + '/saveGenre', genre);
  }
  createCompany(company: any): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.gameUrl + '/saveCompany', company);
  }
  createLanguage(language: any): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.gameUrl + '/saveLanguage', language);
  }

  deleteGame(gameId: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.gameUrl + '/deleteGame/' + gameId);
  }
  deleteGenre(genreId: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.gameUrl + '/deleteGenre/' + genreId);
  }
  deletCompany(companyId: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.gameUrl + '/deleteCompany/' + companyId);
  }
  deleteLanguage(languageId: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.gameUrl + '/deleteLanguage/' + languageId);
  }
}
