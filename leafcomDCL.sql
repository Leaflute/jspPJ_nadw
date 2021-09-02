/* Create, Grant Account */
drop user leafcom cascade;
create user leafcom identified by tiger default tablespace users;
grant connect, resource, create view to leafcom;
grant create view to leafcom;
alter user leafcom account unlock;