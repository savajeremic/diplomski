import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LanguageAddComponent } from './language-add.component';

describe('LanguageAddComponent', () => {
  let component: LanguageAddComponent;
  let fixture: ComponentFixture<LanguageAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LanguageAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LanguageAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
