import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { ApiResponse } from '@/models/api-response';
import { UserGame } from '@/models/user-game';
import { Game } from '@/models/game';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserGameService {
  private baseUrl: string = `${environment.apiUrl}/usergame`;
  private cartGamesSubject: BehaviorSubject<Game[]> = new BehaviorSubject([]);
  private cartGames: Game[] = [];
  private isAddedToCartSubject: BehaviorSubject<boolean> = new BehaviorSubject(true);
  private isAddedToCart: boolean = true;
  private isRemovedFromCartSubject: BehaviorSubject<boolean> = new BehaviorSubject(true);
  private isRemovedFromCart: boolean = true;
  private userGamesSubject: BehaviorSubject<UserGame[]> = new BehaviorSubject([]);
  private userGames: UserGame[] = [];

  constructor(private http: HttpClient) {
    this.cartGamesSubject.subscribe((game: any) => this.cartGames = game);
    this.isAddedToCartSubject.subscribe((state: boolean) => this.isAddedToCart = state);
    this.isRemovedFromCartSubject.subscribe((state: boolean) => this.isRemovedFromCart = state);
    this.userGamesSubject.subscribe((userGame: any) => this.userGames = userGame);
  }

  public get getIsRemovedFromCartSubject() {
    return this.isRemovedFromCartSubject;
  }
  public get getIsAddedToCartSubject() {
    return this.isAddedToCartSubject;
  }
  public get getCartGamesSubject() {
    return this.cartGamesSubject;
  }
  public get getUserGamesSubject() {
    return this.userGamesSubject;
  }
  public getCartSum(): Observable<number> {
    return this.cartGamesSubject.pipe(
      map((games: Game[]) => {
        return games.reduce((previous, current: Game) => {
          return previous + current.price;
        }, 0);
      })
    );
  }
  public removeCart(game: Game) {
    this.cartGamesSubject.next(this.cartGames.filter(_ => _.id !== game.id));
    this.isRemovedFromCartSubject.next(this.isRemovedFromCart = !this.isRemovedFromCart);
  }
  public addCart(game: Game) {
    this.getCartGamesSubject.next([...this.cartGames, game]);
    this.isAddedToCartSubject.next(this.isAddedToCart = !this.isAddedToCart);
  }
  public setCart(games: Game[]) {
    this.getCartGamesSubject.next([...this.cartGames, ...games]);
  }
  public setUserGames(userGames: UserGame[]) {
    this.getUserGamesSubject.next([...this.userGames, ...userGames]);
  }
  public removeUserGame(userGame: UserGame) {
    this.userGamesSubject.next(this.userGames.filter(_ => _.gameId !== userGame.gameId));
  }
  public addUserGame(userGame: UserGame) {
    this.userGamesSubject.next([...this.userGames, userGame]);
  }
  public logout() {
    this.cartGames = [];
    this.userGames = [];
    this.setCart(this.cartGames);
    this.setUserGames(this.userGames);
  }
  public loginUserGames(userId: number) {
    this.getStoreGames(userId).subscribe(data => {
      let allUserGames: Game[] = data.result;
      this.cartGames = allUserGames.filter(game => game.isInCart === true);
      this.cartGamesSubject.next(this.cartGames);
      this.userGames = this.fromAllToUserGames(allUserGames, userId);
      this.userGamesSubject.next(this.userGames);
    });
  }
  fromAllToUserGames(allUserGames: Game[], userId: number) {
    let userGames: UserGame[] = [];
    allUserGames.forEach((game) => {
      if (game.isInCart) {
        userGames.push(new UserGame(userId, game.id, 1));
      }
      if (game.isInWishlist) {
        userGames.push(new UserGame(userId, game.id, 2));
      }
      if (game.isInOwned) {
        userGames.push(new UserGame(userId, game.id, 3));
      }
    });
    return userGames;
  }

  getCart(): Observable<Game[]> {
    return this.cartGamesSubject;
  }
  getStoreGames(userId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/getUpdatedStore/' + userId);
  }
  getUserOrders(userId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/getUserOrders/' + userId);
  }
  getUserCart(userId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/getUserCart/' + userId);
  }
  getWishlist(userId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/getUserWishlist/' + userId);
  }
  getUserGames(userId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/getUserGames/' + userId);
  }
  addToCart(userGame: UserGame): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + '/addToCart', userGame);
  }
  addToWishlist(userGame: UserGame): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + '/addToWishlist', userGame);
  }
  cartToWishlist(userGame: UserGame): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + '/cartToWishlist', userGame);
  }
  wishlistToCart(userGame: UserGame): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + '/wishlistToCart', userGame);
  }
  buyGames(games: UserGame[]): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + '/buyGames', games);
  }
  removeFromCart(userGame: UserGame): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + '/removeFromCart', userGame);
  }
  removeFromWishlist(userGame: UserGame): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + '/removeFromWishlist', userGame);
  }
  filterStoreByDate(isAscending: boolean, userId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/filter/byDate/' + isAscending + '/' + userId);
  }
  filterStoreAlphabetically(userId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/filter/alphabetically/' + userId);
  }
  filterStoreRating(userId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + '/filter/byRating/' + userId);
  }
}
