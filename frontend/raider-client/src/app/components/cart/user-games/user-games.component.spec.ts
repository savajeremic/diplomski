import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserGamesComponent } from './user-games.component';

describe('UserGamesComponent', () => {
  let component: UserGamesComponent;
  let fixture: ComponentFixture<UserGamesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserGamesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserGamesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
