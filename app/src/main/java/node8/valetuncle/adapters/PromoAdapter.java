package node8.valetuncle.adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import node8.valetuncle.PromoDetailActivity;
import node8.valetuncle.R;
import node8.valetuncle.core.models.Promo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

/**
 * Created by stevsut on 11/13/2015.
 */
public class PromoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;
    private LayoutInflater mInflater;
    private RealmResults<Promo> mHistoryItem;
    private int mPreviousPosition = 0;

    public PromoAdapter(Activity mActivity,
                        RealmResults<Promo> mHistoriesItem) {
        this.mActivity = mActivity;
        this.mHistoryItem = mHistoriesItem;
        this.mInflater = LayoutInflater.from(mActivity);
    }

    public void setOrders(RealmResults<Promo> mHistoriesItem) {
        this.mHistoryItem = mHistoriesItem;
        notifyDataSetChanged();
    }


    public RealmResults<Promo> getOrders() {
        return this.mHistoryItem;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(mInflater.inflate(R.layout.promo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Promo historyItem = mHistoryItem.get(position);
        HistoryViewHolder mHistoryViewHolder = (HistoryViewHolder) holder;

        mHistoryViewHolder.promoTV.setText(historyItem.getPromoCode());
//        mHistoryViewHolder.addressTV.setText(historyItem.getAddress());
//        mHistoryViewHolder.dateTV.setText(historyItem.getDate());
//
//        if(!TextUtils.isEmpty(order.getStatusEnum())){
//            if(order.getStatusEnum().equals("GO")) {
//                mHistoryViewHolder.onJob.setVisibility(View.VISIBLE);
//            }else{
//
//                mHistoryViewHolder.onJob.setVisibility(View.GONE);
//            }
//        }
//        mHistoryViewHolder.dateTV.setText(order.getDisplayStartRent());
//        mHistoryViewHolder.pickupTV.setText(order.getPickupLocation());
//        mHistoryViewHolder.priceTV.setText("Rp "+ String.format("%,d",Long.parseLong(Integer.toString(order.getPrice()))));
//        mHistoryViewHolder.guestTV.setText(order.getName());

//        if (position > mPreviousPosition) {
//            AnimationView.animateY(holder, true);
//        } else {
//            AnimationView.animateY(holder, false);
//        }
        mPreviousPosition = position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mHistoryItem.size();
    }


    public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.promoTV) TextView promoTV;

        @OnClick(R.id.detailTV) void toDetail(){
            changeToDetail();
        }

        HistoryViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

//            detailBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    changeToSummary();
//                }
//            });
//            v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Toast.makeText(mActivity, "asd", Toast.LENGTH_SHORT).show();
////                    changetoDetail();
////                    changeToSummary();
//                }
//            });
        }
        private void changeToDetail(){
//
            final Promo promo = mHistoryItem.get(getAdapterPosition());
            Intent intent = new Intent(mActivity.getApplicationContext(), PromoDetailActivity.class);

            intent.putExtra("Id",promo.getId());
            mActivity.startActivity(intent);

        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View v) {

        }
    }

    /**
     * method to remove  an item
     * @param position
     */
    public void remove(int position) {
        mHistoryItem.remove(position);
        notifyItemRemoved(position);
    }

}