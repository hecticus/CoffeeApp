import { ProofModule } from './proof.module';

describe('ProofModule', () => {
  let proofModule: ProofModule;

  beforeEach(() => {
    proofModule = new ProofModule();
  });

  it('should create an instance', () => {
    expect(proofModule).toBeTruthy();
  });
});
