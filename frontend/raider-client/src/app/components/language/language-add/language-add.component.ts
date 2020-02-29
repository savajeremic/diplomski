import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AdminService } from '@/services/admin.service';
import { AlertService } from '@/services/alert.service';
import { GameDetails } from '@/models/game-details';
import { GameService } from '@/services/game.service';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-language-add',
  templateUrl: './language-add.component.html',
  styleUrls: ['./language-add.component.scss']
})
export class LanguageAddComponent implements OnInit {

  @Output() language: EventEmitter<GameDetails>;
  updateLanguage: GameDetails = new GameDetails();
  addForm: FormGroup;
  isSubmitted = false;
  loading = false;
  ifInit: boolean = false;

  constructor(private formBuilder: FormBuilder,
    private router: Router,
    private adminService: AdminService,
    private alertService: AlertService,
    private route: ActivatedRoute,
    private gameService: GameService) { 
      this.language = new EventEmitter();
    }

  ngOnInit() {
    this.route.params.subscribe(params => {
      let id = +params['id'];
      if (id) {
        this.getLanguage(id);
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
      name: [this.updateLanguage.name, Validators.required],
    });
  }

  get formControls() { return this.addForm.controls; }

  addLanguage() {
    this.loading = true;
    let controlsId: number = this.updateLanguage.id;
    let controlsName: string = this.formControls.name.value;
    const language = {
      id: controlsId ? controlsId : null,
      name: controlsName != "" ? controlsName : this.updateLanguage.name,
    }
    this.adminService.createLanguage(language).subscribe(data => {
      if (data.status === 200) {
        this.alertService.success('Language added successfully!', true);
        this.resetValues();
        this.language.emit(data.result);
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

  getLanguage(id: number) {
    this.gameService.getLanguageById(id)
      .pipe(finalize(() => {
        this.initFormUpdated();
        this.ifInit = true;
      }))
      .subscribe(
        data => {
          if (data.status === 200) {
            this.updateLanguage = data.result;
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
