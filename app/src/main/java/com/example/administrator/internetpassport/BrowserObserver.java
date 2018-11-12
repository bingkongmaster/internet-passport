package com.example.administrator.internetpassport;

import android.app.assist.AssistStructure;
import android.app.assist.AssistStructure.*;
import android.os.CancellationSignal;
import android.service.autofill.AutofillService;
import android.service.autofill.Dataset;
import android.service.autofill.FillCallback;
import android.service.autofill.FillContext;
import android.service.autofill.FillRequest;
import android.service.autofill.FillResponse;
import android.service.autofill.SaveCallback;
import android.service.autofill.SaveRequest;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.autofill.AutofillValue;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

/**
 * reference: https://developer.android.com/guide/topics/text/autofill-services
 */
public class BrowserObserver extends AutofillService {

    private static final String TAG = "BrowserObserver";

    @Override
    public void onFillRequest(@NonNull FillRequest request,
                              @NonNull CancellationSignal cancellationSignal,
                              @NonNull FillCallback callback) {
        Log.d(TAG, "onFillRequest");

        List<FillContext> context = request.getFillContexts();
        AssistStructure structure = context.get(context.size() - 1).getStructure();

        List<ViewNode> fields = gatherInputFields(structure);

        RemoteViews suggestion_view = new RemoteViews(getPackageName(), android.R.layout.simple_list_item_1);
        suggestion_view.setTextViewText(android.R.id.text1, "an_example_id (m.facebook.com)");

        if (fields.isEmpty())
        {
            /**
             * callback.onFailure will stop this service, and no more fill-request messages will come.
             * call callback.onSuccess() for no existing formfillings.
             */
            callback.onSuccess(null);
            return;
        }

        FillResponse res = new FillResponse.Builder()
                .addDataset(new Dataset.Builder(suggestion_view)
                        .setValue(fields.get(0).getAutofillId(), AutofillValue.forText("an_example_id"))
                        .setValue(fields.get(1).getAutofillId(), AutofillValue.forText("an_example_pw"))
                        .build())
                .build();

        callback.onSuccess(res);
    }

    protected static List<ViewNode> gatherInputFields(AssistStructure structure) {
        List<ViewNode> fields = new ArrayList<>();

        int nodes = structure.getWindowNodeCount();

        for (int i = 0; i < nodes; i++) {
            WindowNode windowNode = structure.getWindowNodeAt(i);
            ViewNode viewNode = windowNode.getRootViewNode();
            traverseNode(viewNode, fields);
        }

        return fields;
    }

    protected static void traverseNode(ViewNode viewNode, List<ViewNode> fields) {

        if (viewNode.getClassName().contains("EditText")) {

            // ignore web browsers url bar.
            if (!"url_bar".equals(viewNode.getIdEntry())) {
                Log.v(TAG, "EditText: html_entity_id=" + viewNode.getIdEntry());
                fields.add(viewNode);
            }
        }

        for (int i = 0; i < viewNode.getChildCount(); i++) {
            ViewNode childNode = viewNode.getChildAt(i);
            traverseNode(childNode, fields);
        }
    }

    @Override
    public void onSaveRequest(@NonNull SaveRequest request, @NonNull SaveCallback callback) {

    }
}