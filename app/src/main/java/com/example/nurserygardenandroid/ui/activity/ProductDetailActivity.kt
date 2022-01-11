package com.example.nurserygardenandroid.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.nurserygardenandroid.R

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product_detail)

//        var bitmap : Bitmap = resources.getDrawable(R.drawable.product).toBitmap()
//        var rounded : Bitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
//
//        var canvas : Canvas = Canvas(rounded)
//
//        var paint: Paint = Paint()
//
//        paint.isAntiAlias = true;
//        paint.setShader(BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
//
//        canvas.drawRoundRect( RectF(600F, 0F, bitmap.width.toFloat(), bitmap.height.toFloat()), 100F, 100F, paint)
//
//        image__.setImageBitmap(rounded)
    }

    fun cartOnclick(item: android.view.MenuItem) {
        if(item.itemId == R.id.cart){
            var intent = Intent(this, ProductDetailActivity::class.java)
            startActivity(intent)
        }
    }

    fun back_onclik(view: android.view.View) {
        finish()

    }
}

