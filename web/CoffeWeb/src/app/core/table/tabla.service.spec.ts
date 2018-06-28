import { TestBed, inject } from '@angular/core/testing';

import { TablaService } from './tabla.service';

describe('TablaService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TablaService]
    });
  });

  it('should be created', inject([TablaService], (service: TablaService) => {
    expect(service).toBeTruthy();
  }));
});
