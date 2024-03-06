package com.proyecto.trendy.responses;

import com.proyecto.trendy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;

    private User user;

    public AuthenticationResponse(User user) {
        this.user = user;
    }


}
