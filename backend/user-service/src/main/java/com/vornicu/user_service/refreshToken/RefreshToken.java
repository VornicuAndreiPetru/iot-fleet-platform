package com.vornicu.user_service.refreshToken;


import com.vornicu.user_service.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne
    private User user;
    @Column(nullable = false,unique = true)
    private String token;
    @Column(nullable = false)
    private Instant expiryDate;
}
