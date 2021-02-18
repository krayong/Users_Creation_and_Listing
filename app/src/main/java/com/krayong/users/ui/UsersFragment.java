package com.krayong.users.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.krayong.users.R;
import com.krayong.users.adapters.UsersAdapter;
import com.krayong.users.models.User;
import com.krayong.users.util.Connection;
import com.krayong.users.util.Constants;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UsersFragment extends Fragment implements UsersAdapter.UsersOnClickListener {
	private final ArrayList<User> mUsers = new ArrayList<>();
	private RecyclerView mRecyclerView;
	private LinearLayout mEmptyLayout;
	private UsersAdapter mUsersAdapter;
	
	private Dialog mProgressDialog;
	
	public UsersFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_users, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mRecyclerView = view.findViewById(R.id.rv_users);
		mEmptyLayout = view.findViewById(R.id.ll_empty_layout);
		
		mProgressDialog = new Dialog(requireContext());
		mProgressDialog.setContentView(R.layout.dialog_progress);
		mProgressDialog.setCancelable(false);
	}
	
	@Override
	public void onResume() {
		getUsers();
		
		super.onResume();
	}
	
	@Override
	public void onClick(int position) {
		Connection.checkConnection(this);
		
		mProgressDialog.show();
		
		FirebaseDatabase.getInstance()
				.getReference()
				.child(Constants.USERS)
				.child(mUsers.get(position).getId())
				.removeValue()
				.addOnSuccessListener(aVoid -> {
					mUsers.remove(position);
					
					if (mUsers.isEmpty()) {
						mEmptyLayout.setVisibility(View.VISIBLE);
						mRecyclerView.setVisibility(View.GONE);
					} else {
						mEmptyLayout.setVisibility(View.GONE);
						mRecyclerView.setVisibility(View.VISIBLE);
					}
					
					mUsersAdapter.notifyItemRemoved(position);
					
					mProgressDialog.dismiss();
				})
				.addOnFailureListener(e -> {
					Log.e(getClass().getSimpleName(), "FRD Error", e);
					Toast.makeText(requireContext(), "Unable to delete user. Please try again later.", Toast.LENGTH_SHORT).show();
					
					mProgressDialog.dismiss();
				});
	}
	
	private void getUsers() {
		Connection.checkConnection(this);
		
		mUsers.clear();
		
		mProgressDialog.show();
		
		FirebaseDatabase.getInstance()
				.getReference()
				.child(Constants.USERS)
				.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot snapshot) {
						if (snapshot.exists() && snapshot.hasChildren()) {
							Log.i(getClass().getSimpleName(), "Number of children: " + snapshot.getChildrenCount());
							for (DataSnapshot children : snapshot.getChildren()) {
								mUsers.add(children.getValue(User.class));
							}
							
							initRV();
						} else {
							mEmptyLayout.setVisibility(View.VISIBLE);
							mRecyclerView.setVisibility(View.GONE);
							
							mProgressDialog.dismiss();
						}
					}
					
					@Override
					public void onCancelled(@NonNull DatabaseError error) {
						Log.e(getClass().getSimpleName(), "FRD Error: " + error.getDetails(), error.toException());
						Toast.makeText(requireContext(), "Unable to get users. Please try again later.", Toast.LENGTH_SHORT).show();
						
						mProgressDialog.dismiss();
					}
				});
	}
	
	private void initRV() {
		if (mUsers.isEmpty()) {
			mEmptyLayout.setVisibility(View.VISIBLE);
			mRecyclerView.setVisibility(View.GONE);
		} else {
			mEmptyLayout.setVisibility(View.GONE);
			mRecyclerView.setVisibility(View.VISIBLE);
		}
		
		mUsers.sort((o1, o2) -> Long.compare(o2.getCreationDate(), o1.getCreationDate()));
		
		mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
		mUsersAdapter = new UsersAdapter(requireContext(), mUsers, UsersFragment.this);
		mRecyclerView.setAdapter(mUsersAdapter);
		
		mProgressDialog.dismiss();
	}
}