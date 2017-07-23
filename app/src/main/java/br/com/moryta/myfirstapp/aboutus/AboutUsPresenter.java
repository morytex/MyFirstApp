package br.com.moryta.myfirstapp.aboutus;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.Formatter;
import java.util.IllegalFormatException;

import static android.content.ContentValues.TAG;

/**
 * Created by moryta on 22/07/2017.
 */

public class AboutUsPresenter implements AboutUsContract.Presenter {
    private Context context;
    private AboutUsContract.View mAboutUsView;

    public AboutUsPresenter(Context context, AboutUsContract.View view) {
        this.context = context;
        this.mAboutUsView = view;
    }

    @Override
    public void start() {
        // Not necessary, we don't start anything on start/resume lifecycle
    }

    @Override
    public String buildVersionText(String template) {
        String formattedVersion = "";

        try {
            String version = this.context.getPackageManager()
                        .getPackageInfo(this.context.getPackageName(), 0)
                        .versionName;

            Formatter formatter = new Formatter();
            formattedVersion = formatter.format(template, version).toString();

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "AboutUsPresenter.buildVersionText: Package "
                + this.context.getPackageName() + " not found"
                , e);
        } catch (IllegalFormatException e) {
            Log.e(TAG
                    , "AboutUsPresenter.buildVersionText: Invalid template or version string"
                    , e);
        } catch (NullPointerException e) {
            Log.e(TAG
                    , "AboutUsPresenter.buildVersionText: Null pointer, see stack trace"
                    , e);
        }

        return formattedVersion;
    }
}
