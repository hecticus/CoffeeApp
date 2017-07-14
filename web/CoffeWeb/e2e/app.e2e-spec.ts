import { CoffeeWebPage } from './app.po';

describe('coffee-web App', () => {
  let page: CoffeeWebPage;

  beforeEach(() => {
    page = new CoffeeWebPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
