package helpers;

/**
 * Created by adarsh on 7/12/16.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.WindowManager;

import com.example.adarsh.knowprice.R;

import exception.KnowPriceJSONException;

public abstract class InvtAppAsyncTask extends AsyncTask<String, Void, String> {
    Context context;
    boolean showProgress = true;
    String progressMessage = "Loading....";
    public static String FAILURE = "Failure";
    public static String SUCCESS = "Success";
    ProgressDialog progressDialog;

    public InvtAppAsyncTask(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected String doInBackground(String... urls) {
        String result = FAILURE;
        try {
            process();
            result = SUCCESS;
        } catch (Exception exception) {
            Log.e("Application", exception.getMessage(), exception);
            ToastHelper.redToast(context, "Internal error occurred.");
        }
        return result;
    }

    public abstract void process();

    public abstract void afterPostExecute();

    @Override
    protected void onPostExecute(String result) {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        ToastHelper.processPendingToast();
        afterPostExecute();
    }

   // @TargetApi(Build.VERSION_CODES.LOLLIPOP

    @Override
    protected void onPreExecute() {
        Log.d("OneDelAsyncTask", "onPreExecuteStart");
        if (showProgress) {

            progressDialog = createProgressDialog(context);
            progressDialog.show();
        }
        Log.d("OneDelAsyncTask", "onPreExecuteEnd");
        ToastHelper.delayToasting();
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public InvtAppAsyncTask setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
        return this;
    }

    public String getProgressMessage() {
        return progressMessage;
    }

    public void setProgressMessage(String progressMessage) {
        this.progressMessage = progressMessage;
    }
    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progressdialoglayout);
        return dialog;
    }
}