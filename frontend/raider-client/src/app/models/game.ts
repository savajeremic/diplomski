import { GameDetails } from './game-details';

export class Game {
    id: number;
    name: string;
    description: string;
    coverImage: string;
    version: string;
    rating: number;
    votes: number;
    releaseDate: Date;
    size: number;
    price: number;
    genres: GameDetails[];
    companies: GameDetails[];
    languages: GameDetails[];
    isInCart?: boolean;
    isInWishlist?: boolean;
    isInOwned?: boolean;
}
