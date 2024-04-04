import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private readonly TOKEN_KEY = 'TOKEN_KEY';
  private readonly USER_ID_KEY = 'USER_ID_KEY';

  constructor(@Inject(PLATFORM_ID) private platformId: Object) { }

  storeTokenAndUserId(token: string, userId: string) {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem(this.TOKEN_KEY, token);
      localStorage.setItem(this.USER_ID_KEY, userId);
    }
  }

  storeToken(token: string) {
    // localStorage.setItem(this.TOKEN_KEY, token);
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem(this.TOKEN_KEY, token);
    }
  }

  storeUserId(userId: string) {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem(this.USER_ID_KEY, userId);
    }
    // localStorage.setItem(this.USER_ID_KEY, userId);
  }

  getToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem(this.TOKEN_KEY);
    }
    return null;
    // return localStorage.getItem(this.TOKEN_KEY);
  }

  getUserId(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem(this.USER_ID_KEY);
    }
    return null;
    // return localStorage.getItem(this.TOKEN_KEY);
  }

  clearTokenAndUserId() {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem(this.TOKEN_KEY);
      localStorage.removeItem(this.USER_ID_KEY);
    }
  }
}
