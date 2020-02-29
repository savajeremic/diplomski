import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '@/services/user.service';
import { User } from '@/models/user';
import { AlertService } from '@/services/alert.service';

@Component({
  selector: 'app-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.scss']
})
export class UserAddComponent implements OnInit {

  @Output() user: EventEmitter<User>;
  addForm: FormGroup;
  newUser: User;
  isSubmitted = false;
  loading = false;

  constructor(private formBuilder: FormBuilder, 
    private userService: UserService, 
    private alertService: AlertService) {
      this.user = new EventEmitter();
  }

  ngOnInit() {
    this.addForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(6)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  get formControls() { return this.addForm.controls; }


  getUser(id: number) {
    this.userService.getUserById(id).subscribe(
      data => {
        this.newUser = data.result;
      }
    );
  }

  addUser() {
    this.loading = true;
    const registerPayload = {
      username: this.addForm.controls.username.value,
      password: this.addForm.controls.password.value,
      email: this.addForm.controls.email.value
    }
    
    this.userService.createUser(registerPayload)
      .subscribe(data => {
        if (data.status === 200) {
          this.alertService.success('New user added successfully!', true);
          this.resetValues();
          this.user.emit(data.result);
          this.alertService.success(data.message, true);
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

  resetValues() {
    this.isSubmitted = false;
    this.addForm.reset();
  }

}
