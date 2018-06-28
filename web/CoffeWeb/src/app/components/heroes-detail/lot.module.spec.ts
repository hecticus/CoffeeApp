import { LotModule } from '../lot.module';

describe('LotModule', () => {
  let lotModule: LotModule;

  beforeEach(() => {
    lotModule = new LotModule();
  });

  it('should create an instance', () => {
    expect(lotModule).toBeTruthy();
  });
});
