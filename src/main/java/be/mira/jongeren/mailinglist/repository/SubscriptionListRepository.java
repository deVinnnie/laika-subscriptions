package be.mira.jongeren.mailinglist.repository;

import be.mira.jongeren.mailinglist.domain.SubscriptionList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionListRepository extends JpaRepository<SubscriptionList, Long> {

    SubscriptionList findByTitle(String title);
}

