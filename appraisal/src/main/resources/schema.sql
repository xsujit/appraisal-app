drop table appraisal if exists;
drop table appraisal_type if exists;
drop table appraisal_year if exists;
drop table auth_user_group if exists;
drop table comment if exists;
drop table employee if exists;
drop table project if exists;
drop table user if exists;
drop table vote if exists;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;
create table appraisal (description text not null, last_update_date timestamp, signed_off boolean, submit_date timestamp not null, optlock bigint, employee_id bigint not null, appraisal_type tinyint not null, appraisal_year date not null, project_id bigint not null, primary key (appraisal_type, appraisal_year, employee_id));
create table appraisal_type (id bigint not null, active boolean not null, description varchar(255) not null, type tinyint not null, primary key (id));
create table appraisal_year (id bigint not null, active boolean not null, year date not null, primary key (id));
create table auth_user_group (id bigint not null, auth_group varchar(255) not null, user_id bigint not null, primary key (id));
create table comment (id bigint not null, message text not null, submit_date timestamp not null, appraisal_appraisal_type tinyint not null, appraisal_appraisal_year date not null, appraisal_employee_id bigint not null, commenter_employee_id bigint not null, primary key (id));
create table employee (first_name varchar(255) not null, id bigint not null, last_name varchar(255) not null, location varchar(255) not null, user_id bigint not null, primary key (user_id));
create table project (id bigint not null, description varchar(255), title varchar(255), primary key (id));
create table user (id bigint not null, email varchar(255) not null, enabled boolean not null, password varchar(255) not null, project_id bigint not null, primary key (id));
create table vote (submit_date timestamp not null, voter_employee_id bigint not null, appraisal_appraisal_type tinyint not null, appraisal_appraisal_year date not null, appraisal_employee_id bigint not null, primary key (appraisal_appraisal_type, appraisal_appraisal_year, appraisal_employee_id, voter_employee_id));
alter table appraisal_type add constraint UK_11b7ge11y5tqr8u10ubr1i4kd unique (type);
alter table appraisal_year add constraint UK_a63dshwe4wjf3e6cf1osujc9k unique (year);
alter table employee add constraint UK_q3nme3uq00618x7nhnvtm21yj unique (id);
alter table user add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);
alter table appraisal add constraint FK5amils547p5cfma1m5rpvehpa foreign key (employee_id) references employee (id);
alter table appraisal add constraint FKpmkcji2wrms07b5dq97nyeedj foreign key (appraisal_type) references appraisal_type (type);
alter table appraisal add constraint FKmo7a5sg30b5136rjkkary8wlu foreign key (appraisal_year) references appraisal_year (year);
alter table appraisal add constraint FK4eub5vv6yrlpq5liv02sbxx7v foreign key (project_id) references project;
alter table auth_user_group add constraint FKja9vgspdl27k5cfo9ofxeyg6w foreign key (user_id) references user;
alter table comment add constraint FKfyvuv8yetts68fxfg6lgy96sn foreign key (appraisal_appraisal_type, appraisal_appraisal_year, appraisal_employee_id) references appraisal;
alter table comment add constraint FK8vmwd9wuamj9nwbn10715yd4u foreign key (commenter_employee_id) references employee (id);
alter table employee add constraint FK6lk0xml9r7okjdq0onka4ytju foreign key (user_id) references user;
alter table user add constraint FK2yj6e26t1njfy8ld303hdav9y foreign key (project_id) references project;
alter table vote add constraint FKkexhvd32oy35kdejoj5r0ftoe foreign key (voter_employee_id) references employee (id);
alter table vote add constraint FKknl01gr81wfvi4ye251qjwn2d foreign key (appraisal_appraisal_type, appraisal_appraisal_year, appraisal_employee_id) references appraisal;