package br.ufpr.oscarapp.telasDiretores

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.ufpr.oscarapp.R
import br.ufpr.oscarapp.TelaBoasVindas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TelaDiretores : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tela_diretores)

        radioGroup = findViewById(R.id.radioGroupDiretores)
        progressBar = findViewById(R.id.progressBar)

        progressBar.visibility = View.VISIBLE
        fetchDiretores()

        // Botão para confirmar voto
        findViewById<Button>(R.id.btConfirmarVotoDir).setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedId)
                val selectedDiretor = selectedRadioButton.text.toString()


                saveVoteLocally(selectedDiretor)

                Toast.makeText(
                    this,
                    "Voto salvo temporariamente para: $selectedDiretor",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            } else {
                Toast.makeText(this, "Por favor, selecione um diretor.", Toast.LENGTH_SHORT).show()
            }
        }

        // Botão para ir para tela boas vindas
        findViewById<Button>(R.id.btVoltar4).setOnClickListener {
            val intent = Intent(this, TelaBoasVindas::class.java)
            startActivity(intent)
        }
    }

    private fun fetchDiretores() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val diretores = ApiDiretoresClient.api.getDiretores()

                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    populateRadioGroup(diretores)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@TelaDiretores,
                        "Erro ao carregar diretores: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun populateRadioGroup(diretores: List<Diretor>) {
        for (diretor in diretores) {
            val radioButton = RadioButton(this).apply {
                text = diretor.nome
                id = View.generateViewId()
                textSize = 18f
                setPadding(16, 16, 16, 16)
            }
            radioGroup.addView(radioButton)
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            val selectedDiretor = selectedRadioButton.text
            Toast.makeText(
                this,
                "Diretor selecionado: $selectedDiretor",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun saveVoteLocally(directorName: String) {
        val sharedPreferences = getSharedPreferences("UserVotes", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("selectedDirector", directorName) // Store the selected director
        editor.apply()
    }
}

