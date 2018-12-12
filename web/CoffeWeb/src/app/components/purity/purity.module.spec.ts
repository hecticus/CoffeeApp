import { PurityModule } from './purity.module';

describe('PurityModule', () => {
  let purityModule: PurityModule;

  beforeEach(() => {
    purityModule = new PurityModule();
  });

  it('should create an instance', () => {
    expect(purityModule).toBeTruthy();
  });
});
