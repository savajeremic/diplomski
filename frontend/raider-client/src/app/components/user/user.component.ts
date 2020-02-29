import { Component, OnInit } from '@angular/core';
import { UserService } from '@/services/user.service';
import { User } from '@/models/user';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { AuthService } from '@/services/auth.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AlertService } from '@/services/alert.service';
import { __await } from 'tslib';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  user: User = null;
  currentUser: User;
  userId: number;

  isSubmitted = false;
  loading = false;
  updateForm: FormGroup;

  imageChangedEvent: any = '';
  croppedImage: any = '';
  imageFile: any = '';
  currentRate: number;

  imgSrc: any = '';

  constructor(private userService: UserService,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private alertService: AlertService) {
    if (!this.authService.currentUserValue && !this.authService.isAdmin()) {
      this.router.navigate(['/']);
    }
    this.authService.getCurrentUserSubject.subscribe(value => this.currentUser = value);
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      let id = +params['id'];
      this.userId = id;
      this.getUser(id);
    });
  }

  get formControls() { return this.updateForm.controls; }

  getUser(id: number) {
    this.userService.getUserById(id).subscribe(
      data => {
        this.user = data.result;
        let avatar = data.result.avatar;
        this.user.avatar = this.userService.sanitize(avatar);
        this.updateForm = this.formBuilder.group({
          id: [],
          email: [{ value: '', disabled: true }],
          username: [this.user.username, [Validators.required, Validators.minLength(6)]],
          fullname: [''],
          birthday: [''],
          country: [''],
        });
      }
    );

  }

  logout() { }

  getAvatar() {
    this.userService.getAvatar()
      .subscribe(data => {
        this.user.avatar = this.userService.sanitize(data.result);
      });

  }

  updateUser() {
    this.isSubmitted = true;
    this.alertService.clear();
    if (this.updateForm.invalid) {
      return;
    }
    let controlsUsername = this.updateForm.controls.username.value;
    let controlsFullname = this.updateForm.controls.fullname.value;
    let controlsBirthday = this.updateForm.controls.birthday.value;
    let controlsCountry = this.updateForm.controls.country.value;

    const userPayload = {
      id: this.user.id,
      username: controlsUsername != "" ? controlsUsername : this.user.username,
      email: this.user.email,
      fullname: controlsFullname != "" ? controlsFullname : this.user.fullname,
      country: controlsCountry != "" ? controlsCountry : this.user.country,
      birthday: controlsBirthday != "" ? controlsBirthday : this.user.birthday,
    }

    this.userService.updateUser(userPayload).subscribe(
      data => {
        if (data.status === 200) {
          this.alertService.success('User registration successfull!', true);
          if (!this.authService.isAdmin()) {
            this.currentUser.username = data.result.username;
            localStorage.setItem('currentUser', JSON.stringify(this.currentUser));
          }
        } else {
          this.alertService.error(data.message);
        }
      },
      error => {
        this.alertService.error(error);
      }
    );
  }

}
