import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { UserGameService } from '@/services/user-game.service';
import { Game } from '@/models/game';
import { AuthService } from '@/services/auth.service';
import { AlertService } from '@/services/alert.service';
import { UserGame } from '@/models/user-game';
import { Router } from '@angular/router';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {

  games: Game[] = [];
  loading: boolean = true;

  constructor(private userGameService: UserGameService,
    private authService: AuthService,
    private alertService: AlertService,
    private router: Router) {
  }

  ngOnInit() {
    this.userGameService.getUserCart(this.authService.getUserId()).subscribe(
      data => {
        this.games = data.result;
        this.loading = false;
      },
      error => {
        this.alertService.error(error);
        this.loading = false;
      }
    );
  }

  loggedIn() {
    return this.authService.isUserLoggedIn;
  }

  checkout() {
    if (this.games.length <= 0) {
      this.alertService.error("Cart is empty");
      return;
    }
    this.loading = true;
    let cartGames: UserGame[] = [];
    let userId: number = this.authService.getUserId();
    let ownedFlag: number = 1;//hm
    this.games.forEach((game: Game) => {
      cartGames.push(new UserGame(userId, game.id, ownedFlag));
    });
    this.userGameService.buyGames(cartGames).subscribe(data => {
      if (data.status === 200) {
        this.alertService.success('Successfully bought games!', true);
        this.userGameService.loginUserGames(this.authService.getUserId());
        this.router.navigate(['usergames']);
      } else {
        this.alertService.error(data.message);
      }
      this.loading = false;
    },
      error => {
        this.alertService.error(error);
        this.loading = false;
      }
    );
  }

  viewGame(id) {
    this.router.navigate(['/store', id]);
  }

  removeFromCart(game: Game) {
    this.loading = true;
    let gameId = game.id;
    let userGame: UserGame = new UserGame(this.authService.getUserId(), gameId, 1);
    this.userGameService.removeFromCart(userGame)
      .subscribe(data => {
        this.games.find(g => {
          if (g.id === game.id) {
            game.isInCart = !game.isInCart;
          }
        });
        this.userGameService.removeCart(game);
        this.games = this.games.filter(_ => _.id !== game.id);
        this.alertService.success("Removed from cart. ", true);
        this.loading = false;
      },
        error => {
          this.alertService.error(error);
          this.loading = false;
        }
      );
  }

  cartToWishlist(game: Game) {
    if (this.authService.getUserId() == null) {
      return this.alertService.error("You have to be logged in.");
    }
    this.loading = true;
    let gameId = game.id;
    let wishlistGame: UserGame = new UserGame(this.authService.getUserId(), game.id, 1);
    this.userGameService.cartToWishlist(wishlistGame).subscribe(data => {
      if (data.status === 200) {
        this.games.find(g => {
          if (g.id === gameId) {
            game.isInWishlist = true;
            game.isInCart = false;
          }
        });
        this.userGameService.removeCart(game);
        wishlistGame.gameFlagId = 2;
        this.userGameService.addUserGame(wishlistGame);
        this.games = this.games.filter(_ => _.id !== gameId);
        this.alertService.success('Game wished!', true);
      } else {
        this.alertService.error(data.message);
      }
      this.loading = false;
    },
      error => {
        this.alertService.error(error);
        this.loading = false;
      }
    );
  }

  getSum() {
    let total: number = 0;
    this.userGameService.getCartSum().subscribe(data => total = data);
    return total;
  }
}
