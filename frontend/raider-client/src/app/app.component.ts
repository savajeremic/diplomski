import { Component } from '@angular/core';
import { User } from './models/user';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  currentUser: User;
  navbarOpen = false;

  constructor(private authService: AuthService) { 
      this.currentUser = this.authService.currentUserValue;
    }
}
