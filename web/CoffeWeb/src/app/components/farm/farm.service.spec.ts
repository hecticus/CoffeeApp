import { TestBed, inject } from '@angular/core/testing';

import { FarmService } from './farm.service';

describe('FarmService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FarmService]
    });
  });

  it('should be created', inject([FarmService], (service: FarmService) => {
    expect(service).toBeTruthy();
  }));
});
