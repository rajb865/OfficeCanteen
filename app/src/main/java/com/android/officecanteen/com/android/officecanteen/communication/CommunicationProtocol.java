package com.android.officecanteen.com.android.officecanteen.communication;

/**
 * Created by inrsharm04 on 10/13/2015.
 * Class contains all constants, which associates with server field names
 */
public interface CommunicationProtocol {

    /**
     * This errors is documented as
     * general server errors
     */
    interface ServerErrorCode {
        /**
         * Invalid auth token, Re-authenticate the device
         */
        int INVALID_TOKEN_ERROR = 10001;
        /**
         * Invalid session, Re-authenticate the device
         */
        int INVALID_SESSION_ERROR = 10003;
    }

    /**
     * Common response fields for all {@link com.techroots.smartschool.Communication.request.SmartSchoolRequest} requests are
     * suited here
     */
    interface Response {
        /**
         * Status is Error
         */
        String RESULT_STATUS = "result";
        /**
         * Error code
         */
        String RESPONSE_STRING = "response";
    }

    interface ErrorResponse {
        String ERROR_CODE = "errorCode";
        String ERROR_MESSAGE = "errorMessage";
    }

    /**
     * Common values of status field in responses from server
     */
    interface Status {
        /**
         * Success value of status from server
         */
        String SUCCESS = "success";
    }
}
