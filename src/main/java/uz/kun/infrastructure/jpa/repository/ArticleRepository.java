package uz.kun.infrastructure.jpa.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.kun.domain.constants.ArticleStatus;
import uz.kun.domain.entities.ArticleEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {

    List<ArticleEntity> findByRegionId(Integer regionId, Boolean visible, Pageable pageable);
    List<ArticleEntity> findByCategoryId(Integer categoryId, Boolean visible, Pageable pageable);
    List<ArticleEntity> findByTypeId(Integer typeId, Boolean visible, Pageable pageable);

    @Query(value = "SELECT * FROM article WHERE status =:status ORDER BY created_date DESC limit 4", nativeQuery = true)
    List<ArticleEntity>findByLastAddedFourArticle(@Param("status") String status);

//    @Query(value = "SELECT * FROM article as  WHERE region_id =: region_id AND status =:status ORDER BY created_date DESC limit 4", nativeQuery = true)
//    List<ArticleEntity>findByRegionIdTopFourArticle(@Param("region_id") Integer region_id, @Param("status") String status);
//

    @Query("from ArticleEntity as a where a.regionId =: region_id and a.status =: status order by a.createdDate desc limit 4")
    List<ArticleEntity>findByRegionIdTopFourArticle( int region_id, String status);
    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where  a.type_id =:typeId and status =:status order by created_date desc Limit 5", nativeQuery = true)
    List<ArticleEntity> getTypeId(@Param("typeId") Integer typeId, @Param("status") String status);
    Page<ArticleEntity> findByVisible(Boolean visible, Pageable pageable);
    Optional<ArticleEntity> findByTitle(String title);
    List<ArticleEntity> findTop5ByTypeIdAndStatus(Integer typeId, ArticleStatus status, Sort sort);
    Page<ArticleEntity> findAllByTypeId(Integer typeId, Pageable pageable);
    Optional<ArticleEntity> findByIdAndStatus(Integer id, ArticleStatus status);
    Page<ArticleEntity> findByRegionIdAndStatus(Integer regionId, Pageable pageable, ArticleStatus status);
    Page<ArticleEntity> findByCategoryIdAndStatus(Integer cId, Pageable pageable, ArticleStatus status);
    Page<ArticleEntity> findByTypeIdAndStatus(Integer tId, Pageable pageable, ArticleStatus status);
    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible = :visible where id = :id")
    int updateVisible(@Param("visible") Boolean visible, @Param("id") Integer id);
    @Transactional
    @Modifying
    @Query("update ArticleEntity set status = :status where id = :id")
    int updateStatus(@Param("status") ArticleStatus status, @Param("id") Integer id);
    @Modifying
    @Transactional
    @Query(value = "update ArticleEntity set sharedCount= :shared where id=:id")
    void updateSharedCount(@Param("shared") Integer share, @Param("id") Integer id);
    @Modifying
    @Transactional
    @Query(value = "update ArticleEntity set sharedCount = sharedCount +1 where id=:id")
    void updateSharedCount(@Param("id") Integer id);
    @Transactional
    @Modifying
    @Query(value = "update ArticleEntity a set a.viewCount = a.viewCount + 1 where a.id =:id")
    void updateViewCount(@Param("id") Integer id);
}
