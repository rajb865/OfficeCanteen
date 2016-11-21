package com.android.officecanteen.com.android.officecanteen.communication;

import com.parse.Parse;
import com.parse.ParseException;

import android.os.Bundle;

/**
 * Created by inrsharm04 on 10/12/2015.
 */
public interface ICommunicationOperation {

    /**
     * This method should be invoked to preprocess Communication operation.
     *
     * @param extra Data which should be used for handling of operation.
     * @return Code of execution result.
     */
    Result preprocess(final Bundle extra);

    /**
     * This method should be invoked to process Communication operation.
     *
     * @return Code of execution result.
     */
    Result process();

    /**
     * Returns ID of the operation. Each implementator should return its own ID.
     *
     * @return ID of the operation.
     */
    String getOperationId();

    class Result {
        /**
         * Constant to hold unspecified value for codes.
         */
        public static final int UNKNOWN = -1;
        /**
         * Primary code of execution result.
         */
        private int code = UNKNOWN;
        private String errorCode = "";
        private int local_error_code = UNKNOWN;
        /**
         * Additional code of execution.
         */
        private int subCode = UNKNOWN;
        /**
         * Error message
         */
        private String errorMessage = null;
        private String responseString = null;

        /**
         * Constructor to create an instance of result with primary code.
         *
         * @param code primary code of result.
         */
        protected Result(final int code) {
            this.code = code;
        }

        /**
         * Constructor to create an instance of result with primary and
         * secondary code.
         *
         * @param code         primary code of result.
         * @param subCode      secondary code of result.
         * @param errorMessage error message
         */
        protected Result(final int code, final int subCode,
                         final String errorMessage) {
            this.code = code;
            this.subCode = subCode;
            this.errorMessage = errorMessage;
        }

        protected Result(final int code, final String subCode,
                         final String errorMessage) {
            this.code = code;
            this.errorCode = subCode;
            this.errorMessage = errorMessage;
        }

        /**
         * Returns result with code {@link Code#FURTHER_PROCESSING_NEEDED}.
         *
         * @return instance of result with code
         * {@link Code#FURTHER_PROCESSING_NEEDED}.
         */
        public static Result furtherProcessing() {
            return new Result(Code.FURTHER_PROCESSING_NEEDED);
        }

        /**
         * Returns result with code {@link Code#OPERATION_IGNORED}.
         *
         * @return instance of result with code {@link Code#OPERATION_IGNORED}.
         */
        public static Result operationIgnored() {
            return new Result(Code.OPERATION_IGNORED);
        }

        /**
         * Returns result with code {@link Code#GENERAL_FAILURE}.
         *
         * @return instance of result with code {@link Code#GENERAL_FAILURE}.
         */
        public static Result generalFailure() {
            return new Result(Code.GENERAL_FAILURE);
        }

        /**
         * Parameter not provided
         */
        public static Result noParamsFailure() {
            return new Result(Code.PARAMETER_NOT_PROVIDED);
        }

        /**
         * Returns result with code {@link Code#GENERAL_FAILURE}.
         *
         * @param errorMessage Proper error message
         * @return instance of result with code {@link Code#GENERAL_FAILURE}.
         */
        public static Result generalFailure(final String errorMessage) {
            return new Result(Code.GENERAL_FAILURE, UNKNOWN, errorMessage);
        }

        /**
         * Returns result with code {@link Code#SERVER_ERROR}.
         *
         * @param subCode server error status
         * @return instance of result with code {@link Code#SERVER_ERROR} and
         * respective errorSubCode.
         */
        public static Result serverError(final int subCode) {
            return new Result(Code.SERVER_ERROR, subCode, null);
        }

        /**
         * Returns result with code {@link Code#SERVER_ERROR}.
         *
         * @param subCode      server error status
         * @param errorMessage Error message
         * @return instance of result with code {@link Code#SERVER_ERROR} and
         * respective errorSubCode.
         */
        public static Result serverError(final int subCode,
                                         final String errorMessage) {
            return new Result(Code.SERVER_ERROR, subCode, errorMessage);
        }

        public static Result serverError(final String subCode,
                                         final String errorMessage) {
            return new Result(Code.SERVER_ERROR, subCode, errorMessage);
        }

        /**
         * Returns result with code {@link Code#COMPLETED}.
         *
         * @return instance of result with code {@link Code#COMPLETED}.
         */
        public static Result completed() {
            return new Result(Code.COMPLETED);
        }

        /**
         * Returns result with code {@link Code#NO_NETWORK_CONNECTION}.
         *
         * @return instance of result with code
         * {@link Code#NO_NETWORK_CONNECTION}.
         */
        public static Result noNetwork() {
            return new Result(Code.NO_NETWORK_CONNECTION);
        }

        public static Result parseException(ParseException e) {
            return new Result(e.getCode());
        }

        public static Result fromException(final ParseException e) {
            return parseException(e);
        }

        public String getResponseString() {
            return responseString;
        }

        public void setResponseString(String responseString) {
            this.responseString = responseString;
        }

        /**
         * Returns primary code of result.
         *
         * @return primary code of result.
         */
        public int getCode() {
            return this.code;
        }

        /**
         * Returns extra code of result
         *
         * @return Extra code of result
         */
        public int getSubCode() {
            return this.subCode;
        }

        /**
         * Returns error message
         *
         * @return Error message
         */
        public String getErrorMessage() {
            return this.errorMessage;
        }

        public interface Code {
            /**
             * blank parameter provided
             */
            int PARAMETER_NOT_PROVIDED = -10;
            /**
             * Undefined operation
             */
            int UNDEFINED = -9;
            /**
             * If operation is needed in further handling
             */
            int FURTHER_PROCESSING_NEEDED = -7;
            /**
             * If operation can't be processed.
             */
            int OPERATION_IGNORED = -6;
            /**
             * Result code means, that no network connection presented
             * when operation executed.
             */
            int NO_NETWORK_CONNECTION = -5;
            /**
             * Means, that server returns an error.
             */
            int SERVER_ERROR = -2;
            /**
             * Means, that execution was failed.
             */
            int GENERAL_FAILURE = -1;
            /**
             * Means, that execution was completed successfully.
             */
            int COMPLETED = 0;
        }
    }
}
