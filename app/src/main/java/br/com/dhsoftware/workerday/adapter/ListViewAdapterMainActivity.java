package br.com.dhsoftware.workerday.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.dao.Dao;
import br.com.dhsoftware.workerday.fragments.ListViewMainFragment;
import br.com.dhsoftware.workerday.model.Registry;
import br.com.dhsoftware.workerday.util.DateUtil;
import br.com.dhsoftware.workerday.util.Money;
import br.com.dhsoftware.workerday.util.SalaryUtil;
import br.com.dhsoftware.workerday.util.enumObservation;

public class ListViewAdapterMainActivity extends BaseAdapter {

    private List<Registry> registryArrayList;
    private final Activity activity;
    private ListViewMainFragment listViewMainFragment;

    public ListViewAdapterMainActivity(List<Registry> registryArrayList, Activity activity, ListViewMainFragment listViewMainFragment) {
        this.registryArrayList = registryArrayList;
        this.activity = activity;
        this.listViewMainFragment = listViewMainFragment;
    }

    @Override
    public int getCount() {
        return registryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return registryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return registryArrayList.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.main_activity_list_view_adapter, parent, false);
        Registry registry = registryArrayList.get(position);

        TextView textView = view.findViewById(R.id.list_view_main_activity_date);
        textView.setText("Data: " + registry.getDayString());

        textView = view.findViewById(R.id.list_view_main_activity_day);
        textView.setText(DateUtil.getInstanceDateUtil().nameOfDay(registry.getDay()));

        textView = view.findViewById(R.id.list_view_main_activity_time_worked);
        System.out.println("ENUM DO PROJETO: " + registry.getObservation());
        String timeWorked = DateUtil.getInstanceDateUtil().calculateTimeFromRegistryToString(registry);
        textView.setText("Horas trabalhadas: " + timeWorked);

        String extraTime = DateUtil.getInstanceDateUtil().calculateExtraTimeFromRegistryToString(registry);

        System.out.println(extraTime);
        textView = view.findViewById(R.id.list_view_main_activity_time_extra_worked);
        textView.setText("Hora extra realizada: " + extraTime);

        SalaryUtil salaryUtil = new SalaryUtil();
        extraTime = extraTime.substring(0, extraTime.length() - 1);

        Money money = new Money();
        textView = view.findViewById(R.id.list_view_main_activity_day_value);
        textView.setText("Valor do dia: R$ " + money.doubleToStringMoney(String.valueOf(salaryUtil.calculateSalaryPerDay(registry, activity.getApplicationContext(), extraTime))));

        ImageView imageView = view.findViewById(R.id.imageView_remove_listView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dao dao = new Dao(activity.getApplicationContext());
                dao.deleteRegistryFromDao((Registry) getItem(position));
                dao.close();
                listViewMainFragment.updateListView();

            }
        });





        return view;
    }


}
