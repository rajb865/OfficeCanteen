package com.android.officecanteen.com.android.officecanteen.data;

import android.net.Uri;

/**
 * Created by inrsharm04 on 11/21/2015.
 */
public interface DataMember {
    static final Uri QUERY_URI = Uri.parse("content://"
            + DataProvider.AUTHORITY);
    static final String QUERY_PATH = "table/";

    interface TABLE_STUDENT {
        String TABLE = "student";
        String STUDENT_COLUMN_NAME = "name";
        String STUDENT_COLUMN_CLASS_NAME = "className";
        String STUDENT_COLUMN_IMAGE_URL = "imageURL";
        String STUDENT_COLUMN_STUDENT_ID = "studentId";

        static final String PATH_COMMON = QUERY_PATH + TABLE;
        public static final Uri CONTENT_URI = QUERY_URI.buildUpon()
                .appendEncodedPath(PATH_COMMON).build();
    }

    interface TABLE_COMMUNICATION_DATA {
        String TABLE = "communicationdata";

        interface COLUMN {
            String KEY = TABLE + "_key";
            String VALUE = TABLE + "_value";
        }

        String USERNAME = "username";
        String PASSWORD = "password";
        String FULLNAME = "fullname";
        String SESSION_ID = "sessionId";
        String EMAIL_ADDRESS = "emailAddress";
        String MOBILE_NUMBER = "mobileNumber";
        String USER_IMAGE_URL = "userImageURL";
        String SCHOOL_NAME = "schoolName";
        String SCHOOL_IMAGE_URL = "schoolImageURL";

        static final String PATH_COMMON = QUERY_PATH + TABLE;
        public static final Uri CONTENT_URI = QUERY_URI.buildUpon()
                .appendEncodedPath(PATH_COMMON).build();
    }

    interface TABLE_UPDATE_MANAGER {
        String TABLE = "updatemanager";

        interface COLUMN {
            String COLUMN_MODULE_NAME = "moduleName";
            String COLUMN_UPDATED_ON = "updatedOn";
        }

        String MODULE_ATTACHMENT = "attachment";
        String MODULE_ATTENDANCE = "attendance";
        String MODULE_DATE_SHEET = "datesheet";
        String MODULE_HOLIDAYS = "holidays";
        String MODULE_NOTICES = "notice";
        String MODULE_REPORT_CARD = "reportcard";
        String MODULE_STUDENT = "student";
        String MODULE_SUBJECT = "subject";

        static final String PATH_COMMON = QUERY_PATH + TABLE;
        public static final Uri CONTENT_URI = QUERY_URI.buildUpon()
                .appendEncodedPath(PATH_COMMON).build();
    }

    interface TABLE_ATTACHMENT {
        String TABLE = "attachment";
        String ATTACHMENT_COLUMN_NAME = "name";
        String ATTACHMENT_COLUMN_EXTENSION = "extension";
        String ATTACHMENT_COLUMN_URL = "url";
        String ATTACHMENT_COLUMN_HOMEWORK_ID = "homeworkId";

        static final String PATH_COMMON = QUERY_PATH + TABLE;
        public static final Uri CONTENT_URI = QUERY_URI.buildUpon()
                .appendEncodedPath(PATH_COMMON).build();
    }

    interface TABLE_SUBJECTS {
        String TABLE = "subject";
        String SUBJECT_COLUMN_SUBJECT_ID = "subjectId";
        String SUBJECT_COLUMN_HOMEWORK_ID = "homeworkId";
        String SUBJECT_COLUMN_COLOR_CODE = "colorCode";
        String SUBJECT_COLUMN_NAME = "name";
        String SUBJECT_COLUMN_DESCRIPTION = "description";
        String HOMEWORK_COLUMN_DATE = "date";
        String HOMEWORK_COLUMN_DATE_STRING = "dateString";
        String HOMEWORK_COLUMN_DATE_SEQUENCE_NO = "dateSeqNo";

        static final String PATH_COMMON = QUERY_PATH + TABLE;
        public static final Uri CONTENT_URI = QUERY_URI.buildUpon()
                .appendEncodedPath(PATH_COMMON).build();
    }

    interface TABLE_ATTENDANCE {
        String TABLE = "attendance";
        String SESSION_NAME = "sessionName";
        String PRESENT_DAYS = "presentDays";
        String TOTAL_DAYS = "totalDays";
        String COLUMN_ATTENDANCE_ID = "attendanceId";
        String COLUMN_DATE = "attendanceDate";
        String COLUMN_DATE_STRING = "attendanceDateString";
        String COLUMN_DAY_STRING = "attendanceDayString";
        String COLUMN_PRESENT_STATUS = "presentStatus";
        String COLUMN_IN_TIME = "inTime";
        String COLUMN_IN_TIME_STRING = "inTimeString";
        String COLUMN_OUT_TIME = "outTime";
        String COLUMN_OUT_TIME_STRING = "outTimeString";
        String COLUMN_COLOR_CODE = "colorCode";

        static final String PATH_COMMON = QUERY_PATH + TABLE;
        public static final Uri CONTENT_URI = QUERY_URI.buildUpon()
                .appendEncodedPath(PATH_COMMON).build();
    }

    interface TABLE_NOTICE {
        String TABLE = "notice";
        String NOTICE_COLUMN_NOTICE_ID = "noticeId";
        String NOTICE_COLUMN_DATE = "noticeDate";
        String NOTICE_COLUMN_DATE_STRING = "noticeDateString";
        String NOTICE_COLUMN_DESCRIPTION = "description";
        String NOTICE_COLUMN_COLOR_CODE = "colorCode";


        static final String PATH_COMMON = QUERY_PATH + TABLE;
        public static final Uri CONTENT_URI = QUERY_URI.buildUpon()
                .appendEncodedPath(PATH_COMMON).build();
    }

    interface TABLE_HOLIDAYS {
        String TABLE = "holidays";
        String COLUMN_ID = "id";
        String COLUMN_SCHOOL_ID = "SchoolId";
        String COLUMN_START_DATE = "StartDate";
        String COLUMN_END_DATE = "EndDate";
        String COLUMN_NO_OF_DAYS = "NumOfDays";
        String COLUMN_IS_VACATION = "isVacation";
        String COLUMN_HOLIDAY_NAME = "HolidayName";
        String COLUMN_COLOR_CODE = "ColorCode";

        static final String PATH_COMMON = QUERY_PATH + TABLE;
        public static final Uri CONTENT_URI = QUERY_URI.buildUpon()
                .appendEncodedPath(PATH_COMMON).build();
    }

    interface TABLE_REPORT_CARD {
        String TABLE = "reportcard";
        String COLUMN_SESSION_ID = "sessionId";
        String COLUMN_SESSION_NAME = "sessionName";
        String COLUMN_SUBJECT_ID = "subjectId";
        String COLUMN_SUBJECT_NAME = "subjectName";
        String COLUMN_MARKS_OR_GRADE = "marksOrGrade";
        String COLUMN_NOTES = "notes";
        String COLUMN_COLOR_CODE = "colorCode";


        static final String PATH_COMMON = QUERY_PATH + TABLE;
        public static final Uri CONTENT_URI = QUERY_URI.buildUpon()
                .appendEncodedPath(PATH_COMMON).build();
    }

    interface TABLE_DATE_SHEET {
        String TABLE = "datesheet";
        String COLUMN_SUBJECT_ID = "subjectId";
        String COLUMN_SUBJECT = "subject";
        String COLUMN_EXAM_DATE_TIME = "examDateAndTime";
        String COLUMN_DATE_STRING = "examDateString";
        String COLUMN_DAY_STRING = "examDayString";
        String COLUMN_TIME_STRING = "examTimeString";
        String COLUMN_COLOR_CODE = "colorCode";


        static final String PATH_COMMON = QUERY_PATH + TABLE;
        public static final Uri CONTENT_URI = QUERY_URI.buildUpon()
                .appendEncodedPath(PATH_COMMON).build();
    }

}
