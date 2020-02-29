import { Component, OnInit } from '@angular/core';
import { AuthService } from '@/services/auth.service';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss']
})
export class AboutComponent implements OnInit {

  isLoggedIn: boolean = false;

  constructor(private authService: AuthService) {
    this.authService.isUserLoggedIn.subscribe(value => { this.isLoggedIn = value });
  }

  ngOnInit() {
  }

}
