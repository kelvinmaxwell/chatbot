package com.example.chatbox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unchecked")

public class previousmanualadapter extends RecyclerView.Adapter<previousmanualadapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<get_set> contactList;
    private List<get_set> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView issuetype, description,solution,count;
        public RatingBar ratings;




        public MyViewHolder(View view) {
            super(view);

            issuetype = view.findViewById(R.id.issuettxt);

            description=view.findViewById(R.id.descrptiontxt);
            solution=view.findViewById(R.id.solutiontxtm);
            ratings=view.findViewById(R.id.ratingBar2);
            count=view.findViewById(R.id.count);



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public previousmanualadapter(Context context, List<get_set> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.previousemanual, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final get_set contact = contactListFiltered.get(position);
        holder.issuetype.setText(""+contact.getIssuetype()+" ");

        holder.description.setText(""+contact.getdescription()+" ");
        holder.solution.setText(""+contact.getSolution()+" ");
        try{
        holder.ratings.setRating(Float.parseFloat(contact.getRatings()));}
        catch (NullPointerException e)
        {}
        holder.count.setText("" + String.valueOf(((int) Float.parseFloat(contact.getCount()))) + " " + "requests");




//        holder.number.setText("OEM NUMBER  "+contact.getNumber());
//        holder.rating.setRating(contact.getRatings());
//
//        Glide.with(context)
//                .load("https://www.fitinpart.sg/image/cache/data/products/2741e31c64c208f49856b02f17453cba-228x152.jpg")
//                .into(holder.thumbnail);

//        holder.model.setText("model :"+contact.getModel());
//        holder.year.setText("year: "+contact.getYear());
//        holder.main_category.setText(""+contact.getMain_category());
//        holder.sub_category.setText(""+contact.getSub_category());
//        holder.partmaker.setText(""+contact.getPartmaker());


//        Glide.with(context)
//                .load(contact.getImage())
//                .apply(RequestOptions.circleCropTransform())
//                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<get_set> filteredList = new ArrayList<>();
                    for (get_set row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getProduct_name().toLowerCase().contains(charString.toLowerCase())||row.getPartmaker().toLowerCase().contains(charString.toLowerCase())||row.getModel().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<get_set>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(get_set contact);
    }
}