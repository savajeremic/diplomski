import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { GameService } from '@/services/game.service';
import { Router } from '@angular/router';
import { Game } from '@/models/game';
import { AuthService } from '@/services/auth.service';
import { UserGameService } from '@/services/user-game.service';
import { AlertService } from '@/services/alert.service';
import { UserGame } from '@/models/user-game';
import { User } from '@/models/user';
import { GameDetails } from '@/models/game-details';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { AdminService } from '@/services/admin.service';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class GameListComponent implements OnInit {

  games: Game[] = [];
  tempGames: Game[] = [];
  cartGames: any[] = [];
  name: string;
  loading: boolean = true;
  isLoggedIn: boolean = false;
  currentUser: User;
  displayMode: number = 2;
  isCurrentRoute = true;
  p: number = 1;

  genres: GameDetails[] = [];
  companies: GameDetails[] = [];
  languages: GameDetails[] = [];
  prices: any[] = [];
  sortByItems: any[] = [];
  selectedGenres: GameDetails[] = [];
  selectedCompanies: GameDetails[] = [];
  selectedLanguages: GameDetails[] = [];
  selectedPrices: number;
  selectedSortByItems: number;
  selectedSortItem: any[] = [];
  dropdownSettings: IDropdownSettings = {};
  dropdownSettingsRadio: IDropdownSettings = {};

  constructor(private gameService: GameService,
    private authService: AuthService,
    private router: Router,
    private userGameService: UserGameService,
    private adminService: AdminService,
    private alertService: AlertService) {
    this.authService.getCurrentUserSubject.subscribe(value => this.currentUser = value);
    this.authService.isUserLoggedIn.subscribe(value => { this.isLoggedIn = value });
  }

  ngOnInit() {
    this.prices = [
      { value: 0, text: "Free" },
      { value: 5, text: "Under 5 \u20ac" },
      { value: 10, text: "Under 10 \u20ac" },
      { value: 15, text: "Under 15 \u20ac" },
      { value: 25, text: "Under 25 \u20ac" },
      { value: 26, text: "25+ \u20ac" }
    ];
    this.sortByItems = [
      { value: 1, text: "Best selling" },
      { value: 2, text: "Newest first" },
      { value: 3, text: "Oldest first" },
      { value: 4, text: "Alphabetically" },
      { value: 5, text: "Highest rated" },
    ];
    this.selectedSortItem.push(this.sortByItems[0]);
    if (this.currentUser) {
      this.isLoggedIn = true;
    }
    this.initDropdownSettings();
    this.getGames();
    this.loadGameDetails();
  }

  getGames() {
    if (!this.isLoggedIn) {
      this.gameService.getGames().subscribe(
        data => {
          this.games = data.result;
          this.tempGames = data.result;
          this.loading = false;
        }
      );
    }
    else {
      this.userGameService.getStoreGames(this.authService.getUserId()).subscribe(
        data => {
          this.games = data.result;
          this.tempGames = data.result;
          this.loading = false;
          let countUserGames: number[] = [0, 0, 0];
          this.games.forEach((game) => {
            if (game.isInCart) {
              countUserGames[0]++;
            }
            if (game.isInWishlist) {
              countUserGames[1]++;
            }
            if (game.isInOwned) {
              countUserGames[2]++;
            }
          });
        }
      );
    }
  }

  loadGameDetails() {
    this.gameService.getGenres().subscribe(
      data => {
        this.genres = data.result;
      }
    );

    this.gameService.getCompanies().subscribe(
      data => {
        this.companies = data.result;
      }
    );

    this.gameService.getLanguages().subscribe(
      data => {
        this.languages = data.result;
      }
    );
  }


  viewGame(id: number) {
    this.router.navigate(['/store', id]);
  }

  addGame(game: Game) {
    this.games.push(game);
  }

  editGame(id: number) {
    if(this.isAdmin())
      this.router.navigate(['/admin/update-game', id]);
  }

  deleteGame(id: number) {
    if(!this.isAdmin()) {
      return;
    }
    this.loading = true;
    this.adminService.deleteGame(id).subscribe(
      data => {
        if (data.status === 200) {
          this.games = this.games.filter(g => g.id !== id);
          this.alertService.success('Game deleted!', true);
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

  setRoute() {
    let temp = this.isCurrentRoute;
    this.isCurrentRoute = !this.isCurrentRoute;
    return temp;
  }

  isAdmin() {
    return this.authService.isAdmin();
  }

  addToCart(game: Game) {
    if (this.authService.getUserId() == 0) {
      return this.alertService.error("You have to be logged in to put games in cart.");
    }
    let cartGame: UserGame = new UserGame(this.authService.getUserId(), game.id, 1);
    this.userGameService.addToCart(cartGame).subscribe(data => {
      if (data.status === 200) {
        this.userGameService.addCart(game);
        this.games.find(g => {
          if (g.id === game.id) {
            game.isInCart = !game.isInCart;
          }
        });
        this.alertService.success('Added game to cart!', true);
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

  addToWishlist(game: Game) {
    this.loading = true;
    if (this.authService.getUserId() == 0) {
      return this.alertService.error("You have to be logged in to put games in wishlist.");
    }
    let gameId = game.id;
    let wishlistGame: UserGame = new UserGame(this.authService.getUserId(), game.id, 2);
    //this.userGameService.getCartGamesSubject.next([...this.userGameService.getUserGames, game]);
    this.userGameService.addToWishlist(wishlistGame).subscribe(data => {
      if (data.status === 200) {
        this.userGameService.addUserGame(wishlistGame);
        this.games.find(g => {
          if (g.id === gameId) {
            game.isInWishlist = !game.isInWishlist;
          }
        });
        this.alertService.success('Game wished!', true);
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

  removeFromWishlist(game: Game) {
    if (this.authService.getUserId() == 0) {
      return this.alertService.error("You have to be logged in to put games in wishlist.");
    }
    let gameId = game.id;
    let userGame: UserGame = new UserGame(this.authService.getUserId(), gameId, 2);

    this.userGameService.removeFromWishlist(userGame)
      .subscribe(
        data => {
          if (data.status === 200) {
            this.userGameService.removeUserGame(userGame);
            this.games.find(game => {
              if (game.id === gameId) {
                game.isInWishlist = !game.isInWishlist;
              }
            });
            this.alertService.success("Game unwished. ", true);
          } else {
            this.alertService.error(data.message);
          }
        }, error => {
          this.alertService.error(error);
        }
      );
    this.loading = false;
  }

  checkout() {
    this.router.navigate(['user/checkout']);
  }

  toggleItemsView(mode: number) {
    this.displayMode = mode;
    this.p = 1;
  }



  initDropdownSettings() {
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'id',
      textField: 'name',
      enableCheckAll: false,
      allowSearchFilter: true
    };

    this.dropdownSettingsRadio = {
      singleSelection: true,
      enableCheckAll: false,
      allowSearchFilter: false,
      idField: 'value',
      textField: 'text',
      closeDropDownOnSelection: true
    }
  }

  filterGames() {
    this.loading = true;
    let temp: Game[] = this.tempGames;
    let i: number = 0;
    if (this.selectedGenres.length > 0) {
      temp = temp.filter((game: Game) => {
        i = 0;
        return game.genres.find((genre) => {
          return this.selectedGenres.find((selected) => {
            if (selected.name === genre.name) {
              i++;
            }
            if (i == this.selectedGenres.length) {
              return selected.name === genre.name;
            }
          })
        })
      });
    }
    i = 0;
    if (this.selectedCompanies.length > 0) {
      temp = temp.filter((game: Game) => {
        i = 0;
        return game.companies.find((company) => {
          return this.selectedCompanies.find((selected) => {
            if (selected.name === company.name) {
              i++;
            }
            if (i == this.selectedCompanies.length) {
              return selected.name === company.name;
            }
          });
        });
      });
    }
    i = 0;
    if (this.selectedLanguages.length > 0) {
      temp = temp.filter((game: Game) => {
        i = 0;
        return game.languages.find((language) => {
          return this.selectedLanguages.find((selected) => {
            if (selected.name === language.name) {
              i++;
            }
            if (i == this.selectedLanguages.length) {
              return selected.name === language.name;
            }
          });
        });
      });
    }
    if (this.selectedPrices > -1) {
      if (this.selectedPrices === 26) {
        temp = temp.filter((game: Game) => game.price > this.selectedPrices);
      }
      else {
        temp = temp.filter((game: Game) => game.price <= this.selectedPrices);
      }
      temp.sort((a, b) => b.price - a.price);
    }
    this.games = temp;
    this.loading = false;
  }

  onGenreSelect(item: GameDetails) {
    this.selectedGenres.push(item);
    this.filterGames();
  }
  onGenreDeSelect(item: GameDetails) {
    this.removeElementFromCheckbox(this.selectedGenres, item);
    this.filterGames();
  }

  onCompanySelect(item: GameDetails) {
    this.selectedCompanies.push(item);
    this.filterGames();
  }
  onCompanyDeSelect(item: GameDetails) {
    this.removeElementFromCheckbox(this.selectedCompanies, item);
    this.filterGames();
  }

  onLanguageSelect(item: GameDetails) {
    this.selectedLanguages.push(item);
    this.filterGames();
  }
  onLanguageDeSelect(item: GameDetails) {
    this.removeElementFromCheckbox(this.selectedLanguages, item);
    this.filterGames();
  }

  onPriceSelect(item: any) {
    this.selectedPrices = item.value;
    this.filterGames();
  }
  onPriceDeSelect(item: any) {
    this.selectedPrices = -1;
    this.filterGames();
  }

  onSortByItemSelect(item: any) {
    this.selectedSortByItems = item.value;
    this.loading = true;
    switch (this.selectedSortByItems) {
      case 1:
        this.getGames();
        break;
      case 2:
        this.filterGamesByDate(false);
        break;
      case 3:
        this.filterGamesByDate(true);
        break;
      case 4:
        this.filterGamesAlphabetically();
        break;
      case 5:
        this.filterGamesByRating();
        break;
      default:
        this.getGames();
        break;
    }
    this.loading = false;
  }
  onSortByItemDeSelect(item: any) {
    this.selectedSortByItems = -1;
    //this.filterGames();
  }

  removeElementFromCheckbox(list: GameDetails[], item: GameDetails) {
    return list.forEach((detail, index) => {
      if (detail.id === item.id) {
        list.splice(index, 1);
      }
    });
  }

  filterGamesByDate(isAscending: boolean) {
    this.userGameService.filterStoreByDate(isAscending, this.authService.getUserId())
      .subscribe(
        data => {
          this.games = data.result;
          this.tempGames = data.result;
          console.log(this.games);
        }
      );
  }

  filterGamesAlphabetically() {
    this.userGameService.filterStoreAlphabetically(this.authService.getUserId())
      .subscribe(
        data => {
          this.games = data.result;
          this.tempGames = data.result;
          console.log(this.games);
        }
      );
  }

  filterGamesByRating() {
    this.userGameService.filterStoreRating(this.authService.getUserId())
      .subscribe(
        data => {
          this.games = data.result;
          this.tempGames = data.result;
          console.log(this.games);
        }
      );
  }
}
