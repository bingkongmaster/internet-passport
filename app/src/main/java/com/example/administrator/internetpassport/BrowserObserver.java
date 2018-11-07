package com.example.administrator.internetpassport;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class BrowserObserver extends AccessibilityService {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (AccessibilityEvent.eventTypeToString(event.getEventType()).contains("WINDOW")) {
            AccessibilityNodeInfo nodeInfo = event.getSource();
            Log.e("IP", AccessibilityEvent.eventTypeToString(event.getEventType()));
            debugViewHierarchy(nodeInfo, 0);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void debugViewHierarchy(AccessibilityNodeInfo info, int depth) {
        if (info == null)
            return;
        StringBuilder log = new StringBuilder();
        for (int i = 0; i < depth; i++)
            log.append('-');

        Log.d("IP",  log.toString() + info.getClassName() + " " + info.getViewIdResourceName() +
                " \"" + info.getText() + "\"");

        for (int i = 0; i < info.getChildCount(); i++)
        {
            AccessibilityNodeInfo child = info.getChild(i);
            debugViewHierarchy(child, depth + 1);
            if (child != null)
                child.recycle();
        }
    }

    /* this code came from: https://stackoverflow.com/a/40100222 */

    public void getUrls(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null)
            return;
        if (nodeInfo.getText() != null && nodeInfo.getText().length() > 0)
            System.out.println(nodeInfo.getText() + " class: " + nodeInfo.getClassName());
        for (int i = 0; i < nodeInfo.getChildCount(); i++)
        {
            AccessibilityNodeInfo child = nodeInfo.getChild(i);
            getUrls(child);
            if (child != null) {
                child.recycle();
            }
        }
    }

    @Override
    public void onServiceConnected() {
        Log.d("BrowserObserver", "is bound to system");
    }

    @Override
    public void onInterrupt() {

    }
}
