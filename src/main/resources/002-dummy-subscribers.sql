INSERT INTO subscriber(id, voornaam, achternaam, email, active)
VALUES('10', 'Luke', 'Skywalker', 'luke@rebellion.org', true),
      ('20', 'Leia', 'Organa', 'leia@rebellion.org', true),
      ('30', 'Han', 'Solo', 'han@rebellion.org', true);

INSERT INTO SUBSCRIPTION_LIST_SUBSCRIBERS (subscriptions_id, subscribers_id)
VALUES('10', '10'),
      ('10', '20'),
      ('20', '30');
