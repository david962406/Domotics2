package com.fiuady.android.domotics.db;

public final class DomoticsDbSchema {
    public static final class UsersTable {
        public static final String NAME = "users";

        public static final class Columns {
            public static final String ID = "id";
            public static final String PASSWORD = "password";
            public static final String ADMIN = "admin";
            public static final String USERNAME = "user_name";
            public static final String LAST_CONFIGURATION = "last_configuration";
        }//
    }

    public static final class UserDataTable {
        public static final String NAME = "user_data";

        public static final class Columns {
            public static final String USERID = "user_id";
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
        }
    }

    public static final class DoorsTable {
        public static final String NAME = "doors";

        public static final class Columns {
            public static final String ID = "id";
            public static final String PASSWORD = "password";
        }
    }

    public static final class DeviceTable {
        public static final String NAME = "device";

        public static final class Columns {
            public static final String ID = "id";
            public static final String DESCRIPTION = "description";
        }
    }

    public static final class ConfigurationTable {
        public static final String NAME = "configuration";

        public static final class Columns {
            public static final String ID = "id";
            public static final String USERID = "user_id";
            public static final String DEVICEID = "device_id";
        }
    }

    public static final class DeviceConfigurationTable {
        public static final String NAME = "device_configuration";

        public static final class Columns {
            public static final String ID = "id";
            public static final String DEVICEID = "device_id";
            public static final String SENSOR_ACTIVE = "sensor_active";
            public static final String DATA = "data";
            public static final String DEVICE_ACTIVE = "device_active";
        }
    }
}


