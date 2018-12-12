import { TestBed, inject } from '@angular/core/testing';

import { PurityService } from './purity.service';

describe('PurityService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PurityService]
    });
  });

  it('should be created', inject([PurityService], (service: PurityService) => {
    expect(service).toBeTruthy();
  }));
});
