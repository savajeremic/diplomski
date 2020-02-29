import { Component, OnInit } from '@angular/core';
import { GameDetails } from '@/models/game-details';
import { GameService } from '@/services/game.service';
import { AdminService } from '@/services/admin.service';
import { AlertService } from '@/services/alert.service';
import { Router } from '@angular/router';
import { AuthService } from '@/services/auth.service';

@Component({
  selector: 'app-language',
  templateUrl: './language.component.html',
  styleUrls: ['./language.component.scss']
})
export class LanguageComponent implements OnInit {

  languages: GameDetails[];
  loading: boolean = true;
  name: string;
  isCurrentRoute = true;
  p: number = 1;

  constructor(private gameService: GameService,
    private adminService: AdminService,
    private alertService: AlertService,
    private authService: AuthService,
    private router: Router) { }

  ngOnInit() {
    this.loadLanguages();
  }

  setRoute() {
    let temp = this.isCurrentRoute;
    this.isCurrentRoute = !this.isCurrentRoute;
    return temp;
  }

  loadLanguages() {
    this.gameService.getLanguages().subscribe(
      data => {
        this.languages = data.result;
      }
    );
    this.loading = false;
  }

  addLanguage(language: GameDetails) {
    this.languages.push(language);
  }

  editLanguage(language: GameDetails) {
    this.router.navigate(['/admin/update-language', language.id]);
  }

  deleteLanguage(language) {
    this.adminService.deleteLanguage(language.id)
      .subscribe(data => {
        if (data.status === 200) {
          this.languages = this.languages.filter(l => l !== language);
          this.alertService.success('Language deleted successfully! Note that games that had that language dont have one now. Update games? ', true);
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

}
