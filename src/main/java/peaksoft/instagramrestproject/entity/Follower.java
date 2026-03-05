package peaksoft.instagramrestproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "followers")
@Getter
@Setter
@NoArgsConstructor // Добавь аннотации!
public class Follower {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "follower_gen"
    )
    @SequenceGenerator(
            name = "follower_gen",
            sequenceName = "follower_seq",
            allocationSize = 1)
    Long id;

    @ElementCollection
    List<Long> subscribers; // Список ID тех, кто подписан на меня

    @ElementCollection
    List<Long> subscriptions; // Список ID тех, на кого подписан я

    @OneToOne
    User user;
}
