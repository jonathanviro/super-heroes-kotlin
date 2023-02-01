package com.javr.super_heroes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.javr.super_heroes.databinding.ActivityDetalleBinding

class DetalleActivity : AppCompatActivity() {
    companion object{
        const val OBJ_HEROE_KEY = "objHeroe"
        const val BITMAP_KEY = "objBitAmp"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras!!

        //OBTENER LOS DATOS ENVIADOS POR PARAMETRO DESDE EL ACTIVITY MAIN
//        val nombreHeroe = bundle.getString("nombre_heroe") ?: ""
//        val alterEgo = bundle.getString("alter_ego") ?: ""
//        val biografia = bundle.getString("biografia") ?: ""
//        val poder = bundle.getFloat("poder")
//        binding.txtNombreHeroe.text = nombreHeroe
//        binding.txtAlterEgo.text = alterEgo
//        binding.txtBio.text = biografia
//        binding.ratBarPoder.rating = poder

        //OBTENER LOS DATOS ENVIADOS POR OBJETO DESDE EL ACTIVITY MAIN
//        val objHeroe = intent.getParcelableExtra<Heroe>(OBJ_HEROE)!!
        val objHeroe = extras.getParcelable(OBJ_HEROE_KEY) as Heroe?
        if (objHeroe != null) {
            binding.txtNombreHeroe.text = objHeroe.strNombreHeroe
            binding.txtAlterEgo.text = objHeroe.strAlterEgo
            binding.txtBio.text = objHeroe.strBiografia
            binding.ratBarPoder.rating = objHeroe.ftPoder
        }

        val bitmapDirectorio = extras.getString(BITMAP_KEY)
        val bitmap = BitmapFactory.decodeFile(bitmapDirectorio)
        if(bitmap != null){
            binding.imageView.setImageBitmap(bitmap)
        }
    }
}