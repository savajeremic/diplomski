import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from '@/services/user.service';
import { AlertService } from '@/services/alert.service';
import { AuthService } from '@/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  isSubmitted = false;
  loading = false;
  @ViewChild('registerModal', { static: false }) registerModal: ElementRef;

  constructor(private formBuilder: FormBuilder, private router: Router, private route: ActivatedRoute, private authService: AuthService, private userService: UserService, private alertService: AlertService) {
    if (this.authService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      username: ['', [Validators.required, Validators.minLength(6)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]],
      acceptTerms: [false, [Validators.requiredTrue]]
    }, {
      validator: this.MustMatch('password', 'confirmPassword')
    });
  }

  get formControls() { return this.registerForm.controls; }

  register() {
    this.isSubmitted = true;
    this.alertService.clear();
    if (this.registerForm.invalid) {
      return;
    }

    const registerPayload = {
      username: this.registerForm.controls.username.value,
      password: this.registerForm.controls.password.value,
      email: this.registerForm.controls.email.value
    }

    console.log(registerPayload);
    this.userService.createUser(registerPayload)
      .subscribe(
        data => {
          if (data.status === 200) {
            this.alertService.success('User registration successfull!', true);
            this.registerModal.nativeElement.click();
            this.resetValues();
            this.router.navigate(['/']);
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
  }

  resetValues() {
    this.registerForm.setValue({
      username: "",
      password: "",
      email: "",
      confirmPassword: "",
      acceptTerms: false
    });
    this.isSubmitted = false;
    this.registerForm.reset();
  }

  MustMatch(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];

      if (matchingControl.errors && !matchingControl.errors.mustMatch) {
        return;
      }
      if (control.value !== matchingControl.value) {
        matchingControl.setErrors({ mustMatch: true });
      } else {
        matchingControl.setErrors(null);
      }
    }
  }
}
