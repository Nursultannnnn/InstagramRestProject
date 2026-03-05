package peaksoft.instagramrestproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Like {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "like_gen")
    @SequenceGenerator(
            name = "like_gen",
            sequenceName = "like_seq",
            allocationSize = 1)
    Long id;

    // Кто поставил лайк
    @ElementCollection
    List<Long> userIds;

    // Связь с постом (если лайкнули пост)
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    Post post;

    // Связь с комментарием (если лайкнули комментарий)
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    Comment comment;
}