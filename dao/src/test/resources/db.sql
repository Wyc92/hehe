CREATE TABLE student (
id                INT NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 10000),
username          VARCHAR(255) NOT NULL,
password          VARCHAR(255) NOT NULL,
email             VARCHAR(255) NULL,
gender            VARCHAR(255) default 'male' NULL,
PRIMARY KEY (id)
);

CREATE TABLE homework (
id          INT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
student_id   INT NOT NULL,
content       VARCHAR(255),
PRIMARY KEY (id)
);