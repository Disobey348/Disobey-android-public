package com.example.disobey

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class Avatar : AppCompatActivity() {
    lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatar)

        pref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        val infoLl = findViewById<RelativeLayout>(R.id.AvatarViewLayout)
        val saveButton = findViewById<ImageButton>(R.id.SaveImageButton)
        //val savedImageView = findViewById<ImageView>(R.id.SavedAvatarImageView)

        saveButton.setOnClickListener {
            //function call, to get Bitmap from a UI View i.e. In our case a LinearLayout with id infoLl
            val bitmap = getBitmapFromUiView(infoLl)
            //function call, pass the bitmap to save it
            //savedImageView.setImageBitmap(bitmap)
            val file = File(this.filesDir, "avatarimage.png")
            if (file.exists()) {
                file.delete()
            }
            try {
                val outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
                outputStream.close()

                // Store the file path or URI in SharedPreferences
                val editor = pref.edit()
                editor.putString("image_path", file.absolutePath)
                editor.apply()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            saveBitmapImage(bitmap)
        }







        val genderCstLayout = findViewById<LinearLayout>(R.id.GenderCstLayout)
        val topCstLayout = findViewById<LinearLayout>(R.id.TopCstLayout)
        val bottomCstLayout = findViewById<LinearLayout>(R.id.BottomCstLayout)
        val sneakerScrollView = findViewById<LinearLayout>(R.id.SneakerCstLayout)

        val maleScrollView = findViewById<HorizontalScrollView>(R.id.MaleScrollView)
        val femaleScrollView = findViewById<HorizontalScrollView>(R.id.FemaleScrollView)

        val maleSubSecButton = findViewById<Button>(R.id.MaleSubSecButton)
        val femaleSubSecButton = findViewById<Button>(R.id.FemaleSubSecButton)



        val hoodieScrollView = findViewById<HorizontalScrollView>(R.id.HoodieScrollView)
        val teeShirtScrollView = findViewById<HorizontalScrollView>(R.id.TeeShirtScrollView)

        val hoodieSubSecButton = findViewById<Button>(R.id.HoodiesSubSecButton)
        val teeShirtSubSecButton = findViewById<Button>(R.id.TeesSubSecButton)

        val shortsScrollView = findViewById<LinearLayout>(R.id.ShortsScrollView)
        val pantsScrollView = findViewById<HorizontalScrollView>(R.id.PantsScrollView)

        val shortsSubSecButton = findViewById<Button>(R.id.ShortsSubSecButton)
        val pantsSubSecButton = findViewById<Button>(R.id.PantsSubSecButton)


        val hoodieButton1 = findViewById<CardView>(R.id.card_view_Top_Hoodie1)

        val hoodieButton3 = findViewById<CardView>(R.id.card_view_Top_Hoodie3)
        val hoodieButton4 = findViewById<CardView>(R.id.card_view_Top_Hoodie4)
        val hoodieButton5 = findViewById<CardView>(R.id.card_view_Top_Hoodie5)
        val hoodieButton6 = findViewById<CardView>(R.id.card_view_Top_Hoodie6)

        val TeeButton1 = findViewById<CardView>(R.id.card_view_Top_Tee1)
        val TeeButton2 = findViewById<CardView>(R.id.card_view_Top_Tee2)
        val TeeButton3 = findViewById<CardView>(R.id.card_view_Top_Tee3)
        val TeeButton4 = findViewById<CardView>(R.id.card_view_Top_Tee4)
        val TeeButton5 = findViewById<CardView>(R.id.card_view_Top_Tee5)
        val TeeButton6 = findViewById<CardView>(R.id.card_view_Top_Tee6)
        val TeeButton7 = findViewById<CardView>(R.id.card_view_Top_Tee7)





        val pab1 = findViewById<CardView>(R.id.card_view_Bottom1)
        val pab2 = findViewById<CardView>(R.id.card_view_Bottom2)
        val pab3 = findViewById<CardView>(R.id.card_view_Bottom3)
        val pab4 = findViewById<CardView>(R.id.card_view_Bottom4)
        val pab5 = findViewById<CardView>(R.id.card_view_Bottom5)


        val snb1 = findViewById<CardView>(R.id.card_view_Sneaker1)
        val snb2 = findViewById<CardView>(R.id.card_view_Sneaker2)
        val snb3 = findViewById<CardView>(R.id.card_view_Sneaker3)
        val snb4 = findViewById<CardView>(R.id.card_view_Sneaker4)

        val femaleButton = findViewById<CardView>(R.id.card_view_FemaleButton)
        val femaleButton1 = findViewById<CardView>(R.id.card_view_FemaleButton1)
        val femaleButton2 = findViewById<CardView>(R.id.card_view_FemaleButton2)
        val femaleButton3 = findViewById<CardView>(R.id.card_view_FemaleButton3)
        val femaleButton4 = findViewById<CardView>(R.id.card_view_FemaleButton4)
        val femaleButton5 = findViewById<CardView>(R.id.card_view_FemaleButton5)
        val femaleButton6 = findViewById<CardView>(R.id.card_view_FemaleButton6)
        val maleButton = findViewById<CardView>(R.id.card_view_MaleButton)
        val maleButton1 = findViewById<CardView>(R.id.card_view_MaleButton1)
        val maleButton2 = findViewById<CardView>(R.id.card_view_MaleButton2)
        val maleButton3 = findViewById<CardView>(R.id.card_view_MaleButton3)
        val maleButton4 = findViewById<CardView>(R.id.card_view_MaleButton4)
        val maleButton5 = findViewById<CardView>(R.id.card_view_MaleButton5)
        val maleButton6 = findViewById<CardView>(R.id.card_view_MaleButton6)


        val genderCstButton = findViewById<ImageButton>(R.id.GenderCstButton)
        val tShirtCustomizeButton = findViewById<ImageButton>(R.id.TsCstButton)
        val pantsCusomizationButton = findViewById<ImageButton>(R.id.PaCstButton)
        val sneakerCusomizationButton = findViewById<ImageButton>(R.id.SnCstButton)

        val avatarImage = findViewById<ImageView>(R.id.AvatarImageView)
        val torsoImage = findViewById<ImageView>(R.id.TopImageView)
        val pantImage = findViewById<ImageView>(R.id.BottomImageView)
        val SneakerImage = findViewById<ImageView>(R.id.SneakerImageView)
        // set on-click listener





        femaleButton.setOnClickListener{
            avatarImage.setImageResource(R.drawable.female2)
        }

        femaleButton1.setOnClickListener{
            avatarImage.setImageResource(R.drawable.female1)
        }

        femaleButton2.setOnClickListener{
            avatarImage.setImageResource(R.drawable.f1)
        }
        femaleButton3.setOnClickListener{
            avatarImage.setImageResource(R.drawable.f2)
        }
        femaleButton4.setOnClickListener{
            avatarImage.setImageResource(R.drawable.f3)
        }
        femaleButton5.setOnClickListener{
            avatarImage.setImageResource(R.drawable.f4)
        }
        femaleButton6.setOnClickListener{
            avatarImage.setImageResource(R.drawable.f5)
        }



        maleButton.setOnClickListener{
            avatarImage.setImageResource(R.drawable.base)
        }

        maleButton1.setOnClickListener{
            avatarImage.setImageResource(R.drawable.male_avatar_2)
        }
        maleButton2.setOnClickListener{
            avatarImage.setImageResource(R.drawable.m1)
        }
        maleButton3.setOnClickListener{
            avatarImage.setImageResource(R.drawable.m2)
        }
        maleButton4.setOnClickListener{
            avatarImage.setImageResource(R.drawable.m3)
        }
        maleButton5.setOnClickListener{
            avatarImage.setImageResource(R.drawable.m4)
        }
        maleButton6.setOnClickListener{
            avatarImage.setImageResource(R.drawable.m5)
        }



        genderCstButton.setOnClickListener{
            genderCstLayout.isVisible = true
            topCstLayout.isVisible= false
            bottomCstLayout.isVisible = false
            sneakerScrollView.isVisible = false
        }

        tShirtCustomizeButton.setOnClickListener{
            genderCstLayout.isVisible = false
            topCstLayout.isVisible = true
            bottomCstLayout.isVisible = false
            sneakerScrollView.isVisible = false
        }
        pantsCusomizationButton.setOnClickListener{

            topCstLayout.isVisible = false
            genderCstLayout.isVisible = false
            bottomCstLayout.isVisible = true
            sneakerScrollView.isVisible = false
        }

        sneakerCusomizationButton.setOnClickListener{

            topCstLayout.isVisible = false
            genderCstLayout.isVisible = false
            bottomCstLayout.isVisible = false
            sneakerScrollView.isVisible = true
        }


        maleSubSecButton.setOnClickListener{
            maleScrollView.isVisible = true
            femaleScrollView.isVisible = false
        }

        femaleSubSecButton.setOnClickListener{
            maleScrollView.isVisible = false
            femaleScrollView.isVisible = true
        }



        hoodieSubSecButton.setOnClickListener{
            hoodieScrollView.isVisible = true
            teeShirtScrollView.isVisible = false
        }

        teeShirtSubSecButton.setOnClickListener{
            hoodieScrollView.isVisible = false
            teeShirtScrollView.isVisible = true
        }


        shortsSubSecButton.setOnClickListener{
            shortsScrollView.isVisible = true
            pantsScrollView.isVisible = false
        }

        pantsSubSecButton.setOnClickListener{
            shortsScrollView.isVisible = false
            pantsScrollView.isVisible = true
        }



        hoodieButton1.setOnClickListener {
            // your code to perform when the user clicks on the button

            torsoImage.setImageResource( R.drawable.disobey_hoodie_white)

            Log.d("Click", "Hoodie Button 1 On click")

        }


        hoodieButton3.setOnClickListener {
            // your code to perform when the user clicks on the button
            torsoImage.setImageResource( R.drawable.disobey_hoodie_black)
        }

        hoodieButton4.setOnClickListener {
            // your code to perform when the user clicks on the button
            torsoImage.setImageResource( R.drawable.beige)
        }

        hoodieButton5.setOnClickListener {
            // your code to perform when the user clicks on the button
            torsoImage.setImageResource( R.drawable.orange)
        }

        hoodieButton6.setOnClickListener {
            // your code to perform when the user clicks on the button
            torsoImage.setImageResource( R.drawable.thumbhole_hoodie_black)
        }


        TeeButton1.setOnClickListener {
            // your code to perform when the user clicks on the button
            torsoImage.setImageResource( R.drawable.tee_black)
        }
        TeeButton2.setOnClickListener {
            // your code to perform when the user clicks on the button
            torsoImage.setImageResource( R.drawable.tee_purple)
        }
        TeeButton3.setOnClickListener {
            // your code to perform when the user clicks on the button
            torsoImage.setImageResource( R.drawable.tee_white)
        }

        TeeButton4.setOnClickListener {
            // your code to perform when the user clicks on the button
            torsoImage.setImageResource( R.drawable.duelist)
        }
        TeeButton5.setOnClickListener {
            // your code to perform when the user clicks on the button
            torsoImage.setImageResource( R.drawable.controller)
        }
        TeeButton6.setOnClickListener {
            // your code to perform when the user clicks on the button
            torsoImage.setImageResource( R.drawable.initiator)
        }
        TeeButton7.setOnClickListener {
            // your code to perform when the user clicks on the button
            torsoImage.setImageResource( R.drawable.oversized_tee_sentinel)
        }




        pab1.setOnClickListener {
            // your code to perform when the user clicks on the button
            pantImage.setImageResource( R.drawable.maroon_half_pant)
        }
        pab2.setOnClickListener {
            // your code to perform when the user clicks on the button
            pantImage.setImageResource( R.drawable.olive_green_half_pants)
        }
        pab3.setOnClickListener {
            // your code to perform when the user clicks on the button
            pantImage.setImageResource( R.drawable.sc)
        }
        pab4.setOnClickListener {
            // your code to perform when the user clicks on the button
            pantImage.setImageResource( R.drawable.avatar_customization)
        }

        pab5.setOnClickListener {
            // your code to perform when the user clicks on the button
            pantImage.setImageResource( R.drawable.qw)
        }



        snb1.setOnClickListener {
            // your code to perform when the user clicks on the button
            SneakerImage.setImageResource( R.drawable.low_top_sneakers)
        }
        snb2.setOnClickListener {
            // your code to perform when the user clicks on the button
            SneakerImage.setImageResource( R.drawable.high_top_sneakerss)
        }

        snb3.setOnClickListener {
            // your code to perform when the user clicks on the button
            SneakerImage.setImageResource( R.drawable.purple_ghost)
        }


        snb4.setOnClickListener {
            // your code to perform when the user clicks on the button
            SneakerImage.setImageResource( R.drawable.chroma_wave)
        }



    }

    private fun getBitmapFromUiView(view: View?): Bitmap {
        //Define a bitmap with the same size as the view
        view?.setBackgroundColor(Color.TRANSPARENT)
        val returnedBitmap = Bitmap.createBitmap(2048, 2048, Bitmap.Config.ARGB_8888)
        //Bind a canvas to it
        val canvas = Canvas(returnedBitmap)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        val left = (2048 - view?.width!! ?: 0) / 2
        val top = (2048 - view?.height!! ?: 0) / 2
        // Translate the canvas to the center position
        canvas.translate(left.toFloat(), top.toFloat())

        // Draw the view on the canvas
        view?.draw(canvas)

        // draw the view on the canvas
        view?.draw(canvas)
        view?.setBackgroundColor(Color.WHITE)

        //return the bitmap
        return returnedBitmap
    }

    /**Save Bitmap To Gallery
     * @param bitmap The bitmap to be saved in Storage/Gallery*/
    private fun saveBitmapImage(bitmap: Bitmap) {
        val timestamp = System.currentTimeMillis()

        //Tell the media scanner about the new file so that it is immediately available to the user.
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, timestamp)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, timestamp)
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name))
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                try {
                    val outputStream = contentResolver.openOutputStream(uri)
                    if (outputStream != null) {
                        try {
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                            outputStream.close()
                        } catch (e: Exception) {
                            Log.e(TAG, "saveBitmapImage: ", e)
                        }
                    }
                    values.put(MediaStore.Images.Media.IS_PENDING, false)
                    contentResolver.update(uri, values, null, null)

                    Toast.makeText(this, "Saved...", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.e(TAG, "saveBitmapImage: ", e)
                }
            }
        } else {
            val imageFileFolder = File(Environment.getExternalStorageDirectory().toString() + '/' + getString(R.string.app_name))
            if (!imageFileFolder.exists()) {
                imageFileFolder.mkdirs()
            }
            val mImageName = "$timestamp.png"
            val imageFile = File(imageFileFolder, mImageName)
            try {
                val outputStream: OutputStream = FileOutputStream(imageFile)
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    outputStream.close()
                } catch (e: Exception) {
                    Log.e(TAG, "saveBitmapImage: ", e)
                }
                values.put(MediaStore.Images.Media.DATA, imageFile.absolutePath)
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                Log.e(TAG,  imageFileFolder.toString())
                Toast.makeText(this, "Saved..." , Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Log.e(TAG, "saveBitmapImage: ", e)
            }
        }
    }

}