package peaksoft.instagramrestproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import peaksoft.instagramrestproject.enums.Role;

import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_gen"
    )
    @SequenceGenerator(
            name = "user_gen",
            sequenceName = "user_seq",
            allocationSize = 1)

    Long id;
    @Column(unique = true)
    String username;
    String password;
    @Column(unique = true)
    String email;
    @Column(unique = true)
    String phoneNumber;
    Role role;

    // СВЯЗИ
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    UserInfo userInfo;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    Follower follower;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Post> posts;
}
