package com.bu.haw.vier;

import android.animation.Animator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bu.haw.vier.data.DataStorage;

/**
 * Created by christian on 07.11.14.
 */
public class AdministrationFragment extends Fragment{

    private static final String TAG = AdministrationFragment.class.getName();
    // private variables
    View view;
    AccountChoseListener listener;

    // private fragment layout objects
    private Button saveButton;
    private EditText nameSpace;

    public AdministrationFragment(AccountChoseListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_administration, container, false);

        getViews();
        addOnClickListeners();

        return view;
    }

    private void addOnClickListeners() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameSpace.getText().toString();
                if (name == null || name.isEmpty()){
                    Toast.makeText(getActivity(), "Bitte erst den Namen eingeben!", Toast.LENGTH_SHORT).show();
                }else {
                    listener.accountChanged(name + ".txt");
                }
            }
        });
    }

    private void getViews() {
        nameSpace = (EditText) view.findViewById(R.id.nameField);
        saveButton = (Button) view.findViewById(R.id.saveButton);
    }

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimator(transit, enter, nextAnim);
    }

    public interface AccountChoseListener {
        public void accountChanged(String accountName);
    }
}
