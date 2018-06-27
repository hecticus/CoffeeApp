import { Injectable } from '@angular/core';
import { Hero } from './heroes/hero';
import { Heroes } from './heroes/mock-heroes';
import { Observable, of } from 'rxjs';
import { MessageService } from './messages/message.service';

@Injectable({
  providedIn: 'root'
})
export class HeroService {

  constructor(
    private messageService: MessageService
  ) { }

  getHeroes(): Observable<Hero[]> {
    // TODO: send the message _after_ fetching the heroes
    this.messageService.add('HeroService: fetched heroes');
    return of(Heroes);
  }
}
