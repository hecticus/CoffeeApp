import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProviderTypeComponent } from './provider-type.component';

describe('ProviderTypeComponent', () => {
  let component: ProviderTypeComponent;
  let fixture: ComponentFixture<ProviderTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProviderTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProviderTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
