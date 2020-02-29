import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GameGridViewComponent } from './game-grid-view.component';

describe('GameGridViewComponent', () => {
  let component: GameGridViewComponent;
  let fixture: ComponentFixture<GameGridViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GameGridViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GameGridViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
