package br.com.moryta.myfirstapp.aboutus;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Strings;

import java.util.Formatter;
import java.util.IllegalFormatException;

import static com.google.common.base.Preconditions.checkNotNull;

import static android.content.ContentValues.TAG;

/**
 * Created by moryta on 22/07/2017.
 */

public class AboutUsPresenter implements AboutUsContract.Presenter {

    private AboutUsContract.View mAboutUsView;

    public AboutUsPresenter(@NonNull AboutUsContract.View view) {
        this.mAboutUsView = checkNotNull(view, "view cannot be null!");
        this.mAboutUsView.setPresenter(this);
    }

    @Override
    public void start() {
        // Do something
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
