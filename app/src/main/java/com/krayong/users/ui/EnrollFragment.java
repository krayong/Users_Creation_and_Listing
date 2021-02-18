package com.krayong.users.ui;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.krayong.users.R;
import com.krayong.users.models.Gender;
import com.krayong.users.models.User;
import com.krayong.users.util.Connection;
import com.krayong.users.util.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class EnrollFragment extends Fragment {
	private ImageView mProfilePhoto;
	private TextInputLayout mFirstNameTil, mLastNameTil, mDateOfBirthTil, mGenderTil,
			mCountryTil, mStateTil, mHomeTownTil, mPhoneNumberTil, mTelNumberTil;
	
	private Uri mSelectedImageUri;
	private Gender mSelectedGender = Gender.UNDEFINED;
	private Calendar mDateOfBirth;
	
	private Dialog mProgressDialog;
	
	public EnrollFragment() {
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == Constants.EXTERNAL_STORAGE_REQUEST) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				showImageChooser();
			} else {
				Toast.makeText(requireContext(), "Storage Permission is required for Selecting Image", Toast.LENGTH_SHORT).show();
			}
		} else {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_enroll, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mProfilePhoto = view.findViewById(R.id.iv_profile_photo);
		
		mFirstNameTil = view.findViewById(R.id.til_first_name);
		if (mFirstNameTil.getEditText() != null) {
			mFirstNameTil.getEditText().addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
				}
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					mFirstNameTil.setError(null);
					mFirstNameTil.setErrorEnabled(false);
				}
				
				@Override
				public void afterTextChanged(Editable s) {
				
				}
			});
		}
		
		mLastNameTil = view.findViewById(R.id.til_last_name);
		if (mLastNameTil.getEditText() != null) {
			mLastNameTil.getEditText().addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
				}
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					mLastNameTil.setError(null);
					mLastNameTil.setErrorEnabled(false);
				}
				
				@Override
				public void afterTextChanged(Editable s) {
				
				}
			});
		}
		
		mDateOfBirthTil = view.findViewById(R.id.til_date_of_birth);
		if (mDateOfBirthTil.getEditText() != null) {
			mDateOfBirthTil.getEditText().addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
				}
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					mDateOfBirthTil.setError(null);
					mDateOfBirthTil.setErrorEnabled(false);
				}
				
				@Override
				public void afterTextChanged(Editable s) {
				
				}
			});
		}
		
		mGenderTil = view.findViewById(R.id.til_gender);
		if (mGenderTil.getEditText() != null) {
			mGenderTil.getEditText().addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
				}
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					mGenderTil.setError(null);
					mGenderTil.setErrorEnabled(false);
				}
				
				@Override
				public void afterTextChanged(Editable s) {
				
				}
			});
		}
		
		mCountryTil = view.findViewById(R.id.til_country);
		if (mCountryTil.getEditText() != null) {
			mCountryTil.getEditText().addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
				}
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					mCountryTil.setError(null);
					mCountryTil.setErrorEnabled(false);
				}
				
				@Override
				public void afterTextChanged(Editable s) {
				
				}
			});
		}
		
		mStateTil = view.findViewById(R.id.til_state);
		if (mStateTil.getEditText() != null) {
			mStateTil.getEditText().addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
				}
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					mStateTil.setError(null);
					mStateTil.setErrorEnabled(false);
				}
				
				@Override
				public void afterTextChanged(Editable s) {
				
				}
			});
		}
		
		mHomeTownTil = view.findViewById(R.id.til_home_town);
		if (mHomeTownTil.getEditText() != null) {
			mHomeTownTil.getEditText().addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
				}
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					mHomeTownTil.setError(null);
					mHomeTownTil.setErrorEnabled(false);
				}
				
				@Override
				public void afterTextChanged(Editable s) {
				
				}
			});
		}
		
		mPhoneNumberTil = view.findViewById(R.id.til_phone_number);
		if (mPhoneNumberTil.getEditText() != null) {
			mPhoneNumberTil.getEditText().addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
				}
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					mPhoneNumberTil.setError(null);
					mPhoneNumberTil.setErrorEnabled(false);
				}
				
				@Override
				public void afterTextChanged(Editable s) {
				
				}
			});
		}
		
		mTelNumberTil = view.findViewById(R.id.til_tel_number);
		if (mTelNumberTil.getEditText() != null) {
			mTelNumberTil.getEditText().addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
				}
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					mTelNumberTil.setError(null);
					mTelNumberTil.setErrorEnabled(false);
				}
				
				@Override
				public void afterTextChanged(Editable s) {
				
				}
			});
		}
		
		mProfilePhoto.setOnClickListener(v -> showImageChooser());
		view.findViewById(R.id.tv_select_profile_photo).setOnClickListener(v -> showImageChooser());
		
		mDateOfBirthTil.setEndIconOnClickListener(v -> showDatePicker());
		mDateOfBirthTil.setErrorIconOnClickListener(v -> {
			mDateOfBirthTil.setError(null);
			showDatePicker();
		});
		
		MaterialAutoCompleteTextView gender = view.findViewById(R.id.actv_gender);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item,
				new String[]{Gender.MALE.toString(), Gender.FEMALE.toString(), Gender.NON_BINARY.toString()});
		gender.setOnItemClickListener((parent, view1, position, id) -> {
			switch (position) {
				case 0:
					mSelectedGender = Gender.MALE;
					break;
				case 1:
					mSelectedGender = Gender.FEMALE;
					break;
				case 2:
					mSelectedGender = Gender.NON_BINARY;
					break;
				default:
					mSelectedGender = Gender.UNDEFINED;
					break;
			}
		});
		gender.setAdapter(arrayAdapter);
		
		view.findViewById(R.id.btn_add_user).setOnClickListener(v -> addUser());
		
		mProgressDialog = new Dialog(requireContext());
		mProgressDialog.setContentView(R.layout.dialog_progress);
		mProgressDialog.setCancelable(false);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		if (requestCode == Constants.SELECT_IMAGE) {
			if (resultCode == Activity.RESULT_OK && data != null) {
				mSelectedImageUri = data.getData();
				mProfilePhoto.setImageURI(mSelectedImageUri);
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	private void showImageChooser() {
		if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
				== PackageManager.PERMISSION_GRANTED) {
			startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), Constants.SELECT_IMAGE);
		} else {
			ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.EXTERNAL_STORAGE_REQUEST);
		}
	}
	
	private void showDatePicker() {
		DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext());
		datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
			mDateOfBirth = Calendar.getInstance();
			mDateOfBirth.set(year, month, dayOfMonth);
			
			String etc = "th";
			if (dayOfMonth == 1 || dayOfMonth == 21 || dayOfMonth == 31) {
				etc = "st";
			} else if (dayOfMonth == 2 || dayOfMonth == 22) {
				etc = "nd";
			} else if (dayOfMonth == 3 || dayOfMonth == 23) {
				etc = "rd";
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
			
			String date = sdf.format(mDateOfBirth.getTime());
			if (dayOfMonth < 10) {
				date = date.substring(1, 2) + etc + date.substring(2);
			} else {
				date = date.substring(0, 2) + etc + date.substring(2);
			}
			if (mDateOfBirthTil.getEditText() != null) {
				mDateOfBirthTil.getEditText().setText(date);
			}
		});
		datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
		datePickerDialog.show();
	}
	
	private void addUser() {
		mProgressDialog.show();
		
		String firstName = "Karan";
		if (mFirstNameTil.getEditText() != null) {
			firstName = mFirstNameTil.getEditText().getText().toString();
		}
		
		String lastName = "Gourisaria";
		if (mLastNameTil.getEditText() != null) {
			lastName = mLastNameTil.getEditText().getText().toString();
		}
		
		if (mDateOfBirth == null) {
			mDateOfBirth = Calendar.getInstance();
			mDateOfBirth.set(2050, 0, 1);
		}
		
		String country = "India";
		if (mCountryTil.getEditText() != null) {
			country = mCountryTil.getEditText().getText().toString();
		}
		
		String state = "West Bengal";
		if (mStateTil.getEditText() != null) {
			state = mStateTil.getEditText().getText().toString();
		}
		
		String homeTown = "Kolkata";
		if (mHomeTownTil.getEditText() != null) {
			homeTown = mHomeTownTil.getEditText().getText().toString();
		}
		
		String phoneNumber = "9804470987";
		if (mPhoneNumberTil.getEditText() != null) {
			phoneNumber = mPhoneNumberTil.getEditText().getText().toString();
		}
		
		String telNumber = "8777098826";
		if (mTelNumberTil.getEditText() != null) {
			telNumber = mTelNumberTil.getEditText().getText().toString();
		}
		
		if (mSelectedImageUri == null) {
			mProgressDialog.dismiss();
			Toast.makeText(requireContext(), "User Image must be selected", Toast.LENGTH_SHORT).show();
			return;
		}
		
		User user = new User(
				firstName,
				lastName,
				mDateOfBirth.getTimeInMillis(),
				mSelectedGender,
				country, state, homeTown,
				phoneNumber, telNumber,
				mSelectedImageUri.toString()
		);
		
		mFirstNameTil.setErrorEnabled(true);
		mLastNameTil.setErrorEnabled(true);
		mDateOfBirthTil.setErrorEnabled(true);
		mGenderTil.setErrorEnabled(true);
		mCountryTil.setErrorEnabled(true);
		mStateTil.setErrorEnabled(true);
		mHomeTownTil.setErrorEnabled(true);
		mPhoneNumberTil.setErrorEnabled(true);
		mTelNumberTil.setErrorEnabled(true);
		
		mFirstNameTil.setError(user.checkFirstName());
		mLastNameTil.setError(user.checkLastName());
		mDateOfBirthTil.setError(user.checkDateOfBirth());
		mGenderTil.setError(user.checkGender());
		mCountryTil.setError(user.checkCountry());
		mStateTil.setError(user.checkState());
		mHomeTownTil.setError(user.checkHomeTown());
		mPhoneNumberTil.setError(user.checkPhoneNumber());
		mTelNumberTil.setError(user.checkTelNumber());
		
		if (user.isUserValid()) {
			checkPhoneNumber(user);
		} else {
			mProgressDialog.dismiss();
		}
	}
	
	private void checkPhoneNumber(User currentUser) {
		FirebaseDatabase.getInstance()
				.getReference()
				.child(Constants.USERS)
				.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot snapshot) {
						if (snapshot.exists() && snapshot.hasChildren()) {
							for (DataSnapshot child : snapshot.getChildren()) {
								User user = child.getValue(User.class);
								if (user != null && user.getPhoneNumber() != null) {
									if (user.getPhoneNumber().equals(currentUser.getPhoneNumber())) {
										mPhoneNumberTil.setError("User with this phone number already exists");
										
										currentUser.setPhoneNumberValid(false);
									}
								}
							}
							
							if (currentUser.isUserValid()) {
								uploadUserImage(currentUser);
							} else {
								mProgressDialog.dismiss();
							}
						} else {
							uploadUserImage(currentUser);
						}
					}
					
					@Override
					public void onCancelled(@NonNull DatabaseError error) {
						mProgressDialog.dismiss();
					}
				});
	}
	
	private void uploadUserImage(final User user) {
		Connection.checkConnection(this);
		
		StorageReference reference =
				FirebaseStorage.getInstance()
						.getReference()
						.child(Constants.IMAGES)
						.child(user.getId() + mSelectedImageUri.getLastPathSegment());
		
		reference.putFile(mSelectedImageUri)
				.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
					@Override
					public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
						reference.getDownloadUrl()
								.addOnSuccessListener(new OnSuccessListener<Uri>() {
									@Override
									public void onSuccess(Uri uri) {
										Log.i(getClass().getSimpleName(), "Image Download URL: " + uri.toString());
										user.setImageUrl(uri.toString());
										
										uploadUserData(user);
									}
								})
								.addOnFailureListener(new OnFailureListener() {
									@Override
									public void onFailure(@NonNull Exception e) {
										Log.e(getClass().getSimpleName(), "FS Error", e);
										Toast.makeText(requireContext(), "Error Occurred. Please try again later", Toast.LENGTH_SHORT).show();
										
										mProgressDialog.dismiss();
									}
								});
					}
				})
				.addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						Log.e(getClass().getSimpleName(), "FS Error", e);
						Toast.makeText(requireContext(), "Error Occurred. Please try again later", Toast.LENGTH_SHORT).show();
						
						mProgressDialog.dismiss();
					}
				});
	}
	
	private void uploadUserData(final User user) {
		Connection.checkConnection(this);
		
		FirebaseDatabase.getInstance()
				.getReference()
				.child(Constants.USERS)
				.child(user.getId())
				.setValue(user)
				.addOnCompleteListener(task -> {
					mProgressDialog.dismiss();
					
					if (task.isSuccessful()) {
						Toast.makeText(requireContext(), "User Created Successfully", Toast.LENGTH_SHORT).show();
						
						clearInputs();
					} else {
						Toast.makeText(requireContext(), "Error Occurred. Please try again later", Toast.LENGTH_SHORT).show();
						Log.e(getClass().getSimpleName(), "FS Error", task.getException());
					}
				});
	}
	
	private void clearInputs() {
		mProfilePhoto.setImageResource(R.drawable.ic_image_24);
		mSelectedImageUri = null;
		
		if (mFirstNameTil.getEditText() != null) {
			mFirstNameTil.getEditText().setText("");
		}
		
		if (mLastNameTil.getEditText() != null) {
			mLastNameTil.getEditText().setText("");
		}
		
		if (mDateOfBirthTil.getEditText() != null) {
			mDateOfBirthTil.getEditText().setText("");
		}
		mDateOfBirth = null;
		
		if (mGenderTil.getEditText() != null) {
			mGenderTil.getEditText().setText("");
		}
		mSelectedGender = Gender.UNDEFINED;
		
		if (mCountryTil.getEditText() != null) {
			mCountryTil.getEditText().setText("");
		}
		
		if (mStateTil.getEditText() != null) {
			mStateTil.getEditText().setText("");
		}
		
		if (mHomeTownTil.getEditText() != null) {
			mHomeTownTil.getEditText().setText("");
		}
		
		if (mPhoneNumberTil.getEditText() != null) {
			mPhoneNumberTil.getEditText().setText("");
		}
		
		if (mTelNumberTil.getEditText() != null) {
			mTelNumberTil.getEditText().setText("");
		}
	}
}