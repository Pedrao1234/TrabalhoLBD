import React, { useState } from 'react';
import styles from './CadastroAutor.module.css';
function CadastroAutor() {
  const [nome, setNome] = useState('');
  const [cpf, setCpf] = useState('');
  const [dataNascimento, setDataNascimento] = useState('');
  const [sexo, setSexo] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // Implemente a lógica para enviar os dados para o backend aqui
  };

  return (
    <div className={styles.container}>
      <h1>Cadastro de Autor</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} />
        <input type="text" placeholder="CPF" value={cpf} onChange={(e) => setCpf(e.target.value)} />
        <input type="text" placeholder="Data de Nascimento" value={dataNascimento} onChange={(e) => setDataNascimento(e.target.value)} />
        <input type="text" placeholder="Sexo" value={sexo} onChange={(e) => setSexo(e.target.value)} />
        <button type="submit">Cadastrar</button>
      </form>
    </div>
  );
}

export default CadastroAutor;
