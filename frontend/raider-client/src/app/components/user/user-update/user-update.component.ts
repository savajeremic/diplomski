import { Component, OnInit, Input, AfterContentInit, AfterViewInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from '@/services/user.service';
import { User } from '@/models/user';

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update.component.html',
  styleUrls: ['./user-update.component.scss']
})
export class UserUpdateComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private router: Router, private route: ActivatedRoute, private userService: UserService) { }

  addForm: FormGroup;
  user: User;
  loading: boolean = true;
  @Input() receivedParentMessage: User;

  ngOnInit() {
    this.route.params.subscribe(params => {
      let id = +params['id'];
      this.getUser(id);
    });
    this.addForm = this.formBuilder.group({
      id: [],
      username: ['', Validators.required],
      password: ['', Validators.required],
      email: ['', Validators.required],
      fullname: ['', Validators.required],
      birthday: ['', Validators.required],
      country: ['', Validators.required],
      avatar: ['', Validators.required],
      userRole: ['', Validators.required],
    });
    this.loading = false;
  }

  getMessage(user: User) {
    this.receivedParentMessage = user;
  }

  getUser(id: number) {
    this.userService.getUserById(id).subscribe(
      data => {
        this.user = data.result;
      }
    );
  }

  onSubmit() {
    this.loading = true;
    this.userService.createUser(this.addForm.value).subscribe(data => {
      this.router.navigate(['users']);
    });
    this.loading = false;
  }

}
