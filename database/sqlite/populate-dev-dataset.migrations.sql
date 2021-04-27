INSERT INTO passport_user VALUES (
    "85ff5a54-48b9-4294-a91d-2be50bd2a77d",
    "aresearcher",
    "Ana",
    "Researcher",
    "ana.researcher@institute.org",
    "hOZHxY0qEv1f20BMHUklpS4OywV4YXu1ZzjRO8LkHbdLjX7FF0C75N5fqRg9sqvI",
    "60f77e6b5826e56ba8b2456557a3f877a6a94c19c4b427d1ce54d4e92fd13c90"
);

INSERT INTO passport_visa VALUES (
    "670cc2e7-9a9c-4273-9334-beb40d364e5c",
    "Controlled Datasets Dev"
);

INSERT INTO passport_visa_assertion (user_id, visa_id, status) VALUES (
    "85ff5a54-48b9-4294-a91d-2be50bd2a77d",
    "670cc2e7-9a9c-4273-9334-beb40d364e5c",
    "active"
);
