package be.mira.jongeren.mailinglist.repository;

import be.mira.jongeren.mailinglist.domain.SubscriptionCount;
import be.mira.jongeren.mailinglist.domain.SubscriptionList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionCountRepository extends JpaRepository<SubscriptionCount, Long> {

    @Query(value = "select * from subscription_count s where subscription_list_id = :list order by s.timestamp desc limit 1", nativeQuery = true)
    SubscriptionCount findTopSubscriptionCount(@Param("list") SubscriptionList list);

}
