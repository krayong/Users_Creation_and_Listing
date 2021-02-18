package com.krayong.users.ui;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.krayong.users.R;
import com.krayong.users.adapters.TabAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setUpActionBar();
		
		ViewPager2 viewPager = findViewById(R.id.view_pager);
		TabLayout tabLayout = findViewById(R.id.tab_layout);
		
		TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(), getLifecycle());
		tabAdapter.addFragment(new UsersFragment());
		tabAdapter.addFragment(new EnrollFragment());
		
		viewPager.setAdapter(tabAdapter);
		new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
			if (position == 0) {
				tab.setText(R.string.users);
			} else {
				tab.setText(R.string.enroll);
			}
		}).attach();
	}
	
	private void setUpActionBar() {
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setNavigationOnClickListener(view -> onBackPressed());
	}
}