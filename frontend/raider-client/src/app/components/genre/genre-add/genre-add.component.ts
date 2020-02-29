import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { GameService } from '@/services/game.service';
import { AlertService } from '@/services/alert.service';
import { AdminService } from '@/services/admin.service';
import { GameDetails } from '@/models/game-details';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-genre-add',
  templateUrl: './genre-add.component.html',
  styleUrls: ['./genre-add.component.scss']
})
export class GenreAddComponent implements OnInit {

  @Output() newGenre: EventEmitter<GameDetails>;
  updateGenre: GameDetails = new GameDetails();
  addForm: FormGroup;
  isSubmitted = false;
  loading = false;
  ifInit: boolean = false;

  constructor(private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private adminService: AdminService,
    private alertService: AlertService,
    private gameService: GameService,
    private router: Router) {
    this.newGenre = new EventEmitter();
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      let id = +params['id'];
      if (id) {
        this.getGenre(id);
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
      name: [this.updateGenre.name, Validators.required],
    });
  }

  get formControls() { return this.addForm.controls; }

  addGenre() {
    this.loading = true;
    let controlsId: number = this.updateGenre.id;
    let controlsName: string = this.formControls.name.value;
    const genre = {
      id: controlsId ? controlsId : null,
      name: controlsName != "" ? controlsName : this.updateGenre.name,
    }
    this.adminService.createGenre(genre).subscribe(data => {
      if (data.status === 200) {
        this.alertService.success('Genre added successfully!', true);
        this.resetValues();
        this.newGenre.emit(data.result);
        if(genre.id != null) {
          this.router.navigate(['/admin/genre']);
        }
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

  getGenre(id: number) {
    this.gameService.getGenreById(id)
      .pipe(finalize(() => {
        this.initFormUpdated();
        this.ifInit = true;
      }))
      .subscribe(
        data => {
          if (data.status === 200) {
            this.updateGenre = data.result;
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
