package garcia.maria.personas


import android.content.Context
import android.os.Parcel
import android.os.Parcelable

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup

import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.elemento.view.*

class PersonaAdapter(personas:ArrayList<Persona>,contexto: Context): RecyclerView.Adapter<PersonaAdapter.ViewHolder>(){
    var elementos:ArrayList<Persona>?= null //los elementos, representan a las personas que mostraremos
    var contexto: Context?=null //el contexto con el que utilizaremos Picasso e inflaremos nuestra vista

    init { //inicializamos nuestras variables
        this.elementos=personas //los elementos de esta clase, serán las personas que se especifiquen en el constructor
        this.contexto = contexto //el contexto de esta clase, será el mismo que se indique en el constructor
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PersonaAdapter.ViewHolder {
        val vistaPersona:View = LayoutInflater.from(contexto).inflate(R.layout.elemento,p0,false)//creamos la vista "individual"
        val personaViewHolder = ViewHolder(vistaPersona)//instanciamos el viewHolder
        vistaPersona.tag = personaViewHolder //a la vista individual le asignamos como Tag el viewholder que creamos
        return personaViewHolder //retornamos el viewHolder
    }

    override fun getItemCount(): Int {
        return this.elementos!!.count() //retornamos el tamaño de elementos que tenemos, si ponemos un número fijo, solamente esos elementos se mostrarán.
    }

    override fun onBindViewHolder(p0: PersonaAdapter.ViewHolder, p1: Int) {
        /* Este método relaciona la vista con los datos del modelo */

        //vh.nombre.text = elementos[pos].nombre
        p0.nombre!!.text = elementos!![p1].nombre
        p0.genero!!.text = elementos!![p1].genero
        Picasso.get().load(elementos!![p1].foto)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .into(p0.imagen)

    }

    class ViewHolder(vista:View):RecyclerView.ViewHolder(vista) {
        /*  Recibimos la vista y plasmamos los widgets como variables del viewholder */
        var imagen:ImageView? = null
        var nombre:TextView? = null//declaramos como null
        var genero:TextView? = null

        init {
            /* inicializamos las variables con los elementos del layout por su ID */
            imagen = vista.ivPersona  //al inicializarlo relacionamos esta variable con el widget que viene en la vista.
            nombre = vista.tvNombre
            genero = vista.tvGenero
        }

    }
}
