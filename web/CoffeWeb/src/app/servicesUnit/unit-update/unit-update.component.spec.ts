import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitUpdateComponent } from './unit-update.component';

describe('UnitUpdateComponent', () => {
  let component: UnitUpdateComponent;
  let fixture: ComponentFixture<UnitUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnitUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnitUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
