package com.android.officecanteen.com.android.officecanteen.communication;

import android.app.Service;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;

public class CommunicationService extends Service {

    private CommunicationExecutorHandler handler;
    public static final String KEY_PREFIX = "com.techroots.smartschool.Communication.";
    public static final String ACTION_SIGN_UP = KEY_PREFIX + "ACTION_SIGN_UP";

    public CommunicationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        final HandlerThread executorThread = new HandlerThread(
                CommunicationExecutorHandler.class.getSimpleName());
        executorThread.setPriority(Thread.NORM_PRIORITY);
        executorThread.start();
        this.handler = new CommunicationExecutorHandler(
                executorThread.getLooper(), this);
    }

    @Override
    public void onDestroy() {
        if ((null != this.handler) && (null != this.handler.getLooper())) {
            this.handler.getLooper().quit();
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            final String action = intent.getAction();

            // Determine operation code to be executed.
            final ICommunicationOperation operation = getOperation(action);

            if (operation != null) {
                final ICommunicationOperation.Result result = operation.preprocess(intent.getExtras());

                if (result.getCode() == ICommunicationOperation.Result.Code.FURTHER_PROCESSING_NEEDED) {
                    /*
                     * If preprocess() returns FURTHER_PROCESSING_NEEDED,
                     * then operation should be executed in another thread,
                     * so send operation to executor thread using handler.
                     */
                    final Message msg = this.handler.obtainMessage();
                    msg.obj = operation;
                    this.handler.sendMessage(msg);
                } else {
                    /*
                     * preprocess() returns some result,
                     * so further processing of operation in another thread is not needed.
                     * Operation completely is executed in main thread or ignored,
                     * so notify client about this.
                     */
                    CommunicationNotifier.notifyOperationCompleted(this, operation, result);
                }
            }
        }
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException();
    }

    private ICommunicationOperation getOperation(final String action) {
        ICommunicationOperation operation = null;

        if (ACTION_SIGN_UP.equals(action)) {
            operation = new OperationSignUp();
        }
        return operation;
    }
}
