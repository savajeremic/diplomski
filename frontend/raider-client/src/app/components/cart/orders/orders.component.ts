import { Component, OnInit } from '@angular/core';
import { UserGameService } from '@/services/user-game.service';
import { AuthService } from '@/services/auth.service';
import { AlertService } from '@/services/alert.service';
import { Orders } from '@/models/orders';
import { Router } from '@angular/router';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements OnInit {

  orders: Orders[] = [];
  name: string;
  loading: boolean = true;

  constructor(private userGameService: UserGameService, 
    private authService: AuthService, 
    private alertService: AlertService,
    private router: Router) { }

  ngOnInit() {
    this.userGameService.getUserOrders(this.authService.getUserId()).subscribe(
      data => {
        this.orders = data.result;
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
}
