package com.github.juli220620.security;

import com.github.juli220620.model.RoleEntity;
import com.github.juli220620.model.UserEntity;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private static final String SCOPE_CLAIM = "scp";

    @Value("#{${app.security.jwt.lifetime}.longValue}")
    private Long tokenLifetime;

    @Value("${app.security.pr.key.path}")
    private String privateKeyPath;

    public String generateToken(UserEntity user) {
        JWSSigner signer;
        RSAKey privateKey;

        try(var input = getClass().getClassLoader().getResourceAsStream(privateKeyPath)) {
            privateKey = JWK.parseFromPEMEncodedObjects(
                    new String(Objects.requireNonNull(input).readAllBytes())
            )
                    .toRSAKey();
            signer = new RSASSASigner(privateKey);
        } catch (IOException | JOSEException e) {
            throw new RuntimeException(e);
        }

        var claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId().toString())
                .expirationTime(new Date(new Date().getTime() + tokenLifetime))
                .claim(
                        SCOPE_CLAIM,
                        user.getRoles().stream()
                                .map(RoleEntity::getId)
                                .collect(Collectors.joining(" "))
                )
                .build();

        var jwt = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256)
                        .keyID(privateKey.getKeyID()).build(),
                claimsSet
        );

        try {jwt.sign(signer);}
        catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        return jwt.serialize();
    }
}
