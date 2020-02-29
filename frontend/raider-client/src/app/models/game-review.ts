import { User } from './user';

export class GameReview {
    id: number;
    rating: number;
    comment: string;
    title: string;
    gameId: number;
    user: User;
    reviewDate: Date;
}
