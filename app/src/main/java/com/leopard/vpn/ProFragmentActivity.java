package com.leopard.vpn;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.firebase.FirebaseApp;
import com.sanojpunchihewa.glowbutton.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import junit.*;
import org.json.*;

public class ProFragmentActivity extends Fragment {

    private LinearLayout linear1;
    private TextView textview1;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
        View _view = _inflater.inflate(R.layout.pro_fragment, _container, false);
        initialize(_savedInstanceState, _view);
        FirebaseApp.initializeApp(getContext());
        initializeLogic();
        return _view;
    }

    private void initialize(Bundle _savedInstanceState, View _view) {
        linear1 = _view.findViewById(R.id.linear1);
        textview1 = _view.findViewById(R.id.textview1);
    }

    private void initializeLogic() {
    }

}

