package team.lf.task32;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class MainActivity extends AppCompatActivity implements SampleAdapter.Callback {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemAnimator mItemAnimator;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SampleAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mItemAnimator = new SampleItemAnimator();
        mRecyclerView.setItemAnimator(mItemAnimator);
        recyclerMove(mRecyclerView);


    }


    @Override

    public void onItemClick(View view) {
        int itemPosition = mRecyclerView.getChildAdapterPosition(view);
        if (itemPosition != RecyclerView.NO_POSITION) {
            ((SampleAdapter) mAdapter).changeItemNumber(itemPosition);
        }
    }

    public void recyclerMove(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float width = displayMetrics.widthPixels / displayMetrics.density;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX",-width*2,0f );
        animator.setDuration(1000);
        animator.setRepeatCount(1);
        animator.addUpdateListener(valueAnimator -> view.setVisibility(View.VISIBLE));
        animator.start();

    }

}
