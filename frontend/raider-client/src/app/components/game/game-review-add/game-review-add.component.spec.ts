import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GameReviewAddComponent } from './game-review-add.component';

describe('GameReviewAddComponent', () => {
  let component: GameReviewAddComponent;
  let fixture: ComponentFixture<GameReviewAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GameReviewAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GameReviewAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
