import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GameCarouselComponent } from './game-carousel.component';

describe('GameCarouselComponent', () => {
  let component: GameCarouselComponent;
  let fixture: ComponentFixture<GameCarouselComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GameCarouselComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GameCarouselComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
