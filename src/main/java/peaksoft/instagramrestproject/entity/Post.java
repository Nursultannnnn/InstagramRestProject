package peaksoft.instagramrestproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_gen"
    )
    @SequenceGenerator(
            name = "post_gen",
            sequenceName = "post_seq",
            allocationSize = 1)
    Long id;
    String title;
    String description;
    LocalDateTime createdAt;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    User user; // Автор поста

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<Image> images; // Связь с картинками

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<Like> likes;
}
