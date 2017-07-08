package be.mira.jongeren.mailinglist.repository;

import be.mira.jongeren.mailinglist.domain.SubscriptionEvent;
import be.mira.jongeren.mailinglist.domain.SubscriptionList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionEventRepository extends JpaRepository<SubscriptionEvent, Long> {

    @Query(value = "select * from subscription_event s where subscription_list_id = :list order by s.timestamp desc limit 1", nativeQuery = true)
    SubscriptionEvent findTopSubscriptionEvent(@Param("list") SubscriptionList list);

    List<SubscriptionEvent> findBySubscriptionList(SubscriptionList list);

}
