package kopo.poly.jwt;

import io.jsonwebtoken.*;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwtw.token.creator}")
    private String creator;

    @Value("${jwt.token.access.valid.time}")
    private long accessTokenValidTime;

    @Value("${jwt.token.access.name}")
    private String accessTokenName;

    @Value("${jwt.token.refresh.valid.time}")
    private long refreshTokenValidTime;

    @Value("${jwt.token.refresh.name}")
    private String refreshTokenName;

    /**
     * JWT 토큰(Access Token)생성
     *
     * @param userId    회원 아이디(ex. hglee67)
     * @param roles     회원 권한
     * @param tokenType token 유형
     * @return 인증 처리한 정보(로그인 성공, 실패)
     */
    public String createToken(String userId, String roles, JwtTokenType tokenType) {

        log.info(this.getClass().getName() + ".createToken Start!");

        log.info("userId : " + userId);

        Claims claims = Jwts.claims()
                .setIssuer(creator) // JWT 토큰 생성자 기입함
                .setSubject(userId); // 회원아이디 저장 : PK 저장(userId)

        claims.put("roles", roles); // JWT Paylaod에 정의된 기본 옵션 외 정보를 추가 - 사용자 권한 추가
        Date now = new Date();

        log.info(this.getClass().getName() + ".createToken End!");

        // Builder를 통해 토큰 생성
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + (accessTokenValidTime * 1000))) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                .compact();
    }

    /**
     * JWT 토큰(Access Token, Refresh Token)에서 인증 정보 조회 및
     * 아이디, 패스워드가 맞다면, Spring Security를 통해 로그인처리하기
     *
     * @param token 토큰
     * @return 인증 처리한 정보(로그인 성공, 실패)
     */
    public Authentication getAuthentication(String token) {

        log.info(this.getClass().getName() + ".getAuthentication Start!");
        log.info("getAuthentication : " + token);

        // JWT 토큰에 저장된 사용자 아이디 : hglee67
        String userId = CmmUtil.nvl(this.getUserId(token));
        log.info("user_id : " + userId);

        // JWT 토큰에 저장된 사용자 아이디 : ROLE_USER
        String roles = CmmUtil.nvl(this.getUserRoles(token));
        log.info("roles : " + roles);

        Set<GrantedAuthority> pSet = new HashSet<>();
        if (roles.length() > 0) { //DB에 저장된 Role이 있는 경우에만 실행
            for (String role : roles.split(",")) {
                pSet.add(new SimpleGrantedAuthority(role));

            }
        }

        log.info(this.getClass().getName() + ".getAuthentication End!");

        // Spring Security가 로그인 성공된 정보를 Spring Security에서 사용하기 위해
        // Spring Security용 UsernamePasswordAuthenticationToken 생성
        return new UsernamePasswordAuthenticationToken(userId, "", pSet);
    }

    /**
     * JWT 토큰(Access Token, Refresh Token)에서 회원 정보 추출
     *
     * @param token 토큰
     * @return 회원 아이디(ex. hglee67)
     */
    public String getUserId(String token) {

        log.info(this.getClass().getName() + ".getUserId Start!");

        String userId = CmmUtil.nvl(Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject());
        log.info("userId : " + userId);

        log.info(this.getClass().getName() + ".getUserId End!");

        return userId;
    }

    /**
     * JWT 토큰(Access Token, Refresh Token)에서 회원 정보 추출
     *
     * @param token 토큰
     * @return 회원 아이디(ex. hglee67)
     */
    public String getUserRoles(String token) {

        log.info(this.getClass().getName() + ".getUserRoles Start!");
        String roles = CmmUtil.nvl((String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("roles"));

        log.info("roles : " + roles);

        log.info(this.getClass().getName() + ".getUserRoles End!");

        return roles;
    }

    /**
     * 쿠기에 저장된 JWT 토큰(Access Token, Refresh Token) 가져오기
     *
     * @param request   request 정보
     * @param tokenType token 유형
     * @return 쿠기에 저장된 토큰 값
     */
    public String resolveToken(ServerHttpRequest request, JwtTokenType tokenType) {

        log.info(this.getClass().getName() + ".resolveToken Start!");

        String token = ""; // 토큰
        String tokenName = "";

        if (tokenType == JwtTokenType.ACCESS_TOKEN) { // Access Token이라면
            tokenName = accessTokenName;

        } else if (tokenType == JwtTokenType.REFRESH_TOKEN) { // Refresh Token이라면
            tokenName = refreshTokenName;

        }

        HttpCookie cookie = request.getCookies().getFirst(tokenName);

        if (cookie != null) {

            token = CmmUtil.nvl(cookie.getValue());

        }

        log.info(this.getClass().getName() + ".resolveToken End!");

        return token;
    }

    /**
     * JWT 토큰(Access Token, Refresh Token) 상태 확인
     *
     * @param token 토큰
     * @return 상태정보(EXPIRED, ACCESS, DENIED)
     */
    public JwtStatus validateToken(String token) {

        if (token.length() > 0) {

            try {
                Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

                // 토큰 만료여부 체크
                if (claims.getBody().getExpiration().before(new Date())) {
                    return JwtStatus.EXPIRED; // 기간 만료

                } else {
                    return JwtStatus.ACCESS; // 유효한 토큰
                }

            } catch (ExpiredJwtException e) {
                // 만료된 경우에는 refresh token을 확인하기 위해
                return JwtStatus.EXPIRED; // 혹시 몰라서 Exception으로 한번 더 체크 기간 만료

            } catch (JwtException | IllegalArgumentException e) {
                log.info("jwtException : {}", e);

                return JwtStatus.DENIED;
            }

        } else {
            return JwtStatus.DENIED;
        }

    }
}
