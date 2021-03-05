package org.sk.jih.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.util.Log;

import org.sk.jih.MainApplication;

/**
 * This class is used to display dialog.
 *
 * @author Salim Shaikh
 */
public class DialogHelper {
    private static Builder _builder;
    private static AlertDialog _dialog;

    /**
     * Method to display dialog.
     *
     * @param activity    the activity context
     * @param title       the string resource id of dialog title
     * @param mesage      the string resource id of dialog message
     * @param btnNegative the string resource id of dialog negative button
     * @param btnNeutral  the string resource id of dialog neutral button
     * @param btnPositive the string resource id of dialog positive button
     */
    public static void showDialog(final Activity activity, final int title,
                                  final int mesage, final int btnNegative, final int btnNeutral,
                                  final int btnPositive) {
        final DialogInterface.OnClickListener dialogButtonClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (DialogHelper._dialog != null && DialogHelper._dialog.isShowing()) {
                        DialogHelper._dialog.dismiss();
                    }

                    DialogHelper._builder = new Builder(activity);
                    DialogHelper._builder.setTitle(title);
                    DialogHelper._builder.setMessage(mesage);
                    if (btnNegative != -1) {
                        DialogHelper._builder.setNegativeButton(btnNegative,
                                dialogButtonClickListener);
                    }

                    if (btnNeutral != -1) {
                        DialogHelper._builder.setNeutralButton(btnNeutral,
                                dialogButtonClickListener);
                    }

                    if (btnPositive != -1) {
                        DialogHelper._builder.setPositiveButton(btnPositive,
                                dialogButtonClickListener);
                    }

                    DialogHelper._builder.setCancelable(false);

                    DialogHelper._dialog = DialogHelper._builder.create();
                    DialogHelper._dialog.setCanceledOnTouchOutside(false);
                    DialogHelper._dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(MainApplication.LOG_TAG, "DialogHelper: showDialog: e=>" + e);
                }
            }
        });
    }

    /**
     * Method to display dialog.
     *
     * @param activity    the activity context
     * @param title       the string resource id of dialog title
     * @param mesage      the string message
     * @param btnNegative the string resource id of dialog negative button
     * @param btnNeutral  the string resource id of dialog neutral button
     * @param btnPositive the string resource id of dialog positive button
     */
    public static void showDialog(final Activity activity, final int title,
                                  final String mesage, final int btnNegative, final int btnNeutral,
                                  final int btnPositive) {
        final DialogInterface.OnClickListener dialogButtonClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (DialogHelper._dialog != null && DialogHelper._dialog.isShowing()) {
                        DialogHelper._dialog.dismiss();
                    }

                    DialogHelper._builder = new Builder(activity);
                    DialogHelper._builder.setTitle(title);
                    DialogHelper._builder.setMessage(mesage);
                    if (btnNegative != -1) {
                        DialogHelper._builder.setNegativeButton(btnNegative,
                                dialogButtonClickListener);
                    }

                    if (btnNeutral != -1) {
                        DialogHelper._builder.setNeutralButton(btnNeutral,
                                dialogButtonClickListener);
                    }

                    if (btnPositive != -1) {
                        DialogHelper._builder.setPositiveButton(btnPositive,
                                dialogButtonClickListener);
                    }

                    DialogHelper._builder.setCancelable(false);

                    DialogHelper._dialog = DialogHelper._builder.create();
                    DialogHelper._dialog.setCanceledOnTouchOutside(false);
                    DialogHelper._dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(MainApplication.LOG_TAG, "DialogHelper: showDialog: e=>" + e);
                }
            }
        });
    }

    /**
     * Method to display dialog.
     *
     * @param activity        the activity context
     * @param title           the string resource id of dialog title
     * @param mesage          the string resource id of dialog message
     * @param btnNegative     the string resource id of dialog negative button
     * @param btnNeutral      the string resource id of dialog neutral button
     * @param btnPositive     the string resource id of dialog positive button
     * @param onClickListener the {@link DialogInterface.OnClickListener} to handle dialog button click
     *                        events.
     */
    public static void showDialog(final Activity activity, final int title,
                                  final int mesage, final int btnNegative, final int btnNeutral,
                                  final int btnPositive,
                                  final DialogInterface.OnClickListener onClickListener) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (DialogHelper._dialog != null && DialogHelper._dialog.isShowing()) {
                        DialogHelper._dialog.dismiss();
                    }

                    DialogHelper._builder = new Builder(activity);
                    DialogHelper._builder.setTitle(title);
                    DialogHelper._builder.setMessage(mesage);
                    if (btnNegative != -1) {
                        DialogHelper._builder.setNegativeButton(btnNegative, onClickListener);
                    }

                    if (btnNeutral != -1) {
                        DialogHelper._builder.setNeutralButton(btnNeutral, onClickListener);
                    }

                    if (btnPositive != -1) {
                        DialogHelper._builder.setPositiveButton(btnPositive, onClickListener);
                    }

                    DialogHelper._builder.setCancelable(false);

                    DialogHelper._dialog = DialogHelper._builder.create();
                    DialogHelper._dialog.setCanceledOnTouchOutside(false);
                    DialogHelper._dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(MainApplication.LOG_TAG, "DialogHelper: showDialog: e=>" + e);
                }
            }
        });
    }

    /**
     * Method to display dialog.
     *
     * @param activity        the activity context
     * @param title           the string resource id of dialog title
     * @param mesage          the string message
     * @param btnNegative     the string resource id of dialog negative button
     * @param btnNeutral      the string resource id of dialog neutral button
     * @param btnPositive     the string resource id of dialog positive button
     * @param onClickListener the {@link DialogInterface.OnClickListener} to handle dialog button click
     *                        events.
     */
    public static void showDialog(final Activity activity, final int title,
                                  final String mesage, final int btnNegative, final int btnNeutral,
                                  final int btnPositive,
                                  final DialogInterface.OnClickListener onClickListener) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (DialogHelper._dialog != null && DialogHelper._dialog.isShowing()) {
                        DialogHelper._dialog.dismiss();
                    }

                    DialogHelper._builder = new Builder(activity);
                    DialogHelper._builder.setTitle(title);
                    DialogHelper._builder.setMessage(mesage);
                    if (btnNegative != -1) {
                        DialogHelper._builder.setNegativeButton(btnNegative, onClickListener);
                    }

                    if (btnNeutral != -1) {
                        DialogHelper._builder.setNeutralButton(btnNeutral, onClickListener);
                    }

                    if (btnPositive != -1) {
                        DialogHelper._builder.setPositiveButton(btnPositive, onClickListener);
                    }

                    DialogHelper._builder.setCancelable(false);

                    DialogHelper._dialog = DialogHelper._builder.create();
                    DialogHelper._dialog.setCanceledOnTouchOutside(false);
                    DialogHelper._dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(MainApplication.LOG_TAG,"DialogHelper: showDialog: e=>" + e);
                }
            }
        });
    }
}
