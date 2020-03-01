import { Component, OnInit } from '@angular/core';
import { Game } from '@/models/game';
import { UserGameService } from '@/services/user-game.service';
import { AuthService } from '@/services/auth.service';
import { AlertService } from '@/services/alert.service';
import { UserGame } from '@/models/user-game';
import { Router } from '@angular/router';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.scss']
})
export class WishlistComponent implements OnInit {

  games: Game[] = [];
  name: string;
  loading: boolean = true;

  constructor(private userGameService: UserGameService, 
    private authService: AuthService, 
    private alertService: AlertService,
    private router: Router) { }

  ngOnInit() {
    this.userGameService.getWishlist(this.authService.getUserId()).subscribe(
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

  viewGame(id) {
    this.router.navigate(['/store', id]);
  }

  removeFromWishlist(game: Game) {
    this.loading = true;
    let gameId = game.id;
    let userGame: UserGame = new UserGame(this.authService.getUserId(), gameId, 2);
    this.userGameService.removeFromWishlist(userGame)
      .subscribe(data => {
        this.games.find(g => {
          if (g.id === game.id) {
            game.isInWishlist = !game.isInWishlist;
          }
        });
        this.userGameService.removeUserGame(userGame);
        this.games = this.games.filter(_ => _.id !== game.id);
        this.alertService.success("Removed from wishlist. ", true);
        this.loading = false;
      },
        error => {
          this.alertService.error(error);
          this.loading = false;
        }
      );
  }

  wishlistToCart(game: Game) {
    if (this.authService.getUserId() == 0) {
      return this.alertService.error("You have to be logged in.");
    }
    this.loading = true;
    let gameId = game.id;
    let wishlistGame: UserGame = new UserGame(this.authService.getUserId(), game.id, 2);
    this.userGameService.wishlistToCart(wishlistGame).subscribe(data => {
      if (data.status === 200) {
        this.games.find(g => {
          if (g.id === gameId) {
            game.isInWishlist = false;
            game.isInCart = true;
          }
        });
        this.userGameService.addCart(game);
        this.userGameService.removeUserGame(wishlistGame);
        this.games = this.games.filter(_ => _.id !== gameId);
        this.alertService.success('Game added to cart!', true);
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

}
