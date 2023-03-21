package project.newsite.Config.Oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import project.newsite.Auth.AuthToken;
import project.newsite.DTO.User;
import project.newsite.Repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;

    //구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    //구글 로그인 버튼 클릭->구글 로그인창->로그인 완료->code를 리턴(OAuth-Client라이브러리)->AccessToken 요청
    //위 과정이 userRequest 정보
    //userRequest 정보에서 loadUser 함수 호출 -> 회원 프로필 정보 가져옴
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthorizationException {
        System.out.println("userRequest:"+userRequest);
        System.out.println("getClientRegistration:"+userRequest.getClientRegistration());
        System.out.println("getAccessToken:"+userRequest.getAccessToken());
        System.out.println("getAttributes:"+super.loadUser(userRequest).getAttributes());

        OAuth2User oAuth2User=super.loadUser(userRequest);
        String provider=userRequest.getClientRegistration().getClientId(); //google
        String providerId=oAuth2User.getAttribute("sub");
        String username=provider+"_"+providerId;
        String password= bCryptPasswordEncoder.encode("겟인데어");
        String email= oAuth2User.getAttribute("email");
        String role="ROLE_USER";

        User user=userRepository.findByUsername(username);
        if(user==null){
            user=User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(user);
        }

        //what is loaduser
        //return super.loadUser(userRequest);
        return new AuthToken(user,oAuth2User.getAttributes());
    }
}