import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { User } from '@/models/user';
import { UserService } from '@/services/user.service';
import { Router } from '@angular/router';
import { AuthService } from '@/services/auth.service';
import { AlertService } from '@/services/alert.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  users: User[];
  username: string;
  loading: boolean = true;
  isCurrentRoute = true;
  p: number = 1;

  constructor(private userService: UserService,
    private alertService: AlertService,
    private router: Router) {
  }

  ngOnInit() {
    this.getUsers();
  }

  setRoute() {
    let temp = this.isCurrentRoute;
    this.isCurrentRoute = !this.isCurrentRoute;
    return temp;
  }

  getUsers() {
    this.userService.getUsers().subscribe(
      data => {
        this.users = data.result;
        this.users.forEach((user) => user.avatar = this.userService.sanitize(user.avatar));
        this.loading = false;
      }
    );
    this.loading = false;
  }

  deleteUser(user: User): void {
    this.userService.deleteUser(user.id)
      .subscribe(data => {
        if (data.status === 200) {
          this.users = this.users.filter(u => u !== user);
          this.alertService.success(data.message, true);
        } else {
          this.alertService.error(data.message);
          this.loading = false;
        }
      },
        error => {
          this.alertService.error(error);
          this.loading = false;
        }
      );
    this.loading = false;
  }

  editUser(id: number): void {
    this.router.navigate(['admin/edit-user', id]);
  }

  addUser(user: User) {
    this.users.push(user);
  }

}
