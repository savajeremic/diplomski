import { Component, OnInit } from '@angular/core';
import { GameDetails } from '@/models/game-details';
import { GameService } from '@/services/game.service';
import { AdminService } from '@/services/admin.service';
import { AlertService } from '@/services/alert.service';
import { AuthService } from '@/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-genre',
  templateUrl: './genre.component.html',
  styleUrls: ['./genre.component.scss']
})
export class GenreComponent implements OnInit {

  genres: GameDetails[];
  name: string;
  loading: boolean = true;
  isCurrentRoute = true;
  p: number = 1;

  constructor(private gameService: GameService, 
    private adminService: AdminService, 
    private alertService: AlertService, 
    private authService: AuthService,
    private router: Router) { }

  ngOnInit() {
    this.loadGenres();
  }

  setRoute() {
    let temp = this.isCurrentRoute;
    this.isCurrentRoute = !this.isCurrentRoute;
    return temp;
  }

  loadGenres() {
    this.gameService.getGenres().subscribe(
      data => {
        this.genres = data.result;
      }
    );
    this.loading = false;
  }

  addGenre(genre: GameDetails) {
    this.genres.push(genre);
  }

  editGenre(genre: GameDetails) {
    if(this.isAdmin())
      this.router.navigate(['/admin/update-genre', genre.id]);
  }

  deleteGenre(genre) {
    this.adminService.deleteGenre(genre.id)
      .subscribe(data => {
        if (data.status === 200) {
          this.genres = this.genres.filter(g => g !== genre);
          this.alertService.success('Genre deleted successfully! Note that games that had that genre dont have one now. Update games? ', true);
        } else {
          this.alertService.error(data.message);
        }
      },
        error => {
          this.alertService.error(error);
        }
      );
    this.loading = false;
  };

  isAdmin() {
    return this.authService.isAdmin();
  }
}
