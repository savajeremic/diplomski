import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ImageCropperModule } from 'ngx-image-cropper';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { UserComponent } from './components/user/user.component';
import { LoginComponent } from './components/user/login/login.component';
import { TokenInterceptor } from './helpers/token.interceptor';
import { ErrorInterceptor } from './helpers/error.interceptor';
import { HomeComponent } from './pages/home/home.component';
import { HeaderComponent } from './pages/header/header.component';
import { FooterComponent } from './pages/footer/footer.component';
import { GameComponent } from './components/game/game.component';
import { GameListComponent } from './components/game/game-list/game-list.component';
import { UserListComponent } from './components/user/user-list/user-list.component';
import { UserAddComponent } from './components/user/user-add/user-add.component';
import { UserProfileComponent } from './components/user/user-profile/user-profile.component';
import { GameCarouselComponent } from './pages/home/game-carousel/game-carousel.component';
import { GameShowcaseComponent } from './pages/home/game-showcase/game-showcase.component';
import { UserUpdateComponent } from './components/user/user-update/user-update.component';
import { RegisterComponent } from './components/user/register/register.component';
import { AlertComponent } from './components/alert/alert.component';

import { GameAddComponent } from './components/game/game-add/game-add.component';
import { GenreComponent } from './components/genre/genre.component';
import { GenreAddComponent } from './components/genre/genre-add/genre-add.component';
import { CompanyComponent } from './components/company/company.component';
import { CompanyAddComponent } from './components/company/company-add/company-add.component';
import { LanguageComponent } from './components/language/language.component';
import { LanguageAddComponent } from './components/language/language-add/language-add.component';
import { SearchPipe } from './helpers/search.pipe';
import { SearchComponent } from './pages/search/search.component';
import { CartComponent } from './components/cart/cart.component';
import { CheckoutComponent } from './components/cart/checkout/checkout.component';
import { UserGamesComponent } from './components/cart/user-games/user-games.component';
import { WishlistComponent } from './components/cart/wishlist/wishlist.component';
import { OrdersComponent } from './components/cart/orders/orders.component';
import { GameGridViewComponent } from './components/game/game-grid-view/game-grid-view.component';
import { UserPasswordComponent } from './components/user/user-password/user-password.component';
import { MustMatchDirective } from './helpers/must-match.directive';
import { AboutComponent } from './pages/about/about.component';
import { ContactComponent } from './pages/contact/contact.component';
import { GameReviewAddComponent } from './components/game/game-review-add/game-review-add.component';


@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    UserProfileComponent,
    UserAddComponent,
    UserListComponent,
    LoginComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    GameComponent,
    GameListComponent,
    UserListComponent,
    UserAddComponent,
    UserProfileComponent,
    GameCarouselComponent,
    GameShowcaseComponent,
    UserUpdateComponent,
    RegisterComponent,
    AlertComponent,
    GameAddComponent,
    GenreComponent,
    GenreAddComponent,
    CompanyComponent,
    CompanyAddComponent,
    LanguageComponent,
    LanguageAddComponent,
    SearchPipe,
    SearchComponent,
    CartComponent,
    CheckoutComponent,
    UserGamesComponent,
    WishlistComponent,
    OrdersComponent,
    GameGridViewComponent,
    UserPasswordComponent,
    MustMatchDirective,
    AboutComponent,
    ContactComponent,
    GameReviewAddComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    ImageCropperModule,
    NgMultiSelectDropDownModule,
    BrowserAnimationsModule,
    NgbModule,
    NgxPaginationModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
