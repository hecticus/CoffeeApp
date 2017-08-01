import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MdMenuComponent } from './md-menu.component';

describe('MdMenuComponent', () => {
  let component: MdMenuComponent;
  let fixture: ComponentFixture<MdMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MdMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MdMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
