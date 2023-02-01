package com.javr.super_heroes

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import com.javr.super_heroes.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var imagenHeroe: ImageView
    private var heroeBitmap: Bitmap? = null
    private var pathImagen = ""

    private val getContenido = registerForActivityResult(ActivityResultContracts.TakePicture()){
        success ->
        if(success && pathImagen.isNotEmpty()){
            heroeBitmap = BitmapFactory.decodeFile(pathImagen)
            imagenHeroe.setImageBitmap(heroeBitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardar.setOnClickListener {
            val strNombreHeroe:String = binding.edtNombre.text.toString()
            val strAlterEgo:String = binding.edtAlterEgo.text.toString()
            val strBiografia:String = binding.edtBiografia.text.toString()
            val ftPoder:Float = binding.ratBarPoder.rating
            //Pasar Datos entre activities por parametros
            //openDetalleActivity(strNombreHeroe, strAlterEgo, strBiografia, ftPoder)

            //Pasar datos entre activities por Objetos
            val objHeroe = Heroe(strNombreHeroe, strAlterEgo, strBiografia, ftPoder)
            openDetalleActivity(objHeroe)
        }

        imagenHeroe = binding.imgHeroe
        binding.imgHeroe.setOnClickListener {
            abrirCamara()
        }
    }

    private fun abrirCamara(){
        val file = crearArchivoImagen()
        val uri = if(Build.VERSION.SDK_INT >+ Build.VERSION_CODES.N){
            FileProvider.getUriForFile(this, "$packageName.provider", file)
        }else{
            Uri.fromFile(file)
        }

        getContenido.launch(uri)
    }

    private fun crearArchivoImagen(): File {
        val nombreArchivo = "superhero_image"
        val directorioArchivo = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(nombreArchivo, ".jpg", directorioArchivo)
        pathImagen = file.absolutePath
        return  file
    }

    private fun openDetalleActivity(strNombreHeroe:String, strAlterEgo:String, strBiografia:String,ftPoder: Float){
        val intent = Intent(this, DetalleActivity::class.java)
        intent.putExtra("nombre_heroe", strNombreHeroe)
        intent.putExtra("alter_ego", strAlterEgo)
        intent.putExtra("biografia", strBiografia)
        intent.putExtra("poder", ftPoder)
        startActivity(intent)
    }

    private fun openDetalleActivity(objHeroe: Heroe) {
        val intent = Intent(this, DetalleActivity::class.java)
        intent.putExtra(DetalleActivity.OBJ_HEROE_KEY, objHeroe)
        intent.putExtra(DetalleActivity.BITMAP_KEY, pathImagen)
        startActivity(intent)
    }
}