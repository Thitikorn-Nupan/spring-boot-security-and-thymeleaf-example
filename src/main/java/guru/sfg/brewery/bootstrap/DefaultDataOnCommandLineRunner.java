/*
 *  Copyright 2020 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.models.*;
import guru.sfg.brewery.dto.BeerStyleEnum;
import guru.sfg.brewery.models.security.Authority;
import guru.sfg.brewery.models.security.Role;
import guru.sfg.brewery.models.security.User;
import guru.sfg.brewery.repositories.*;
import guru.sfg.brewery.repositories.sercurity.AuthorityRepository;
import guru.sfg.brewery.repositories.sercurity.RoleRepository;
import guru.sfg.brewery.repositories.sercurity.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
//

/**
 * Created by jt on 2019-01-26.
 drop table USERS_ROLES ;
 drop table ROLES_AUTHORITIES ;
 drop table BEER_ORDER ;
 drop table BEER_INVENTORY ;
 drop table BEER_ORDER_LINE ;
 drop table BEER ;
 drop table AUTHORITIES ;
 drop table ROLES ;
 drop table USERS ;
 drop table CUSTOMER ;
 drop table BEER_ORDER ;
 drop table BREWERY ;
 drop table CUSTOMER ;
 *** Big thank jpa!
 Hibernate: drop table if exists authorities CASCADE
 Hibernate: drop table if exists beer CASCADE
 Hibernate: drop table if exists beer_inventory CASCADE
 Hibernate: drop table if exists beer_order CASCADE
 Hibernate: drop table if exists beer_order_line CASCADE
 Hibernate: drop table if exists brewery CASCADE
 Hibernate: drop table if exists customer CASCADE
 Hibernate: drop table if exists roles CASCADE
 Hibernate: drop table if exists roles_authorities CASCADE
 Hibernate: drop table if exists users CASCADE
 Hibernate: drop table if exists users_roles CASCADE
 Hibernate: drop sequence if exists hibernate_sequence
 Hibernate: create sequence hibernate_sequence start with 1 increment by 1
 Hibernate: create table authorities (id integer not null, permission varchar(255), primary key (id))
 Hibernate: create table beer (id varchar not null, created_date timestamp, last_modified_date timestamp, version bigint, beer_name varchar(255), beer_style integer, min_on_hand integer, price decimal(19,2), quantity_to_brew integer, upc varchar(255), primary key (id))
 Hibernate: create table beer_inventory (id varchar not null, created_date timestamp, last_modified_date timestamp, version bigint, quantity_on_hand integer, beer_id varchar, primary key (id))
 Hibernate: create table beer_order (id varchar not null, created_date timestamp, last_modified_date timestamp, version bigint, customer_ref varchar(255), order_status integer, order_status_callback_url varchar(255), customer_id varchar, primary key (id))
 Hibernate: create table beer_order_line (id varchar not null, created_date timestamp, last_modified_date timestamp, version bigint, order_quantity integer, quantity_allocated integer, beer_id varchar, beer_order_id varchar, primary key (id))
 Hibernate: create table brewery (id varchar not null, created_date timestamp, last_modified_date timestamp, version bigint, brewery_name varchar(255), primary key (id))
 Hibernate: create table customer (id varchar not null, created_date timestamp, last_modified_date timestamp, version bigint, api_key varchar, customer_name varchar(255), primary key (id))
 Hibernate: create table roles (id integer not null, name varchar(255), primary key (id))
 Hibernate: create table roles_authorities (role_id integer not null, authority_id integer not null, primary key (role_id, authority_id))
 Hibernate: create table users (id integer not null, account_non_expired boolean, account_non_locked boolean, credentials_non_expired boolean, enabled boolean, password varchar(255), username varchar(255), customer_id varchar, primary key (id))
 Hibernate: create table users_roles (user_id integer not null, role_id integer not null, primary key (user_id, role_id))
 Hibernate: alter table beer add constraint UK_p9mb364xktkjqmprmg89u2etr unique (upc)
 Hibernate: alter table beer_inventory add constraint FKrcn9po4vdekbhm8mpoj5f35d9 foreign key (beer_id) references beer
 Hibernate: alter table beer_order add constraint FK5siih2e7vpx70nx4wexpxpji foreign key (customer_id) references customer
 Hibernate: alter table beer_order_line add constraint FKslskqsf79v6iekvb6d3gcc1l4 foreign key (beer_id) references beer
 Hibernate: alter table beer_order_line add constraint FKhkgofxhwx8yw9m3vat8mgtnxs foreign key (beer_order_id) references beer_order
 Hibernate: alter table roles_authorities add constraint FKt69njxcgfcto5wcrd9ocmb35h foreign key (authority_id) references authorities
 Hibernate: alter table roles_authorities add constraint FKq3iqpff34tgtkvnn545a648cb foreign key (role_id) references roles
 Hibernate: alter table users add constraint FKj1nvw1u6pgkabmhbcqkvatr8s foreign key (customer_id) references customer
 Hibernate: alter table users_roles add constraint FKj6m8fwv7oqv74fcehir1a9ffy foreign key (role_id) references roles
 Hibernate: alter table users_roles add constraint FK2o0jvgh89lemvvo17cbqvdxaa foreign key (user_id) references users
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into authorities (permission, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into roles (name, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into roles (name, id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into roles (name, id) values (?, ?)
 Hibernate: select role0_.id as id1_7_1_, role0_.name as name2_7_1_, authoritie1_.role_id as role_id1_8_3_, authority2_.id as authorit2_8_3_, authority2_.id as id1_0_0_, authority2_.permission as permissi2_0_0_ from roles role0_ left outer join roles_authorities authoritie1_ on role0_.id=authoritie1_.role_id left outer join authorities authority2_ on authoritie1_.authority_id=authority2_.id where role0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select authority0_.id as id1_0_0_, authority0_.permission as permissi2_0_0_ from authorities authority0_ where authority0_.id=?
 Hibernate: select role0_.id as id1_7_1_, role0_.name as name2_7_1_, authoritie1_.role_id as role_id1_8_3_, authority2_.id as authorit2_8_3_, authority2_.id as id1_0_0_, authority2_.permission as permissi2_0_0_ from roles role0_ left outer join roles_authorities authoritie1_ on role0_.id=authoritie1_.role_id left outer join authorities authority2_ on authoritie1_.authority_id=authority2_.id where role0_.id=?
 Hibernate: select role0_.id as id1_7_1_, role0_.name as name2_7_1_, authoritie1_.role_id as role_id1_8_3_, authority2_.id as authorit2_8_3_, authority2_.id as id1_0_0_, authority2_.permission as permissi2_0_0_ from roles role0_ left outer join roles_authorities authoritie1_ on role0_.id=authoritie1_.role_id left outer join authorities authority2_ on authoritie1_.authority_id=authority2_.id where role0_.id=?
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: insert into roles_authorities (role_id, authority_id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into users (account_non_expired, account_non_locked, credentials_non_expired, customer_id, enabled, password, username, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into users_roles (user_id, role_id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into users (account_non_expired, account_non_locked, credentials_non_expired, customer_id, enabled, password, username, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into users_roles (user_id, role_id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into users (account_non_expired, account_non_locked, credentials_non_expired, customer_id, enabled, password, username, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into users_roles (user_id, role_id) values (?, ?)
 Hibernate: select count(*) as col_0_0_ from users user0_
 2568-02-12 16:24:41.768 DEBUG 17172 --- [           main] g.s.b.b.DefaultDataOnCommandLineRunner   : Users Loaded: 3
 Hibernate: insert into brewery (created_date, last_modified_date, version, brewery_name, id) values (?, ?, ?, ?, ?)
 Hibernate: insert into beer (created_date, last_modified_date, version, beer_name, beer_style, min_on_hand, price, quantity_to_brew, upc, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into beer_inventory (created_date, last_modified_date, version, beer_id, quantity_on_hand, id) values (?, ?, ?, ?, ?, ?)
 Hibernate: insert into beer (created_date, last_modified_date, version, beer_name, beer_style, min_on_hand, price, quantity_to_brew, upc, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into beer_inventory (created_date, last_modified_date, version, beer_id, quantity_on_hand, id) values (?, ?, ?, ?, ?, ?)
 Hibernate: insert into beer (created_date, last_modified_date, version, beer_name, beer_style, min_on_hand, price, quantity_to_brew, upc, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into beer_inventory (created_date, last_modified_date, version, beer_id, quantity_on_hand, id) values (?, ?, ?, ?, ?, ?)
 Hibernate: insert into customer (created_date, last_modified_date, version, api_key, customer_name, id) values (?, ?, ?, ?, ?, ?)
 Hibernate: select beer0_.id as id1_1_, beer0_.created_date as created_2_1_, beer0_.last_modified_date as last_mod3_1_, beer0_.version as version4_1_, beer0_.beer_name as beer_nam5_1_, beer0_.beer_style as beer_sty6_1_, beer0_.min_on_hand as min_on_h7_1_, beer0_.price as price8_1_, beer0_.quantity_to_brew as quantity9_1_, beer0_.upc as upc10_1_ from beer beer0_
 Hibernate: select beerinvent0_.beer_id as beer_id6_2_0_, beerinvent0_.id as id1_2_0_, beerinvent0_.id as id1_2_1_, beerinvent0_.created_date as created_2_2_1_, beerinvent0_.last_modified_date as last_mod3_2_1_, beerinvent0_.version as version4_2_1_, beerinvent0_.beer_id as beer_id6_2_1_, beerinvent0_.quantity_on_hand as quantity5_2_1_ from beer_inventory beerinvent0_ where beerinvent0_.beer_id=?
 Hibernate: select beerinvent0_.beer_id as beer_id6_2_0_, beerinvent0_.id as id1_2_0_, beerinvent0_.id as id1_2_1_, beerinvent0_.created_date as created_2_2_1_, beerinvent0_.last_modified_date as last_mod3_2_1_, beerinvent0_.version as version4_2_1_, beerinvent0_.beer_id as beer_id6_2_1_, beerinvent0_.quantity_on_hand as quantity5_2_1_ from beer_inventory beerinvent0_ where beerinvent0_.beer_id=?
 Hibernate: select beerinvent0_.beer_id as beer_id6_2_0_, beerinvent0_.id as id1_2_0_, beerinvent0_.id as id1_2_1_, beerinvent0_.created_date as created_2_2_1_, beerinvent0_.last_modified_date as last_mod3_2_1_, beerinvent0_.version as version4_2_1_, beerinvent0_.beer_id as beer_id6_2_1_, beerinvent0_.quantity_on_hand as quantity5_2_1_ from beer_inventory beerinvent0_ where beerinvent0_.beer_id=?
 Hibernate: insert into beer_order (created_date, last_modified_date, version, customer_id, customer_ref, order_status, order_status_callback_url, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into beer_order_line (created_date, last_modified_date, version, beer_id, beer_order_id, order_quantity, quantity_allocated, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into beer_order (created_date, last_modified_date, version, customer_id, customer_ref, order_status, order_status_callback_url, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into beer_order_line (created_date, last_modified_date, version, beer_id, beer_order_id, order_quantity, quantity_allocated, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into beer_order (created_date, last_modified_date, version, customer_id, customer_ref, order_status, order_status_callback_url, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into beer_order_line (created_date, last_modified_date, version, beer_id, beer_order_id, order_quantity, quantity_allocated, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: select role0_.id as id1_7_, role0_.name as name2_7_ from roles role0_ where role0_.name=?
 Hibernate: insert into customer (created_date, last_modified_date, version, api_key, customer_name, id) values (?, ?, ?, ?, ?, ?)
 Hibernate: insert into customer (created_date, last_modified_date, version, api_key, customer_name, id) values (?, ?, ?, ?, ?, ?)
 Hibernate: insert into customer (created_date, last_modified_date, version, api_key, customer_name, id) values (?, ?, ?, ?, ?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into users (account_non_expired, account_non_locked, credentials_non_expired, customer_id, enabled, password, username, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into users_roles (user_id, role_id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into users (account_non_expired, account_non_locked, credentials_non_expired, customer_id, enabled, password, username, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into users_roles (user_id, role_id) values (?, ?)
 Hibernate: call next value for hibernate_sequence
 Hibernate: insert into users (account_non_expired, account_non_locked, credentials_non_expired, customer_id, enabled, password, username, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into users_roles (user_id, role_id) values (?, ?)
 Hibernate: select beer0_.id as id1_1_, beer0_.created_date as created_2_1_, beer0_.last_modified_date as last_mod3_1_, beer0_.version as version4_1_, beer0_.beer_name as beer_nam5_1_, beer0_.beer_style as beer_sty6_1_, beer0_.min_on_hand as min_on_h7_1_, beer0_.price as price8_1_, beer0_.quantity_to_brew as quantity9_1_, beer0_.upc as upc10_1_ from beer beer0_ where beer0_.upc=?
 Hibernate: select beerinvent0_.beer_id as beer_id6_2_0_, beerinvent0_.id as id1_2_0_, beerinvent0_.id as id1_2_1_, beerinvent0_.created_date as created_2_2_1_, beerinvent0_.last_modified_date as last_mod3_2_1_, beerinvent0_.version as version4_2_1_, beerinvent0_.beer_id as beer_id6_2_1_, beerinvent0_.quantity_on_hand as quantity5_2_1_ from beer_inventory beerinvent0_ where beerinvent0_.beer_id=?
 Hibernate: insert into beer_order (created_date, last_modified_date, version, customer_id, customer_ref, order_status, order_status_callback_url, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into beer_order_line (created_date, last_modified_date, version, beer_id, beer_order_id, order_quantity, quantity_allocated, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: select beer0_.id as id1_1_, beer0_.created_date as created_2_1_, beer0_.last_modified_date as last_mod3_1_, beer0_.version as version4_1_, beer0_.beer_name as beer_nam5_1_, beer0_.beer_style as beer_sty6_1_, beer0_.min_on_hand as min_on_h7_1_, beer0_.price as price8_1_, beer0_.quantity_to_brew as quantity9_1_, beer0_.upc as upc10_1_ from beer beer0_ where beer0_.upc=?
 Hibernate: select beerinvent0_.beer_id as beer_id6_2_0_, beerinvent0_.id as id1_2_0_, beerinvent0_.id as id1_2_1_, beerinvent0_.created_date as created_2_2_1_, beerinvent0_.last_modified_date as last_mod3_2_1_, beerinvent0_.version as version4_2_1_, beerinvent0_.beer_id as beer_id6_2_1_, beerinvent0_.quantity_on_hand as quantity5_2_1_ from beer_inventory beerinvent0_ where beerinvent0_.beer_id=?
 Hibernate: insert into beer_order (created_date, last_modified_date, version, customer_id, customer_ref, order_status, order_status_callback_url, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into beer_order_line (created_date, last_modified_date, version, beer_id, beer_order_id, order_quantity, quantity_allocated, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: select beer0_.id as id1_1_, beer0_.created_date as created_2_1_, beer0_.last_modified_date as last_mod3_1_, beer0_.version as version4_1_, beer0_.beer_name as beer_nam5_1_, beer0_.beer_style as beer_sty6_1_, beer0_.min_on_hand as min_on_h7_1_, beer0_.price as price8_1_, beer0_.quantity_to_brew as quantity9_1_, beer0_.upc as upc10_1_ from beer beer0_ where beer0_.upc=?
 Hibernate: select beerinvent0_.beer_id as beer_id6_2_0_, beerinvent0_.id as id1_2_0_, beerinvent0_.id as id1_2_1_, beerinvent0_.created_date as created_2_2_1_, beerinvent0_.last_modified_date as last_mod3_2_1_, beerinvent0_.version as version4_2_1_, beerinvent0_.beer_id as beer_id6_2_1_, beerinvent0_.quantity_on_hand as quantity5_2_1_ from beer_inventory beerinvent0_ where beerinvent0_.beer_id=?
 Hibernate: insert into beer_order (created_date, last_modified_date, version, customer_id, customer_ref, order_status, order_status_callback_url, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: insert into beer_order_line (created_date, last_modified_date, version, beer_id, beer_order_id, order_quantity, quantity_allocated, id) values (?, ?, ?, ?, ?, ?, ?, ?)
 Hibernate: select count(*) as col_0_0_ from beer_order beerorder0_

 SELECT * FROM AUTHORITIES ;
 SELECT * FROM ROLES_AUTHORITIES ;
 SELECT * FROM USERS ;
 SELECT * FROM USERS_ROLES ;
 SELECT * FROM BEER_INVENTORY ;

 */
@Slf4j
@RequiredArgsConstructor // is worked instead constructor and autowire
// @Component
public class DefaultDataOnCommandLineRunner implements CommandLineRunner {

    public static final String TASTING_ROOM = "Tasting Room";
    public static final String ST_PETE_DISTRIBUTING = "St Pete Distributing";
    public static final String DUNEDIN_DISTRIBUTING = "Dunedin Distributing";
    public static final String KEY_WEST_DISTRIBUTORS = "Key West Distributors";


    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    private final BreweryRepository breweryRepository;
    private final BeerRepository beerRepository;
    private final BeerInventoryRepository beerInventoryRepository;
    private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
//        loadSecurityData();
//        loadBreweryData();
//        loadTastingRoomData();
//        loadCustomerData();
    }

    private void loadCustomerData() {

        // get role's customer
        Role customerRole = roleRepository.findByName("CUSTOMER").orElseThrow();

        //create customers
        Customer stPeteCustomer = customerRepository.save(Customer.builder()
                .customerName(ST_PETE_DISTRIBUTING)
                .apiKey(UUID.randomUUID())
                .build());

        Customer dunedinCustomer = customerRepository.save(Customer.builder()
                .customerName(DUNEDIN_DISTRIBUTING)
                .apiKey(UUID.randomUUID())
                .build());

        Customer keyWestCustomer = customerRepository.save(Customer.builder()
                .customerName(KEY_WEST_DISTRIBUTORS)
                .apiKey(UUID.randomUUID())
                .build());

        // this is customer (ROLE 51)
        User aUser = userRepository.save(User.builder().username("a")
                .password(passwordEncoder.encode("password"))
                .customer(stPeteCustomer)
                .role(customerRole).build());

        User bUser = userRepository.save(User.builder().username("b")
                .password(passwordEncoder.encode("password"))
                .customer(dunedinCustomer)
                .role(customerRole).build());

        User cUser = userRepository.save(User.builder().username("c")
                .password(passwordEncoder.encode("password"))
                .customer(keyWestCustomer)
                .role(customerRole).build());

        //create orders
        createOrder(stPeteCustomer);
        createOrder(dunedinCustomer);
        createOrder(keyWestCustomer);

        log.debug("Orders Loaded: {}", beerOrderRepository.count());
    }

    private BeerOrder createOrder(Customer customer) {
        return beerOrderRepository.save(BeerOrder.builder()
                .customer(customer)
                .orderStatus(OrderStatusEnum.NEW)
                .beerOrderLines(Set.of(BeerOrderLine.builder()
                        .beer(beerRepository.findByUpc(BEER_1_UPC))
                        .orderQuantity(2)
                        .build()))
                .build());
    }


    private void loadSecurityData() {
        // *** save to table authorities (id,name)
        // *** 1 ,beer.create
        // *** 2 ,beer.read
        // *** 3 ,beer.update
        // *** ...
        // beer auths
        Authority createBeer = authorityRepository.save(Authority.builder().permission("beer.create").build());
        Authority readBeer = authorityRepository.save(Authority.builder().permission("beer.read").build());
        Authority updateBeer = authorityRepository.save(Authority.builder().permission("beer.update").build());
        Authority deleteBeer = authorityRepository.save(Authority.builder().permission("beer.delete").build());

        // customer auths
        Authority createCustomer = authorityRepository.save(Authority.builder().permission("customer.create").build());
        Authority readCustomer = authorityRepository.save(Authority.builder().permission("customer.read").build());
        Authority updateCustomer = authorityRepository.save(Authority.builder().permission("customer.update").build());
        Authority deleteCustomer = authorityRepository.save(Authority.builder().permission("customer.delete").build());

        // customer brewery
        Authority createBrewery = authorityRepository.save(Authority.builder().permission("brewery.create").build());
        Authority readBrewery = authorityRepository.save(Authority.builder().permission("brewery.read").build());
        Authority updateBrewery = authorityRepository.save(Authority.builder().permission("brewery.update").build());
        Authority deleteBrewery = authorityRepository.save(Authority.builder().permission("brewery.delete").build());

        // beer order
        Authority createOrder = authorityRepository.save(Authority.builder().permission("order.create").build());
        Authority readOrder = authorityRepository.save(Authority.builder().permission("order.read").build());
        Authority updateOrder = authorityRepository.save(Authority.builder().permission("order.update").build());
        Authority deleteOrder = authorityRepository.save(Authority.builder().permission("order.delete").build());

        Authority createOrderCustomer = authorityRepository.save(Authority.builder().permission("customer.order.create").build());
        Authority readOrderCustomer = authorityRepository.save(Authority.builder().permission("customer.order.read").build());
        Authority updateOrderCustomer = authorityRepository.save(Authority.builder().permission("customer.order.update").build());
        Authority deleteOrderCustomer = authorityRepository.save(Authority.builder().permission("customer.order.delete").build());

        // *** save to table roles (id,name)
        // *** 1 , ADMIN
        // *** 2 , CUSTOMER
        // *** 3 , USER
        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());
        Role customerRole = roleRepository.save(Role.builder().name("CUSTOMER").build());


        // ** prepare roles_authorities attributes
        HashSet<Authority> adminAuthorities = new HashSet<Authority>(
                Set.of(
                        createBeer, updateBeer, readBeer, deleteBeer,
                        createCustomer, readCustomer, updateCustomer, deleteCustomer,
                        createBrewery, readBrewery, updateBrewery, deleteBrewery,
                        createOrder, readOrder, updateOrder, deleteOrder,
                        createOrderCustomer, readOrderCustomer, updateOrderCustomer, deleteOrderCustomer
                )
        );


        HashSet<Authority> userAuthorities = new HashSet<Authority>(
                Set.of(
                        readBeer
                )
        );

        HashSet<Authority> customerAuthorities = new HashSet<Authority>(
                Set.of(
                        readBeer, readCustomer, readBrewery,
                        createOrderCustomer, readOrderCustomer,
                        updateOrderCustomer, deleteOrderCustomer
                )
        );

        adminRole.setAuthorities(adminAuthorities);

        userRole.setAuthorities(userAuthorities);

        customerRole.setAuthorities(customerAuthorities);

        // *** save to table roles_authorities (role id,authority id)
        roleRepository.saveAll(Arrays.asList(adminRole, customerRole, userRole));


        // *** save to tables user and users_roles ***
        userRepository.save(User.builder()
                .username("admin")
                .password(passwordEncoder.encode("12345"))
                .role(adminRole)
                .build());

        userRepository.save(User.builder()
                .username("user")
                .password(passwordEncoder.encode("12345"))
                .role(userRole)
                .build());

        userRepository.save(User.builder()
                .username("scott")
                .password(passwordEncoder.encode("12345"))
                .role(customerRole)
                .build());

        log.debug("Users Loaded: {}", userRepository.count());
    }

    private void loadBreweryData() {
        // **

        breweryRepository.save(Brewery
                .builder()
                .breweryName("Cage Brewing")
                .build());

        Beer mangoBobs = Beer.builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyleEnum.IPA)
                .minOnHand(12)
                .quantityToBrew(200)
                .upc(BEER_1_UPC)
                .build();

        beerRepository.save(mangoBobs);
        beerInventoryRepository.save(BeerInventory.builder()
                .beer(mangoBobs)
                .quantityOnHand(500)
                .build());

        Beer galaxyCat = Beer.builder()
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyleEnum.PALE_ALE)
                .minOnHand(12)
                .quantityToBrew(200)
                .upc(BEER_2_UPC)
                .build();

        beerRepository.save(galaxyCat);
        beerInventoryRepository.save(BeerInventory.builder()
                .beer(galaxyCat)
                .quantityOnHand(500)
                .build());

        Beer pinball = Beer.builder()
                .beerName("Pinball Porter")
                .beerStyle(BeerStyleEnum.PORTER)
                .minOnHand(12)
                .quantityToBrew(200)
                .upc(BEER_3_UPC)
                .build();

        beerRepository.save(pinball);
        beerInventoryRepository.save(BeerInventory.builder()
                .beer(pinball)
                .quantityOnHand(500)
                .build());

    }

    private void loadTastingRoomData() {

        Customer tastingRoom = Customer.builder()
                .customerName(TASTING_ROOM)
                .apiKey(UUID.randomUUID())
                .build();

        customerRepository.save(tastingRoom);

        beerRepository.findAll().forEach(beer -> {
            beerOrderRepository.save(BeerOrder.builder()
                    .customer(tastingRoom)
                    .orderStatus(OrderStatusEnum.NEW)
                    .beerOrderLines(Set.of(BeerOrderLine.builder()
                            .beer(beer)
                            .orderQuantity(2)
                            .build()))
                    .build());
        });
    }


}
