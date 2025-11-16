package com.example.appf1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appf1.R
import com.example.appf1.Utils
import com.example.appf1.databinding.ItemDriverBinding
import model.Driver

/**
 * Este es el adaptador para la lista de Pilotos.
 * Usa ListAdapter, que es una versión moderna de RecyclerView.Adapter
 * que maneja automáticamente las animaciones de la lista (gracias a DriverDiff).
 * No recibe un listener porque no hacemos nada al pulsar en un piloto.
 */
class DriverAdapter : ListAdapter<Driver, DriverAdapter.DriverViewHolder>(DriverDiff()) {

    /**
     * Se llama cuando el RecyclerView necesita crear una nueva "fila" (ViewHolder).
     * Infla el layout XML (item_driver.xml) usando ViewBinding.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val binding = ItemDriverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DriverViewHolder(binding)
    }

    /**
     * Se llama cuando el RecyclerView quiere mostrar los datos de un piloto
     * en una fila específica (holder).
     */
    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        // Obtenemos el piloto de la lista en esa posición y llamamos a bind()
        holder.bind(getItem(position))
    }

    /**
     * Esta clase interna representa una "fila" de la lista.
     * Guarda la referencia al binding (b) para no tener que buscar las vistas (findViewById).
     */
    inner class DriverViewHolder(private val b: ItemDriverBinding) : RecyclerView.ViewHolder(b.root) {

        /**
         * Esta es la función CLAVE.
         * Conecta los datos del objeto 'Driver' (de la BD) con las Vistas (TextViews, ImageView)
         * del layout 'item_driver.xml'.
         */
        fun bind(driver: Driver) {
            // Ponemos el nombre del piloto en el TextView 'txtName'
            b.txtName.text = driver.name
            // Ponemos el nombre del equipo
            b.txtTeam.text = driver.teamName
            // Ponemos el número (y lo convertimos de Int a String)
            b.txtNumber.text = driver.number.toString()

            // Usamos nuestra función 'Utils' para cargar la foto de retrato con Coil
            // Le pasamos la URL del piloto, el ImageView donde cargarla, y el contexto.
            Utils.loadImageInto(driver.imageUrl, b.imgDriver, b.root.context)

            // Ponemos los puntos (y añadimos " PTS")
            b.txtPoints.text = "${driver.points} PTS"
        }
    }

    /**
     * Esta clase ayuda al ListAdapter a saber qué ha cambiado en la lista
     * para animar solo las filas necesarias.
     */
    class DriverDiff : DiffUtil.ItemCallback<Driver>() {
        // Comprueba si el item es el mismo (por su ID)
        override fun areItemsTheSame(oldItem: Driver, newItem: Driver) = oldItem.driverId == newItem.driverId
        // Comprueba si el contenido del item ha cambiado (todos los campos)
        override fun areContentsTheSame(oldItem: Driver, newItem: Driver) = oldItem == newItem
    }
}