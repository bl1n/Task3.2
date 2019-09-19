package team.lf.task32;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SampleItemAnimator extends DefaultItemAnimator {


    @Override
    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
        return true;

    }


    @NonNull
    @Override
    public ItemHolderInfo recordPreLayoutInformation(@NonNull RecyclerView.State state,
                                                     @NonNull RecyclerView.ViewHolder viewHolder,
                                                     int changeFlags,
                                                     @NonNull List<Object> payloads) {
        TextInfo textInfo = new TextInfo();
        textInfo.setFrom(viewHolder);
        return textInfo;
    }


    @NonNull
    @Override
    public ItemHolderInfo recordPostLayoutInformation(@NonNull RecyclerView.State state,
                                                      @NonNull RecyclerView.ViewHolder viewHolder) {
        TextInfo textInfo = new TextInfo();
        textInfo.setFrom(viewHolder);
        return textInfo;
    }


    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder,
                                 @NonNull final RecyclerView.ViewHolder newHolder,
                                 @NonNull ItemHolderInfo preInfo,
                                 @NonNull ItemHolderInfo postInfo) {
        final SampleAdapter.SampleViewHolder holder = (SampleAdapter.SampleViewHolder) newHolder;
        int width = holder.itemView.getWidth();
        final TextInfo preColorTextInfo = (TextInfo) preInfo;
        final TextInfo postColorTextInfo = (TextInfo) postInfo;
        int currentTextColor = holder.mTextView.getResources().getColor(R.color.colorPrimary);
        ObjectAnimator fadeToTransparent = ObjectAnimator.ofArgb(holder.mTextView, "textColor", currentTextColor, Color.TRANSPARENT);
        ObjectAnimator fadeFromTransparent = ObjectAnimator.ofArgb(holder.mTextView, "textColor", Color.TRANSPARENT, currentTextColor);
        AnimatorSet tcAnim = new AnimatorSet();
        tcAnim.playSequentially(fadeToTransparent, fadeFromTransparent);
        ObjectAnimator oldTextMove = ObjectAnimator.ofFloat(holder.mTextView, View.X, 0, width/2);
        ObjectAnimator newTextMove = ObjectAnimator.ofFloat(holder.mTextView, View.X, -width/2, 0);
        oldTextMove.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                holder.mTextView.setText(preColorTextInfo.text);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                holder.mTextView.setText(postColorTextInfo.text);
            }
        });

        AnimatorSet textAnim = new AnimatorSet();
        textAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        textAnim.playSequentially(oldTextMove, newTextMove);
        AnimatorSet overallAnim = new AnimatorSet();
        overallAnim.setDuration(1000).playTogether(tcAnim, textAnim);
        overallAnim.addListener(new AnimatorListenerAdapter() {

            @Override

            public void onAnimationEnd(Animator animation) {
                dispatchAnimationFinished(newHolder);

            }

        });
        overallAnim.start();
        return super.animateChange(oldHolder, newHolder, preInfo, postInfo);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
            View view = holder.itemView;
            SpringAnimation animation = new SpringAnimation(view, new FloatPropertyCompat<View>("translationY") {
                @Override
                public float getValue(View object) {
                    return view.getTranslationY();
                }

                @Override
                public void setValue(View object, float value) {

                    view.setTranslationY(value);
                }
            });
            SpringForce force = new SpringForce(0f);
            force.setStiffness(SpringForce.STIFFNESS_MEDIUM);
            force.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
            animation.setSpring(force);
            view.setY(300);
            animation.start();
        return super.animateAdd(holder);
    }

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {

        return super.animateRemove(holder);
    }

    private class TextInfo extends ItemHolderInfo {
        private String text;

        @NonNull
        @Override
        public ItemHolderInfo setFrom(@NonNull RecyclerView.ViewHolder holder) {
            if (holder instanceof SampleAdapter.SampleViewHolder) {
                SampleAdapter.SampleViewHolder viewHolder = (SampleAdapter.SampleViewHolder) holder;
                text = (String) viewHolder.mTextView.getText();
            }
            return super.setFrom(holder);
        }

    }

}
