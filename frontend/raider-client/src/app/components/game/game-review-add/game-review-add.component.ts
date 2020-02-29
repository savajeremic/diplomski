import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { GameReview } from '@/models/game-review';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '@/services/user.service';
import { UserGameService } from '@/services/user-game.service';
import { GameService } from '@/services/game.service';
import { AlertService } from '@/services/alert.service';
import { AdminService } from '@/services/admin.service';
import { AuthService } from '@/services/auth.service';
import { Game } from '@/models/game';
import { User } from '@/models/user';

@Component({
  selector: 'app-game-review-add',
  templateUrl: './game-review-add.component.html',
  styleUrls: ['./game-review-add.component.scss']
})
export class GameReviewAddComponent implements OnInit {

  @Output() review: EventEmitter<GameReview>;
  @Input() game: Game;
  user: User = null;
  newReview: GameReview = new GameReview();
  addForm: FormGroup;
  userRating: number = 0;
  isSubmitted: boolean = false;
  loading: boolean = false;
  ifInit: boolean = false;

  constructor(private formBuilder: FormBuilder,
    private alertService: AlertService,
    private gameService: GameService,
    private authService: AuthService) {
    this.authService.getCurrentUserSubject.subscribe(value => this.user = value);
    this.review = new EventEmitter();
  }

  ngOnInit() {
    this.initForm();
  }

  private initForm() {
    this.addForm = this.formBuilder.group({
      title: ['', Validators.required],
      comment: ['', Validators.required],
      userRating: ['0', [Validators.required, Validators.min(1)]]
    });
  }

  get formControls() { return this.addForm.controls; }

  submitReview() {
    this.isSubmitted = true;
    this.loading = true;
    if (this.authService.getUserId() < 1 || this.authService.getUserId() == null) {
      this.alertService.error("You need to be logged in for this.");
      this.loading = false;
      return;
    }
    if (this.addForm.invalid) {
      console.log("invalid form");
      this.loading = false;
      return;
    }
    let gameReview: GameReview = new GameReview();
    gameReview.gameId = this.game.id;
    gameReview.rating = this.userRating;
    gameReview.title = this.addForm.controls.title.value;
    gameReview.comment = this.addForm.controls.comment.value;
    gameReview.user = new User();
    gameReview.user.id = this.authService.getUserId();
    console.log(gameReview);
    console.log(JSON.stringify(gameReview));
    

    this.gameService.createGameReview(gameReview).subscribe(
      data => {
        if (data.status === 200) {
          this.alertService.success('Game reviewed successfully!', true);
          this.review.emit(data.result);
          this.addForm.reset();
        } else {
          this.alertService.error(data.message);
        }
      },
      error => {
        this.alertService.error(error);
      }
    )
    this.loading = false
    this.isSubmitted = false;
  }

  resetValues() {
    this.addForm.reset();
    this.loading = false
    this.isSubmitted = false;
  }

}
