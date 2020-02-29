import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '@/services/auth.service';
import { AlertService } from '@/services/alert.service';
import { UserService } from '@/services/user.service';
import { User } from '@/models/user';

@Component({
  selector: 'app-user-password',
  templateUrl: './user-password.component.html',
  styleUrls: ['./user-password.component.scss']
})
export class UserPasswordComponent implements OnInit {

  @ViewChild('passwordModal', {static:false}) passwordModal: ElementRef;
  passwordForm: FormGroup;
  isSubmitted: boolean = false;
  loading: boolean = false;
  currentUser: User;

  model: any = {};

  constructor(private alertService: AlertService,
    private userService: UserService,
    private authService: AuthService) {
    this.authService.getCurrentUserSubject.subscribe(value => this.currentUser = value);
  }

  ngOnInit() {
  }

  updatePassword() {
    this.isSubmitted = true;
    this.alertService.clear();
    this.loading = true;
    const passwordDto = {
      oldPassword: this.model.oldPassword,
      newPassword: this.model.newPassword
    }
    
    this.userService.updatePassword(passwordDto).subscribe(
      data => {
        if (data.status === 200) {
          this.alertService.success(data.message, true);
          this.passwordModal.nativeElement.click();
          this.resetValues();
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

  resetValues() {
    this.isSubmitted = false;
    this.loading = false;
  }
}