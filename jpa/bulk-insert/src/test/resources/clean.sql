SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE MEMBER;
ALTER TABLE MEMBER
    AUTO_INCREMENT = 1;

TRUNCATE TABLE MEMBER_SECURITY;
ALTER TABLE MEMBER_SECURITY
    AUTO_INCREMENT = 1;

TRUNCATE TABLE ADDRESS;
ALTER TABLE ADDRESS
    AUTO_INCREMENT = 1;

TRUNCATE TABLE ADMIN;
ALTER TABLE ADMIN
    AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;