package com.lisko.mealfactory.view.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
import com.lisko.mealfactory.databinding.DialogCustomListBinding
import com.lisko.mealfactory.databinding.DialogSelectImageBinding
import com.lisko.mealfactory.utils.Constants
import com.lisko.mealfactory.view.adapters.CustomListAdapter
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*

class AddUpdateMealActivity : AppCompatActivity() {
    private lateinit var mealBinding: ActivityAddUpdateMealBinding
    private var mImagePath=""
    private lateinit var mCustomDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mealBinding= ActivityAddUpdateMealBinding.inflate(layoutInflater)
        setContentView(mealBinding.root)

        setupActionBar()
        mealBinding.ivAddPhoto.setOnClickListener {
            launchDialog()
        }

        mealBinding.etType.setOnClickListener {
            recyclerDialog(resources.getString(R.string.rv_mealtype),
                Constants.mealTypes(), Constants.MEAL_TYPE)
        }

        mealBinding.etCategory.setOnClickListener {
            recyclerDialog(resources.getString(R.string.rv_mealcategory),
                Constants.mealCategories(), Constants.MEAL_CATEGORY)
        }

        mealBinding.etTime.setOnClickListener {
            recyclerDialog(resources.getString(R.string.rv_mealtime),
                Constants.mealCookTime(), Constants.MEAL_COOKING_TIME)
        }

        mealBinding.btAddMeal.setOnClickListener{
            validateEntries()
        }

    }

    private fun validateEntries() {
        if(TextUtils.isEmpty(mImagePath)){
            Toast.makeText(this@AddUpdateMealActivity,
            resources.getString(R.string.err_image), Toast.LENGTH_SHORT).show()

        }else if(mealBinding.etTitle.text!!.isEmpty()){
            Toast.makeText(this@AddUpdateMealActivity,
                resources.getString(R.string.err_title), Toast.LENGTH_SHORT).show()

        }else if(mealBinding.etType.text!!.isEmpty()){
            Toast.makeText(this@AddUpdateMealActivity,
                resources.getString(R.string.err_type), Toast.LENGTH_SHORT).show()

        }else if(mealBinding.etCategory.text!!.isEmpty()){
            Toast.makeText(this@AddUpdateMealActivity,
                resources.getString(R.string.err_category), Toast.LENGTH_SHORT).show()

        }else if(mealBinding.etIngredients.text!!.isEmpty()){
            Toast.makeText(this@AddUpdateMealActivity,
                resources.getString(R.string.err_ingredients), Toast.LENGTH_SHORT).show()

        }else if(mealBinding.etTime.text!!.isEmpty()){
            Toast.makeText(this@AddUpdateMealActivity,
                resources.getString(R.string.err_time), Toast.LENGTH_SHORT).show()

        }else if(mealBinding.etSteps.text!!.isEmpty()){
            Toast.makeText(this@AddUpdateMealActivity,
                resources.getString(R.string.err_steps), Toast.LENGTH_SHORT).show()
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
        mCustomDialog= Dialog(this)
        val binding: DialogSelectImageBinding = DialogSelectImageBinding.inflate(layoutInflater)
        mCustomDialog.setContentView(binding.root)
        mCustomDialog.setTitle(R.string.dialog_selectImage)
        mCustomDialog.show()
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

            mCustomDialog.dismiss()
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

            mCustomDialog.dismiss()
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

                    mImagePath= saveImageToStorage(thumbnail)
                    Log.i("ImagePath", mImagePath)
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
                        .load(photoUri).centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(object: RequestListener<Drawable>{
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Log.e("TAG","Error Loading image",e)
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                resource?.let{
                                    val bitmap= resource.toBitmap()
                                    mImagePath= saveImageToStorage(bitmap)
                                }
                                return false

                            }

                        })
                        .into(mealBinding.ivMealImage)
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
    private fun saveImageToStorage(bitmap: Bitmap) :String{
        val wrapper= ContextWrapper(applicationContext)

        var file= wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file= File(file, "${UUID.randomUUID()}.jpg")

        try{
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)
            outputStream.flush()
            outputStream.close()
        }catch (e : FileNotFoundException){
            e.printStackTrace()
        }
        return file.absolutePath
    }

    private fun recyclerDialog(title: String, itemsList: List<String>, selection: String){
        mCustomDialog= Dialog(this)
        val bindingDialog = DialogCustomListBinding.inflate(layoutInflater)
        mCustomDialog.setContentView(bindingDialog.root)
        bindingDialog.tvTitle.text= title
        bindingDialog.rvList.layoutManager= LinearLayoutManager(this)

        val adapter= CustomListAdapter(this, itemsList, selection)
        bindingDialog.rvList.adapter= adapter
        mCustomDialog.show()
    }

    fun selectedItem(item: String, selection: String){
        when(selection){
            Constants.MEAL_TYPE ->{
                mCustomDialog.dismiss()
                mealBinding.etType.setText(item)
            }
            Constants.MEAL_COOKING_TIME ->{
                mCustomDialog.dismiss()
                mealBinding.etTime.setText(item)
            }
            Constants.MEAL_CATEGORY ->{
                mCustomDialog.dismiss()
                mealBinding.etCategory.setText(item)
            }
        }
    }

    companion object{
        private const val CAMERA=1
        private const val GALLERY=2

        private const val IMAGE_DIRECTORY="MealImages"
    }


}