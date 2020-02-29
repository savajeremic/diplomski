import { Component, OnInit } from '@angular/core';
import { GameService } from '@/services/game.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Game } from '@/models/game';
import { GameReview } from '@/models/game-review';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '@/services/auth.service';
import { AlertService } from '@/services/alert.service';
import { UserGameService } from '@/services/user-game.service';
import { UserService } from '@/services/user.service';
import { UserGame } from '@/models/user-game';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit {

  game: Game = null;
  gameReviews: GameReview[] = [];
  gameGlobalRating: number = 0;
  userRating: number = 0;
  userReviewCount: number = 0;
  p: number = 1;
  addForm: FormGroup;
  isSubmitted: boolean = false;
  loading: boolean = true;
  isCurrentRoute = true;

  constructor(private gameService: GameService,
    private authService: AuthService,
    private userGameService: UserGameService,
    private userService: UserService,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private alertService: AlertService,
    private router: Router) {

  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      let gameId = +params['id'];
      this.getGame(gameId);
      this.getGameReviews(gameId);
    });
    this.initForm();
  }

  private initForm() {
    this.addForm = this.formBuilder.group({
      title: ['', Validators.required],
      comment: ['', Validators.required],
      userRating: ['0', [Validators.required, Validators.min(1)]]
    });
  }

  setRoute() {
    let temp = this.isCurrentRoute;
    this.isCurrentRoute = !this.isCurrentRoute;
    return temp;
  }

  get formControls() { return this.addForm.controls; }

  addToCart(game: Game) {
    if (this.authService.getUserId() == null) {
      return this.alertService.error("You have to be logged in to put games in cart.");
    }
    let cartGame: UserGame = new UserGame(this.authService.getUserId(), game.id, 1);
    this.userGameService.addToCart(cartGame).subscribe(data => {
      if (data.status === 200) {
        this.userGameService.addCart(game);
        this.alertService.success('Added game to cart!', true);
      } else {
        this.alertService.error(data.message);
      }
    },
      error => {
        this.alertService.error(error);
      }
    );
    this.loading = false;
  }

  checkout() {
    this.router.navigate(['user/checkout']);
  }

  userGames() {
    this.router.navigate(['user/usergames']);
  }

  seeWishlist() {
    this.router.navigate(['user/wishlist']);
  }

  private getGame(gameId: number) {
    this.gameService.getGameById(gameId).subscribe(
      data => {
        if (data.status === 200) {
          this.game = data.result;
          this.gameGlobalRating = this.game.rating;
        } else {
          this.alertService.error(data.message);
        }
      },
      error => {
        this.alertService.error(error);
      }
    );

    if (this.authService.getUserId() != 0)
      this.userGameService.getStoreGames(this.authService.getUserId()).subscribe(
        data => {
          let games = data.result;
          this.loading = false;
          this.game = games.find(_ => _.id === this.game.id);
        }
      );
  }
  private getGameReviews(gameId: number) {
    this.gameService.getGameReviewsByGameId(gameId).subscribe(
      data => {
        if (data.status === 200) {
          this.gameReviews = data.result;
          this.gameReviews.forEach(gr => gr.user.avatar = this.userService.sanitize(gr.user.avatar));
          console.log(data.result);
        } else {
          this.alertService.error(data.message);
        }
      },
      error => {
        this.alertService.error(error);
      }
    )
  }
  submitReview(gameReview: GameReview) {
    gameReview.user.avatar = this.userService.sanitize(gameReview.user.avatar)
    this.gameReviews.push(gameReview);
  }
  getUserReviewsCount(userId: number) {
    return this.gameReviews.filter(gr => gr.user.id == userId).length;
  }
}
