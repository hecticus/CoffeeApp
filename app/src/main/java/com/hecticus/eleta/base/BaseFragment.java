package com.hecticus.eleta.base;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hecticus.eleta.R;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by roselyn545 on 15/9/17.
 */

public abstract class BaseFragment extends Fragment {

    @BindView(R.id.linear_layout)
    protected LinearLayout mainLinearLayout;

    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

    protected View rootView = null;

    protected Unbinder unbinder;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public abstract void initViews();

    public abstract void initString();
}
