import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FarmServicesComponent } from './farm-services.component';

describe('FarmServicesComponent', () => {
  let component: FarmServicesComponent;
  let fixture: ComponentFixture<FarmServicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FarmServicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FarmServicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
