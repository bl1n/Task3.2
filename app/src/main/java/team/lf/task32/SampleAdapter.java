package team.lf.task32;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.SampleViewHolder> {

    private Callback mCallback;
    private ArrayList<String> mDataset;


    SampleAdapter(Context context) {
        mDataset = dataset();
        if (context instanceof Callback) {
            mCallback = (Callback) context;
        }
    }

    private ArrayList<String> dataset() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add("Item " + i);
        }
        return list;
    }

    void addItem() {
        Random r = new Random();
        int size = mDataset.size();
        String element = "Item " + r.nextInt(100);
        mDataset.add(size, element);
        notifyItemInserted(size);
    }

    void removeItem(){
        mDataset.remove(0);
        notifyItemRemoved(0);
    }


    @NonNull
    @Override
    public SampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_holder, parent, false);
        textView.setOnClickListener(v -> mCallback.onItemClick(v));
        return new SampleViewHolder(textView);
    }


    @Override
    public void onBindViewHolder(@NonNull SampleViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position));
    }


    @Override
    public void onViewAttachedToWindow(@NonNull SampleViewHolder holder) {

        super.onViewAttachedToWindow(holder);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    void changeItemNumber(int position) {
        int number = new Random().nextInt(100);
        mDataset.set(position, "Item " + number);
        notifyItemChanged(position);

    }

    static class SampleViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        SampleViewHolder(TextView textView) {
            super(textView);
            mTextView = textView;
        }
    }


    interface Callback {
        void onItemClick(View view);
    }

}