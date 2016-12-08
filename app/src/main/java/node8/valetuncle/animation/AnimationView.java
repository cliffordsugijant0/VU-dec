package node8.valetuncle.animation;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by stevsut on 8/27/2015.
 */
public class AnimationView {
    /**
     * method provide animation for the recycler view
     * @param holder
     * @param goesDown
     */
    public static void animateY(RecyclerView.ViewHolder holder, boolean goesDown) {

        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown == true ? 100 : -100, 0);
        animatorTranslateY.setDuration(500);
        animatorTranslateY.start();
    }
}
