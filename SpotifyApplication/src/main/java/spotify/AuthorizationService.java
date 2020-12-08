package spotify;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

@Service
public class AuthorizationService {
	@Autowired
	AuthConfigs authConfigs;
	
	SpotifyApi spotifyApi;
	String accessToken = null;
	String refreshToken;
	
	public String getAuthorizationCode() throws URISyntaxException {
		
		spotifyApi = new SpotifyApi.Builder()
				.setClientId(authConfigs.getClientId())
				.setClientSecret(authConfigs.getClientSecret())
				.setRedirectUri(authConfigs.getRedirectURI())
				.build();
		
		AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
				.scope(authConfigs.getScope())
				.build();
		URI codeURI = authorizationCodeUriRequest.execute();
		System.out.println("Authorization Code URI: " + codeURI.toString());
		return codeURI.toString();
	}
	
	public String getAuthorizationTokens() {
		AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(authConfigs.code).build();
		try {
			AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
			accessToken = authorizationCodeCredentials.getAccessToken();
			refreshToken = authorizationCodeCredentials.getRefreshToken();
			System.out.println("Access and Refresh Tokens: " + accessToken + '\n' + refreshToken);
			return accessToken;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setCode(String code, String state) {
		authConfigs.code = code;
		authConfigs.state = state;
	}
	
	/*
	public String getAuthorizationCode() throws URISyntaxException {
        // set query parameters
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("scope", authConfigs.getScope());
        queryParams.put("response_type", "code");
        queryParams.put("redirect_uri", authConfigs.getRedirectURI().toString());
        queryParams.put("client_id", authConfigs.getClientId());

        // prepare request headers
        // requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        // hit request
        ResponseEntity<String> responseEntity = restCall(queryParams);

        // check response
        //checkResponseCodeExpectedString(responseEntity, Arrays.asList(CREATED, OK), "authorize");
        String body = responseEntity.getBody();
        System.out.println(body);
        return body;
	}
	
	ResponseEntity<String> restCall(RestTemplate restTemplate, String message, 
									String url, HttpMethod method, 
									HttpEntity entity, Class responseClass, 
									Map<String, String> queryParameters) {
		
		ResponseEntity<String> responseEntity;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri);
		queryParams.keySet().forEach(key -> builder.queryParam(key, queryParams.get(key)));
		RestTemplate restTemplate = new RestTemplate();
		responseEntity = restTemplate.exchange(builder.build(false).toUri(), HttpMethod.GET, entity, String.class);
		
		return responseEntity;
	}


	public void getTokens() {
		RestTemplate restTemplate = new RestTemplate();
		
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
        // prepare create index url
        String createIndexUrl = authConfigs.getTokenUrl();

        // prepare request headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        requestHeaders.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(
                (authConfigs.getClientId() + ":" + authConfigs.getClientSecret()).getBytes()
        ));

        // set body
        requestBody.put("grant_type", Arrays.asList("authorization_code"));
        requestBody.put("code", Arrays.asList(authConfigs.getCode()));
        requestBody.put("redirect_uri", Arrays.asList(authConfigs.getRedirectURI().toString()));

        // hit request
        ResponseEntity<JSONObject> responseEntity = restCall(createIndexUrl, HttpMethod.POST,
                new HttpEntity<>(requestBody, requestHeaders), JSONObject.class, null);

        // check response
        checkResponseCodeExpected(responseEntity, Arrays.asList(NO_CONTENT, OK), "getToken");
	}
	*/
}
