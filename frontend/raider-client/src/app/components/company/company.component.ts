import { Component, OnInit } from '@angular/core';
import { GameDetails } from '@/models/game-details';
import { GameService } from '@/services/game.service';
import { AdminService } from '@/services/admin.service';
import { AlertService } from '@/services/alert.service';
import { AuthService } from '@/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.scss']
})
export class CompanyComponent implements OnInit {
  companies: GameDetails[];
  name: string;
  loading: boolean = true;
  isCurrentRoute = true;
  p: number = 1;

  constructor(private gameService: GameService, 
    private adminService: AdminService, 
    private alertService: AlertService,
    private router: Router) { }

  ngOnInit() {
    this.loadCompanies();
  }

  setRoute() {
    let temp = this.isCurrentRoute;
    this.isCurrentRoute = !this.isCurrentRoute;
    return temp;
  }

  loadCompanies() {
    this.gameService.getCompanies().subscribe(
      data => {
        this.companies = data.result;
      }
    );
    this.loading = false;
  }

  addCompany(company: GameDetails){
    this.companies.push(company);
  }

  editCompany(company) {
    this.router.navigate(['/admin/update-company', company.id]);
  }

  deleteCompany(company) {
    this.adminService.deletCompany(company.id)
      .subscribe(data => {
        if (data.status === 200) {
          this.companies = this.companies.filter(c => c !== company);
          this.alertService.success('Company deleted successfully! Note that games that had that company dont have one now. Update games? ', true);
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

}
