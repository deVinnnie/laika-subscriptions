INSERT INTO subscriber(id, voornaam, achternaam, email, active)
VALUES('10', 'Luke', 'Skywalker', 'luke@rebellion.org', true),
      ('20', 'Leia', 'Organa', 'leia@rebellion.org', true),
      ('30', 'Han', 'Solo', 'han@rebellion.org', true);

INSERT INTO SUBSCRIPTION_LIST_SUBSCRIBERS (subscriptions_id, subscribers_id)
VALUES('10', '10'),
      ('10', '20'),
      ('20', '20'),
      ('20', '30');


INSERT INTO SUBSCRIPTION_COUNT (id, count, timestamp, subscription_list_id)
VALUES('10', '1', '2015-01-01 18:00:00', 10),
      ('20', '2', '2015-01-02 17:00:00', 10),
      ('30', '1', '2015-01-02 17:00:00', 20),
      ('40', '2', '2015-01-02 19:00:00', 20);

