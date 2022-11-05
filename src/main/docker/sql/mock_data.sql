\c assignment-db;

INSERT INTO "assignment_user" (user_id, "name") VALUES('e4bdcb7d-6510-4b86-9ba6-2e05577834f2', 'testUser1');
INSERT INTO "assignment_user" (user_id, "name") VALUES('2df80744-8101-43d4-b676-529e5e93cda8', 'testUser2');
INSERT INTO "assignment_user" (user_id, "name") VALUES('b83d82e9-71d7-455b-9fa0-9242986299b9', 'testUser3');

INSERT INTO screen (screen_id, content_json, "name") VALUES('61dd27f5-1c92-4464-bb29-9395dd0841f7', '{"testJsonKey1" : "testJsonValue1"}', 'testScreen1');
INSERT INTO screen (screen_id, content_json, "name") VALUES('bd88542b-62a4-4f82-8eb1-04e39594dcb0', '{"testJsonKey2" : "testJsonValue2"}', 'testScreen2');