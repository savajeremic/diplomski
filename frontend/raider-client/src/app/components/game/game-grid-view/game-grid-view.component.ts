import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { Game } from '@/models/game';

@Component({
  selector: 'app-game-grid-view',
  templateUrl: './game-grid-view.component.html',
  styleUrls: ['./game-grid-view.component.scss']
})
export class GameGridViewComponent implements OnInit {

  @Output() checkoutGame: EventEmitter<Game>;
  @Output() addCart: EventEmitter<Game>;
  @Output() addWishlist: EventEmitter<Game>;
  @Output() removeWishlist: EventEmitter<Game>;
  @Input() game: Game;
  @Input() loading: boolean;

  constructor() {
    this.checkoutGame = new EventEmitter();
    this.addCart = new EventEmitter();
    this.addWishlist = new EventEmitter();
    this.removeWishlist = new EventEmitter();
  }

  ngOnInit() {
  }

  public checkout(): void {
    this.checkoutGame.emit(this.game);
  }

  public addToCart(): void {
    this.addCart.emit(this.game);
  }

  public addToWishlist(): void {
    this.addWishlist.emit(this.game);
  }

  public removeFromWishlist(): void {
    this.removeWishlist.emit(this.game);
  }

}
