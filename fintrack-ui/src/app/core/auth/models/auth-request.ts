export interface AuthRequest {
    email: string;
    password: string;
}


export interface AuthResponse {
    token: string;
}

export interface SignUpRequest {
    email: string;
    password: string;
    role: string;
}