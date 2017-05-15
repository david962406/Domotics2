CREATE TABLE [users](
    [id] INTEGER PRIMARY KEY NOT NULL UNIQUE,
    [password] TEXT NOT NULL,
    [admin] BOOLEAN NOT NULL,
    [user_name] TEXT NOT NULL,
    [last_configuration] INTEGER DEFAULT NULL);

CREATE TABLE [user_data](
    [user_id] INTEGER NOT NULL REFERENCES users([id]),
    [first_name] TEXT NOT NULL,
    [last_name] TEXT NOT NULL);

CREATE TABLE [doors](
    [id] INTEGER PRIMARY KEY NOT NULL UNIQUE,
    [password] INTEGER NOT NULL);

CREATE TABLE [device](
    [id] INTEGER PRIMARY KEY,
    [description] TEXT NOT NULL);


CREATE TABLE [configuration](
    [id] INTEGER PRIMARY KEY NOT NULL UNIQUE,
    [user_id] INTEGER NOT NULL REFERENCES users([id]),
    [device_id] INTEGER NOT NULL REFERENCES device([id]));

CREATE TABLE [device_configuration](
    [id] INTEGER PRIMARY KEY NOT NULL,
    [device_id] INTEGER NOT NULL REFERENCES device([id]),
    [sensor_active] BOOLEAN,
    [data] INTEGER,
    [device_active] BOOLEAN NOT NULL);