package local.martic20.img;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by marti.casas on 17/05/17.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ElementViewHolder> {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ElementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView personName;
        ImageView personPhoto;
        Button order;

        ElementViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.name);
            personPhoto = (ImageView)itemView.findViewById(R.id.img);
            order = (Button)itemView.findViewById(R.id.order);

            order.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (v.getId() == order.getId()){
               Intent intent= new Intent(v.getContext(), Description_Activity.class);

                intent.putExtra("ID", getAdapterPosition());
                v.getContext().startActivity(intent);


            }
        }
    }


    List<Elements> elements;


    public MyAdapter(List<Elements> element){
        this.elements = element;

    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    public ElementViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        ElementViewHolder evh = new ElementViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ElementViewHolder personViewHolder, int i) {
        personViewHolder.personName.setText(elements.get(i).name);
        personViewHolder.personPhoto.setImageResource(elements.get(i).img);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}



