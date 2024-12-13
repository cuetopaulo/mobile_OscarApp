from flask import Flask, request, jsonify
import sqlite3
import random
import os

app = Flask(__name__)

def conectar_banco():
    return sqlite3.connect("oscar.db")

def inicializar_banco():

    if not os.path.exists("oscar.db"):
        print("Creating the database oscar.db...")

    conn = conectar_banco()
    cursor = conn.cursor()
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS usuarios (
            id INTEGER PRIMARY KEY,
            login TEXT NOT NULL,
            senha TEXT NOT NULL
        )
    """)

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS tokens (
            id_usuario INTEGER PRIMARY KEY,
            token INTEGER,
            FOREIGN KEY (id_usuario) REFERENCES usuarios (id)
        )
    """)

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS votos (
            id_usuario INTEGER,
            filme TEXT,
            diretor TEXT,
            timestamp TEXT DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (id_usuario) REFERENCES usuarios (id)
        )
    """)

    cursor.executemany("""
        INSERT OR IGNORE INTO usuarios (id, login, senha) VALUES (?, ?, ?)
    """, [
        (1, "nathaly", "senha1"),
        (2, "paulo", "senha2"),
        (3, "rafa", "senha3"),
        (4, "vanessa", "senha4"),
        (5,"teste5","senha5")
    ])
    
    conn.commit()
    conn.close()

inicializar_banco()

@app.route('/login', methods=['POST'])
def login():
    dados = request.json
    conn = conectar_banco()
    cursor = conn.cursor()

    cursor.execute("SELECT id FROM usuarios WHERE login = ? AND senha = ?", (dados['login'], dados['senha']))
    usuario = cursor.fetchone()
    conn.close()

    if usuario:
        token = random.randint(1, 100)  
        conn = conectar_banco()
        cursor = conn.cursor()

 
        cursor.execute("REPLACE INTO tokens (id_usuario, token) VALUES (?, ?)", (usuario[0], token))
        conn.commit()
        conn.close()

        return jsonify({"token": token})
    
    return jsonify({"erro": "Login ou senha invalidos"}), 401

@app.route('/voto', methods=['POST'])
def registrar_voto():
    dados = request.json
    token = dados.get("token")
    filme = dados.get("filme")
    diretor = dados.get("diretor")


    conn = conectar_banco()
    cursor = conn.cursor()


    cursor.execute("SELECT id_usuario FROM tokens WHERE token = ?", (token,))
    usuario = cursor.fetchone()

    if not usuario:
        conn.close()
        return jsonify({"erro": "Token invalido"}), 403  


    cursor.execute("SELECT * FROM votos WHERE id_usuario = ?", (usuario[0],))
    if cursor.fetchone():
        conn.close()
        return jsonify({"erro": "Token invalido"}), 403  


    cursor.execute("INSERT INTO votos (id_usuario, filme, diretor) VALUES (?, ?, ?)", (usuario[0], filme, diretor))
    conn.commit()
    conn.close()

    return jsonify({"mensagem": "Voto registrado com sucesso"})


@app.route('/checa_voto', methods=['POST'])
def check_vote():
    dados = request.json
    login = dados.get("login") 

    conn = conectar_banco()
    cursor = conn.cursor()

    cursor.execute("SELECT id FROM usuarios WHERE login = ?", (login,))
    usuario = cursor.fetchone()

    if usuario:
        user_id = usuario[0]

        cursor.execute("SELECT * FROM votos WHERE id_usuario = ?", (user_id,))
        voto = cursor.fetchone()
        conn.close()

        if voto:
            return jsonify({"voted": True})
        return jsonify({"voted": False}) 

    conn.close()
    return jsonify({"erro": "Login invalido"}), 403  

@app.route('/pega_voto_detalhes', methods=['POST'])
def get_vote_details():
    dados = request.json
    login = dados.get("login")  

    conn = conectar_banco()
    cursor = conn.cursor()

    cursor.execute("SELECT id FROM usuarios WHERE login = ?", (login,))
    usuario = cursor.fetchone()

    if usuario:
        user_id = usuario[0]

        cursor.execute("""
            SELECT filme, diretor, datetime(timestamp,'-3 hours') AS timestamp
            FROM votos
            WHERE id_usuario = ?
        """, (user_id,))
        voto = cursor.fetchone()
        conn.close()

        if voto:
            return jsonify({
                "filme": voto[0],
                "diretor": voto[1],
                "timestamp": voto[2]
            })
        else:
            return jsonify({"erro": "Usuário não votou"}), 404

    conn.close()
    return jsonify({"erro": "Login inválido"}), 403

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
