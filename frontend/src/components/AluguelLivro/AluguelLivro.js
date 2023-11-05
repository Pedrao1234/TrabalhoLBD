import React, { useState } from 'react';
import './AluguelLivro.css';
function AluguelLivro() {
  const [usuario, setUsuario] = useState('');
  const [livro, setLivro] = useState('');
  const [dataAluguel, setDataAluguel] = useState('');
  const [dataDevolucao, setDataDevolucao] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // Implemente a lógica para enviar os dados para o backend aqui
  };

  return (
    <div>
      <h1>Aluguel de Livro</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Nome do Usuário" value={usuario} onChange={(e) => setUsuario(e.target.value)} />
        <input type="text" placeholder="Nome do Livro" value={livro} onChange={(e) => setLivro(e.target.value)} />
        <input type="text" placeholder="Data de Aluguel" value={dataAluguel} onChange={(e) => setDataAluguel(e.target.value)} />
        <input type="text" placeholder="Data de Devolução" value={dataDevolucao} onChange={(e) => setDataDevolucao(e.target.value)} />
        <button type="submit">Alugar</button>
      </form>
    </div>
  );
}

export default AluguelLivro;
