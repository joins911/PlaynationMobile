package com.myapps.playnation.Fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.DataConnector;

public class DialogSendCommentFragment extends DialogFragment {
	private Button btnSave;
	private Button btnCancel;
	private TextView txtComment;
	private TextView lblSendComment;

	private DataConnector con;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		con = DataConnector.getInst();
		// TODO Auto-generated method stub
		View mView = inflater.inflate(
				R.layout.commponent_sendgamegroupcommentinvite, container,
				false);
		btnSave = (Button) mView.findViewById(R.id.btnSend);
		btnCancel = (Button) mView.findViewById(R.id.btnCancel);
		txtComment = (TextView) mView.findViewById(R.id.txtComment);
		lblSendComment = (TextView) mView.findViewById(R.id.txtNotificationMsg);
		if (this.getTag().equals("Group")) {
			lblSendComment.setText(getActivity().getResources().getString(
					R.string.lblSendGroupComment));
		} else {
			lblSendComment.setText(getActivity().getResources().getString(
					R.string.lblSendGameComment));
		}

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle args = getArguments();
				if (args != null) {
					con.functionQuery(args.getString(Keys.ID_PLAYER), args
							.getString(Keys.functionAnotherID), args
							.getString(Keys.functionPhpName), args
							.getString(Keys.functionAction), txtComment
							.getText().toString());
					dismiss();
				}
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		return mView;
	}
}
