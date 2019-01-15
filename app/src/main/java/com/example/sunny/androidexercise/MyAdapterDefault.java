package com.example.sunny.androidexercise;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MyAdapterDefault extends RecyclerView.Adapter<MyAdapterDefault.MyViewHolder> {

    Activity activity;
    List<Integer> itemList;
    int itemSizePx;
    int rowCount;

    int deletedPos;
    int animDuration;
    int itemSpacingPx;


    public MyAdapterDefault(Activity activity, List<Integer> itemList, int itemHeightDp, int rowCount, int animDuration, int itemSpacingDp) {
        this.itemList = itemList;
        this.activity = activity;
        this.animDuration=animDuration;

        itemSpacingPx = (int)TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, itemSpacingDp, activity.getResources().getDisplayMetrics());

        itemSizePx = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, itemHeightDp,
                activity.getResources().getDisplayMetrics()));
        this.rowCount = rowCount;
        deletedPos = itemList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);

        return new MyAdapterDefault.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        myViewHolder.tv_item.setText(String.valueOf(itemList.get(position)));
        myViewHolder.rl_parent.getLayoutParams().height = itemSizePx;

        myViewHolder.rl_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
               /* itemList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,getItemCount());*/

                ObjectAnimator animation = ObjectAnimator.ofFloat(view, "rotationY", 0.0f, 180f);
                animation.setDuration(animDuration);
                animation.setTarget(myViewHolder.rl_parent);

                animation.start();


                animation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                      /*  deletedPos = position;
                        enableAnimation = true;
                        if(position==itemList.size()-1){
                            itemList.remove(position);
                        }*/

                       itemList.remove(position);
                       notifyItemRemoved(position);
                       notifyItemRangeChanged(position,getItemCount());
                       myViewHolder.itemView.setRotationY(0);
                       myViewHolder.itemView.setAlpha(0f);

                       /*notifyDataSetChanged();*/
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
            rl_parent.setPadding(itemSpacingPx, itemSpacingPx, itemSpacingPx, itemSpacingPx);

            tv_item = (TextView) itemView.findViewById(R.id.tv_item);

        }
    }




}
