import React from 'react';
import { Link } from 'react-router-dom';
import './HomePage.css'; // Importe o arquivo CSS

function HomePage() {
  return (
    <div className="container">
      <h2>Bem-vindo à Biblioteca</h2>
      <p>Escolha uma opção abaixo:</p>

      <ul>
        <li>
          <Link to="/cadastro-usuario">Cadastro de Usuário</Link>
        </li>
        <li>
          <Link to="/cadastro-autor">Cadastro de Autor</Link>
        </li>
        <li>
          <Link to="/cadastro-livro">Cadastro de Livro</Link>
        </li>
        <li>
          <Link to="/aluguel-livro">Aluguel de Livro</Link>
        </li>
      </ul>
    </div>
  );
}

export default HomePage;
