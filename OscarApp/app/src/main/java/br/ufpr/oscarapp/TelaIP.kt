package br.ufpr.oscarapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.text.TextWatcher
import android.text.Editable

class TelaIP : AppCompatActivity() {

    companion object {
        // Variável pública para armazenar o IP do servidor
        var IpServidor: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informar_ip)

        // Referências aos componentes do layout
        val ipEditText = findViewById<EditText>(R.id.serverIP)
        val salvarButton = findViewById<Button>(R.id.btCadastrarSalvarUsu)
        val voltarButton = findViewById<Button>(R.id.btVoltar)

        // Adicionar TextWatcher para aceitar apenas números e pontos
        ipEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                val text = charSequence.toString()
                // Permitir apenas números e pontos
                if (!text.matches("^[0-9.]*$".toRegex())) {
                    // Remover o último caractere se não for número ou ponto
                    ipEditText.setText(text.substring(0, text.length - 1))
                    ipEditText.setSelection(ipEditText.text.length)  // Manter o cursor na última posição
                }
            }

            override fun afterTextChanged(charSequence: Editable?) {}
        })

        // Configuração do botão Salvar
        salvarButton.setOnClickListener {
            val ipInformado = ipEditText.text.toString()

            if (isIpValid(ipInformado)) {
                IpServidor = ipInformado
                Toast.makeText(this, "IP salvo com sucesso: $IpServidor", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "IP inválido. Por favor, insira um IP válido.", Toast.LENGTH_SHORT).show()
            }
        }

        // Configuração do botão Voltar
        voltarButton.setOnClickListener {
            finish() // Fecha a tela atual e retorna para a anterior
        }
    }

    // Função para validar o formato do IP
    private fun isIpValid(ip: String): Boolean {
        val ipRegex = Regex("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")
        return ipRegex.matches(ip)
    }
}
