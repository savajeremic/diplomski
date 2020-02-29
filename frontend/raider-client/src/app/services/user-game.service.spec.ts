import { TestBed } from '@angular/core/testing';

import { UserGameService } from './user-game.service';

describe('UserGameService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UserGameService = TestBed.get(UserGameService);
    expect(service).toBeTruthy();
  });
});
