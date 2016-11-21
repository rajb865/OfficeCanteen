package com.android.officecanteen.com.android.officecanteen.communication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by inrsharm04 on 10/12/2015.
 * This class is responsible for notifying clients of
 * {@link CommunicationService} about status of operations
 */
public class CommunicationNotifier {
    /**
     * For logging.
     */
    private static final String TAG = CommunicationNotifier.class
            .getSimpleName();
    /**
     * Common prefix for all extra's keys.
     */
    private static final String KEY_PREFIX = "nfctags.stc.com.nfckids.communication.";
    /**
     * Action which notifies that operation is completed.
     */
    public static final String ACTION_OPERATION_COMPLETED = KEY_PREFIX
            + "ACTION_OPERATION_COMPLETED";
    /**
     * Extra key which shows what type of operation is handled.
     */
    public static final String EXTRA_KEY_OPERATION = KEY_PREFIX + "operation";
    /**
     * Extra key which result status of handled operation
     */
    public static final String EXTRA_KEY_RESULT_CODE = KEY_PREFIX
            + "result_code";
    /**
     * Extra key which result of sub code of server
     */
    public static final String EXTRA_KEY_RESULT_SUBCODE = KEY_PREFIX
            + "result_subcode";
    /**
     * Extra key which result of error message of server
     */
    public static final String EXTRA_KEY_RESULT_ERROR_MESSAGE = KEY_PREFIX
            + "error_message";

    public static final String EXTRA_KEY_RESULT_RESPONSE_STRING = KEY_PREFIX
            + "response_string";

    /**
     * Notifies clients about operation completion. Sends broadcast message with
     * {@link CommunicationNotifier#ACTION_OPERATION_COMPLETED}.
     *
     * @param context   {@link Context} to be used to send broadcast message.
     * @param operation Code of the operation, which was completed.
     * @param result    Result of operation (see {@link ICommunicationOperation.Result}).
     */
    protected static void notifyOperationCompleted(final Context context,
                                                   final ICommunicationOperation operation, final ICommunicationOperation.Result result) {

        final String operationId = operation.getOperationId();

        Log.i(TAG,
                "[Operation completed] " + operationId + " : "
                        + result.getCode());

        if (null != context) {
            final Intent intent = new Intent(ACTION_OPERATION_COMPLETED);

            intent.putExtra(EXTRA_KEY_OPERATION, operationId);
            intent.putExtra(EXTRA_KEY_RESULT_CODE, result.getCode());
            intent.putExtra(EXTRA_KEY_RESULT_SUBCODE, result.getSubCode());
            intent.putExtra(EXTRA_KEY_RESULT_ERROR_MESSAGE, result.getErrorMessage());
            intent.putExtra(EXTRA_KEY_RESULT_RESPONSE_STRING, result.getResponseString());
            intent.setPackage(context.getPackageName());
            context.sendBroadcast(intent, null);
        }
    }
}
