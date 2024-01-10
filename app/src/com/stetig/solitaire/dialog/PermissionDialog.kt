package com.stetig.solitaire.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.pdfview.subsamplincscaleimageview.SubsamplingScaleImageView
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.PermissionActivity
//import com.rajat.pdfviewer.util.FileUtils
import com.stetig.solitaire.databinding.DialogPermissionBinding
import java.io.File

class PermissionDialog(context: Context) : Dialog(context,R.style.WideDialogSecond) {

    private lateinit var binding: DialogPermissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_permission,
            null,
            false
        )
        setContentView(binding.root)
        binding.pdfView.fromAsset("app_privacy_policy.pdf").show()
        binding.agreeBtn.setOnClickListener {
            context.startActivity(Intent(context, PermissionActivity::class.java))
            dismiss()
        }
    }
}