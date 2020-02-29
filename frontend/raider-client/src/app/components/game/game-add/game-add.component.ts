import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Game } from '@/models/game';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { GameService } from '@/services/game.service';
import { ActivatedRoute, Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { GameDetails } from '@/models/game-details';
import { AdminService } from '@/services/admin.service';
import { AlertService } from '@/services/alert.service';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-game-add',
  templateUrl: './game-add.component.html',
  styleUrls: ['./game-add.component.scss']
})
export class GameAddComponent implements OnInit {

  addForm: FormGroup;
  @Output() newGame: EventEmitter<Game>;
  updateGame: Game = new Game();
  selectedGenres: GameDetails[] = [];
  selectedCompanies: GameDetails[] = [];
  selectedLanguages: GameDetails[] = [];
  isSubmitted = false;
  loading = false;
  genres: GameDetails[] = [];
  companies: GameDetails[];
  languages: GameDetails[];
  dropdownSettings: IDropdownSettings = {};
  ifInit: boolean = false;

  constructor(private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private gameService: GameService,
    private adminService: AdminService,
    private alertService: AlertService) {
    this.newGame = new EventEmitter();
  }

  ngOnInit() {
    this.loadGenres();
    this.loadCompanies();
    this.loadLanguages();
    this.route.params.subscribe(params => {
      let id = +params['id'];
      if (id) {
        this.getGame(id);
      } else {
        this.initForm();
        this.ifInit = true;
      }
    });
    this.initDropdownSettings();
  }

  getGame(id: number) {
    this.gameService.getGameById(id)
      .pipe(finalize(() => {
        this.updateGame.genres.forEach(genre => {
          this.selectedGenres.push(genre);
        });
        this.updateGame.companies.forEach(company => {
          this.selectedCompanies.push(company);
        });
        this.updateGame.languages.forEach(language => {
          this.selectedLanguages.push(language);
        });
        this.initFormUpdated();
        this.ifInit = true;
      }))
      .subscribe(
        data => {
          this.updateGame = data.result;
        }
      );
  }

  initForm() {
    this.addForm = this.formBuilder.group({
      id: [],
      name: ['', Validators.required],
      description: ['', Validators.required],
      coverImage: ['assets/images/games/0.jpg', Validators.required],
      version: [0],
      releaseDate: ['', Validators.required],
      size: [0],
      price: [0],
      rating: [{ value: 0, disabled: true }],
      votes: [{ value: 0, disabled: true }],
    });
  }

  initFormUpdated() {
    this.addForm = this.formBuilder.group({
      id: [],
      name: [this.updateGame.name, Validators.required],
      description: [this.updateGame.description, Validators.required],
      coverImage: [this.updateGame.coverImage, Validators.required],
      version: [this.updateGame.version],
      releaseDate: [this.updateGame.releaseDate, Validators.required],
      size: [this.updateGame.size],
      price: [this.updateGame.price],
      rating: [{ value: this.updateGame.rating, disabled: true }],
      votes: [{ value: this.updateGame.votes, disabled: true }],
    });
  }

  get formControls() { return this.addForm.controls; }

  loadGenres() {
    this.gameService.getGenres().subscribe(
      data => {
        this.genres = data.result;
      }
    );
  }

  loadCompanies() {
    this.gameService.getCompanies().subscribe(
      data => {
        this.companies = data.result;
      }
    );
  }

  loadLanguages() {
    this.gameService.getLanguages().subscribe(
      data => {
        this.languages = data.result;
      }
    );
  }

  initDropdownSettings() {
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'id',
      textField: 'name',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      allowSearchFilter: true
    };
  }

  onGenreSelect(item: GameDetails) {
    this.selectedGenres.push(item);
  }
  onSelectAllGenres(items: GameDetails[]) {
    this.selectedGenres = items;
  }
  onGenreDeSelect(item: GameDetails) {
    this.removeElementFromCheckbox(this.selectedGenres, item);
  }
  onDeSelectAllGenres() {
    this.selectedGenres = [];
  }

  onCompanySelect(item: GameDetails) {
    this.selectedCompanies.push(item);
  }
  onSelectAllCompanies(items: GameDetails[]) {
    this.selectedCompanies = items
  }
  onCompanyDeSelect(item: GameDetails) {
    this.removeElementFromCheckbox(this.selectedCompanies, item);
  }
  onDeSelectAllCompanies() {
    this.selectedCompanies = [];
  }

  onLanguageSelect(item: GameDetails) {
    this.selectedLanguages.push(item);
  }
  onSelectAllLanguages(items: GameDetails[]) {
    this.selectedLanguages = items;
  }
  onLanguageDeSelect(item: GameDetails) {
    this.removeElementFromCheckbox(this.selectedLanguages, item);
  }
  onDeSelectAllLanguages() {
    this.selectedLanguages = [];
  }

  removeElementFromCheckbox(list: GameDetails[], item: GameDetails) {
    return list.forEach((detail, index) => {
      if (detail.id === item.id) {
        list.splice(index, 1);
      }
    });
  }

  addGame() {
    this.isSubmitted = true;
    this.loading = true;
    this.alertService.clear();
    if (this.addForm.invalid) {
      this.loading = false;
      return;
    }
    let id: number = this.updateGame.id;
    let controlsName = this.addForm.controls.name.value;
    let controlsDescription = this.addForm.controls.description.value;
    let controlsCoverImage = this.addForm.controls.coverImage.value;
    let controlsVersion = this.addForm.controls.version.value;
    let controlsRating = this.addForm.controls.rating.value;
    let controlsVotes = this.addForm.controls.votes.value;
    let controlsReleaseDate = this.addForm.controls.releaseDate.value;
    let controlsSize = this.addForm.controls.size.value;
    let controlsPrice = this.addForm.controls.price.value;
    const game = {
      id: id ? id : null,
      name: controlsName != "" ? controlsName : this.updateGame.name,
      description: controlsDescription != "" ? controlsDescription : this.updateGame.description,
      coverImage: controlsCoverImage != "" ? controlsCoverImage : this.updateGame.coverImage,
      version: controlsVersion != "" ? controlsVersion : this.updateGame.version,
      rating: controlsRating != "" ? controlsRating : this.updateGame.rating,
      votes: controlsVotes != "" ? controlsVotes : this.updateGame.votes,
      releaseDate: controlsReleaseDate != "" ? controlsReleaseDate : this.updateGame.releaseDate,
      size: controlsSize != "" ? controlsSize : this.updateGame.size,
      price: controlsPrice != "" ? controlsPrice : this.updateGame.price,
      genres: Array.from(this.selectedGenres),
      companies: Array.from(this.selectedCompanies),
      languages: Array.from(this.selectedLanguages),
    }
    this.adminService.createGame(game).subscribe(
      data => {
        if (data.status === 200) {
          this.alertService.success('Game added successfully!', true);
          this.resetValues();
          this.newGame.emit(data.result);
          this.router.navigate(['/store']);
        } else {
          this.alertService.error(data.message);
        }
        this.loading = false;
      },
      error => {
        this.alertService.error(error);
        this.loading = false;
      }
    );

  }

  resetValues() {
    this.addForm.setValue({
      id: "",
      name: "",
      description: "",
      coverImage: "",
      version: "",
      rating: "",
      votes: "",
      releaseDate: "",
      size: "",
      price: ""
    });
    this.selectedGenres = [],
      this.selectedCompanies = [],
      this.selectedLanguages = [],
      this.isSubmitted = false;
    this.addForm.reset();
  }

  handleUpload(fileName){
    console.log(fileName.target.value);
    //C:\fakepath\16.png"
    let coverImagePath: string = fileName.target.value.substring(12);
    this.formControls.coverImage.setValue("assets/images/games/" + coverImagePath);
    console.log(this.formControls.coverImage.value);
    
  }
}
