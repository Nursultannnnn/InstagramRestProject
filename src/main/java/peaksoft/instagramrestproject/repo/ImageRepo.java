package peaksoft.instagramrestproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.instagramrestproject.entity.Image;

public interface ImageRepo extends JpaRepository<Image, Long> {}
