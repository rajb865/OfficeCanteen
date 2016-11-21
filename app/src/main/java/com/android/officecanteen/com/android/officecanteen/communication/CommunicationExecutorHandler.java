package com.android.officecanteen.com.android.officecanteen.communication;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by inrsharm04 on 10/8/2015.
 */
public class CommunicationExecutorHandler extends Handler {
    /**
     * Weak reference to the context of the handler to be used to send
     * notifications.
     */
    private WeakReference<Context> context = null;

    /**
     * General constructor.
     *
     * @param looper  Looper of worker thread.
     * @param context Client's context.
     */
    protected CommunicationExecutorHandler(final Looper looper, final Context context) {
        super(looper);
        this.context = new WeakReference<Context>(context);
    }

    @Override
    public void handleMessage(final Message msg) {
        final ICommunicationOperation operation = ((ICommunicationOperation) msg.obj);
        final ICommunicationOperation.Result result = operation.process();

        CommunicationNotifier.notifyOperationCompleted(getContext(), operation,
                result);
    }

    /**
     * Returns handler's context.
     *
     * @return {@link Context} in which handler works.
     */
    private Context getContext() {
        if (this.context != null) {
            return this.context.get();
        }
        return null;
    }
}
