import { TestBed, inject } from '@angular/core/testing';

import { LotService } from './lot.service';

describe('LotService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LotService]
    });
  });

  it('should be created', inject([LotService], (service: LotService) => {
    expect(service).toBeTruthy();
  }));
});
