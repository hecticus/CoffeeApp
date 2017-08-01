import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitAllComponent } from './unit-all.component';

describe('UnitAllComponent', () => {
  let component: UnitAllComponent;
  let fixture: ComponentFixture<UnitAllComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnitAllComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnitAllComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
