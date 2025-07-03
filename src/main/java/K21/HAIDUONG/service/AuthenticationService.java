package K21.HAIDUONG.service;

import K21.HAIDUONG.dto.request.AuthenticationRequest;
import K21.HAIDUONG.dto.request.IntrospectRequest;
import K21.HAIDUONG.dto.response.ApiResponse;
import K21.HAIDUONG.dto.response.AuthenticationResponse;
import K21.HAIDUONG.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    ApiResponse<AuthenticationResponse> authenticate(AuthenticationRequest request) throws JOSEException;

    ApiResponse<IntrospectResponse> introspect(IntrospectRequest request) throws ParseException, JOSEException;
}
