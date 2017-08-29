package com.xixinfei.apex.android.common.listener;

import android.app.Dialog;

public interface DialogButtonListener {
	public void btnOkClicked(Dialog dialog, Integer tag);
	public void btnCancelClicked(Dialog dialog, Integer tag);
}
