package team.lf.task32;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;

public class MainActivity extends AppCompatActivity implements SampleAdapter.Callback {

    private RecyclerView mRecyclerView;
    private SampleAdapter mAdapter;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new SampleAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemAnimator itemAnimator = new SampleItemAnimator();
        mRecyclerView.setItemAnimator(itemAnimator);
        recyclerMove(mRecyclerView);


    }


    @Override
    public void onItemClick(View view) {
        int itemPosition = mRecyclerView.getChildAdapterPosition(view);
        if (itemPosition != RecyclerView.NO_POSITION) {
            mAdapter.changeItemNumber(itemPosition);
        }
    }

    public void recyclerMove(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float width = displayMetrics.widthPixels / displayMetrics.density;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -width * 2, 0f);
        animator.setDuration(2000);
        animator.addUpdateListener(valueAnimator -> view.setVisibility(View.VISIBLE));
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_item: {
                mAdapter.addItem();
                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                break;
            }
            case R.id.remove_item: {
                if (mAdapter.getItemCount() > 0)
                    mAdapter.removeItem();
                break;
            }

        }


        return super.onOptionsItemSelected(item);
    }
}
