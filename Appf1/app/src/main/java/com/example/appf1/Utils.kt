package com.example.appf1

import android.content.Context
import android.widget.ImageView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley

/**
 * Singleton de Volley para gestionar la cola de peticiones.
 */
object VolleySingleton {
    private var instance: com.android.volley.RequestQueue? = null

    fun getInstance(context: Context) =
        instance ?: synchronized(this) {
            instance ?: Volley.newRequestQueue(context.applicationContext).also {
                instance = it
            }
        }
}

/**
 * Objeto Utils con funciones de ayuda.
 */
object Utils {

    /**
     * Carga una imagen desde una URL en un ImageView usando Volley.
     * Muestra una imagen de error si falla la carga.
     */
    fun loadImageInto(url: String?, view: ImageView, context: Context) {
        if (url.isNullOrEmpty()) {
            view.setImageResource(R.drawable.ic_broken_image)
            return
        }

        val queue = VolleySingleton.getInstance(context)

        // ImageRequest es lo que pedía la profesora
        val imageRequest = ImageRequest(
            url,
            { response ->
                // Imagen cargada correctamente
                view.setImageBitmap(response)
            },
            0, // max width
            0, // max height
            ImageView.ScaleType.CENTER_CROP,
            null,
            { _ ->
                // Error al cargar la imagen
                view.setImageResource(R.drawable.ic_broken_image)
            }
        )
        queue.add(imageRequest)
    }
}