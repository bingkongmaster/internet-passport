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
        Log.d(TAG, "fillrequest");

        List<FillContext> context = request.getFillContexts();
        AssistStructure structure = context.get(context.size() - 1).getStructure();

        List<ViewNode> fields = traverseStructure(structure);

        RemoteViews suggestion_view = new RemoteViews(getPackageName(), android.R.layout.simple_list_item_1);
        suggestion_view.setTextViewText(android.R.id.text1, "my_username");

        if (fields.isEmpty()) return;

        FillResponse res = new FillResponse.Builder()
                .addDataset(new Dataset.Builder(suggestion_view)
                        .setValue(fields.get(0).getAutofillId(), AutofillValue.forText("facebook.com"))
                        .build())
                .build();

        callback.onSuccess(res);
    }

    public List<ViewNode> traverseStructure(AssistStructure structure) {
        List<ViewNode> fields = new ArrayList<>();

        int nodes = structure.getWindowNodeCount();

        for (int i = 0; i < nodes; i++) {
            WindowNode windowNode = structure.getWindowNodeAt(i);
            ViewNode viewNode = windowNode.getRootViewNode();
            traverseNode(viewNode, fields);
        }

        return fields;
    }

    public void traverseNode(ViewNode viewNode, List<ViewNode> fields) {

        if (viewNode.getClassName().contains("EditText")) {
            Log.d(TAG, viewNode.toString());
            fields.add(viewNode);
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