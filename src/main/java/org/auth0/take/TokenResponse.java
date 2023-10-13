package org.auth0.take;

public class TokenResponse {
    private int tokens;

    public TokenResponse(int tokens) {
        this.tokens = tokens;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }
}
