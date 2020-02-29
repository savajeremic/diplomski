import { UserRole } from './user-role';

export class User {
    id: number;
    username: string;
    password: string;
    email: string;
    fullname: string;
    birthday: string;
    country: string;
    avatar: any;
    userRole: UserRole;
    token?: string;
}
