package K21.HAIDUONG.service.impl;

import K21.HAIDUONG.dto.request.AuthenticationRequest;
import K21.HAIDUONG.dto.request.IntrospectRequest;
import K21.HAIDUONG.dto.response.ApiResponse;
import K21.HAIDUONG.dto.response.AuthenticationResponse;
import K21.HAIDUONG.dto.response.IntrospectResponse;
import K21.HAIDUONG.exception.AppException;
import K21.HAIDUONG.exception.ErrorCode;
import K21.HAIDUONG.model.Role;
import K21.HAIDUONG.model.User;
import K21.HAIDUONG.repository.UserRepository;
import K21.HAIDUONG.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationServiceImpl implements AuthenticationService {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    @Value("${app.secreteKey}")
    String secreteKey;

    @Override
    public ApiResponse<AuthenticationResponse> authenticate(AuthenticationRequest request) throws JOSEException {
        User user = userRepository.findByEmail(request.getEmail().trim()).orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));
        boolean isPasswordCorrect = passwordEncoder.matches(request.getPassword().trim(), user.getPassword());
        return ApiResponse.<AuthenticationResponse>builder()
                .result(AuthenticationResponse.builder()
                        .isAuthenticated(isPasswordCorrect)
                        .token(isPasswordCorrect ? generateToken(user) : null)
                        .build())
                .build();
    }

    @Override
    public ApiResponse<IntrospectResponse> introspect(IntrospectRequest request) throws ParseException, JOSEException {
        var signedJWT = SignedJWT.parse(request.getToken());
        JWSVerifier verifier = new MACVerifier(secreteKey);
        boolean isToken = signedJWT.verify(verifier);
        boolean isExpired = new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime());
        return ApiResponse.<IntrospectResponse>builder()
                .result(IntrospectResponse.builder()
                        .isValid(isExpired && isToken)
                        .build())
                .build();
    }

    public String generateToken(User user) throws JOSEException {
        JWSSigner signer = new MACSigner(secreteKey);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("hieu.com")
                .issueTime(new Date())
                .expirationTime(new Date(new Date().getTime() + 1 * 60 * 1000))
                .claim("hai duong", "k21")
                .claim("scope", buildScope(user))
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(signer);
        String token = signedJWT.serialize();
        return token;
    }

    public String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        Set<Role> roles = user.getRoles();

        if (!CollectionUtils.isEmpty(roles)) {
            roles.forEach(role -> {
                stringJoiner.add(role.getName());
            });
        }
        return stringJoiner.toString().trim();
    }
}
