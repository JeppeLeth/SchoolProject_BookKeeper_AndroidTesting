package edu.kea.pm.bookkeeper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import edu.kea.pm.bookkeeper.R;
import edu.kea.pm.bookkeeper.activity.BookInfoActivity;

public class LoopUpFragment extends Fragment {

	private EditText mTextField;
	private Button mButtonScan;
	private Button mButtonLookUp;

	public LoopUpFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_look_up, container,
				false);
		mTextField = (EditText) rootView.findViewById(R.id.isbnEditText);
		mButtonLookUp = (Button) rootView.findViewById(R.id.buttonLookUp);
		mButtonScan = (Button) rootView.findViewById(R.id.buttonScan);

		mButtonLookUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				lookupIsbnInNewActivity();
			}
		});
		mTextField.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					lookupIsbnInNewActivity();
					handled = true;
				}
				return handled;
			}
		});

		mButtonScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				IntentIntegrator intentIntegrator = new IntentIntegrator(
						getActivity());
				intentIntegrator
						.initiateScan(IntentIntegrator.TARGET_BARCODE_SCANNER_ONLY); // or
																						// QR_CODE_TYPES
																						// if
																						// you
																						// need
																						// to
																						// scan
																						// Q
			}
		});

		return rootView;
	}

	private void lookupIsbnInNewActivity() {
		try {
			Long.parseLong(mTextField.getText().toString().trim());
			Intent intent = new Intent(getActivity(), BookInfoActivity.class);
			intent.putExtra(BookInfoActivity.BUNDLE_BARCODE, mTextField
					.getText().toString().trim());
			getActivity().startActivity(intent);
		} catch (NumberFormatException e) {
			Toast toast = Toast.makeText(getActivity(), "Input is not valid",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	public void openBookForISBN(String isbn) {
		mTextField.setText(isbn);
		mTextField.setSelection(mTextField.length());
	}

}
