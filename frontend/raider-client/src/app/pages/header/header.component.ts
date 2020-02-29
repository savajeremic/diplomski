import { Component, OnInit, Input } from '@angular/core';
import { User } from '@/models/user';
import { Router } from '@angular/router';
import { GameService } from '@/services/game.service';
import { AuthService } from '@/services/auth.service';
import { GameDetails } from '@/models/game-details';
import { UserGameService } from '@/services/user-game.service';
import { UserGame } from '@/models/user-game';
import { Game } from '@/models/game';
import { AlertService } from '@/services/alert.service';
import { Observable, of } from 'rxjs';
import { state, style, transition, animate, trigger, query, stagger, keyframes, useAnimation } from '@angular/animations';
import { cartAnimation } from '@/helpers/cart-animation';
import { Orders } from '@/models/orders';
import { UserService } from '@/services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  animations: [
    trigger('addToCart', [
      state('add', style({ boxShadow: '0 0 0 0px rgba(58, 168, 108, 0.5)' })),
      transition('add => *', [
        useAnimation(cartAnimation, {
          params: { boxShadow: '58, 168, 108', background: '#3aa86c80' }
        })
      ]),
      transition('* => add', [
        useAnimation(cartAnimation, {
          params: { boxShadow: '58, 168, 108', background: '#3aa86c80' }
        })
      ]),
    ]),
    trigger('removeFromCart', [
      state('remove', style({ boxShadow: '0 0 0 0px rgba(245, 8, 8, 0.5)' })),
      transition('remove => *', [
        useAnimation(cartAnimation, {
          params: { boxShadow: '198, 20, 44', background: '#c6142c80' }
        })
      ]),
      transition('* => remove', [
        useAnimation(cartAnimation, {
          params: { boxShadow: '198, 20, 44', background: '#c6142c80' }
        })
      ]),
    ]),
  ]
})
export class HeaderComponent implements OnInit {

  @Input() game: Game;
  currentUser: User = null;
  genres: GameDetails[] = [];
  cartGames: Game[] = [];
  cartGamesObs: Observable<Game[]> = of([]);
  isAddedObs: Observable<boolean>;
  isRemovedObs: Observable<boolean>;
  isLoggedIn: boolean;
  isAdded: boolean = true;
  isRemoved: boolean = true;

  userGames: UserGame[] = [];
  userGamesObs: Observable<UserGame[]> = of([]);

  orders: Orders[] = [];

  constructor(private authService: AuthService,
    private gameService: GameService,
    private userService: UserService,
    private router: Router,
    private userGameService: UserGameService,
    private alertService: AlertService) {
    this.authService.getCurrentUserSubject.subscribe(value => this.currentUser = value);
    this.authService.isUserLoggedIn.subscribe(value => { this.isLoggedIn = value });

    this.cartGamesObs = this.userGameService.getCartGamesSubject;
    this.cartGamesObs.subscribe((game: any) => this.cartGames = game);

    this.isAddedObs = this.userGameService.getIsAddedToCartSubject;
    this.isAddedObs.subscribe((state: boolean) => this.isAdded = state);

    this.isRemovedObs = this.userGameService.getIsRemovedFromCartSubject;
    this.isRemovedObs.subscribe((state: boolean) => this.isRemoved = state);

    this.userGamesObs = this.userGameService.getUserGamesSubject;
    this.userGamesObs.subscribe((userGame: any) => this.userGames = userGame);
  }

  ngOnInit() {
    this.headerInit();
    this.loadGenres();
  }

  headerInit() {
    if (this.currentUser) {
      this.currentUser.avatar = null;
      this.getUserAvatar();
      this.loadCartGames();
      this.loadOrders();
      this.isLoggedIn = true;
      console.log(this.currentUser.avatar);
    }
  }

  logout() {
    this.authService.logout();
    this.userGameService.logout();
    this.cartGames = [];
    this.cartGamesObs = of([]);
    this.userGames = [];
    this.userGamesObs = of([]);
    this.orders = [];
  }

  getUserAvatar() {
    this.userService.getUserById(this.currentUser.id).subscribe(
      data => {
        let avatar = data.result.avatar;
        this.currentUser.avatar = this.userService.sanitize(avatar);
      }
    );
  }

  loadOrders() {
    this.userGameService.getUserOrders(this.currentUser.id)
      .subscribe(data => {
        this.orders = data.result;
      });
  }

  loadGenres() {
    this.gameService.getGenres().subscribe(
      data => {
        this.genres = data.result;
      }
    );
  }

  loadCartGames() {
    let userId: number = this.currentUser.id;
    this.userGameService.getStoreGames(userId).subscribe(
      data => {
        let allUserGames: Game[] = data.result;
        let userGames: UserGame[] = this.userGameService.fromAllToUserGames(allUserGames, userId);

        this.userGameService.setUserGames(userGames);
        allUserGames = allUserGames.filter(game => game.isInCart === true);
        this.userGameService.setCart(allUserGames);
      }
    );
  }

  removeFromCart(game: Game) {
    let gameId = game.id;
    let userGame: UserGame = new UserGame(this.authService.getUserId(), gameId, 1);
    this.userGameService.removeFromCart(userGame)
      .subscribe(data => {
        this.cartGames.find(g => {
          if (g.id === game.id) {
            game.isInCart = !game.isInCart;
          }
        });
        this.userGameService.removeCart(game);
        this.cartGames = this.cartGames.filter(_ => _.id !== game.id);
        this.alertService.success("Removed from cart. ", true);
      });
  }

  cartToWishlist(game: Game) {
    if (this.authService.getUserId() == null) {
      return this.alertService.error("You have to be logged in.");
    }
    let gameId = game.id;
    let wishlistGame: UserGame = new UserGame(this.authService.getUserId(), game.id, 1);
    this.userGameService.cartToWishlist(wishlistGame).subscribe(data => {
      if (data.status === 200) {
        this.cartGames.find(g => {
          if (g.id === gameId) {
            game.isInWishlist = true;
            game.isInCart = false;
          }
        });
        this.userGameService.removeCart(game);
        wishlistGame.gameFlagId = 2;
        this.userGameService.addUserGame(wishlistGame);
        this.cartGames = this.cartGames.filter(_ => _.id !== game.id);
        this.alertService.success('Game wished!', true);
      } else {
        this.alertService.error(data.message);
      }
    },
      error => {
        this.alertService.error(error);
      }
    );
  }

  addToWishlist(game: Game) {
    if (this.authService.getUserId() == null) {
      return this.alertService.error("You have to be logged in to put games in wishlist.");
    }
    let gameId = game.id;
    let wishlistGame: UserGame = new UserGame(this.authService.getUserId(), game.id, 2);
    this.userGameService.addToWishlist(wishlistGame).subscribe(data => {
      if (data.status === 200) {
        this.cartGames.find(g => {
          if (g.id === gameId) {
            game.isInWishlist = true;
          }
        });
        this.alertService.success('Game wished!', true);
      } else {
        this.alertService.error(data.message);
      }
    },
      error => {
        this.alertService.error(error);
      }
    );
  }

  getSum() {
    let total: number = 0;
    this.userGameService.getCartSum().subscribe(data => total = data);
    return total;
  }

  getFlagCount(flagId: number) {
    return this.userGames.filter((_) => _.gameFlagId === flagId).length;
  }

  isAdmin() {
    return this.authService.isAdmin();
  }

  viewGame(id) { }

  checkout() {
    this.router.navigate(['/user/checkout']);
  }
}
