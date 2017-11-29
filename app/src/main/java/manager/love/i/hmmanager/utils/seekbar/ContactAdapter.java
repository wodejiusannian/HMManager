package manager.love.i.hmmanager.utils.seekbar;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import manager.love.i.hmmanager.R;

/**
 * @Author: duke
 * @DateTime: 2016-08-12 15:34
 * @Description:
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    public static final int SHOW_HEADER_VIEW = 1;   //显示header
    public static final int DISMISS_HEADER_VIEW = 2;//隐藏header
    private ArrayList<Contact> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public ContactAdapter(ArrayList<Contact> list) {
        this.list = list;
    }

    public void updateData(ArrayList<Contact> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.seekbar_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, final int position) {
        if (list == null || list.size() <= 0)
            return;
        final Contact contact = list.get(position);
        holder.tvHeader.setText(contact.firstPinYin);
        holder.tvName.setText(contact.name);
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(holder.getLayoutPosition(), contact);
            }
        });
        if (position == 0) {
            holder.tvHeader.setText(contact.firstPinYin);
            holder.tvHeader.setVisibility(View.VISIBLE);
        } else {
            if (!TextUtils.equals(contact.firstPinYin, list.get(position - 1).firstPinYin)) {
                holder.tvHeader.setVisibility(View.VISIBLE);
                holder.tvHeader.setText(contact.firstPinYin);
                holder.itemView.setTag(SHOW_HEADER_VIEW);
            } else {
                holder.tvHeader.setVisibility(View.GONE);
                holder.itemView.setTag(DISMISS_HEADER_VIEW);
            }
        }
        holder.itemView.setContentDescription(contact.firstPinYin);
    }

    @Override
    public int getItemCount() {
        return (list == null || list.size() <= 0) ? 0 : list.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView tvHeader;
        public TextView tvName;

        public ContactViewHolder(View itemView) {
            super(itemView);
            tvHeader = (TextView) itemView.findViewById(R.id.tv_header);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Contact contact);
    }
}