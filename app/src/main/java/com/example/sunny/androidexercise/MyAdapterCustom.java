package com.example.sunny.androidexercise;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MyAdapterCustom extends RecyclerView.Adapter<MyAdapterCustom.MyViewHolder> {

    Activity activity;
    List<Integer> itemList;
    int itemSizePx;
    int rowCount;

    int animDuration = 300;
    boolean enableAnimation = false;
    int deletedPos;
    int itemSpacingPx;


    public MyAdapterCustom(Activity activity, List<Integer> itemList, int itemHeightDp, int rowCount, int animDuration, int itemSpacingDp) {
        this.itemList = itemList;
        this.activity = activity;

        itemSpacingPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, itemSpacingDp, activity.getResources().getDisplayMetrics());

        itemSizePx = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, itemHeightDp,
                activity.getResources().getDisplayMetrics()));
        this.rowCount = rowCount;
        this.animDuration = animDuration;
        deletedPos = itemList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);

        return new MyAdapterCustom.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        myViewHolder.tv_item.setText(String.valueOf(itemList.get(position)));
        myViewHolder.rl_parent.getLayoutParams().height = itemSizePx;
        myViewHolder.setIsRecyclable(false);

        TranslateAnimation translateAnimation;

        if (enableAnimation) {
            if (position > deletedPos) {
                if (position != 0 && position % rowCount == 0) {
                    //for first item of each row
                    translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                            Animation.RELATIVE_TO_SELF, rowCount - 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1);
                    translateAnimation.setDuration(animDuration);
                    translateAnimation.setStartOffset(((position / rowCount) - (deletedPos / rowCount)) * animDuration);
                    translateAnimation.setFillEnabled(true);

                    translateAnimation.setFillAfter(true);

                    myViewHolder.itemView.startAnimation(translateAnimation);
                } else {
                    translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                            Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
                    translateAnimation.setDuration(animDuration);

                    translateAnimation.setStartOffset(((position / rowCount) - (deletedPos / rowCount)) * animDuration);
                    translateAnimation.setFillEnabled(true);

                    translateAnimation.setFillAfter(true);
                    myViewHolder.itemView.startAnimation(translateAnimation);
                }

                if (position == itemList.size() - 1) {
                    translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            itemList.remove(deletedPos);
                            enableAnimation = false;
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            }
        }


        myViewHolder.rl_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                ObjectAnimator animation = ObjectAnimator.ofFloat(myViewHolder.itemView,
                        "rotationY", 0.0f, 180f);
                animation.setDuration(animDuration);
                animation.start();

                animation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        deletedPos = position;
                        enableAnimation = true;
                        if (position == itemList.size() - 1) {
                            itemList.remove(position);
                        }
                       // myViewHolder.itemView.setRotationY(0);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_parent;
        TextView tv_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rl_parent = (RelativeLayout) itemView.findViewById(R.id.rl_parent);
            tv_item = (TextView) itemView.findViewById(R.id.tv_item);
            rl_parent.setPadding(itemSpacingPx, itemSpacingPx, itemSpacingPx, itemSpacingPx);


        }
    }


}
