package com.krayong.users.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.krayong.users.R;
import com.krayong.users.models.User;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
	private final Context mContext;
	private final ArrayList<User> mUsers;
	private final UsersOnClickListener mUsersOnClickListener;
	
	public UsersAdapter(Context context, ArrayList<User> users, UsersOnClickListener usersOnClickListener) {
		mContext = context;
		mUsers = users;
		mUsersOnClickListener = usersOnClickListener;
	}
	
	@NonNull
	@Override
	public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new UsersViewHolder(
				LayoutInflater.from(mContext)
						.inflate(R.layout.item_user, parent, false)
		);
	}
	
	@Override
	public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
		User user = mUsers.get(position);

		Glide.with(mContext)
				.load(user.getImageUrl())
				.centerCrop()
				.placeholder(R.drawable.ic_person_24)
				.into(holder.userImage);
		
		holder.name.setText(String.format(mContext.getString(R.string.name), user.getFirstName(), user.getLastName()));
		
		holder.gender.setText(user.getGender().toString());
		
		Calendar calendar = Calendar.getInstance();
		Calendar dateOfBirth = Calendar.getInstance();
		dateOfBirth.setTimeInMillis(user.getDateOfBirth());
		
		int age = calendar.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
		if (calendar.get(Calendar.DAY_OF_YEAR) < dateOfBirth.get(Calendar.DAY_OF_YEAR)) {
			age = age - 1;
		}
		
		holder.age.setText(String.valueOf(age));
		
		holder.homeTown.setText(user.getHomeTown());
	}
	
	@Override
	public int getItemCount() {
		return mUsers.size();
	}
	
	public interface UsersOnClickListener {
		void onClick(int position);
	}
	
	public class UsersViewHolder extends RecyclerView.ViewHolder {
		private final ShapeableImageView userImage;
		private final TextView name, gender, age, homeTown;
		
		public UsersViewHolder(@NonNull View itemView) {
			super(itemView);
			userImage = itemView.findViewById(R.id.siv_user_image);
			name = itemView.findViewById(R.id.tv_name);
			gender = itemView.findViewById(R.id.tv_gender);
			age = itemView.findViewById(R.id.tv_age);
			homeTown = itemView.findViewById(R.id.tv_home_town);
			
			ImageButton delete = itemView.findViewById(R.id.ib_user_delete);
			delete.setOnClickListener(v -> mUsersOnClickListener.onClick(getAdapterPosition()));
		}
	}
}