package pl.edu.uj.tcs.quizhub.models.Database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.uj.tcs.quizhub.models.UserPermission;

@Data
@Entity
@Builder
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "user_permissions")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserPermission userPermissions = UserPermission.USER;
}