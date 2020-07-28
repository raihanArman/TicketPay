package id.co.myproject.ticketpay.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import id.co.myproject.ticketpay.R;

public class MallViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_mall_name, tv_mall_address;

    public MallViewHolder(View itemView) {
        super(itemView);
        tv_mall_name = itemView.findViewById(R.id.mall_name);
        tv_mall_address = itemView.findViewById(R.id.mall_address);
    }




}
