package com.example.cloudsky.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.cloudsky.dto.LoginRequestDto;
import com.example.cloudsky.entity.UserRoleEnum;
import com.example.cloudsky.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/dev/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(username, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
    }

    public void deleteAuthentication(HttpServletResponse response, Authentication authResult) throws IOException, ServletException {
        log.info("로그아웃 시도");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(username);
        jwtUtil.deleteCookie(token, response);
        response.sendRedirect("/dev/user/login-page"); // "/"로 리다이렉트
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
        // 협업할 때, 클라이언트 쪽에 추가적이 메세지 라던가 데이터를 넘겨주세요 라고 한다면
        // 여기서 status만 설정하는 것이 아니라
        // response 객체, content-type 그리고 메세지 등을 담아서 보낼 수도 있음
    }

    public void deleteAuthentication(HttpServletResponse response, Authentication authResult) throws IOException, ServletException {
        log.info("로그아웃 시도");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(username, role);
        jwtUtil.deleteCookie(token, response);
        response.sendRedirect("/dev/user/login-page"); // "/"로 리다이렉트
    }
}
// 여기는 보시는 대로 로그인 한 유저의 토큰을 생성하는 파일입니다.