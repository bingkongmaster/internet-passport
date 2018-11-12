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

    static class AutofillContext {
        ArrayList<ViewNode> m_fields;
        String m_domain;

        public AutofillContext() {
            m_fields = new ArrayList<>();
        }
    }

    @Override
    public void onFillRequest(@NonNull FillRequest request,
                              @NonNull CancellationSignal cancellationSignal,
                              @NonNull FillCallback callback) {
        Log.d(TAG, "onFillRequest");

        List<FillContext> context = request.getFillContexts();
        AssistStructure structure = context.get(context.size() - 1).getStructure();

        AutofillContext afcx = gatherInputFields(structure);

        RemoteViews suggestion_view = new RemoteViews(getPackageName(), android.R.layout.simple_list_item_1);
        suggestion_view.setTextViewText(android.R.id.text1, "an_example_id (" + afcx.m_domain + ")");

        if (afcx.m_fields.isEmpty())
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
                        .setValue(afcx.m_fields.get(0).getAutofillId(), AutofillValue.forText("an_example_id"))
                        .setValue(afcx.m_fields.get(1).getAutofillId(), AutofillValue.forText("an_example_pw"))
                        .build())
                .build();

        callback.onSuccess(res);
    }

    protected static AutofillContext gatherInputFields(AssistStructure structure) {
        AutofillContext afcx = new AutofillContext();

        int nodes = structure.getWindowNodeCount();

        for (int i = 0; i < nodes; i++) {
            WindowNode windowNode = structure.getWindowNodeAt(i);
            ViewNode viewNode = windowNode.getRootViewNode();
            traverseNode(viewNode, afcx);
        }

        return afcx;
    }

    protected static void traverseNode(ViewNode viewNode, AutofillContext afcx) {

        if (viewNode.getClassName().contains("EditText")) {

            // ignore web browsers url bar.
            if (!"url_bar".equals(viewNode.getIdEntry())) {
                Log.v(TAG, "EditText: html_entity_id=" + viewNode.getIdEntry());
                afcx.m_fields.add(viewNode);
            } else {
                Log.v(TAG,"domain: " + viewNode.getWebDomain());
                afcx.m_domain = viewNode.getWebDomain();
            }
        }

        for (int i = 0; i < viewNode.getChildCount(); i++) {
            ViewNode childNode = viewNode.getChildAt(i);
            traverseNode(childNode, afcx);
        }
    }

    @Override
    public void onSaveRequest(@NonNull SaveRequest request, @NonNull SaveCallback callback) {

    }
}