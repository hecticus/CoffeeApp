package com.hecticus.eleta.base;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hecticus.eleta.R;

import butterknife.BindView;

/**
 * Created by roselyn545 on 15/9/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.linear_layout)
    protected LinearLayout mainLinearLayout;

    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

    public abstract void initViews();
    public abstract void initString();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            this.finish();

        return true;
    }
}
