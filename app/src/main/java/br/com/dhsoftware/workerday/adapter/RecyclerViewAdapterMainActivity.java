package br.com.dhsoftware.workerday.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.dhsoftware.workerday.FragmentController;
import br.com.dhsoftware.workerday.MainActivity;
import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.dao.Dao;
import br.com.dhsoftware.workerday.fragments.AddRegistryFragment;
import br.com.dhsoftware.workerday.model.Registry;
import br.com.dhsoftware.workerday.util.DateUtil;
import br.com.dhsoftware.workerday.util.Money;
import br.com.dhsoftware.workerday.util.SalaryUtil;

public class RecyclerViewAdapterMainActivity extends RecyclerView.Adapter<RecyclerViewAdapterMainActivity.ViewHolder> {

    ArrayList<Registry> registries;
    Context context;
    RecyclerView recyclerView;
    FragmentManager fragmentManager;

    public RecyclerViewAdapterMainActivity(ArrayList<Registry> registries, Context context, RecyclerView recyclerView, FragmentManager fragmentManager) {
        this.registries = registries;
        this.context = context;
        this.recyclerView = recyclerView;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_registry, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Registry registry = registries.get(position);

        holder.textViewDate.setText("Data: " + registry.getDayString());
        holder.textViewDay.setText(DateUtil.getInstanceDateUtil().nameOfDay(registry.getDay()));
        String timeWorked = DateUtil.getInstanceDateUtil().calculateTimeFromRegistryToString(registry);
        holder.textViewTimeWorked.setText("Horas trabalhadas: " + timeWorked);
        String extraTime = DateUtil.getInstanceDateUtil().calculateExtraTimeFromRegistryToString(registry);
        holder.textViewExtraTime.setText("Hora extra realizada: " + extraTime);
        SalaryUtil salaryUtil = new SalaryUtil();
        extraTime = extraTime.substring(0, extraTime.length() - 1);

        Money money = new Money();
        holder.textViewDayValue.setText("Valor do dia: R$ " +
                money.doubleToStringMoney(String.valueOf(salaryUtil.calculateSalaryPerDay(registry, context, extraTime))));


    }


    @Override
    public int getItemCount() {
        return registries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView textViewDate, textViewDay, textViewTimeWorked, textViewExtraTime, textViewDayValue;
        protected ImageView imageViewDelete;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            textViewDate = (TextView) itemView.findViewById(R.id.list_view_main_activity_date);
            textViewDay = (TextView) itemView.findViewById(R.id.list_view_main_activity_day);
            textViewTimeWorked = (TextView) itemView.findViewById(R.id.list_view_main_activity_time_worked);
            textViewExtraTime = (TextView) itemView.findViewById(R.id.list_view_main_activity_time_extra_worked);
            textViewDayValue = (TextView) itemView.findViewById(R.id.list_view_main_activity_day_value);
            imageViewDelete = (ImageView) itemView.findViewById(R.id.imageView_remove_listView);

            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerView.getChildAdapterPosition(itemView);
                    Dao dao = new Dao(context);
                    dao.deleteRegistryFromDao(registries.get(position));
                    dao.close();


                    registries.remove(position);
                    notifyItemRemoved(position);

                    /*registries.remove(position);
                    recyclerView.removeViewAt(position);
                    .notifyItemRemoved(position);
                    mAdapter.notifyItemRangeChanged(position, list.size());*/

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerView.getChildAdapterPosition(itemView);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("registry", registries.get(position));

                    AddRegistryFragment addRegistryFragment = new AddRegistryFragment();
                    addRegistryFragment.setArguments(bundle);

                    FragmentController fragmentController = new FragmentController(fragmentManager);
                    fragmentController.setFragmentAddRegistry(addRegistryFragment);
                }
            });
        }
    }
}