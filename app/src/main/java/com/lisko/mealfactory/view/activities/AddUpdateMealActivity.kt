package com.lisko.mealfactory.view.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.lisko.mealfactory.R
import com.lisko.mealfactory.databinding.ActivityAddUpdateMealBinding
import com.lisko.mealfactory.databinding.DialogSelectImageBinding
import java.lang.Exception

class AddUpdateMealActivity : AppCompatActivity() {
    private lateinit var mealBinding: ActivityAddUpdateMealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mealBinding= ActivityAddUpdateMealBinding.inflate(layoutInflater)
        setContentView(mealBinding.root)

        setupActionBar()

        mealBinding.ivAddPhoto.setOnClickListener {
            launchDialog()
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(mealBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mealBinding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun launchDialog(){
        val custom= Dialog(this)
        val binding: DialogSelectImageBinding = DialogSelectImageBinding.inflate(layoutInflater)
        custom.setContentView(binding.root)
        custom.setTitle(R.string.dialog_selectImage)
        custom.show()
        binding.ivDialogCamera.setOnClickListener{
            //Toast.makeText(this,"Camera clicked", Toast.LENGTH_SHORT).show()
            Dexter.withContext(this).withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
             //   Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(object : MultiplePermissionsListener{
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if(report!!.areAllPermissionsGranted()){
                        //Toast.makeText(this@AddUpdateMealActivity,"Camera permission enabled", Toast.LENGTH_SHORT).show()
                        val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(intent, CAMERA)

                    }
                    else{
                        showAlertDialogPermissions()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showAlertDialogPermissions()
                }

            }).onSameThread().check()

            custom.dismiss()
        }

        binding.ivDialogGallery.setOnClickListener {
            Dexter.withContext(this@AddUpdateMealActivity).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(
                object: PermissionListener{
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        val intent= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(intent, GALLERY)
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        Toast.makeText(this@AddUpdateMealActivity, "Read permission denied",
                            Toast.LENGTH_SHORT).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                        showAlertDialogPermissions()
                    }

                }).onSameThread().check()

            custom.dismiss()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            if(requestCode== CAMERA){
                data?.let {
                    val thumbnail: Bitmap= data.extras!!.get("data") as Bitmap
                    //mealBinding.ivMealImage.setImageBitmap(thumbnail)
                    Glide.with(this)
                        .load(thumbnail).centerCrop().into(mealBinding.ivMealImage)
                    //mealBinding.ivAddPhoto.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_edit))
                    Glide.with(this).load(R.drawable.ic_edit).into(mealBinding.ivAddPhoto)
                }

            }
            if(requestCode== GALLERY){
                data?.let{
                    val photoUri= data.data
                    //mealBinding.ivMealImage.setImageURI(photoUri)
                    //mealBinding.ivAddPhoto.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_edit))

                    Glide.with(this)
                        .load(photoUri).centerCrop().into(mealBinding.ivMealImage)
                    Glide.with(this).load(R.drawable.ic_edit).into(mealBinding.ivAddPhoto)
                }
            }
        }
    }

    private fun showAlertDialogPermissions(){
        AlertDialog.Builder(this).setMessage("Permissions denied\n"+
        "You can change them in settings").setPositiveButton("Go to settings"){
            _, _ ->
            try {
                val intent= Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri= Uri.fromParts("package", packageName, null)
                intent.data=uri
                startActivity(intent)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }.setNegativeButton("Cancel"){
            dialog, _ ->
                dialog.dismiss()
        }.show()
    }
    companion object{
        private const val CAMERA=1
        private const val GALLERY=2

    }
}