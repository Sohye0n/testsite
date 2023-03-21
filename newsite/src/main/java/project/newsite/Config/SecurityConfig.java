package project.newsite.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.newsite.Config.Oauth.PrincipalOauth2UserService;
import project.newsite.ExceptionHandler.LoginExceptionHandler;
import project.newsite.Filter.LoginLimitFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public LoginExceptionHandler loginExceptionHandler(){
        return new LoginExceptionHandler();
    }

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer configure(){
        return (web)->web.ignoring().requestMatchers(
                        "/h2-console/**"
                        ,"/favicon.ico"
                        ,"/error"
                );
    }

    @Bean
    public LoginLimitFilter loginLimitFilter(){
        return new LoginLimitFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .csrf().disable()

            //.addFilterBefore(loginLimitFilter(), UsernamePasswordAuthenticationFilter.class)

            .authorizeHttpRequests()
            .requestMatchers("/home").permitAll()
            .requestMatchers("/board").hasAnyRole("USER")
            .requestMatchers("/mypage").hasAnyRole("USER")
            .anyRequest().permitAll()

            .and()
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/LoginForm") //form url
            .failureHandler(loginExceptionHandler())

            .and()
            .oauth2Login()
            .loginPage("/LoginForm")
            .userInfoEndpoint()
            .userService(principalOauth2UserService);

        return http.build();
    }
}
