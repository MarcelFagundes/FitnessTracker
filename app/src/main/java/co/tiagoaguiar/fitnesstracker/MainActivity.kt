package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    //private lateinit var btnImc: LinearLayout
    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawbleId = R.drawable.ic_baseline_wb_sunny_24,
                textStringId = R.string.label_imc,
                color = Color.GREEN
            )
        )
        mainItems.add(
            MainItem(
                id = 2,
                drawbleId = R.drawable.ic_baseline_visibility_24,
                textStringId = R.string.label_tmb,
                color = Color.YELLOW
            )
        )

        // 1) o layout xml
        // 2) a onde a recycleview vai aparecer (tela principal, tela cheia)
        // 3) logica - conectar o xml da celula dentro do recycleview + a sua qtde de elementos dinamicos
        //1 forma
        //val adapter = MainAdapter(mainItems, this)
//        val adapter = MainAdapter(mainItems, object :OnItemClickListener {
//            //Metodo 2: IMPL Via Metodo anonimo
//            override fun onClick(id: Int) {
//                when(id) {
//                    1 -> {
//                        val intent = Intent(this@MainActivity, ImcActivity::class.java)
//                        startActivity(intent)
//                    }
//                    2 -> {
//                        //abrir outra activity
//                    }
//                }
//                Log.i("teste", "Clicou $id !!")
//            }
//
//        })

        val adapter = MainAdapter(mainItems) { id ->
            //Metodo 3: IMPL Via Funcoes
            when(id) {
                1 -> {
                    val intent = Intent(this@MainActivity, ImcActivity::class.java)
                    startActivity(intent)
                }
                2 -> {
                    //abrir outra activity
                    val intent = Intent(this@MainActivity, TmbActivity::class.java)
                    startActivity(intent)
                }
            }
            Log.i("teste", "Clicou $id !!")
        }

        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        //rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.layoutManager = GridLayoutManager(this, 2)

        // classe para administrar a recycleview e suas  celulas (os seu layouts de itens)
        // Adapter ->

//        btnImc = findViewById(R.id.btn_imc)
//
//        btnImc.setOnClickListener {
//            //navegar para uma próxima tela
//            //Intenção intent
//            val i = Intent(this, ImcActivity::class.java)
//            startActivity(i)
//        }
    }

    //METODO 1 : Usando IMPL Interface via activity
//    override fun onClick(id: Int) {
//        when(id) {
//            1 -> {
//                  val intent = Intent(this, ImcActivity::class.java)
//                  startActivity(intent)
//            }
//            2 -> {
//                //abrir outra activity
//            }
//        }
//       Log.i("teste", "Clicou $id !!")
//    }

    private inner class MainAdapter(
        private val mainItems: List<MainItem>,
       // private val onItemClickListener: OnItemClickListener
        private  val onItemClickListener: (Int) -> Unit,
        ): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

        // 1 - Qual é o layou xml da celula especifica (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        // 2 - metodo disparado toda vez que houver uma rolagem na tela e for necessário trocar
        // o conteudo da celula
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)
        }

        // 3 - informa qtas celulas essa listagem terá
        override fun getItemCount(): Int {
            //return 15
            return mainItems.size
        }

        //é a classa de celula em si
        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            fun bind(item: MainItem) {
                //val text = item.textStringId
                //val buttonTest:Button = itemView.findViewById(R.id.btn_item)
                //buttonTest.setText(item.textStringId)
                val img: ImageView = itemView.findViewById(R.id.item_img_icon)
                val name: TextView = itemView.findViewById(R.id.item_txt_name)
//            val container: LinearLayout = itemView as LinearLayout
                val container: LinearLayout = itemView.findViewById(R.id.item_conteiner_imc)

                img.setImageResource(item.drawbleId)
                name.setText(item.textStringId)
                container.setBackgroundColor(item.color)

                //Abrir atividade um item . Não conseguimos porque não conseguuimos distinguir os itens
                container.setOnClickListener {
                  // Metodo 3 - aqui ele é uma referencia de uma funcção
                    onItemClickListener.invoke(item.id)
                    // Aqui ele é uma referencia de interface
                    //onItemClickListener.onClick(item.id)
//                val intent = Intent(this, ImcActivity::class.java)
//                startActivity(intent)
//
                }
            }
        }
    }




    //3 maneiras de escutar eventos de click usando celular (viewholder) activities
    //1 - impl. Interface anonima
    //2 - Objetos anonimos
    //3 Imple via funções
}