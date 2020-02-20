package com.example.iplmarket;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    private DecimalFormat myFormatter = new DecimalFormat("###,###원");

    private ArrayList<item_class> item_list;

    public ExpandableListAdapter(ArrayList<item_class> item_list) {
        this.item_list = item_list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view;
        switch (type) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.one_list_layout2, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
                LayoutInflater child_inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = child_inflater.inflate(R.layout.one_list_layout, parent, false);
                ListChildViewHolder child = new ListChildViewHolder(view);
                return child;
        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final item_class item = item_list.get(position);
        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = item;
                itemController.header_title.setText(item.content_text);
                if (item.invisibleChildren == null) {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                } else {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                }
                itemController.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<item_class>();
                            int count = 0;
                            int pos = item_list.indexOf(itemController.refferalItem);
                            while (item_list.size() > pos + 1 && item_list.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(item_list.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                        } else {
                            int pos = item_list.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (item_class i : item.invisibleChildren) {
                                item_list.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                            item.invisibleChildren = null;
                        }
                    }
                });
                break;
            case CHILD:
                final ListChildViewHolder childController = (ListChildViewHolder) holder;
                Glide.with(((ListChildViewHolder) holder).image.getContext()).load(item_list.get(position).getImage_link()).into(((ListChildViewHolder) holder).image);
                childController.content_text.setText(item_list.get(position).content_text);
                childController.quantity_text.setText("수량: " + item_list.get(position).getQuantity());
                childController.price_text.setText("가격: " + myFormatter.format(item_list.get(position).getTotalPrice()) );
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return item_list.get(position).type;
    }


    @Override
    public int getItemCount() {
        return item_list.size();
    }

    private class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;
        public item_class refferalItem;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = itemView.findViewById(R.id.header_title);
            btn_expand_toggle = itemView.findViewById(R.id.btn_expand_toggle);
        }
    }


    private class ListChildViewHolder extends RecyclerView.ViewHolder {

        private TextView quantity_text, content_text, price_text;
        private ImageView image;

        public ListChildViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            quantity_text = itemView.findViewById(R.id.quantity_text);
            content_text = itemView.findViewById(R.id.content_text);
            price_text = itemView.findViewById(R.id.price_text);
        }
    }

    public static class item_class {
        private String content_text;
        private String image_link;
        private int quantity;
        private int total_price;    //상품개수 * 가격

        public int type;
        public ArrayList<item_class> invisibleChildren;


        item_class(int type, String content_text, String image_link, int price, int quantity)
        {
            this.type = type;
            this.content_text = content_text;
            this.image_link = image_link;
            this.quantity = quantity;
            this.total_price = price*quantity;
        }

        public String getImage_link() { return image_link; }
        public int getQuantity() { return quantity; }
        public int getTotalPrice() { return total_price; }
    }
}
