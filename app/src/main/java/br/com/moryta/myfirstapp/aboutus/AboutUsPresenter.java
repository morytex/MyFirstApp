package br.com.moryta.myfirstapp.aboutus;

import android.util.Log;

import com.google.common.base.Strings;

import java.util.Formatter;
import java.util.IllegalFormatException;

import static android.content.ContentValues.TAG;

/**
 * Created by moryta on 22/07/2017.
 */

public class AboutUsPresenter implements AboutUsContract.Presenter {
    private AboutUsContract.View mAboutUsView;

    public AboutUsPresenter(AboutUsContract.View view) {
        this.mAboutUsView = view;
    }

    @Override
    public void start() {
        // Not necessary, we don't start anything on start/resume lifecycle
    }

    @Override
    public String buildVersionText(String template, String version) {
        if (Strings.isNullOrEmpty(version)) {
            return null;
        }

        String formattedVersion = null;
        try {
            Formatter formatter = new Formatter();
            formattedVersion = formatter.format(template, version).toString();

        } catch (IllegalFormatException e) {
            Log.e(TAG, "AboutUsPresenter.buildVersionText: Invalid template or version string", e);
        }

        return formattedVersion;
    }
}
