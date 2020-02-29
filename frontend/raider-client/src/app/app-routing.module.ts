import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/user/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { UserListComponent } from './components/user/user-list/user-list.component';
import { UserAddComponent } from './components/user/user-add/user-add.component';
import { GameListComponent } from './components/game/game-list/game-list.component';
import { UserComponent } from './components/user/user.component';
import { GameComponent } from './components/game/game.component';
import { AuthGuard } from './guards/auth.guard';
import { GameAddComponent } from './components/game/game-add/game-add.component';
import { GenreAddComponent } from './components/genre/genre-add/genre-add.component';
import { GenreComponent } from './components/genre/genre.component';
import { LanguageComponent } from './components/language/language.component';
import { LanguageAddComponent } from './components/language/language-add/language-add.component';
import { CompanyComponent } from './components/company/company.component';
import { CompanyAddComponent } from './components/company/company-add/company-add.component';
import { CheckoutComponent } from './components/cart/checkout/checkout.component';
import { UserGamesComponent } from './components/cart/user-games/user-games.component';
import { WishlistComponent } from './components/cart/wishlist/wishlist.component';
import { OrdersComponent } from './components/cart/orders/orders.component';
import { AboutComponent } from './pages/about/about.component';
import { ContactComponent } from './pages/contact/contact.component';

const routes: Routes = [
  {
    path: 'admin', canActivate: [AuthGuard], data: { roles: ['admin'] },
    children:
      [
        { path: 'users', component: UserListComponent },
        { path: 'add-user', component: UserAddComponent },
        { path: 'edit-user/:id', component: UserComponent },
        {
          path: 'game', component: GameListComponent,
          children:
            [
              { path: 'add-game', component: GameAddComponent },
            ]
        },
        {
          path: 'genre', component: GenreComponent,
          children:
            [
              { path: 'add-genre', component: GenreAddComponent },
            ]
        },
        {
          path: 'company', component: CompanyComponent,
          children:
            [
              { path: 'add-company', component: CompanyAddComponent },
            ]
        },
        {
          path: 'language', component: LanguageComponent,
          children:
            [
              { path: 'add-language', component: LanguageAddComponent },
            ]
        },
        { path: 'update-game/:id', component: GameAddComponent },
        { path: 'update-genre/:id', component: GenreAddComponent },
        { path: 'update-company/:id', component: CompanyAddComponent },
        { path: 'update-language/:id', component: LanguageAddComponent },
      ]
  },
  {
    path: 'user', canActivate: [AuthGuard], data: { roles: ['user'] },
    children:
      [
        { path: 'profile/:id', component: UserComponent },
        { path: 'checkout', component: CheckoutComponent },
        { path: 'orders', component: OrdersComponent },
        { path: 'usergames', component: UserGamesComponent },
        { path: 'wishlist', component: WishlistComponent },
      ]
  },
  { path: 'store', component: GameListComponent },
  { path: 'store/:id', component: GameComponent },
  { path: 'about', component: AboutComponent },
  { path: 'contact', component: ContactComponent },
  { path: '', component: HomeComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
