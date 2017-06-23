import { CoffeWebPage } from './app.po';

describe('coffe-web App', function() {
  let page: CoffeWebPage;

  beforeEach(() => {
    page = new CoffeWebPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
