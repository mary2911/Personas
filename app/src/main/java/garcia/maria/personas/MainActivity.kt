package garcia.maria.personas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var personas:ArrayList<Persona>?=null
    var adapter:PersonaAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerPersonas.layoutManager = GridLayoutManager(applicationContext,1)!!
        recyclerPersonas.setHasFixedSize(true)
        personas = ArrayList()
        adapter = PersonaAdapter(personas!!,this)
        recyclerPersonas.adapter = adapter


        val cache = DiskBasedCache(cacheDir,1024*1024)
        val network = BasicNetwork(HurlStack())
        val requestQueue = RequestQueue(cache,network).apply {
            start()
        }
        val url = "https://randomuser.me/api/?results=10"
        val jsonObjectPersonas =
            JsonObjectRequest(Request.Method.GET,url,null,Response.Listener { response ->
                //Log.d("respuesta:" , response.toString())
                val resultadosJSON = response.getJSONArray("results")

                for (indice in 0..resultadosJSON.length()-1){
                    val personajson = resultadosJSON.getJSONObject(indice)
                    val genero = personajson.getString("gender")

                    val nombreJson = personajson.getJSONObject("name")
                    val nombrepersona = "${nombreJson.getString("title")} ${nombreJson.getString("first") }" +
                            " ${nombreJson.getString("last")}"
                    val fotoJSON = personajson.getJSONObject("picture")
                    val foto = fotoJSON.getString("large")

                    val locationJSON = personajson.getJSONObject("location")
                    val cordJSON = locationJSON.getJSONObject("coordinates")
                    val latitud = cordJSON.getString("latitude").toDouble()
                    val longitud = cordJSON.getString("longitude").toDouble()

                    personas!!.add(Persona(nombrepersona,foto,longitud,latitud,genero))

                }

                adapter!!.notifyDataSetChanged()
        }, Response.ErrorListener { error ->
            Log.wtf("error volley",error.localizedMessage)
        })

        requestQueue.add(jsonObjectPersonas)

    }
}
