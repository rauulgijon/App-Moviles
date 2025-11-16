package com.example.appf1

import android.content.Context
import android.widget.ImageView
import coil.load

/**
 * Objeto Utils con funciones de ayuda.
 */
object Utils {

    /**
     * Carga una imagen desde una URL en un ImageView usando Coil.
     * Muestra una imagen de error si falla la carga.
     */
    fun loadImageInto(url: String?, view: ImageView, context: Context) {
        if (url.isNullOrEmpty()) {
            view.setImageResource(R.drawable.ic_broken_image)
            return
        }

        view.load(url) {
            crossfade(true) // Animación suave de aparición
            placeholder(R.drawable.ic_broken_image) // Imagen a mostrar mientras carga
            error(R.drawable.ic_broken_image) // Imagen a mostrar si hay un error
        }
    }
}