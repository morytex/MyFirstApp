package br.com.moryta.myfirstapp.aboutus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.moryta.myfirstapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsFragment extends Fragment implements AboutUsContract.View {
    private AboutUsContract.Presenter mAboutUsPresenter;
    private Context mContext;

    @BindView(R.id.tvVersion)
    TextView tvVersion;

    public AboutUsFragment() {
        // Required empty public constructor
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

        String version = mAboutUsPresenter.buildVersionText(getString(R.string.about_us_version_template));
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
