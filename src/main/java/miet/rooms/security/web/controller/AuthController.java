package miet.rooms.security.web.controller;

import miet.rooms.security.web.exception.AuthenticationException;
import miet.rooms.security.util.JwtTokenUtil;
import miet.rooms.security.service.JdbcUserDetailsService;
import miet.rooms.security.service.TokenService;
import miet.rooms.security.web.request.Credentials;
import miet.rooms.security.web.response.JwtTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JdbcUserDetailsService userDetailsService;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JdbcUserDetailsService userDetailsService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Credentials credentials)
            throws AuthenticationException {

        authenticate(credentials.getUsername(), credentials.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails);

        tokenService.saveToken(token, credentials);

        return ResponseEntity.ok(new JwtTokenResponse(token));
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "").trim();

        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtTokenResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "").trim();
        tokenService.removeToken(token);
        return ResponseEntity.ok("");
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("INVALID_CREDENTIALS", e);
        }
    }
}

