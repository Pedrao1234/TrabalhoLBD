import React, { useState } from 'react';
import styles from './DevolverLivro.module.css'
function DevolverLivro() {
  const [usuario, setUsuario] = useState('');
  const [livro, setLivro] = useState('');
  const [dataDevolucao, setDataDevolucao] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // Implemente a lógica para registrar a devolução do livro no sistema aqui
    // Você pode enviar os dados para o backend e atualizar o estado do livro como "disponível"
  };

  return (
    <div className={styles.container}>
      <h1>Devolução de Livro</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Nome do Usuário" value={usuario} onChange={(e) => setUsuario(e.target.value)} />
        <input type="text" placeholder="Nome do Livro" value={livro} onChange={(e) => setLivro(e.target.value)} />
        <input type="text" placeholder="Data de Devolução" value={dataDevolucao} onChange={(e) => setDataDevolucao(e.target.value)} />
        <button type="submit">Registrar Devolução</button>
      </form>
    </div>
  );
}

export default DevolverLivro;
