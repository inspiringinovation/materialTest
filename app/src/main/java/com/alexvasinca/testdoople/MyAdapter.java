package com.alexvasinca.testdoople;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Alex Vasinca on 02/05/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    /*
    * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
    * It means that the viewHolder will represent my icon and the text next to it in the NavDrawer.
    * */

    //Used to store and pass the object that implements to the interface
    private ClickListener clickListener;

    private Context context;

    private LayoutInflater inflater;

    List<Information> data = Collections.emptyList();

    public MyAdapter(Context context, List<Information> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //every time a new row is to be displayed
        //the onCreateViewHolder will be called

        //The Layout Inflater converts XML appearance definition into View objects
        //It's a time consuming operation as it's recursively convert all XML children into objects
        View view = inflater.inflate(R.layout.custom_row, viewGroup, false);
        Log.d("Alex", "onCreateViewHolder called");
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        Information current = data.get(i);
        Log.d("Alex", "onBindViewHolder called " + i);
        holder.text.setText(current.title);
        holder.icon.setImageResource(current.iconId);
    }

    /*
    *  Sets the object that implements the interface
    * */
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    /*
     * This piece of s**t gave me a hard time as nothing appeared on the NavDrawer
     *
     * Must never forget to do this ever again !
     */
    public int getItemCount() {
        return data.size();
    }

    /*
    *  The viewHolder's purpose is find things once and then cast them
    *  because findById and always creating new objects are expensive operations
    *  that mai affect performance of the device.
    * */
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            text = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, SubActivity.class));
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }
}
