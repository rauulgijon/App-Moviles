package com.example.appf1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appf1.R
import com.example.appf1.Utils
import com.example.appf1.databinding.ItemRaceBinding
import model.Race

/**
 * Adaptador para la lista de Carreras (en Schedule y Results).
 * * Recibe un "listener" en su constructor. El listener es una función que
 * se ejecutará cuando el usuario pulse en una fila.
 * La Activity (ej: ScheduleActivity) es la que decide QUÉ HACE ese listener.
 * * (Race) -> Unit significa: "Una función que recibe un objeto Race y no devuelve nada".
 */
class RaceAdapter(private val listener: (Race) -> Unit) :
// Hereda de ListAdapter, que gestiona la lista y animaciones automáticamente
// usando "RaceDiff" para comparar items.
    ListAdapter<Race, RaceAdapter.RaceViewHolder>(RaceDiff()) {

    /**
     * Se llama cuando el RecyclerView necesita crear una nueva "fila" (ViewHolder).
     * Infla (crea) el layout XML (item_race.xml) usando ViewBinding.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceViewHolder {
        val binding = ItemRaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RaceViewHolder(binding)
    }

    /**
     * Se llama cuando el RecyclerView quiere mostrar los datos de una Carrera (en 'position')
     * en una fila específica (holder).
     */
    override fun onBindViewHolder(holder: RaceViewHolder, position: Int) {
        // Obtenemos la carrera de la lista en esa posición y llamamos a bind()
        holder.bind(getItem(position))
    }

    /**
     * Esta clase interna representa una "fila" de la lista.
     * Guarda la referencia al binding (b) para no tener que buscar las vistas (findViewById).
     */
    inner class RaceViewHolder(private val b: ItemRaceBinding) : RecyclerView.ViewHolder(b.root) {

        /**
         * Conecta los datos del objeto 'r' (Race) con las Vistas del layout 'item_race.xml'.
         */
        fun bind(r: Race) {
            // Rellenamos los campos del layout con los datos del objeto Race
            b.txtRound.text = "ROUND ${r.round}"
            // Usamos el país si existe, si no, el nombre (quitando "Gran Premio de")
            b.txtRaceName.text = r.country ?: r.name.replace("Gran Premio de ", "")
            b.txtCircuitName.text = r.circuit
            b.txtRaceDate.text = r.date

            // Cargar imagen con Coil (a través de Utils)
            if (!r.imageUrl.isNullOrEmpty()) {
                Utils.loadImageInto(r.imageUrl, b.imgRace, b.root.context)
            } else {
                b.imgRace.setImageResource(R.drawable.ic_broken_image) // Imagen de error
            }

            // Asignamos el listener al 'root' (toda la tarjeta)
            // Cuando se pulse, se llamará a la función 'listener' que nos pasaron
            // al crear el adaptador, enviándole la carrera 'r' de esta fila.
            b.root.setOnClickListener { listener(r) }
        }
    }

    /**
     * Esta clase ayuda al ListAdapter a saber qué ha cambiado en la lista
     * para animar solo las filas necesarias. Es más eficiente que notifyDataSetChanged().
     */
    class RaceDiff : DiffUtil.ItemCallback<Race>() {
        // Comprueba si el item es el mismo (por su ID único)
        override fun areItemsTheSame(oldItem: Race, newItem: Race) = oldItem.id == newItem.id
        // Comprueba si el contenido del item ha cambiado (compara todos los campos)
        override fun areContentsTheSame(oldItem: Race, newItem: Race) = oldItem == newItem
    }
}