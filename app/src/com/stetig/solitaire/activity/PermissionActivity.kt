package com.stetig.solitaire.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.stetig.solitaire.R
import com.stetig.solitaire.databinding.ActivityPermissionBinding
import com.stetig.solitaire.utils.checkAllPermissionAllow

class PermissionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPermissionBinding

    // Microphone
    // Notification
    // PhoneCalls
    // Contacts
    // Overlay
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initCheckPermission()
        initCheckListener()
    }

    private fun initCheckListener() {
        binding.switchCallLog.setOnCheckedChangeListener { _, b ->
            if (b) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_CALL_LOG
                    ), 100
                )
            }
        }
        /*binding.switchContacts.setOnCheckedChangeListener { _, b ->
            if (b) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_PHONE_STATE
                    ), 101
                )
            }
        }*/
        binding.switchPhone.setOnCheckedChangeListener { _, b ->
            if (b) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_PHONE_STATE,
                    ), 102
                )
            }
        }
        binding.switchMicroPhone.setOnCheckedChangeListener { _, b ->
            if (b) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.RECORD_AUDIO
                    ), 103
                )
            }
        }
        binding.switchNotifications.setOnCheckedChangeListener { _, b ->
            if (b) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(
                            Manifest.permission.POST_NOTIFICATIONS
                        ), 104
                    )
                }
            }
        }
        binding.switchOverlay.setOnCheckedChangeListener { _, b ->
            if (b) {
                val intent1 = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")
                )
                startActivityForResult(intent1, 105)
            }
        }
        binding.allowPermission.setOnClickListener {
            if (checkAllPermissionAllow()) {
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Please allow mention above all permissions.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        if (checkAllPermissionAllow()) {
            binding.allowPermission.background =
                ContextCompat.getDrawable(this, R.drawable.permission_btn)
        } else {
            binding.allowPermission.background =
                ContextCompat.getDrawable(this, R.drawable.permission_btn_disable)
        }
    }


    private fun initCheckPermission() {
        if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            binding.switchCallLog.isChecked = true
            binding.switchCallLog.isEnabled = false
        }
        /*if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            binding.switchContacts.isChecked = true
            binding.switchContacts.isEnabled = false
        }*/
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            binding.switchPhone.isChecked = true
            binding.switchPhone.isEnabled = false
        }
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            binding.switchMicroPhone.isChecked = true
            binding.switchMicroPhone.isEnabled = false
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            binding.notificationPermissionLay.visibility = View.VISIBLE
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                binding.switchNotifications.isChecked = true
                binding.switchNotifications.isEnabled = false
            }
        } else {
            binding.notificationPermissionLay.visibility = View.GONE
        }
        if (Settings.canDrawOverlays(this)) {
            binding.switchOverlay.isChecked = true
            binding.switchOverlay.isEnabled = false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            105 -> {
                if (Settings.canDrawOverlays(this)) {
                    binding.switchOverlay.isChecked = true
                    binding.switchOverlay.isEnabled = false
                } else {
                    binding.switchOverlay.isChecked = false
                }
            }
        }
        if (checkAllPermissionAllow()) {
            binding.allowPermission.background =
                ContextCompat.getDrawable(this, R.drawable.permission_btn)
        } else {
            binding.allowPermission.background =
                ContextCompat.getDrawable(this, R.drawable.permission_btn_disable)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            100 -> {
                if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                    binding.switchCallLog.isChecked = true
                    binding.switchCallLog.isEnabled = false
                } else {
                    binding.switchCallLog.isChecked = false
                }
            }

            /*101 -> {
                if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    binding.switchContacts.isChecked = true
                    binding.switchContacts.isEnabled = false
                } else {
                    binding.switchContacts.isChecked = false
                }
            }*/

            102 -> {
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                        Manifest.permission.READ_PHONE_STATE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    binding.switchPhone.isChecked = true
                    binding.switchPhone.isEnabled = false
                } else {
                    binding.switchPhone.isChecked = false
                }
            }

            103 -> {
                if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                    binding.switchMicroPhone.isChecked = true
                    binding.switchMicroPhone.isEnabled = false
                } else {
                    binding.switchMicroPhone.isChecked = false
                }
            }

            104 -> {
                if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    binding.switchNotifications.isChecked = true
                    binding.switchNotifications.isEnabled = false
                } else {
                    binding.switchNotifications.isChecked = false
                }
            }
        }
        if (checkAllPermissionAllow()) {
            binding.allowPermission.background =
                ContextCompat.getDrawable(this, R.drawable.permission_btn)
        } else {
            binding.allowPermission.background =
                ContextCompat.getDrawable(this, R.drawable.permission_btn_disable)
        }
    }

    override fun onBackPressed() {

    }
}