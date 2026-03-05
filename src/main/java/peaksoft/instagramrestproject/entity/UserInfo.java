package peaksoft.instagramrestproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import peaksoft.instagramrestproject.enums.Gender;

@Entity
@Table(name = "usersInfo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfo {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_info_gen"
    )
    @SequenceGenerator(
            name = "user_info_gen",
            sequenceName = "user_info_seq",
            allocationSize = 1)
    Long id;
    String fullName;
    String biography;
    Gender gender;
    String image;

    @OneToOne (cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    User user;
}
