import React, { useState } from 'react';
import './CadastroLivro.css';

function CadastroLivro() {
  const [nome, setNome] = useState('');
  const [dataLancamento, setDataLancamento] = useState('');
  const [autor, setAutor] = useState('');
  const [sumario, setSumario] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // Implemente a lógica para enviar os dados para o backend aqui
  };

  return (
    <div>
      <h1>Cadastro de Livro</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} />
        <input type="text" placeholder="Data de Lançamento" value={dataLancamento} onChange={(e) => setDataLancamento(e.target.value)} />
        <input type="text" placeholder="Autor" value={autor} onChange={(e) => setAutor(e.target.value)} />
        <textarea placeholder="Sumário" value={sumario} onChange={(e) => setSumario(e.target.value)} />
        <button type="submit">Cadastrar</button>
      </form>
    </div>
  );
}

export default CadastroLivro;
