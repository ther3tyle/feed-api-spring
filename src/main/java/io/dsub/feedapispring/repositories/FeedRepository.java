package io.dsub.feedapispring.repositories;

import io.dsub.feedapispring.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findAllByMdName(String mdName);
    void deleteById(Long id);
    void deleteAllByMdName(String mdName);
}
