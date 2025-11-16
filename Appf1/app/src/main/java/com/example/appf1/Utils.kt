package com.example.appf1

import android.content.Context
import android.widget.ImageView
import coil.load // Importamos la librería Coil

/**
 * Objeto 'Utils' (Utilidades).
 * Es un 'object' (un Singleton en Kotlin) para guardar funciones "ayudantes"
 * que queremos usar desde varias partes de la app (Adapters, Activities, etc.).
 */
object Utils {

    /**
     * Carga una imagen desde una URL en un ImageView usando Coil.
     * Muestra una imagen de error si la URL está vacía o falla la carga.
     *
     * @param url La URL de la imagen (String).
     * @param view El ImageView donde se cargará la imagen (ej: b.imgDriver).
     * @param context El contexto de la app (normalmente 'this' desde la Activity
     * o 'b.root.context' desde un Adapter).
     */
    fun loadImageInto(url: String?, view: ImageView, context: Context) {
        if (url.isNullOrEmpty()) {
            // Si la URL es nula o vacía, ponemos la imagen de error y terminamos.
            view.setImageResource(R.drawable.ic_broken_image)
            return
        }

        // Magia de Coil: .load() es una función de extensión que carga la URL.
        view.load(url) {
            crossfade(true) // Añade una animación suave de aparición
            placeholder(R.drawable.ic_broken_image) // Imagen a mostrar mientras carga
            error(R.drawable.ic_broken_image) // Imagen a mostrar si hay un error de carga
        }
    }
}