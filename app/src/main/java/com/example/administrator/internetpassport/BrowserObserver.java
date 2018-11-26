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
import android.util.Pair;
import android.view.autofill.AutofillValue;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * reference: https://developer.android.com/guide/topics/text/autofill-services
 */
public class BrowserObserver extends AutofillService {

    private static final String TAG = "BrowserObserver";
    static final ArrayList<Pair<String, DB_KEYS>> entity_help;
    static {
        entity_help = new ArrayList<>();
        entity_help.add(new Pair<>("id", DB_KEYS.K_ID));
        entity_help.add(new Pair<>("password", DB_KEYS.K_PW));
        entity_help.add(new Pair<>("pw", DB_KEYS.K_PW));
        entity_help.add(new Pair<>("pswd", DB_KEYS.K_PW));
        entity_help.add(new Pair<>("email", DB_KEYS.K_EMAIL));
        entity_help.add(new Pair<>("phone", DB_KEYS.K_PHONE));
        entity_help.add(new Pair<>("name", DB_KEYS.K_NAME));
        entity_help.add(new Pair<>("username", DB_KEYS.K_ID));
        entity_help.add(new Pair<>("name1", DB_KEYS.K_NAME1));
        entity_help.add(new Pair<>("name2", DB_KEYS.K_NAME2));
        entity_help.add(new Pair<>("first name", DB_KEYS.K_NAME1));
        entity_help.add(new Pair<>("last name", DB_KEYS.K_NAME2));
    }

     enum DB_KEYS {
        K_ID,
        K_PW,
        K_EMAIL,
        K_PHONE,
        K_NAME,
        K_NAME1,
        K_NAME2;

         @Override public String toString() {
            switch (this) {
                case K_ID: return "Id";
                case K_PW: return "Password";
                case K_EMAIL: return "Email";
                case K_PHONE: return "Phone number";
                case K_NAME: return "Full name";
                case K_NAME1: return "First name";
                case K_NAME2: return "Second name";
            }
            return "";
         }
    }

    static final HashMap<DB_KEYS, String> example_data;
    static {
        example_data = new HashMap<>();
        example_data.put(DB_KEYS.K_ID, "kaist123");
        example_data.put(DB_KEYS.K_PW, "0000");
        example_data.put(DB_KEYS.K_EMAIL, "kaist123@kaist.ac.kr");
        example_data.put(DB_KEYS.K_PHONE, "010-1234-5678");
        example_data.put(DB_KEYS.K_NAME, "Lee Jin Woo");
        example_data.put(DB_KEYS.K_NAME1, "Jin Woo");
        example_data.put(DB_KEYS.K_NAME2, "Lee");
    }

    static class AutofillContext {
        ArrayList<Pair<ViewNode, DB_KEYS>> m_fields;
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

        /**
         * Cannot load autofill information until login.
         */
        if (!LoginInfo.getInstance().m_logined) {
            Log.v(TAG, "you have to login first!");
            callback.onSuccess(null);
            return;
        }

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

        Dataset.Builder dataset = new Dataset.Builder(suggestion_view);
        StringBuilder msg = new StringBuilder();
        for (Pair<ViewNode, DB_KEYS> entry : afcx.m_fields) {
            ViewNode node = entry.first;
            DB_KEYS key = entry.second;

            dataset.setValue(node.getAutofillId(), AutofillValue.forText(example_data.get(key)));
            if (msg.length() != 0)
                msg.append(", ");
            msg.append(key);
        }
        msg.append(" (" + afcx.m_domain + ")");

        suggestion_view.setTextViewText(android.R.id.text1, msg.toString());

        FillResponse res = new FillResponse.Builder()
                .addDataset(dataset.build())
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
                String hint = viewNode.getHint();
                DB_KEYS k;

                k = matchHintWithKey(viewNode.getHint());
                if (k == null)
                    k = matchHintWithKey(viewNode.getIdEntry());


                Log.v(TAG, "EditText: html_entity_id=" + viewNode.getIdEntry() +
                        " hint=" + viewNode.getHint());
                if (k != null)
                    afcx.m_fields.add(new Pair<ViewNode, DB_KEYS>(viewNode, k));

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

    protected static DB_KEYS matchHintWithKey(String hint) {
        if (hint == null) return null;

        DB_KEYS k = null;

        for (Pair<String, DB_KEYS> entry : entity_help) {
            String key = entry.first;
            DB_KEYS val = entry.second;

            if (hint.toLowerCase().contains(key)) {
                k = val;
            }
        }

        return k;
    }

    @Override
    public void onSaveRequest(@NonNull SaveRequest request, @NonNull SaveCallback callback) {

    }
}