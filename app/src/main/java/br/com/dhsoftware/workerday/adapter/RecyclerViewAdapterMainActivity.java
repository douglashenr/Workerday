package br.com.dhsoftware.workerday.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.dao.Dao;
import br.com.dhsoftware.workerday.fragments.AddRegistryFragment;
import br.com.dhsoftware.workerday.model.Registry;
import br.com.dhsoftware.workerday.model.User;
import br.com.dhsoftware.workerday.util.DateUtil;

import br.com.dhsoftware.workerday.util.SalaryUtil;

public class RecyclerViewAdapterMainActivity extends RecyclerView.Adapter<RecyclerViewAdapterMainActivity.ViewHolder> {

    private ArrayList<Registry> registries;
    private Context context;
    private RecyclerView recyclerView;
    private FragmentManager fragmentManager;
    private User user;

    public RecyclerViewAdapterMainActivity(ArrayList<Registry> registries, Context context, RecyclerView recyclerView, FragmentManager fragmentManager) {
        this.registries = registries;
        this.context = context;
        this.recyclerView = recyclerView;
        this.fragmentManager = fragmentManager;
        user = new User(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_registry, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Registry registry = registries.get(position);

        holder.textViewDate.setText("Data: " + registry.getDayString());
        holder.textViewDay.setText(DateUtil.getInstanceDateUtil().nameOfDay(registry.getDay()));
        String timeWorked = DateUtil.getInstanceDateUtil().calculateTimeFromRegistryToString(registry);
        holder.textViewTimeWorked.setText("Horas trabalhadas: " + timeWorked);
        if(!user.isCompTime()) {
            String extraTime = DateUtil.getInstanceDateUtil().calculateExtraTimeFromRegistryToString(registry);
            holder.textViewExtraTime.setText("Hora extra realizada: " + extraTime);
            SalaryUtil salaryUtil = new SalaryUtil();
            extraTime = extraTime.substring(0, extraTime.length() - 1);


            holder.textViewDayValue.setText("Valor do dia: R$ " +
                    salaryUtil.doubleToStringMoney(user.getGrossSalary().getSalaryPerDay(registry, extraTime)));

        }else{
            holder.textViewDayValue.setVisibility(View.INVISIBLE);
            String extraTime = DateUtil.getInstanceDateUtil().calculateExtraTimeFromRegistryToString(registry);
            holder.textViewExtraTime.setText("Para o banco de horas: " + extraTime);
        }


    }


    @Override
    public int getItemCount() {
        return registries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDate, textViewDay, textViewTimeWorked, textViewExtraTime, textViewDayValue;
        ImageView imageViewDelete;


        ViewHolder(@NonNull final View itemView) {
            super(itemView);

            textViewDate = itemView.findViewById(R.id.list_view_main_activity_date);
            textViewDay = itemView.findViewById(R.id.list_view_main_activity_day);
            textViewTimeWorked = itemView.findViewById(R.id.list_view_main_activity_time_worked);
            textViewExtraTime = itemView.findViewById(R.id.list_view_main_activity_time_extra_worked);
            textViewDayValue = itemView.findViewById(R.id.list_view_main_activity_day_value);
            imageViewDelete = itemView.findViewById(R.id.imageView_remove_listView);

            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = recyclerView.getChildAdapterPosition(itemView);

                    AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(context);
                    builderAlertDialog.setMessage(R.string.warning_delete);
                    builderAlertDialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Dao dao = new Dao(context);
                            dao.deleteRegistryFromDao(registries.get(position));
                            dao.close();
                            registries.remove(position);
                            notifyItemRemoved(position);
                            dialog.cancel();
                        }
                    });
                    builderAlertDialog.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
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
