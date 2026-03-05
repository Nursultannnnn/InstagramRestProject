package peaksoft.instagramrestproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "image_gen")
    @SequenceGenerator(
            name = "image_gen",
            sequenceName = "image_seq",
            allocationSize = 1)
    Long id;

    String imageURL;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    Post post;
}