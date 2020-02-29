import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GenreAddComponent } from './genre-add.component';

describe('GenreAddComponent', () => {
  let component: GenreAddComponent;
  let fixture: ComponentFixture<GenreAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GenreAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GenreAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
