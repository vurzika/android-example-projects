package com.viktorija.alarmapp.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viktorija.alarmapp.R;
import com.viktorija.alarmapp.database.AlarmEntity;
import com.viktorija.alarmapp.details.DetailsActivity;
import com.viktorija.alarmapp.utilities.AlarmUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.viktorija.alarmapp.details.DetailsActivity.EXTRA_ALARM_ID;

// Adapter responsible for returning individual views in lists
public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.ViewHolder> {

    private final List <AlarmEntity> mAlarmList;

    private Context mContext;
    private SharedPreferences mPreferences;

    // constructor
    public AlarmRecyclerViewAdapter(Context context, List<AlarmEntity> alarmList) {
        this.mContext = context;
        this.mAlarmList = alarmList;

        // adding preferences to the constructor
        mPreferences = android.support.v7.preference.PreferenceManager
                // getting the setting as a SharedPreferences object
                .getDefaultSharedPreferences(mContext);
    }

    // onCreateViewHolder responsible for inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.one_list_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        AlarmEntity alarmItem = mAlarmList.get(position);

        // todo: Take from preferences what is current mode
        // based on that set time and set AMPM
        boolean isAmPmMode = mPreferences.getBoolean(mContext.getString(R.string.pref_key_time_mode_am_pm),
                mContext.getResources().getBoolean(R.bool.pref_key_time_mode_am_pm_default));

        viewHolder.mTime.setText(AlarmUtilities.getFormattedTime(alarmItem, isAmPmMode));
        if (isAmPmMode) {
            viewHolder.mAmPm.setText(AlarmUtilities.getAmPm(alarmItem));
        } else {
            viewHolder.mAmPm.setText("");
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra(EXTRA_ALARM_ID, alarmItem.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAlarmList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_time)
        TextView mTime;

        @BindView(R.id.text_am_pm)
        TextView mAmPm;


        // Inner class. Represents each item, connects with xml elements.
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
