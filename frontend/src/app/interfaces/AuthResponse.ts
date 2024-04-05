export interface AuthResponse {
    token: string;
    errors: (null | string)[];
    userId: string;
}