package com.example.moviesland.Ui.image_result_zoom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.moviesland.R
import com.jsibbold.zoomage.ZoomageView
import com.ortiz.touchview.TouchImageView

class ImageViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)

        //set full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        val intent = getIntent()
        val zoomageView: ZoomageView = findViewById(R.id.zoom_image_view)

        if (intent.extras!=null)
        {
            if (intent.extras!!.getString("image_uri")!=null)
            {
                var baseimageURL = "https://image.tmdb.org/t/p/w500"

                val uri = intent.extras!!.getString("image_uri")
                Glide.with(this).load(baseimageURL +uri).into(zoomageView)
            }
        }

    }
}