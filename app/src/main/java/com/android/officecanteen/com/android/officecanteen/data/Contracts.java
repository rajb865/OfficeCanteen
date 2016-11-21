package com.android.officecanteen.com.android.officecanteen.data;

/**
 * Contract for interacting with content provider. This contract allows access
 * to tables, so caller able to modify content.
 */
public class Contracts {

    /**
     * Contract for table {Tables.CommunicationData}. Data this contract
     * represents is stored as key-value pairs.
     */
    public static class CommunicationData {

        /** Key for storing session id. */
        public static final String SESSION_ID = "session_id";
        /** Key for storing umbrella id. */
        public static final String UMBRELLA_ID = "umbrella_id";
        /** Key for storing user email */
        public static final String USER_EMAIL = "user_email";
        /** Key for storing device id */
        public static final String DEVICE_ID = "device_id";
        /** Key for storing registration id */
        public static final String REGISTRATION_ID = "registration_id";
        /** Key for storing provider id */
        public static final String PROVIDER_ID = "provider_id";
        /** Key for storing google authorization token */
        public static final String GOOGLE_AUTH_TOKEN = "google_auth_token";
        /** Key for storing first name of user */
        public static final String USER_FIRST_NAME = "user_first_name";
        /** Key for storing last name of user */
        public static final String USER_LAST_NAME = "user_last_name";
        /** Key for storing restore user flag */
        public static final String RESTORE_USER = "restore_user";
        
    }


}
