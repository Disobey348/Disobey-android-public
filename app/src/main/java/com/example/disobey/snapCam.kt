package com.example.disobey

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.snap.camerakit.support.app.CameraActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class snapCam : AppCompatActivity() {
// removed snapkit lens ids and group ids from code
    lateinit var captureResultLabel :TextView
    lateinit var imageView : ImageView
    lateinit var videoView :VideoView
    lateinit var bitmap: Bitmap
    lateinit var uri: Uri
    var typ =".jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snap_cam)

        val type = Integer.parseInt(intent.getStringExtra("Type"))
        captureResultLabel = findViewById<TextView>(R.id.label_capture_result)
        imageView = findViewById<ImageView>(R.id.image_preview)
        videoView = findViewById<VideoView>(R.id.video_preview).apply {
            setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = true
            }
        }
        if(type==1){
            typeLensId= intent.getStringExtra("lens").toString()
        }
        captureLauncher.launch(
            CameraActivity.Configuration.WithLens(
                cameraFacingFront = false
            )
        )
        findViewById<Button>(R.id.button_capture_lens).setOnClickListener {
            captureLauncher.launch(
                CameraActivity.Configuration.WithLens(
                    cameraFacingFront = false
                )
            )
        }
        findViewById<Button>(R.id.download).setOnClickListener{
                Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun download() {
        TODO("Not yet implemented")

    }

    val clearMediaPreviews = {
        videoView.visibility = View.GONE
        imageView.visibility = View.GONE
    }
    val captureLauncher = (this as ComponentActivity).registerForActivityResult(CameraActivity.Capture) { result ->
        Log.d(ContentValues.TAG, "Got capture result: $result")
        when (result) {
            is CameraActivity.Capture.Result.Success.Video -> {
                videoView.visibility = View.VISIBLE
                videoView.setVideoURI(result.uri)
//                Toast.makeText(this, "${result.uri}", Toast.LENGTH_SHORT).show()
                videoView.start()
                imageView.visibility = View.GONE
                captureResultLabel.text = null
                uri = result.uri
                typ=".mp4"

            }
            is CameraActivity.Capture.Result.Success.Image -> {
                imageView.visibility = View.VISIBLE
                imageView.setImageURI(result.uri)
//                Toast.makeText(this, "${result.uri}", Toast.LENGTH_SHORT).show()
                videoView.visibility = View.GONE
                captureResultLabel.text = null
                uri = result.uri
                typ=".jpg"
            }
            is CameraActivity.Capture.Result.Cancelled -> {
                captureResultLabel.text = getString(R.string.label_result_none)
                clearMediaPreviews()
            }
            is CameraActivity.Capture.Result.Failure -> {
                captureResultLabel.text = getString(
                    R.string.label_result_failure, result.exception.toString()
                )
                clearMediaPreviews()
            }
//                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, imageUri))
//            } else {
//                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
//            }

        }
    }
//    ----------------------------------------------------------------------------------------------
    fun saveMediaToStorage(bitmap: Bitmap) {
        //Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"

        //Output stream
        var fos: OutputStream? = null

        //For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //getting the contentResolver
            this?.contentResolver?.also { resolver ->

                //Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    //putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
//                    Primary directory Download not allowed for content://media/external/images/media; allowed directories are [DCIM, Pictures]
                }

                //Inserting the contentValues to contentResolver and getting the Uri
                //INTERNAL_CONTENT_URI is to store to internal storage denied
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                //Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            //These for devices running on android < Q
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this, "downloaded", Toast.LENGTH_SHORT).show()
        }
    }
//    ----------------------------------------------------------------------------------------------
}