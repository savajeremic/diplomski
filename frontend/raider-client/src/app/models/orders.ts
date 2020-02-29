import { Game } from './game';

export class Orders {
    id: number;
    date: string;
    total: number;
    code: string;
    games: Game[];

    constructor(id: number, date: string, total: number, code: string, games: Game[]) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.code = code;
        this.games = games;
    }
}
