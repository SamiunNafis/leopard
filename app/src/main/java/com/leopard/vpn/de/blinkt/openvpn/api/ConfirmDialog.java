/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.blinkt.openvpn.api;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.leopard.vpn.R;

public class ConfirmDialog extends Activity implements CompoundButton.OnCheckedChangeListener, DialogInterface.OnClickListener {
    private static final String TAG = "OpenVPNVpnConfirm";
    private String mPackage;
    private Button mButton;
    private AlertDialog mAlert;

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mPackage = getCallingPackage();
            if (mPackage == null) {
                finish();
                return;
            }
            PackageManager pm = getPackageManager();
            ApplicationInfo app = pm.getApplicationInfo(mPackage, 0);
            View view = View.inflate(this, R.layout.apiconfirm, null);
            ((ImageView) view.findViewById(R.id.icon)).setImageDrawable(app.loadIcon(pm));
            ((TextView) view.findViewById(R.id.prompt)).setText(getString(R.string.prompt, app.loadLabel(pm), getString(R.string.appname)));
            ((CompoundButton) view.findViewById(R.id.check)).setOnCheckedChangeListener(this);
            Builder builder = new AlertDialog.Builder(this);
            builder.setView(view);
            builder.setIconAttribute(android.R.attr.alertDialogIcon);
            builder.setTitle(android.R.string.dialogalerttitle);
            builder.setPositiveButton(android.R.string.ok, this);
            builder.setNegativeButton(android.R.string.cancel, this);
            mAlert = builder.create();
            mAlert.setCanceledOnTouchOutside(false);
            mAlert.setOnShowListener(new OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    mButton = mAlert.getButton(DialogInterface.BUTTONPOSITIVE);
                    mButton.setEnabled(false);
                }
            });
            
            mAlert.show();
        } catch (Exception e) {
            Log.e(TAG, "onResume", e);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULTCANCELED);
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean checked) {
        mButton.setEnabled(checked);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTONPOSITIVE) {
            ExternalAppDatabase extapps = new ExternalAppDatabase(this);
            extapps.addApp(mPackage);
            setResult(RESULTOK);
            finish();
        }
        if (which == DialogInterface.BUTTONNEGATIVE) {
            setResult(RESULTCANCELED);
            finish();
        }
    }
}