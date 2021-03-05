package org.sk.jih.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.sk.jih.MainActivity;
import org.sk.jih.MainApplication;
import org.sk.jih.R;
import org.sk.jih.VehicleActivity;
import org.sk.jih.modal.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleListViewHolder> {

    private MainActivity activity;
    private final LayoutInflater inflater;
    private List<Vehicle> vehicles;

    public VehicleAdapter(MainActivity context, List<Vehicle> vehicles) {
        inflater = LayoutInflater.from(context);
        this.vehicles = vehicles;
        this.activity = context;
    }

    @Override
    public VehicleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.vehicle_item, parent, false);
        VehicleListViewHolder viewHolder = new VehicleListViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VehicleListViewHolder holder, final int position) {
        final Vehicle vehicle = vehicles.get(position);

        holder.tvVehicleNo.setText(vehicle.getVehicleNumber());

        holder.tvVehicleNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), VehicleActivity.class);
                intent.putExtra("vehicleNo", vehicle.getVehicleNumber());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (vehicles != null) {
            return vehicles.size();
        }
        return 0;
    }

    public void filterList(ArrayList<Vehicle> filteredList) {
        vehicles = filteredList;
        notifyDataSetChanged();
    }

    public void setData(List<Vehicle> vehiclesData){
        vehicles = vehiclesData;
        notifyDataSetChanged();
    }

    class VehicleListViewHolder extends RecyclerView.ViewHolder {
        TextView tvVehicleNo;

        public VehicleListViewHolder(View itemView) {
            super(itemView);
            tvVehicleNo = itemView.findViewById(R.id.tvVehicleNo);
        }
    }



}


