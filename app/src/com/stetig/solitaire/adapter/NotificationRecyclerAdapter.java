package com.stetig.solitaire.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stetig.solitaire.data.ServerNotification;
import com.stetig.solitaire.databinding.CardViewNotifcationBinding;
import com.stetig.solitaire.utils.Utils;

import java.util.ArrayList;

public abstract class NotificationRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ServerNotification.Notificationlist> recordsArrayList;

    public NotificationRecyclerAdapter(Context context, ArrayList<ServerNotification.Notificationlist> cartList) {
        this.context = context;
        this.recordsArrayList = cartList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardViewNotifcationBinding view = CardViewNotifcationBinding.inflate(layoutInflater, parent, false);

        return new CartProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CartProjectViewHolder currCardViewHolder = (CartProjectViewHolder) holder;
        try {
            if (recordsArrayList.get(position) != null) {
                currCardViewHolder.binding.opportunityName.setText(Utils.checkValueOrGiveDef(recordsArrayList.get(position).getOppname()));
                currCardViewHolder.binding.lstCallAtmStatus.setText(Utils.checkValueOrGiveDef(recordsArrayList.get(position).getType()));
            }

            Log.e(getClass().getName(), "onBindViewHolder: " + recordsArrayList.get(position).getNotificationid());

            currCardViewHolder.binding.mtrlCard.setOnClickListener(n -> {

                _markAsCompleted(recordsArrayList.get(position).getNotificationid(), recordsArrayList.get(position).getOppid(), recordsArrayList.get(position).getType());

//                OpportunityDetailsBottomSheet profileBottomSheet = new OpportunityDetailsBottomSheet(recordsArrayList.get(position).getOppid());
//                AppCompatActivity appCompatActivity = (AppCompatActivity) context;
//                profileBottomSheet.show(appCompatActivity.getSupportFragmentManager(), "profile_sheet");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void _markAsCompleted(String nId, String OppId, String type);

    @Override
    public int getItemCount() {
        return recordsArrayList.size();
    }

    static class CartProjectViewHolder extends RecyclerView.ViewHolder {
        CardViewNotifcationBinding binding;

        public CartProjectViewHolder(@NonNull CardViewNotifcationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
