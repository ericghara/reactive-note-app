package org.ericgha.reactivetodolist.services;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.SignedJWT;
import org.ericgha.reactivetodolist.dtos.ToDoUser;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.text.ParseException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static reactor.core.publisher.Mono.when;

@SpringBootTest(classes = {JwtToDoUserConverter.class} )
class JwtToDoUserConverterTest {

    final static String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJqaUY2bWQtVDJ1MUphbTBsTkQ5N1RKb193T2R4cEFvTjZj" +
            "X3p4c2dGaTNnIn0.eyJleHAiOjE2NTczMDI2OTksImlhdCI6MTY1NzMwMjM5OSwiYXV0aF90aW1lIjoxNjU3MzAyMzk3LCJqdGkiOiJ" +
            "jN2I1YTM2Yy0zYWE4LTRhZWYtODE5Zi00NDQ0ZDk5ZjdjNzIiLCJpc3MiOiJodHRwczovL2F1dGguZXJpY2doYS5jb20vcmVhbG1zL2" +
            "NsaWVudCIsInN1YiI6IjI3MGFlZWI1LWViMDYtNDkwYi1iNjg1LTFiNjAwMDRlOTIwMyIsInR5cCI6IkJlYXJlciIsImF6cCI6IlRvR" +
            "G8iLCJub25jZSI6IjQ2NmFlNmZjLTZmZjEtNGUxZS04Mjk4LWEyNWNiYjA1MzA3OSIsInNlc3Npb25fc3RhdGUiOiJiYmFkM2Q3Yy04" +
            "Yzg0LTRiNjItYTVjYS02MzQ3YmM2ODZjMDAiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6ODA" +
            "4MCIsImh0dHA6Ly9sb2NhbGhvc3Q6ODA5MCIsImh0dHBzOi8vYXV0aC5lcmljZ2hhLmNvbSIsImh0dHA6Ly9sb2NhbGhvc3Q6MzAwMC" +
            "IsImh0dHBzOi8vY2xvdWRwcmludC5lcmljZ2hhLmNvbSJdLCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwic2lkIjoiYmJhZ" +
            "DNkN2MtOGM4NC00YjYyLWE1Y2EtNjM0N2JjNjg2YzAwIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoiVGVzcyBUZXN0ZXJz" +
            "b24iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ0ZXN0IiwiZ2l2ZW5fbmFtZSI6IlRlc3MiLCJmYW1pbHlfbmFtZSI6IlRlc3RlcnNvbiI" +
            "sImVtYWlsIjoidGVzdEB0ZXN0LmNvbSJ9.JGSmNrrhijs7qM7xNqW3lOL9fp5pCVSsO_2_4xa6uj3OOJ42C8CHLO0eHZKjabGEj0LKo" +
            "kD4F4FkfHLcHtiCCDfnpXVtwoLJziVIlWuT93G5vCuY7PT8B9Mo3HD9XM0iHbtEaWTnw_jPYiHaLN7oJ_qfUy6ia2GU-QOYULZN6AbL" +
            "qckPQv4PzVF1Ue1UuywhQ_dhjMhV-_sYZZ-NCKVHFAaTwVtukDisJHZGN8OwxczJqWVH3RSDu9y5Qx22DlBZWhLdF8YOMebdRgAamVq" +
            "rDDinxHKpJPAff9a-OMrti8w-B60Bagep7UKCxwQ1644zYav2ILDp8kg56bkUZEpzaw";

    @Autowired
    JwtToDoUserConverter converter;
    @Mock
    Jwt jwt;

    @Test
    void convert() throws ParseException {
        ToDoUser expected = ToDoUser.builder()
                .userId( "270aeeb5-eb06-490b-b685-1b60004e9203" )
                .username( "test" )
                .email( "test@test.com" )
                .emailVerified( false )
                .fullName( "Tess Testerson" )
                .firstName( "Tess" )
                .lastName( "Testerson" )
                .realm( "ToDo" )
                .build();
        // spring jwt converter tough to bootstrap and nimbus and spring use different jwt objects...
        Map<String, Object> claims = SignedJWT.parse( token )
                                              .getJWTClaimsSet()
                                              .getClaims();
        Answer<String> ans = invocation -> {
            String string = (String) invocation.getArgument( 0 );
            return (String) claims.get(string);
        };
        when(jwt.getClaimAsString( anyString() )).thenAnswer( ans ) ;

        ToDoUser found = converter.convert( jwt );
        assertEquals(expected, found);
    }
}