package chiangmai.repository;

import chiangmai.domain.Landmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandmarkRepository extends JpaRepository<Landmark, Long> {
    @Query(
            value = "SELECT * FROM landmark l WHERE ST_Distance_Sphere(point(l.x, l.y), point(:y, :x)) <= 100",
            nativeQuery = true
    )
    List<Landmark> findStoresWithin100M(@Param("x") double x, @Param("y") double y);
}
