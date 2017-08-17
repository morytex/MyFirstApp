package br.com.moryta.myfirstapp.aboutus;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.moryta.myfirstapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsFragment extends Fragment implements AboutUsContract.View {
    private static final String TAG = "AboutUsFragment";

    private AboutUsContract.Presenter mAboutUsPresenter;
    private Context mContext;

    @BindView(R.id.tvVersion)
    TextView tvVersion;

    public AboutUsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment AboutUsFragment.
     */
    public static AboutUsFragment newInstance() {
        AboutUsFragment fragment = new AboutUsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(AboutUsFragment.this, view);

        String version = null;
        try {
            version = this.mContext
                    .getPackageManager()
                    .getPackageInfo(this.mContext.getPackageName(), 0)
                    .versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "AboutUsFragment.onCreateView: Null pointer, see stack trace", e);
        }

        version = mAboutUsPresenter
                .buildVersionText(getString(R.string.about_us_version_template), version);

        this.tvVersion.setText(version);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setPresenter(AboutUsContract.Presenter presenter) {
        this.mAboutUsPresenter = presenter;
    }
}
