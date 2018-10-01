export class AccessTokenResponse {
	access_token: string;
	token_type: string;
	expires_in: number;
	refresh_token: string;
	scope: string; // optional
	state: string; // optional
	user_id: number;
}