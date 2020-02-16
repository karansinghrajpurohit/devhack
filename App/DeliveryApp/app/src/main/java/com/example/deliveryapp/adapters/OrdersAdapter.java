package com.example.deliveryapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.deliveryapp.R;
import com.example.deliveryapp.model.Orders;

import java.util.List;

/**
 * Created by Anirudh on 26/03/18.
 */
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>{

    Context context;
    List<Orders> ordersList;
    OnItemClickListener listener;

    public OrdersAdapter(Context context, List<Orders> ordersList, OnItemClickListener listener) {
        this.context = context;
        this.ordersList = ordersList;
        this.listener = listener;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.order_item,null);
        return new OrderViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;

        Orders order = ordersList.get(position);
        holder.customeName.setText("Customer Name: "+order.getCustName());
        holder.customeName.getLayoutParams().width = width;

        holder.orderId.setText("Order Id: "+order.getOrderId());
        holder.orderId.getLayoutParams().width = width;

        holder.productName.setText("Product Name: "+order.getProductName());
        holder.productName.getLayoutParams().width = width;

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position, ordersList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder{
        LinearLayout mainLayout;
        TextView orderId,productName,customeName;
        public OrderViewHolder(View itemView) {
            super(itemView);
            mainLayout = (LinearLayout) itemView.findViewById(R.id.mainLayout);
            orderId = (TextView) itemView.findViewById(R.id.orderId);
            productName = (TextView) itemView.findViewById(R.id.productName);
            customeName = (TextView) itemView.findViewById(R.id.customeName);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position, Orders order);
    }
}
