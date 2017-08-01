import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitPanelComponent } from './unit-panel.component';

describe('UnitPanelComponent', () => {
  let component: UnitPanelComponent;
  let fixture: ComponentFixture<UnitPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnitPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnitPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
