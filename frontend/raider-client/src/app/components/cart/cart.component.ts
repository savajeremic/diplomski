import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { Game } from '@/models/game';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  @Output() addCartGame: EventEmitter<Game>;
  @Output() removeCartGame: EventEmitter<Game>;
  @Output() cartToWishlist: EventEmitter<Game>;
  @Input() game: any;

  constructor(private router: Router) { 
    this.addCartGame = new EventEmitter();
    this.removeCartGame = new EventEmitter();
    this.cartToWishlist = new EventEmitter();
  }

  ngOnInit() {
  }

  public removeFromCart(): void{
    this.removeCartGame.emit(this.game);
  }

  public addToCart(): void{
    this.addCartGame.emit(this.game);
  }

  public moveToWishlist(): void{
    this.cartToWishlist.emit(this.game);
  }

  viewGame(id) {
    this.router.navigate(['/store', id]);
  }

}
