import { Component, OnInit } from '@angular/core';
import { Game } from '@/models/game';
import { UserGameService } from '@/services/user-game.service';
import { AuthService } from '@/services/auth.service';
import { AlertService } from '@/services/alert.service';

@Component({
  selector: 'app-user-games',
  templateUrl: './user-games.component.html',
  styleUrls: ['./user-games.component.scss']
})
export class UserGamesComponent implements OnInit {

  games: Game[] = [];
  name: string;
  loading: boolean = true;

  constructor(private userGameService: UserGameService, 
    private authService: AuthService, 
    private alertService: AlertService) { }

  ngOnInit() {
    this.userGameService.getUserGames(this.authService.getUserId()).subscribe(
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

}
