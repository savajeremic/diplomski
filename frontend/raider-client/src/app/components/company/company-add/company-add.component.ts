import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AdminService } from '@/services/admin.service';
import { AlertService } from '@/services/alert.service';
import { GameDetails } from '@/models/game-details';
import { GameService } from '@/services/game.service';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-company-add',
  templateUrl: './company-add.component.html',
  styleUrls: ['./company-add.component.scss']
})
export class CompanyAddComponent implements OnInit {

  @Output() company: EventEmitter<GameDetails>;
  updatedCompany: GameDetails = new GameDetails();
  addForm: FormGroup;
  isSubmitted = false;
  loading = false;
  ifInit: boolean = false;

  constructor(private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private adminService: AdminService,
    private alertService: AlertService,
    private gameService: GameService) {
    this.company = new EventEmitter();
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      let id = +params['id'];
      if (id) {
        this.getCompany(id);
      } else {
        this.initForm();
        this.ifInit = true;
      }
    });
  }

  initForm() {
    this.addForm = this.formBuilder.group({
      name: ['', Validators.required]
    });
  }

  initFormUpdated() {
    this.addForm = this.formBuilder.group({
      name: [this.updatedCompany.name, Validators.required],
    });
  }

  get formControls() { return this.addForm.controls; }

  addCompany() {
    this.loading = true;
    let controlsId: number = this.updatedCompany.id;
    let controlsName: string = this.formControls.name.value;
    const company = {
      id: controlsId ? controlsId : null,
      name: controlsName != "" ? controlsName : this.updatedCompany.name,
    }
    if (this.addForm.invalid) {
      return;
    }
    this.adminService.createCompany(company).subscribe(data => {
      if (data.status === 200) {
        this.alertService.success('Company added successfully!', true);
        this.resetValues();
        this.company.emit(data.result);
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

  getCompany(id: number) {
    this.gameService.getCompanyById(id)
      .pipe(finalize(() => {
        this.initFormUpdated();
        this.ifInit = true;
      }))
      .subscribe(
        data => {
          if (data.status === 200) {
            this.updatedCompany = data.result;
          } else {
            this.alertService.error(data.message);
          }
        },
        error => {
          this.alertService.error(error);
        }
      );
  }

  resetValues() {
    this.addForm.setValue({
      name: "",
    });
    this.isSubmitted = false;
    this.addForm.reset();
  }

}
