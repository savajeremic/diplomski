export class UserGame {
    userId: number;
    gameId: number;
    gameFlagId: number;

    constructor(userId: number, gameId: number, gameFlagId: number) {
        this.userId = userId;
        this.gameId = gameId;
        this.gameFlagId = gameFlagId;
    }
}
