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

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private AboutUsContract.Presenter mAboutUsPresenter;

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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            mContext = context;
            mListener.onAboutUsFragmentAttach();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setPresenter(AboutUsContract.Presenter presenter) {
        this.mAboutUsPresenter = presenter;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onAboutUsFragmentAttach();
    }
}
