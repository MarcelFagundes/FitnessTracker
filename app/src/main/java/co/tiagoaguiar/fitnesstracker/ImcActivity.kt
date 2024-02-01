    package co.tiagoaguiar.fitnesstracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import co.tiagoaguiar.fitnesstracker.model.Calc

class ImcActivity : AppCompatActivity() {

    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editWeight = findViewById(R.id.edit_imc_weight)
        editHeight = findViewById(R.id.edit_imc_height)

        val btnSend: Button = findViewById(R.id.btn_imc_send)

        btnSend.setOnClickListener{
            if (!validate()) {
                Toast.makeText(this, R.string.fields_messages, Toast.LENGTH_LONG).show()
                                            //O R.strin é da pasta resources
                return@setOnClickListener
            }

            //Aqui deu sucesso
            val weight = editWeight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()

            val result = calculateImc(weight, height)

            //Teste
            Log.d("Test", "resultado: $result")

            val imcResponseId = imcResponse(result)
            //Toast.makeText(this, imcResponseId,Toast.LENGTH_SHORT).show()
            //Usara o Dialog no lugar do Toast

            val dialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.imc_response,result))
                .setMessage(imcResponseId)
                 //Lambda
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    //aqui vai rodar depois do click
                }
                //Lambda
                .setNegativeButton(R.string.save) { dialog, which ->
                    // Thread criada para evitar travamentos na tela principal
                    Thread{
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "imc", res = result))

                        //Executar nao UI da primeira thread
                        runOnUiThread{
//                            //Toast.makeText(this@ImcActivity, R.string.saved, Toast.LENGTH_SHORT).show()
//                            val intent = Intent(this@ImcActivity, ListCalcActivity::class.java)
//
//                            //transportar dados de uma tela para outra
//                            intent.putExtra("type", "imc")
//
//                            startActivity(intent)
                            openListActivity()
                        }

                    }.start()

                }
                .create()
                .show()

            //val title = getString(R.string.imc_response,result)

//            dialog.setTitle(getString(R.string.imc_response,result))
//            dialog.setMessage(imcResponseId)
////            dialog.setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
////                override fun onClick(dialog: DialogInterface?, which: Int) {
////                    //aqui vai rodar depois do click
////                }
////
////            })
//            dialog.setPositiveButton(android.R.string.ok) { dialog, which ->
//                //aqui vai rodar depois do click
//            }
            //val d = dialog.create()
            //d.show()
        //Esconder o teclado
           val service =  getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
           service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
    /*
        class R {
            imc_severely_low_weight = 654464654654654
            imc_low_weight =  45646546546546
        }
        class layout {
        }

        R.string
        R.layout
        R.id
    */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_search) {
            finish() // destruir a atividade em execução
            openListActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openListActivity() {
        //Toast.makeText(this@ImcActivity, R.string.saved, Toast.LENGTH_SHORT).show()

        val intent = Intent(this@ImcActivity, ListCalcActivity::class.java)

        //transportar dados de uma tela para outra
        intent.putExtra("type", "imc")

        startActivity(intent)
    }

    @StringRes //Garante que o dado tem no recurso string.xml
    private fun imcResponse(imc: Double): Int {
        return when {
            imc < 15.0 -> R.string.imc_severely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 25.0 -> R.string.normal
            imc < 30.0 -> R.string.imc_high_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40.0 -> R.string.imc_severely_low_weight
            else -> R.string.imc_extreme_weight
        }
//        if (imc < 15.0) {
//            return  R.string.imc_severely_low_weight
//        } else if (imc < 16.0) {
//            return R.string.imc_very_low_weight
//        } else if (imc < 18.5) {
//            return R.string.imc_low_weight
//        } else if (imc < 25.0) {
//            return R.string.normal
//        } else if (imc < 30.0) {
//            return R.string.imc_high_weight
//        } else if (imc < 35.0) {
//            return R.string.imc_so_high_weight
//        } else if (imc < 40.0) {
//            return R.string.imc_extreme_weight
//        } else {
//            return R.string.imc_extreme_weight
//        }
    }

    private fun calculateImc(weight: Int, height: Int): Double {
        // peso / (altura * altura)
        return weight / ( (height / 100.0) * (height / 100.0) )
    }

    private fun validate():Boolean {
        //Não pode inserir valores nulo/ vazio
        //Não pode inserir/ começar com 0

        //Opção 1: Usar if e else
//        if (editWeight.text.toString().isNotEmpty()
//            && editHeight.text.toString().isNotEmpty()
//            && !editWeight.text.toString().startsWith("0")
//            && !editHeight.text.toString().startsWith("0")
//
//            return true
//        } else {
//            return false
//        }

        //Opção 2: Usar return pra simular o if e else
//        if (editWeight.text.toString().isNotEmpty()
//            && editHeight.text.toString().isNotEmpty()
//            && !editWeight.text.toString().startsWith("0")
//            && !editHeight.text.toString().startsWith("0")) {
//            return true
//        }
//        return false

        //Opção 3: return direto o que for verdadeiro
       return (editWeight.text.toString().isNotEmpty()
            && editHeight.text.toString().isNotEmpty()
            && !editWeight.text.toString().startsWith("0")
            && !editHeight.text.toString().startsWith("0"))
       }
}