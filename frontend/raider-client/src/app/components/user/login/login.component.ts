import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { AuthService } from '@/services/auth.service';
import { AlertService } from '@/services/alert.service';
import { UserGameService } from '@/services/user-game.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  isSubmitted: boolean = false;
  loading: boolean = false;
  returnUrl: string;
  @ViewChild('loginModal', { static: false }) loginModal: ElementRef;

  constructor(private formBuilder: FormBuilder, 
    private router: Router, 
    private route: ActivatedRoute, 
    private authService: AuthService, 
    private alertService: AlertService,
    private userGameService: UserGameService) {
    if (this.authService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.minLength(5)]],
      password: ['', [Validators.required, Validators.minLength(5)]],
    });

    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  get formControls() { return this.loginForm.controls; }

  login() {
    this.isSubmitted = true;
    this.alertService.clear();
    if (this.loginForm.invalid) {
      return;
    }
    this.loading = true;
    const loginPayload = {
      email: this.loginForm.controls.email.value,
      password: this.loginForm.controls.password.value
    }

    this.authService.login(loginPayload).subscribe(
      data => {
        if (data.status === 200) {
          console.log(this.returnUrl);
          this.alertService.success('User login successfull!', true);
          this.loginModal.nativeElement.click();
          this.resetValues();
          this.userGameService.loginUserGames(this.authService.getUserId());
          this.router.navigate(['/']);
        } else {
          this.alertService.error(data.message);
          this.loading = false;
        }
      },
      error => {
        this.alertService.error(error);
        this.loading = false;
      });
  }

  resetValues() {
    this.loginForm.setValue({
      email: "",
      password: ""
    });
    this.isSubmitted = false;
    this.loading = false;
  }
}
