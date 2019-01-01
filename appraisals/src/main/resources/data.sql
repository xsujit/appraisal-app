INSERT INTO appraisal_year (id, year, active) VALUES (1, '2018-01-01', '1');
INSERT INTO appraisal_year (id, year, active) VALUES (2, '2017-01-01', '0');
INSERT INTO appraisal_year (id, year, active) VALUES (3, '2019-01-01', '0');
INSERT INTO appraisal_type (id, type, description, active) VALUES (1, '0', 'Mid Year', 0);
INSERT INTO appraisal_type (id, type, description, active) VALUES (2, '1', 'Year End', 1);
INSERT INTO project (id, description, title) VALUES ('1', 'SLC Onsite TnM', 'SLC Onsite', );
INSERT INTO project (id, description, title) VALUES ('2', 'Morrisons Onsite TnM', 'Morrisons Onsite', );
INSERT INTO user (email, password, id, project_id, ENABLED) VALUES ('jack.bauer@mastek.com', '$2a$11$N82GiTabGmYk45vXnGPhG.2QnAOsAlrdHlamiA7o8LWFw45i0r8j2', '1000', 2, true);
INSERT INTO auth_user_group (auth_group, user_id, id) VALUES ('USER', 1000, 2000);
INSERT INTO employee (first_name, id, last_name, location, user_id) VALUES ('Jack', 11227, 'Bauer', 'Glasgow', 1000);
INSERT INTO user (email, password, id, project_id, ENABLED) VALUES ('james.bond@mastek.com', '$2a$11$N82GiTabGmYk45vXnGPhG.2QnAOsAlrdHlamiA7o8LWFw45i0r8j2', '1001', 2, true);
INSERT INTO auth_user_group (auth_group, user_id, id) VALUES ('USER', 1001, 2001);
INSERT INTO employee (first_name, id, last_name, location, user_id) VALUES ('James', 11228, 'Bond', 'Glasgow', 1001);
INSERT INTO user (email, password, id, project_id, ENABLED) VALUES ('stuart.little@mastek.com', '$2a$11$N82GiTabGmYk45vXnGPhG.2QnAOsAlrdHlamiA7o8LWFw45i0r8j2', '1002', 1, true);
INSERT INTO auth_user_group (auth_group, user_id, id) VALUES ('USER', 1002, 2002);
INSERT INTO employee (first_name, id, last_name, location, user_id) VALUES ('Stuart', 11229, 'Little', 'Mumbai', 1002);

INSERT INTO appraisal (last_update_date, project_id, signed_off, submit_date, appraisal_type, appraisal_year, employee_id, DESCRIPTION, OPTLOCK) 
VALUES (sysdate, 2, true, sysdate, 1, '2018-01-01', 11227, 'Jack appraisal for 2018 year end', 0);
INSERT INTO appraisal (last_update_date, project_id, signed_off, submit_date, appraisal_type, appraisal_year, employee_id, DESCRIPTION, OPTLOCK) 
VALUES (sysdate, 2, true, sysdate, 0, '2018-01-01', 11227, 'My appraisal for 2018 mid year', 0);

INSERT INTO appraisal (last_update_date, project_id, signed_off, submit_date, appraisal_type, appraisal_year, employee_id, DESCRIPTION, OPTLOCK) 
VALUES (sysdate, 2, true, sysdate, 1, '2018-01-01', 11228, 'James appraisal for 2018 year end', 0);
INSERT INTO appraisal (last_update_date, project_id, signed_off, submit_date, appraisal_type, appraisal_year, employee_id, DESCRIPTION, OPTLOCK) 
VALUES (sysdate, 2, true, sysdate, 0, '2018-01-01', 11228, 'James appraisal for 2018 mid year', 0);

INSERT INTO appraisal (last_update_date, project_id, signed_off, submit_date, appraisal_type, appraisal_year, employee_id, DESCRIPTION, OPTLOCK) 
VALUES (sysdate, 1, true, sysdate, 1, '2018-01-01', 11229, 'Stuart appraisal for 2018 year end', 0);
INSERT INTO appraisal (last_update_date, project_id, signed_off, submit_date, appraisal_type, appraisal_year, employee_id, DESCRIPTION, OPTLOCK) 
VALUES (sysdate, 1, true, sysdate, 0, '2018-01-01', 11229, 'Stuart appraisal for 2018 mid year', 0);


INSERT INTO VOTE (SUBMIT_DATE, VOTER_EMPLOYEE_ID, APPRAISAL_APPRAISAL_TYPE, APPRAISAL_APPRAISAL_YEAR, APPRAISAL_EMPLOYEE_ID) 
VALUES (SYSDATE, 11228, 0, '2018-01-01', 11227);
INSERT INTO VOTE (SUBMIT_DATE, VOTER_EMPLOYEE_ID, APPRAISAL_APPRAISAL_TYPE, APPRAISAL_APPRAISAL_YEAR, APPRAISAL_EMPLOYEE_ID) 
VALUES (SYSDATE, 11228, 1, '2018-01-01', 11229);
INSERT INTO VOTE (SUBMIT_DATE, VOTER_EMPLOYEE_ID, APPRAISAL_APPRAISAL_TYPE, APPRAISAL_APPRAISAL_YEAR, APPRAISAL_EMPLOYEE_ID) 
VALUES (SYSDATE, 11228, 0, '2018-01-01', 11229);
INSERT INTO VOTE (SUBMIT_DATE, VOTER_EMPLOYEE_ID, APPRAISAL_APPRAISAL_TYPE, APPRAISAL_APPRAISAL_YEAR, APPRAISAL_EMPLOYEE_ID) 
VALUES (SYSDATE, 11227, 0, '2018-01-01', 11228);
INSERT INTO VOTE (SUBMIT_DATE, VOTER_EMPLOYEE_ID, APPRAISAL_APPRAISAL_TYPE, APPRAISAL_APPRAISAL_YEAR, APPRAISAL_EMPLOYEE_ID) 
VALUES (SYSDATE, 11227, 1, '2018-01-01', 11228);

INSERT INTO COMMENT (ID, MESSAGE, SUBMIT_DATE, COMMENTER_EMPLOYEE_ID, APPRAISAL_APPRAISAL_TYPE, APPRAISAL_APPRAISAL_YEAR, APPRAISAL_EMPLOYEE_ID)
VALUES (1000, 'Good job!', sysdate, 11228, 0, '2018-01-01', 11227);
INSERT INTO COMMENT (ID, MESSAGE, SUBMIT_DATE, COMMENTER_EMPLOYEE_ID, APPRAISAL_APPRAISAL_TYPE, APPRAISAL_APPRAISAL_YEAR, APPRAISAL_EMPLOYEE_ID)
VALUES (1001, 'I agree, this has been a good performance. He has guided me through my stories and solved difficult queries.', sysdate, 11229, 0, '2018-01-01', 11227);
INSERT INTO COMMENT (ID, MESSAGE, SUBMIT_DATE, COMMENTER_EMPLOYEE_ID, APPRAISAL_APPRAISAL_TYPE, APPRAISAL_APPRAISAL_YEAR, APPRAISAL_EMPLOYEE_ID)
VALUES (1002, 'I agree, this has been a good performance. He has guided me through my stories and solved difficult queries.', sysdate, 11228, 1, '2018-01-01', 11229);