package org.sk.jih.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import org.sk.jih.MainApplication;


/**
 * This class is used to display custom progress dialog.
 *
 * @author Salim Shaikh
 */
public class CustomProgressDialog {
    private static ProgressDialog _progressDialog;

    /**
     * Method to show progress dialog
     *
     * @param activity     the activity context
     * @param messageResId the string resource id
     */
    public static void show(final Activity activity, final int messageResId) {
        try {
            if(activity != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            CustomProgressDialog._progressDialog = new ProgressDialog(activity);
                            CustomProgressDialog._progressDialog.setMessage(activity
                                    .getString(messageResId));
                            CustomProgressDialog._progressDialog.setIndeterminate(false);
                            CustomProgressDialog._progressDialog.setCancelable(false);
                            CustomProgressDialog._progressDialog.setCanceledOnTouchOutside(false);
                            CustomProgressDialog._progressDialog.show();
                        } catch (Exception e) {
                            Log.e(MainApplication.LOG_TAG,
                                    "CustomProgressDialog: show: run: Exception->"
                                            + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            Log.e(MainApplication.LOG_TAG, "CustomProgressDialog: show: Exception->"
                    + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method to show progress dialog
     *
     * @param activity the activity context
     * @param message  the string message
     */
    public static void show(final Activity activity, final String message) {
        try {
            if(activity != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            CustomProgressDialog._progressDialog = new ProgressDialog(activity);
                            CustomProgressDialog._progressDialog.setMessage(message);
                            CustomProgressDialog._progressDialog.setIndeterminate(false);
                            CustomProgressDialog._progressDialog.setCancelable(true);
                            CustomProgressDialog._progressDialog.setCanceledOnTouchOutside(false);
                            CustomProgressDialog._progressDialog.show();
                        } catch (Exception e) {
                            Log.e(MainApplication.LOG_TAG,
                                    "CustomProgressDialog: show: run: Exception->"
                                            + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            Log.e(MainApplication.LOG_TAG, "CustomProgressDialog: show: Exception->"
                    + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method to dismiss previously showing progress dialog
     *
     * @param activity the activity context
     */
    public static void dismiss(Activity activity) {
        try {
            if(activity != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (CustomProgressDialog._progressDialog != null
                                    && CustomProgressDialog._progressDialog.isShowing()) {
                                CustomProgressDialog._progressDialog.dismiss();
                            }
                        } catch (Exception e) {
                            Log.e(MainApplication.LOG_TAG,
                                    "CustomProgressDialog: dismiss: run: Exception->"
                                            + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            Log.e(MainApplication.LOG_TAG, "CustomProgressDialog: dismiss: Exception->"
                    + e.getMessage());
            e.printStackTrace();
        }
    }
}
